package com.k00ntz.aoc2021

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day9Test {

    val inputString = "2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678\n"

    val input = inputString.trim().split("\n").map { it.toCharArray() }

    val day9 = Day9()

    @Test
    fun part1() {
        assertEquals(15, day9.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(1134, day9.part2(input))
    }
}