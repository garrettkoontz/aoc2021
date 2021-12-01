package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

class Day1 : Day<List<Int>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it.toInt() }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Int>): Int {
        return numberOfIncreases(input)
    }

    fun numberOfIncreases(ints: List<Int>): Int =
        ints.fold(Pair(ints.first(), 0)) { acc: Pair<Int, Int>, i: Int ->
            Pair(
                i,
                if (acc.first < i) acc.second + 1 else acc.second
            )
        }.second

    override fun part2(input: List<Int>): Int {
        return numberOfWindowIncreases(input, 3)
    }

    fun numberOfWindowIncreases(ints: List<Int>, size: Int): Int {
        return numberOfIncreases( ints.windowed(size, 1).map { it.sum() })
    }
}


fun main() {
    println("Day 1")
    Day1().run()
}
