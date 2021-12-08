package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseLine
import java.util.*

class Day6 : Day<Map<Int, Long>, Int, Long> {
    override fun run() {
        val inputFile =
            parseLine("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                it.split(",").map { it.toInt() }
            }
        val mapInput = inputFile.groupBy { it }.mapValues { (_, v) -> v.size.toLong() }
        measureAndPrintTime { print(part1(mapInput)) }
        measureAndPrintTime { print(part2(mapInput)) }
    }

    override fun part1(input: Map<Int, Long>): Int {
        return runTicks(
            input, 80
        ).entries.sumOf { it.value.toInt() }
    }

    private fun tick(input: Map<Int, Long>): Map<Int, Long> {
        val initMap: MutableMap<Int, Long> =
            (0..7).map { Pair(it, input.getOrDefault(it + 1, 0)) }.associate { it }.toMutableMap()
        initMap[8] = input.getOrDefault(0, 0)
        initMap[6] = input.getOrDefault(7, 0) + input.getOrDefault(0, 0)
        return initMap
    }

    fun runTicks(input: Map<Int, Long>, numberOfTicks: Int): Map<Int, Long> =
        (1..numberOfTicks).fold(input) { acc: Map<Int, Long>, _: Int ->
            tick(acc)
        }

    override fun part2(input: Map<Int, Long>): Long {
        return runTicks(input, 256).entries.sumOf { it.value }
    }

}

fun main() {
    println("Day 6")
    Day6().run()
}