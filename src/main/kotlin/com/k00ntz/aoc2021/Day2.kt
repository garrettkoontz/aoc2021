package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.*
import java.util.*


class Day2 : Day<List<Day2.Move>, Int, Int> {

    enum class Direction(val vec: Point) {
        FORWARD(Point(0, 1)),
        DOWN(Point(1, 0)),
        UP(Point(-1, 0));

        fun move(pt: Point, units: Int, aim: Int): Pair<Point, Int> =
            when (this) {
                UP -> Pair(pt, aim - units)
                DOWN -> Pair(pt, aim + units)
                FORWARD -> {
                    Pair(pt + Point((units * aim), units), aim)
                }
            }

    }

    data class Move(val direction: Direction, val units: Int) {
        companion object {
            fun parseMove(str: String): Move {
                val strs = str.split(" ")
                val dir = Direction.valueOf(strs[0].uppercase())
                val units = strs[1].toInt()
                return Move(dir, units)
            }
        }

        fun makeMove(pointAim: Pair<Point,Int>): Pair<Point, Int> =
            direction.move(pointAim.first, units, pointAim.second)
    }

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { Move.parseMove(it) }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<Move>): Int {
        val position = input.fold(Point(0, 0)) { acc: Point, move: Move ->
            acc + (move.direction.vec * move.units)
        }
        return position.x() * position.y()
    }

    override fun part2(input: List<Move>): Int {
        val position = input.fold(Pair(Point(0, 0), 0)) { acc: Pair<Point,Int>, move: Move ->
            move.makeMove(acc)
        }
        return position.first.x() * position.first.y()
    }
}

fun main() {
    println("Day 2")
    Day2().run()
}