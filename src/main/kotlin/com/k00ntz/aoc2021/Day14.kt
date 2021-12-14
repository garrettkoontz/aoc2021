package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.groupSeparatedByEmpty
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

fun String.toPairMap() = this.windowed(2, 1).groupBy({ it[0] to it[1] }, { 1L }).mapValues { it.value.sum() }

class Day14 : Day<Day14.PolymerInput, Int, Long> {
    data class PolymerInput(val startString: String, val rules: List<Rule>) {
        fun toPolymer() =
            Polymer(
                this.startString.first(),
                this.startString.last(),
                this.rules.toMap(),
                this.startString.toPairMap()
            )
    }

    data class Rule(val from: String, val to: String) {
        fun apply(str: String): String? {
            val index = str.indexOf(from)
            return if (index >= 0) str.substring(0, index + 1) + to + str.substring(index + 1) else null
        }
    }

    data class Polymer(
        val startChar: Char,
        val endChar: Char,
        val pairMap: Map<Pair<Char, Char>, Char>,
        val pairCount: Map<Pair<Char, Char>, Long>
    ) {
        fun next() = Polymer(startChar, endChar, pairMap, pairCount
            .flatMap {
                val middleChar = pairMap[it.key]!!
                listOf(
                    Pair(it.key.first, middleChar) to it.value,
                    Pair(middleChar, it.key.second) to it.value
                )
            }.groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() })

        fun counts(): Map<Char, Long> {
            val counts = pairCount
                .map { it.key.first to it.value }
                .groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() }
                .toMutableMap()
            counts[endChar] = counts[endChar]!! + 1
            return counts
        }
    }

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }

        val input = parse(inputFile)

        measureAndPrintTime { print(part1(input)) }
        measureAndPrintTime { print(part2(input)) }
    }

    internal fun parse(inputFile: List<String>) =
        groupSeparatedByEmpty(inputFile).let {

            PolymerInput(it.first().first(), it[1].map { it.split(" -> ").let { Rule(it[0], it[1]) } })
        }

    fun apply(str: String, rules: List<Rule>): String {
        val windows = str.windowed(2, 1).flatMap { s -> rules.mapNotNull { r -> r.apply(s) } }
        return str.first() + windows.joinToString(separator = "") { it.drop(1) }
    }

    override fun part1(input: PolymerInput): Int {
        val polymer = (0 until 10).fold(input.startString) { acc: String, _: Int ->
            apply(acc, input.rules)
        }

        val sizes = polymer.toCharArray().groupBy { it }.mapValues { it.value.size }
        return sizes.maxOf { it.value } - sizes.minOf { it.value }
    }

    override fun part2(input: PolymerInput): Long {
        val polymer = input.toPolymer()
        val chainedPolymer = (0 until 40).fold(polymer) { acc, _ ->
            acc.next()
        }
        val counts = chainedPolymer.counts()
        return counts.maxOf { it.value } - counts.minOf { it.value }
    }


}

private fun List<Day14.Rule>.toMap(): Map<Pair<Char, Char>, Char> =
    this.associate { (it.from[0] to it.from[1]) to it.to[0] }

fun main() {
    println("Day 14")
    Day14().run()
}