package com.k00ntz.aoc2021

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13Test {

    val inputString = """6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5"""

    val day13 = Day13()

    val input = day13.parse(inputString.split("\n"))

    @Test
    fun part1() {
        assertEquals(17, day13.part1(input))
    }
}