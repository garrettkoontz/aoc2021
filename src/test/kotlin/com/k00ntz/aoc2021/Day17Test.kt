package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Point
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17Test {

    val day17 = Day17()

    val p1 = Day17.Probe(Point(7, 2))
    val p2 = Day17.Probe(Point(6, 3))
    val p3 = Day17.Probe(Point(9, 0))
    val p4 = Day17.Probe(Point(17, -4))
    val pBest = Day17.Probe(Point(6, 9))
    val targetArea = Day17.TargetArea(20, 30, -10, -5)
    val targetArea2 = Day17.TargetArea(235, 259, -118, -62)


    @Test
    fun part1() {
        assertNotNull(p1.hitsTargetArea(targetArea))
        assertNotNull(p2.hitsTargetArea(targetArea))
        assertNotNull(p3.hitsTargetArea(targetArea))
        assertNull(p4.hitsTargetArea(targetArea))
        assertEquals(45, pBest.hitsTargetArea(targetArea))
        assertEquals(45, day17.part1(targetArea))
    }

    @Test
    fun part2() {
        assertEquals(112, day17.part2(targetArea))
    }
}