package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

class Day8 : Day<List<Day8.SignalOutput>, Int, Int> {

    data class SignalOutput(val signalPatterns: List<String>, val outputValue: List<String>) {

        fun findOutputValue(): Int {
            val decodeMap = decode()
            val decodedNumbers = outputValue.map { decodeMap[it]!! }
            return decodedNumbers.joinToString(separator = "").toInt()
        }

        private fun decode(): Map<String, Int> {
            val signalGroups = signalPatterns.groupBy { it.length }
            val one = signalGroups[2]!!.first() to 1
            val four = signalGroups[4]!!.first() to 4
            val seven = signalGroups[3]!!.first() to 7
            val eight = signalGroups[7]!!.first() to 8
            val group6 = signalGroups[6]!!
            val group5 = signalGroups[5]!!
            val oneHash = one.first.toHashSet()
            val fourHash = four.first.toHashSet()
            val three: Pair<String, Int> = group5.first { it.toHashSet().containsAll(oneHash) } to 3
            val six: Pair<String, Int> = group6.first { !it.toHashSet().containsAll(oneHash) } to 6
            val nine: Pair<String, Int> = group6.first { it.toHashSet().containsAll(fourHash) } to 9
            val zero: Pair<String, Int> = group6.minus(setOf(six.first, nine.first)).first() to 0
            val two: Pair<String, Int> = group5.first { nine.first.toHashSet().minus(it.toHashSet()).size == 2 } to 2
            val five: Pair<String, Int> = group5.first { it != three.first && nine.first.toHashSet().minus(it.toHashSet()).size == 1 } to 5
            return mapOf(zero, one, two, three, four, five, six, seven, eight, nine)
        }

        companion object {
            fun String.sortCharacters(): String {
                val c = this.toCharArray()
                Arrays.sort(c)
                return String(c)
            }

            fun parse(str: String): SignalOutput {
                val firstSplit = str.split("|")
                return SignalOutput(firstSplit[0].trim().split(" ").map { it.sortCharacters() },
                    firstSplit[1].trim().split(" ").map { it.sortCharacters() })
            }
        }
    }

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                SignalOutput.parse(it) }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<SignalOutput>): Int {
        val sizes = setOf(2, 4, 3, 7)
        return input.flatMap { it.outputValue.filter { sizes.contains(it.length) } }.size
    }

    override fun part2(input: List<SignalOutput>): Int =
        input.sumOf { it.findOutputValue() }

}

fun main() {
    println("Day 8")
    Day8().run()
}