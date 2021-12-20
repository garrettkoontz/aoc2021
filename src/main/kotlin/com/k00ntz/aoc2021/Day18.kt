package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max

fun Day18.SnailPair.explode(): Day18.RegularNumber {
    val ln = this.left as Day18.RegularNumber
    val rn = this.right as Day18.RegularNumber
    val prev = ln.previous
    val nxt = rn.next
    val new = Day18.RegularNumber(0)
    prev?.next = new
    nxt?.previous = new
    prev?.value = prev?.value?.plus(ln.value) ?: 0
    nxt?.value = nxt?.value?.plus(rn.value) ?: 0
    new.previous = prev
    new.next = nxt
    return new
}

class Day18 : Day<List<String>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    sealed interface SnailNumber {
        fun magnitude(): Int
        fun head(): RegularNumber
        fun tail(): RegularNumber
        operator fun plus(other: SnailNumber): SnailNumber = SnailPair(this, other).also {
            val t = it.left.tail()
            val h = it.right.head()
            t.next = h
            h.previous = t
            it.reduce()
        }
    }

    class SnailPair(var left: SnailNumber, var right: SnailNumber) : SnailNumber {

        override fun head(): RegularNumber {
            return left.head()
        }

        override fun tail(): RegularNumber {
            return right.tail()
        }

        override fun magnitude(): Int {
            return 3 * left.magnitude() + 2 * right.magnitude()
        }

        private fun explodes() {
            var examine = ArrayDeque<Pair<SnailNumber, Int>>(listOf(Pair(this, 0)))
            while (examine.isNotEmpty()) {
                val (sn, depth) = examine.pop()
                if (sn is SnailPair) {
                    if (depth == 3) {
                        if (sn.left is SnailPair) {
                            sn.left = (sn.left as SnailPair).explode()
                            examine = ArrayDeque<Pair<SnailNumber, Int>>(listOf(Pair(this, 0)))
                            continue
                        } else if (sn.right is SnailPair) {
                            sn.right = (sn.right as SnailPair).explode()
                            examine = ArrayDeque<Pair<SnailNumber, Int>>(listOf(Pair(this, 0)))
                            continue
                        }
                    } else {
                        examine.push(Pair(sn.right, depth + 1))
                        examine.push(Pair(sn.left, depth + 1))
                    }
                }
            }
        }

        private fun splits() {
            var examine = ArrayDeque<Pair<SnailNumber, Int>>(listOf(Pair(this, 0)))
            while (examine.isNotEmpty()) {
                val (sn, depth) = examine.pop()
                if (sn is SnailPair) {
                    if (sn.left is RegularNumber && (sn.left as RegularNumber).value > 9) {
                        sn.left = (sn.left as RegularNumber).split()
                        examine = ArrayDeque<Pair<SnailNumber, Int>>(listOf(Pair(this, 0)))
                        explodes()
                        continue
                    } else if (sn.right is RegularNumber && (sn.right as RegularNumber).value > 9) {
                        sn.right = (sn.right as RegularNumber).split()
                        examine = ArrayDeque<Pair<SnailNumber, Int>>(listOf(Pair(this, 0)))
                        explodes()
                        continue
                    } else {
                        examine.push(Pair(sn.right, depth + 1))
                        examine.push(Pair(sn.left, depth + 1))
                    }
                }
            }
        }

        fun reduce() {
            explodes()
            splits()
        }

        override fun toString(): String {
            return "[$left, $right]"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SnailPair

            if (left != other.left) return false
            if (right != other.right) return false

            return true
        }

        override fun hashCode(): Int {
            var result = left.hashCode()
            result = 31 * result + right.hashCode()
            return result
        }

    }

    class RegularNumber(
        var value: Int,
    ) :
        SnailNumber {
        override fun head(): RegularNumber {
            var r = this
            while (r.previous != null) {
                r = r.previous!!
            }
            return r
        }

        override fun tail(): RegularNumber {
            var r = this
            while (r.next != null) {
                r = r.next!!
            }
            return r
        }

        var previous: RegularNumber? = null
        var next: RegularNumber? = null

        override fun magnitude(): Int {
            return value
        }

        fun split(): SnailPair {
            val l = RegularNumber(floor(value / 2.0).toInt())
            val r = RegularNumber(ceil(value / 2.0).toInt())
            l.previous = this.previous
            this.previous?.next = l
            l.next = r
            r.previous = l
            r.next?.previous = r
            r.next = this.next
            this.next?.previous = r
            return SnailPair(l, r)
        }

        override fun toString(): String {
            return "$value"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as RegularNumber

            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            return value
        }
    }


    fun parse(strs: List<String>): List<SnailNumber> =
        strs.map { SnailNumberParser().parse(it) }

    class SnailNumberParser {
        fun parse(str: String): SnailNumber {
            return parse(str, 0).first
        }

        private var previousRegularNumber: RegularNumber? = null

        fun parse(str: String, idx: Int): Pair<SnailNumber, Int> =
            when {
                str[idx] == ',' || str[idx] == ']' -> parse(str, idx + 1)
                (str[idx].isDigit()) -> {
                    val rn = RegularNumber(str[idx].digitToInt())
                    rn.previous = previousRegularNumber
                    previousRegularNumber?.next = rn
                    previousRegularNumber = rn
                    Pair(rn, idx + 2)
                }
                else -> parsePair(str, idx)
            }

        fun parsePair(str: String, idx: Int): Pair<SnailPair, Int> {
            return if (str[idx + 1].isDigit()) {
                val rn = RegularNumber(str[idx + 1].digitToInt())
                rn.previous = previousRegularNumber
                previousRegularNumber?.next = rn
                previousRegularNumber = rn
                val (second, remainingIdx) = parse(str, idx + 3)
                Pair(SnailPair(rn, second), remainingIdx)
            } else {
                val (first, newIdx) = parsePair(str, idx + 1)
                val (second, remainingIdx) = parse(str, newIdx)
                Pair(SnailPair(first, second), remainingIdx)
            }
        }
    }

    companion object Pairs {
        fun magnitude(l: List<Pair<Int, Int>>): Int {
            val m = l.toMutableList()
            listOf(Pair(1, 2), Pair(2, 2), Pair(3, 3), Pair(4, 3), Pair(5, 2))
            while (m.size > 1) {
                val maxLevel = m.maxOf { it.second }
                val removals = mutableSetOf<Int>()
                for (i in 1 until m.size) {
                    val previousNotRemoved = !removals.contains(i - 1)
                    val isSameLevelAsPrevious = m[i].second == m[i - 1].second
                    val isMaxLevel = m[i].second == maxLevel
                    if (isSameLevelAsPrevious && isMaxLevel && previousNotRemoved) {
                        m[i - 1] = Pair(m[i - 1].first * 3 + m[i].first * 2, m[i].second - 1)
                        removals.add(i)
                    }
                }
                removals.map { m[it] }.forEach { m.remove(it) }
            }
            return m.first().first
        }

        fun parsePairs(strs: List<String>): List<List<Pair<Int, Int>>> =
            strs.map { parsePair(it) }

        fun parsePair(str: String): List<Pair<Int, Int>> {
            var depth = 0
            val lst = mutableListOf<Pair<Int, Int>>()
            for (c in str.toCharArray()) {
                when {
                    c == '[' -> depth++
                    c == ']' -> depth--
                    c.isDigit() -> lst.add(Pair(c.digitToInt(), depth))
                    else -> continue
                }
            }
            return lst
        }

        fun treePlus(l1: List<Pair<Int, Int>>, l2: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
            val m = l1.map { Pair(it.first, it.second + 1) }
                .toMutableList()
            m.addAll(l2.map { Pair(it.first, it.second + 1) })
            pairReduce(m)
            return m
        }

        fun pairReduce(lst: MutableList<Pair<Int, Int>>) {
            while (shouldExplode(lst) || shouldSplit(lst)) {
                explode(lst)
                if (shouldExplode(lst))
                    continue
                if (shouldSplit(lst))
                    split(lst)
            }
        }

        fun explode(lst: MutableList<Pair<Int, Int>>) {
            val f = lst.indexOfFirst { it.second == 5 }
            if (f > -1) {
                val s = f + 1
                if (f > 0) {
                    val left = lst[f - 1]
                    lst[f - 1] = Pair(left.first + lst[f].first, left.second)
                }
                if (s < lst.size - 1) {
                    val right = lst[s + 1]
                    lst[s + 1] = Pair(right.first + lst[s].first, right.second)
                }
                lst[f] = Pair(0, lst[f].second - 1)
                lst.removeAt(s)
            }
        }

        fun split(lst: MutableList<Pair<Int, Int>>) {
            val f = lst.indexOfFirst { it.first > 9 }
            if (f > -1) {
                val fPair = lst[f]
                val l = floor(fPair.first / 2.0).toInt()
                val r = ceil(fPair.first / 2.0).toInt()
                lst[f] = Pair(l, fPair.second + 1)
                lst.add(f + 1, Pair(r, fPair.second + 1))
            }
        }

        private fun shouldExplode(lst: List<Pair<Int, Int>>): Boolean =
            lst.any { it.second == 5 }

        private fun shouldSplit(lst: List<Pair<Int, Int>>): Boolean =
            lst.any { it.first > 9 }
    }

    override fun part1(input: List<String>): Int =
        magnitude(parsePairs(input)
            .reduce { acc: List<Pair<Int, Int>>, pair: List<Pair<Int, Int>> ->
                treePlus(acc, pair)
            })

    fun largestSum(a: List<Pair<Int,Int>>, b: List<Pair<Int,Int>>): Int =
        max( magnitude(treePlus(a, b)), magnitude(treePlus(b,a)))

    override fun part2(input: List<String>): Int {
        var max = 0
        val p = parsePairs(input)
        for(i in p.indices){
            for (j in (i + 1 until p.size)){
                max = max(max, largestSum(p[i], p[j]))
            }
        }
        return max
    }

}

fun main() {
    println("Day 18")
    Day18().run()
}