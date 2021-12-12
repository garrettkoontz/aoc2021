package com.k00ntz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12Test {

    fun parse(str: String): List<Pair<String, String>> =
        str.split("\n").map { it.split("-").let { Pair(it[0], it[1]) } }

    val inputString1 = """start-A
start-b
A-c
A-b
b-d
A-end
b-end"""

    val input1 = parse(inputString1)
    val inputString2 = """dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc"""

    val inputString3 = """fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW"""

    val input2 = parse(inputString2)
    val input3 = parse(inputString3)
    val day12 = Day12()

    @Test
    fun part1() {
        assertEquals(10, day12.part1(input1))
        assertEquals(19, day12.part1(input2))
        assertEquals(226, day12.part1(input3))
    }

    @Test
    fun part2() {
        assertEquals(36, day12.part2(input1))
        assertEquals(103, day12.part2(input2))
        assertEquals(3509, day12.part2(input3))
    }
}