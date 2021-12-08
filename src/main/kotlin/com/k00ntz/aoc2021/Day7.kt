package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseLineForNumbers
import java.util.*
import kotlin.math.abs

class Day7 : Day<List<Int>, Int, Int> {
    override fun run() {
        val inputFile = parseLineForNumbers("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt")

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    fun fuelToMoveTo(
        input: List<Int>,
        position: Int,
        fn: (Int, Int) -> Int = { currentPosition, newPosition -> abs(currentPosition - newPosition) }
    ): Int =
        input.sumOf { fn(it, position) }

    override fun part1(input: List<Int>): Int {
        val min = input.minOrNull()!!
        val max = input.maxOrNull()!!
        return (min..max).minOf { fuelToMoveTo(input, it) }
    }

    override fun part2(input: List<Int>): Int {
        val min = input.minOrNull()!!
        val max = input.maxOrNull()!!
        val fn = { currentPosition: Int, newPosition: Int ->
            (abs(currentPosition - newPosition)).let {
                it + ((it - 1) * it / 2)
            }
        }
        return (min..max).minOf { fuelToMoveTo(input, it, fn) }
    }

}

fun main() {
    println("Day 7")
    Day7().run()
}