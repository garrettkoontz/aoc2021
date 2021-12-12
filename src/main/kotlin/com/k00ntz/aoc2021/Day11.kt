package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.*
import java.util.*
import kotlin.collections.ArrayDeque

class Day11 : Day<List<List<Int>>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                it.toCharArray().map { it.digitToInt() }
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<List<Int>>): Int {
        var flashes = 0
        var grid = input
        for (i in (0 until 100)) {
            val (f, g) = tick(grid)
            flashes += f
            grid = g
        }
        return flashes
    }

    fun tick(grid: List<List<Int>>): Pair<Int, List<List<Int>>> {
        val up1Grid: MutableList<MutableList<Int>> = grid.map { it.map { it + 1 }.toMutableList() }.toMutableList()
        val xLength = grid.first().size
        val yLength = grid.size
        val points: ArrayDeque<Point> = ArrayDeque((0 until xLength).flatMap { x ->
            (0 until yLength).map {
                Point(x, it)
            }
        })

        while (points.isNotEmpty()) {
            val pt = points.removeFirst()
            if (up1Grid.getPoint(pt) > 9) {
                val neighbors = pt.validAroundNeighbors(up1Grid) { it != 0 }
                up1Grid.setPoint(pt) { 0 }
                neighbors.forEach {
                    up1Grid.setPoint(it) { i: Int ->
                        i + 1
                    }
                }
                points.addAll(neighbors)
            }
        }

        return Pair(up1Grid.sumOf { it.filter { it == 0 }.size }, up1Grid)
    }

    override fun part2(input: List<List<Int>>): Int {
        var flashes = 0
        var grid = input
        val targetFlashes = input.size * input.first().size
        var i = 0
        while (flashes != targetFlashes) {
            val (f, g) = tick(grid)
            flashes = f
            grid = g
            i++
        }
        return i
    }

}

private fun <E> MutableList<MutableList<E>>.setPoint(pt: Point, fn: (E) -> E) {
    this[pt.y()][pt.x()] = fn(this[pt.y()][pt.x()])
}

fun main() {
    println("Day 10")
    Day11().run()
}