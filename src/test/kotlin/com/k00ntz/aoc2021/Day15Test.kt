package com.k00ntz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day15Test {

    val inputString = """1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581"""

    val input = inputString.split("\n").map { it.toCharArray().map { it.digitToInt() } }

    val day15 = Day15()

    @Test
    fun part1() {
        assertEquals(40, day15.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(315, day15.part2(input))
    }
}