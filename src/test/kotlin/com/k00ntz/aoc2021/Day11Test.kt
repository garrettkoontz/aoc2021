package com.k00ntz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11Test {

    val inputString = """5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526"""

    val input = inputString.split("\n").map { it.toCharArray().map { it.digitToInt() } }

    val day11 = Day11()

    @Test
    fun part1() {
        assertEquals(1656, day11.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(195, day11.part2(input))
    }

    @Test
    fun tick() {
        assertEquals(
            Pair(0, """6594254334
3856965822
6375667284
7252447257
7468496589
5278635756
3287952832
7993992245
5957959665
6394862637""".split("\n").map { it.toCharArray().map { it.digitToInt() } }), day11.tick(input)
        )
    }
}