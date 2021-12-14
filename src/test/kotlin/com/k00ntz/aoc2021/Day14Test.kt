package com.k00ntz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14Test {

    val inputString = """NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C"""

    val day14 = Day14()

    val input = day14.parse(inputString.split("\n"))

    @Test
    fun applyRules() {
        assertEquals("NCNBCHB", day14.apply(input.startString, input.rules))
    }

    @Test
    fun part1() {
        assertEquals(1588, day14.part1(input))
    }

    @Test
    fun polymer() {
        assertEquals("NCNBCHB".toPairMap(), input.toPolymer().next().pairCount)
        assertEquals("NBCCNBBBCBHCB".toPairMap(), input.toPolymer().next().next().pairCount)
        assertEquals("NBBBCNCCNBBNBNBBCHBHHBCHB".toPairMap(), input.toPolymer().next().next().next().pairCount)
        assertEquals(
            "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB".toPairMap(),
            input.toPolymer().next().next().next().next().pairCount
        )
        val polymer = input.toPolymer()
        val chainedPolymer = (0 until 10).fold(polymer) { acc, _ ->
            acc.next()
        }
        val counts = chainedPolymer.counts()
        assertEquals(1588, counts.maxOf { it.value } - counts.minOf { it.value })
    }
}