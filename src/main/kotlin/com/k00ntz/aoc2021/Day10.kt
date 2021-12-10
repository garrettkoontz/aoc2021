package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

class Day10 : Day<List<String>, Int, Long> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    val corruptScoreMap = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val autocompleteScoreMap = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    val closeToOpenMap = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<'
    )

    val openToCloseMap = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )

    val closers = closeToOpenMap.keys

    override fun part1(input: List<String>): Int {
        return input.mapNotNull { findCorrupted(it) }.sumOf {
            corruptScoreMap[it.first] ?: 0
        }
    }

    fun findCorrupted(str: String): Pair<Char, Int>? {
        val stack = ArrayDeque<Char>()
        for (i in (str.indices)) {
            val c = str[i]
            val s = stack.peek()
            if (closers.contains(c))
                if (closeToOpenMap[c] == s) stack.pop()
                else return Pair(c, i)
            else
                stack.push(c)
        }
        return null
    }

    override fun part2(input: List<String>): Long {
        val newInput = input.filter { findCorrupted(it) == null }
        val completions = newInput.map { completeString(it) }.map { scoreCompletion(it) }.sorted()
        return completions[completions.size / 2]
    }

    private fun scoreCompletion(str: String): Long =
        str.fold(0L) { acc: Long, c: Char ->
            acc * 5 + autocompleteScoreMap[c]!!
        }


    private fun completeString(str: String): String {
        val stack = ArrayDeque<Char>()
        for (i in (str.indices)) {
            val c = str[i]
            val s = stack.peek()
            if (closers.contains(c) && closeToOpenMap[c] == s)
                stack.pop()
            else
                stack.push(c)
        }
        var r = ""
        for (s in stack) {
            r += openToCloseMap[s]
        }
        return r
    }

}

fun main() {
    println("Day 10")
    Day10().run()
}
