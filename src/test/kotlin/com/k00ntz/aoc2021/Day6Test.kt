package com.k00ntz.aoc2021

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day6Test {

    val day6 = Day6()

    val input = listOf(3,4,3,1,2)

    val inputMap = input.groupBy { it }.mapValues { it.value.size.toLong() }

    @Test
    fun runTicks() {
        assertEquals(input.size.toLong(), day6.runTicks(inputMap, 1).entries.sumOf { it.value })
        assertEquals(26L, day6.runTicks(inputMap, 18).entries.sumOf { it.value })
        assertEquals(5934L, day6.runTicks(inputMap, 80).entries.sumOf { it.value })
        assertEquals(26984457539L, day6.runTicks(inputMap, 256).entries.sumOf { it.value })

    }
}