package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

class Day12 : Day<List<Pair<String, String>>, Int, Int> {

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") {
                it.split("-").let { Pair(it[0], it[1]) }
            }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    fun makeMap(input: List<Pair<String, String>>): Map<String, Set<String>> {
        val group1 = input.groupBy({ it.first }, { it.second }).toMutableMap()
        val group2 = input.groupBy({ it.second }, { it.first })
        group2.forEach { (k, v) ->
            group1.merge(k, v) { a: List<String>, b: List<String> -> a.plus(b) }
        }
        return group1.mapValues { it.value.toSet() }
    }

    sealed interface Search

    data class SearchPoint(
        val pathSoFar: List<String>,
        val visitedSmallCaves: Set<String> = setOf(),
        var doubleVisited: String? = end
    ) : Search {
        companion object {
            val end = "end"
            val smallCaveRegex = "[a-z]+".toRegex()
            fun isSmallCave(str: String): Boolean =
                smallCaveRegex.matches(str)
        }

        fun getNextNodes(map: Map<String, Set<String>>): Set<String> {
            val last = pathSoFar.last()
            return map[last]!!.minus(setOf("start"))
        }

        fun nextSearchPoint(nextCave: String, doubleVisited: String? = null): Search =
            if (nextCave == end) {
                Path(pathSoFar.plus(end))
            } else {
                SearchPoint(
                    pathSoFar.plus(nextCave),
                    if (isSmallCave(nextCave)) visitedSmallCaves.plus(nextCave) else visitedSmallCaves,
                    this.doubleVisited ?: doubleVisited
                )
            }

        fun nextSearchPoints(map: Map<String, Set<String>>): List<Search> =
            getNextNodes(map).mapNotNull {
                if(visitedSmallCaves.contains(it))
                    if(doubleVisited != null) null else Pair(it, it)
                else {
                    Pair(it, null)
                }
            }.map {
                nextSearchPoint(it.first, it.second)
            }
    }

    class Path(val l: List<String>) : Search

    override fun part1(input: List<Pair<String, String>>): Int {
        val map: Map<String, Set<String>> = makeMap(input)
        val start = "start"
        var inProgress = setOf(SearchPoint(listOf(start), setOf(start)))
        val paths = mutableSetOf<List<String>>()
        while (inProgress.isNotEmpty()) {
            val nexts = inProgress.flatMap { it.nextSearchPoints(map) }
            inProgress = nexts.filterIsInstance<SearchPoint>().toSet()
            paths.addAll(nexts.filterIsInstance<Path>().map { it.l })
        }
        return paths.size
    }

    override fun part2(input: List<Pair<String, String>>): Int {
        val map: Map<String, Set<String>> = makeMap(input)
        val start = "start"
        var inProgress = setOf(SearchPoint(listOf(start), setOf(start), null))
        val paths = mutableSetOf<List<String>>()
        while (inProgress.isNotEmpty()) {
            val nexts = inProgress.flatMap { it.nextSearchPoints(map) }
            inProgress = nexts.filterIsInstance<SearchPoint>().toSet()
            paths.addAll(nexts.filterIsInstance<Path>().map { it.l })
        }
        return paths.size
    }

}

fun main() {
    println("Day 12")
    Day12().run()
}