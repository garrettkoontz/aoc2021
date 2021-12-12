package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.*
import java.util.*

class Day10 : Day<List<List<Int>>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                it.toCharArray().map { it.digitToInt() }
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<List<Int>>): Int {

    }

    fun tick(grid: List<List<Int>>): Pair<Int, List<List<Int>>> {
        val up1Grid = grid.map { it.map { it + 1 } }
        val xLength = grid.first().size
        val yLength = grid.size
        val points: List<Point> = (0 until xLength).flatMap { x ->
            (0 until yLength).map {
                Point(x, it)
            }
        }
        points.forEach {
            val neighbors = it.validAroundNeighbors(up1Grid)

        }
    }

    override fun part2(input: List<List<Int>>): Int {
        return super.part2(input)
    }

}

fun main() {
    println("Day 10")
    Day10().run()
}