package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.*
import java.lang.Integer.min
import java.util.*
import kotlin.math.max
import kotlin.math.sign

class Day5 : Day<List<Day5.Vents>, Int, Int> {
    data class Vents(val start: Point, val end: Point) {
        companion object {
            private val regex = "([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)".toRegex()
            fun parseVents(str: String): Vents =
                regex.matchEntire(str)!!.destructured.let { (x1, y1, x2, y2) ->
                    Vents(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt()))
                }
        }
    }

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { Vents.parseVents(it) }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Vents>): Int =
        part(input, true)

    private fun List<MutableList<Int>>.addLineSegment(vents: Vents, skipDiagonals: Boolean = false) {
        val pt1: Point = vents.start
        val pt2 = vents.end
        when {
            diffX(pt1, pt2) && !diffY(pt1, pt2) -> {
                val lessX = min(pt1.x(), pt2.x())
                val moreX = max(pt1.x(), pt2.x())
                (lessX..moreX).forEach { this[it][pt1.y()] = this[it][pt1.y()] + 1 }
            }
            diffY(pt1, pt2) && !diffX(pt1, pt2) -> {
                val lessY = min(pt1.y(), pt2.y())
                val moreY = max(pt1.y(), pt2.y())
                (lessY..moreY).forEach { this[pt1.x()][it] = this[pt1.x()][it] + 1 }
            }
            else -> {
                if (!skipDiagonals) {
                    val (lessX, moreX) = if (pt1.x() < pt2.x()) Pair(pt1, pt2) else Pair(pt2, pt1)
                    val yDir = (moreX.y() - lessX.y()).sign
                    (lessX.x()..moreX.x()).forEachIndexed { index, i ->
                        this[i][lessX.y() + (index * yDir)] = this[i][lessX.y() + (index * yDir)] + 1
                    }
                }
            }
        }
    }

    private fun diffX(pt1: Point, pt2: Point) = pt1.x() != pt2.x()
    private fun diffY(pt1: Point, pt2: Point) = pt1.y() != pt2.y()

    override fun part2(input: List<Vents>): Int =
        part(input)

    fun part(input: List<Vents>, skipDiagonals: Boolean = false): Int{
        val maxX = input.maxOf { max(it.start.x(), it.end.x()) }
        val maxY = input.maxOf { max(it.start.y(), it.end.y()) }
        val floorGrid: List<MutableList<Int>> = (0..maxX).map { (0..maxY).map { 0 }.toMutableList() }
        input.forEach { floorGrid.addLineSegment(it, skipDiagonals) }
        return floorGrid.flatten().filter { it > 1 }.size
    }

}

fun main() {
    println("Day 5")
    Day5().run()
}