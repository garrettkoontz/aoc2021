package com.k00ntz.aoc2021

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day5Test {

    val inputString = """0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"""

    val input = inputString.split("\n").map { Day5.Vents.parseVents(it) }

    val day5 = Day5()

    @Test
    fun part1() {
        assertEquals(5, day5.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(12, day5.part2(input))
    }
}