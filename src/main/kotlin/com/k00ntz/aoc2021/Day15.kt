package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.*
import java.lang.Integer.min
import java.util.*

class Day15 : Day<List<List<Int>>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                it.toCharArray().map { it.digitToInt() }
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    data class PriorityPair(val point: Point, val value: Int) : Comparable<PriorityPair> {
        override fun compareTo(other: PriorityPair): Int =
            this.value.compareTo(other.value)
    }

    fun getBestPathValueDykstras(grid: List<List<Int>>): Int {
        val heap = PriorityQueue(listOf(PriorityPair(Point(0, 0), 0)))
        var iterations = 0
        val endPoint = Point(grid.first().size - 1, grid.size - 1)
        while (true) {
            iterations++
            val (currentPoint, pathValue) = heap.poll()
            if (currentPoint == endPoint) {
                return pathValue
            }
            val neighbors = currentPoint.validCrossNeighbors(grid)
            heap.addAll(neighbors.map { PriorityPair(it, pathValue + grid.getPoint(it)) })
        }
    }

    fun getBestPathValue(grid: List<List<Int>>): Int {
        val pathGrid: MutableList<MutableList<Int>> =
            (grid.indices).map { grid.first().indices.map { 0 }.toMutableList() }.toMutableList()
        val endPoint = Point(grid.first().size - 1, grid.size - 1)
        pathGrid.setPoint(endPoint, grid.getPoint(endPoint))
        var pointList = endPoint.validBackNeighbors(grid)
        while (pointList.isNotEmpty()) {
            pointList.forEach {
                pathGrid.setPoint(
                    it,
                    grid.getPoint(it) + it.validForwardNeighbors(grid).minOf { pathGrid.getPoint(it) })
            }
            pointList = pointList.flatMap { it.validBackNeighbors(grid) }.toSet()
        }
        pointList = endPoint.validBackNeighbors(grid)
        while (pointList.isNotEmpty()) {
            pointList.forEach {
                val minNeighborValue = it.validCrossNeighbors(grid).minOf { pathGrid.getPoint(it) }
                pathGrid.setPoint(
                    it,
                    min(pathGrid.getPoint(it), grid.getPoint(it) + minNeighborValue))
            }
            pointList = pointList.flatMap { it.validBackNeighbors(grid) }.toSet()
        }

        return Point(0,0).validForwardNeighbors(grid).minOf { pathGrid.getPoint(it) }
    }

    override fun part1(input: List<List<Int>>): Int {
        return getBestPathValue(input)
    }

    override fun part2(input: List<List<Int>>): Int {
        return super.part2(input)
    }

}

fun main() {
    println("Day 15")
    Day15().run()
}

//597 too high