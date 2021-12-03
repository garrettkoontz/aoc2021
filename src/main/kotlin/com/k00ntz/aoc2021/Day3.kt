package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

fun String.not(): String = String(this.map { if (it == '0') '1' else '0' }.toCharArray())

class Day3 : Day<List<String>, Int, Int> {
    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }

        measureAndPrintTime { print(part1(inputFile)) }
        measureAndPrintTime { print(part2(inputFile)) }
    }

    override fun part1(input: List<String>): Int {
        val gammaString = mostCommonBits(input)
        val epsilonString = gammaString.not()
        val gamma = gammaString.toInt(2)
        val epsilon = epsilonString.toInt(2)
        return gamma * epsilon
    }

    fun mostCommonBits(binaryStrings: List<String>): String {
        var output = ""
        for (i in binaryStrings.first().indices) {
            output += mostCommonBitAt(binaryStrings, i)
        }
        return output
    }

    fun mostCommonBitAt(binaryStrings: List<String>, position: Int): Char {
        if(binaryStrings.size == 1) return binaryStrings[0][position]
        val (ones, zeros) = partitionBits(binaryStrings, position)
        return if (ones.size < zeros.size) '0' else '1'
    }

    fun partitionBits(binaryStrings: List<String>, position: Int): Pair<List<Char>, List<Char>> {
        return binaryStrings.map { it[position] }.partition { it == '1' }
    }

    fun leastCommonBitAt(binaryStrings: List<String>, position: Int): Char {
        if(binaryStrings.size == 1) return binaryStrings[0][position]
        val (ones, zeros) = partitionBits(binaryStrings, position)
        return if (ones.size < zeros.size) '1' else '0'
    }

    override fun part2(input: List<String>): Int {
        val (oxygen, co2) = findOxygenAndCO2(input)
        return (oxygen.toInt(2) * co2.toInt(2))
    }

    fun findOxygenAndCO2(binaryStrings: List<String>): Pair<String, String> {
        var oxygen = ""
        var oxyStrings = binaryStrings
        var co2 = ""
        var co2Strings = binaryStrings
        for (i in binaryStrings.first().indices) {
            val b1 = mostCommonBitAt(oxyStrings, i)
            oxyStrings = oxyStrings.filter { it[i] == b1 }
            oxygen += b1
            val b2 = leastCommonBitAt(co2Strings, i)
            co2Strings = co2Strings.filter { it[i] == b2 }
            co2 += b2
        }
        return Pair(oxygen, co2)
    }

}

fun main() {
    println("Day 3")
    Day3().run()
}