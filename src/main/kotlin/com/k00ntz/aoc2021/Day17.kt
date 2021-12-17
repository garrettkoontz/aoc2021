package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.*
import java.util.*
import kotlin.math.max

class Day17 : Day<Day17.TargetArea, Int, Int> {
    data class TargetArea(val x1: Int, val x2: Int, val y1: Int, val y2: Int){
        fun contains(probe: Probe): Boolean {
            val pos = probe.position
            return pos.x() in x1..x2 && pos.y() in y1 .. y2
        }

        fun isPast(probe: Probe) : Boolean =
            probe.position.x() > x2 || probe.position.y() < y1
    }

    override fun run() {
        val inputFile =
            parseLine("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                "target area: x=([-]?[0-9]+)..([-]?[0-9]+), y=-([-]?[0-9]+)..([-]?[0-9]+)"
                    .toRegex().matchEntire(it)!!.destructured.let { (x1, x2, y1, y2) ->
                        TargetArea(x1.toInt(), x2.toInt(), y1.toInt(), y2.toInt())
                    }
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    class Probe(val position: Point, val velocity: Point) {
        fun next(): Probe =
            Probe(position + velocity, velocity.decayVelocity())

        fun Point.decayVelocity(): Point =
            Point(
                when {
                    this.x() > 0 -> this.x() - 1
                    this.x() < 0 -> this.x() + 1
                    else -> 0
                }, this.y() - 1
            )

        fun hitsTargetArea(targetArea: TargetArea): Int? {
            var p = this
            var maxY = p.position.y()
            while(!targetArea.isPast(p)){
                if(targetArea.contains(p)) return maxY
                p = p.next()
                maxY = max(maxY, p.position.y())
            }
            return null
        }
    }


    override fun part1(input: TargetArea): Int {

    }

    override fun part2(input: TargetArea): Int {
        return super.part2(input)
    }

}

fun main() {
    println("Day 17")
    Day17().run()
}