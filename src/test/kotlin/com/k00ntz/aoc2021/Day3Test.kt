package com.k00ntz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3Test {

    val day3 = Day3()

    val input = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010"
    )
    
    @Test
    fun part1() {
        assertEquals(198, day3.part1(input))
    }

    @Test
    fun mostCommonBits() {
        assertEquals("10110", day3.mostCommonBits(input))
    }

    @Test
    operator fun not() {
        assertEquals("01001", "10110".not())
    }

    @Test
    fun part2(){
        assertEquals(230, day3.part2(input))
    }
}