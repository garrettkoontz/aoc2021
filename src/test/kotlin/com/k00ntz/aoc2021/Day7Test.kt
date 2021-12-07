package com.k00ntz.aoc2021

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day7Test {

    val inputString = "16,1,2,0,4,2,7,1,2,14"
    val input = inputString.split(",").map { it.toInt() }

    val day7 = Day7()

    @Test
    fun fuelToMoveTo() {
        assertEquals(37, day7.fuelToMoveTo(input, 2))
        assertEquals(41, day7.fuelToMoveTo(input, 1))
        assertEquals(39, day7.fuelToMoveTo(input, 3))
        assertEquals(71, day7.fuelToMoveTo(input, 10))
    }

    @Test
    fun part1() {
        assertEquals(37, day7.part1(input))
    }

    @Test
    fun part2() {
        assertEquals(168, day7.part2(input))
    }
}