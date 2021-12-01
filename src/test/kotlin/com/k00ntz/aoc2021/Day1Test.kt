package com.k00ntz.aoc2021

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day1Test {

    val day1 = Day1()

    val input = listOf(
        199,
        200,
        208,
        210,
        200,
        207,
        240,
        269,
        260,
        263
    )

    @Test
    fun numberOfIncreases() {
        assertEquals(7, day1.numberOfIncreases(input))
    }

    @Test
    fun numberOfWindowIncreases() {
        assertEquals(5, day1.numberOfWindowIncreases(input, 3))
    }
}