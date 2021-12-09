package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.*
import java.util.*

class Day9 : Day<List<CharArray>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                it.toCharArray()
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<CharArray>): Int {
        val sum = findMins(input)
            .sumOf { pt -> input.getPoint(pt).digitToInt() + 1 }
        return sum
    }

    override fun part2(input: List<CharArray>): Int {
        val mins = findMins(input)
        val threeLargest = mins.map { basinSize(input, it) }.sortedDescending().take(3)
        return threeLargest.fold(1) { acc: Int, i: Int -> acc * i }
    }

    fun basinSize(map: List<CharArray>, pt: Point): Int {
        val ridge = '9'
        val pts = mutableSetOf(pt)
        val toMapPoints = ArrayDeque(listOf(pt))
        while (toMapPoints.isNotEmpty()) {
            val mapPoint = toMapPoints.poll()
            val neighbors = mapPoint.validCrossNeighbors(map) { it != ridge }
            val toMaps = neighbors.minus(pts)
            toMapPoints.addAll(toMaps)
            pts.addAll(toMaps)
        }
        return pts.size
    }

    fun findMins(input: List<CharArray>): List<Point> {
        val yLength = input.size
        val xLength = input.first().size
        val points: List<Point> = (0 until xLength).flatMap { x ->
            (0 until yLength).map { y ->
                Point(x, y)
            }
        }
        val neighborsPairs = points.map { Pair(it, it.validCrossNeighbors(input)) }
        val mins = neighborsPairs
            .filter { (pt, neighbors) -> input.getPoint(pt) < neighbors.minOf { input.getPoint(it) } }
        return mins.map { it.first }
    }

}

fun main() {
    println("Day 9")
    Day9().run()
}

// 422 too low