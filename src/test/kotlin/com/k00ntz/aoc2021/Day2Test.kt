package com.k00ntz.aoc2021

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2Test {

    val day2 = Day2()

    val input = listOf(
        "forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2"
    ).map { Day2.Move.parseMove(it) }

    @Test
    fun part1() {
        assertEquals(150, day2.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(900, day2.part2(input))
    }
}