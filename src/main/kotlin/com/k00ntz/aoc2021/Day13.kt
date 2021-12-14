package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.*
import java.util.*

typealias FoldInput = Pair<List<Point>, List<Day13.Fold>>

class Day13 : Day<FoldInput, Int, String> {
    fun parse(strs: List<String>): FoldInput {
        val groups = groupSeparatedByEmpty(strs)
        val points = groups.first().map {
            val s = it.split(",")
            Point(s[0].toInt(), s[1].toInt())
        }
        val folds = groups[1].map {
            "fold along ([x|y])=([0-9]+)".toRegex()
                .matchEntire(it)!!.destructured.let { (l, n) -> if (l == "x") FoldX(n.toInt()) else FoldY(n.toInt()) }
        }
        return Pair(points, folds)
    }

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }

        val input = parse(inputFile)
        measureAndPrintTime { print(part1(input)) }
        measureAndPrintTime { print(part2(input)) }
    }

    sealed class Fold(val amount: Int) {
        abstract fun fold(points: List<Point>): List<Point>
    }

    class FoldX(amount: Int) : Fold(amount) {
        override fun fold(points: List<Point>): List<Point> {
            val (same, folded) = points.partition { it.x() < amount }
            return same.plus(folded.map {
                val newX = 2 * amount - it.x()
                Point(newX, it.y())
            }).toSet().toList()
        }
    }

    class FoldY(amount: Int) : Fold(amount) {
        override fun fold(points: List<Point>): List<Point> {
            val (same, folded) = points.partition { it.y() < amount }
            return same.plus(folded.map {
                val newY = 2 * amount - it.y()
                Point(it.x(), newY)
            }).toSet().toList()
        }
    }

    override fun part1(input: FoldInput): Int {
        return input.second.first().fold(input.first).size
    }

    override fun part2(input: FoldInput): String {
        val folded = input.second.fold(input.first) { acc: List<Point>, fold: Fold ->
            fold.fold(acc)
        }
        val foldDraw = object : PointDrawable{
            override val default: String = "."
            override fun draw(): String = "#"
        }
        val map = folded.associateWith { foldDraw }
        return map.draw()
    }

}

fun main() {
    println("Day 13")
    Day13().run()
}

// CEJKLUGJ