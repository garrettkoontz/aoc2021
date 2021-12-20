package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseLine
import java.util.*

class Day16 : Day<String, Int, Long> {
    val charMap = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111"
    )

    fun parseToBinary(str: String): String =
        str.toCharArray().joinToString(separator = "") { charMap[it]!! }

    override fun run() {
        val inputFile =
            parseLine("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }
        val input = parseToBinary(inputFile)
        measureAndPrintTime { print(part1(input)) }
        measureAndPrintTime { print(part2(input)) }
    }

    sealed class Type(val version: Int, val typeId: Int) {
        open fun calcVersion() = version
        abstract fun evaluate(): Long
    }

    class LiteralValue(version: Int, val value: Long) : Type(version, 4) {
        override fun evaluate(): Long = value

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is LiteralValue) return false

            if (value != other.value) return false
            if (version != other.version) return false

            return true
        }

        override fun hashCode(): Int {
            return value.toInt() * 31 + version
        }
    }

    abstract class Operator(version: Int, typeId: Int, val packets: List<Type>) : Type(version, typeId) {
        override fun calcVersion() = packets.sumOf { it.calcVersion() } + version
    }

    class Sum(version: Int, packets: List<Type>) : Operator(version, 0, packets) {
        override fun evaluate(): Long =
            packets.sumOf { it.evaluate() }
    }

    class Product(version: Int, packets: List<Type>) : Operator(version, 1, packets) {
        override fun evaluate(): Long =
            packets.map { it.evaluate() }.fold(1L) { acc: Long, l: Long -> acc * l }
    }

    class Minimum(version: Int, packets: List<Type>) : Operator(version, 2, packets) {
        override fun evaluate(): Long =
            packets.minOf { it.evaluate() }
    }

    class Maximum(version: Int, packets: List<Type>) : Operator(version, 3, packets) {
        override fun evaluate(): Long =
            packets.maxOf { it.evaluate() }
    }

    class GT(version: Int, packets: List<Type>) : Operator(version, 5, packets) {
        override fun evaluate(): Long =
            if (packets.first().evaluate() > packets[1].evaluate()) 1 else 0
    }

    class LT(version: Int, packets: List<Type>) : Operator(version, 6, packets) {
        override fun evaluate(): Long = if (packets.first().evaluate() < packets[1].evaluate()) 1 else 0
    }

    class EQ(version: Int, packets: List<Type>) : Operator(version, 7, packets) {
        override fun evaluate(): Long = if (packets.first().evaluate() == packets[1].evaluate()) 1 else 0
    }

    fun createOperator(version: Int, typeId: Int, packets: List<Type>): Operator =
        when (typeId) {
            0 -> Sum(version, packets)
            1 -> Product(version, packets)
            2 -> Minimum(version, packets)
            3 -> Maximum(version, packets)
            5 -> GT(version, packets)
            6 -> LT(version, packets)
            7 -> EQ(version, packets)
            else -> {
                throw RuntimeException("unknown type $typeId")
            }
        }

    fun parsePacket(str: String): Type {
        val version = str.substring(0, 3).toInt(2)
        val typeId = str.substring(3, 6).toInt(2)
        if (typeId == 4) {
            val (value, _) = parseLiteralValue(str.substring(6)) // add 6 to restId to get back to the point in this string.
            return LiteralValue(version, value)
        } else {
            val (value, _) = parseOperator(version, typeId, str.substring(6))
            return value
        }
    }

    fun parseLengthPackets(totalLength: Int, str: String): List<Type> {
        var idx = 0
        val result = mutableListOf<Type>()
        while (idx < totalLength) {
            val version = str.substring(idx, idx + 3).toInt(2)
            val typeId = str.substring(idx + 3, idx + 6).toInt(2)
            if (typeId == 4) {
                val (value, restIdx) = parseLiteralValue(str.substring(idx + 6)) // add 6 to restId to get back to the point in this string.
                result.add(LiteralValue(version, value))
                idx += restIdx + 6
            } else {
                val (value, restIdx) = parseOperator(version, typeId, str.substring(idx + 6))
                result.add(value)
                idx += restIdx + 6
            }
        }
        return result
    }

    fun parseNumberOfPackets(number: Int, str: String): Pair<List<Type>, Int> {
        var idx = 0
        val result = mutableListOf<Type>()
        while (result.size < number) {
            val version = str.substring(idx, idx + 3).toInt(2)
            val typeId = str.substring(idx + 3, idx + 6).toInt(2)
            if (typeId == 4) {
                val (value, restIdx) = parseLiteralValue(str.substring(idx + 6)) // add 6 to restId to get back to the point in this string.
                result.add(LiteralValue(version, value))
                idx += restIdx + 6
            } else {
                val (value, restIdx) = parseOperator(version, typeId, str.substring(idx + 6))
                result.add(value)
                idx += restIdx + 6
            }
        }
        return Pair(result, idx)
    }

    fun parseOperator(version: Int, typeId: Int, str: String): Pair<Operator, Int> {
        when (str[0]) {
            '1' -> {
                val numberOfSubPackets = str.substring(1, 12).toInt(2)
                val (value, restIdx) = parseNumberOfPackets(numberOfSubPackets, str.substring(12))
                return Pair(createOperator(version, typeId, value), restIdx + 12)
            }
            '0' -> {
                val lengthInBits = str.substring(1, 16).toInt(2)
                return Pair(
                    createOperator(version, typeId, parseLengthPackets(lengthInBits, str.substring(16))),
                    16 + lengthInBits
                )
            }
            else -> {
                throw RuntimeException("not expecting ${str[6]} in length type id position")
            }
        }
    }

    fun parseLiteralValue(str: String): Pair<Long, Int> {
        var idx = 0
        val step = 5
        var stop = false
        var result = ""
        while (!stop) {
            val flag = str[idx]
            val subStr = str.substring(idx + 1, idx + step)
            if (flag == '0') stop = true
            result += subStr
            idx += step
        }
        return Pair(result.toLong(2), idx)
    }

    override fun part1(input: String): Int {
        val packet = parsePacket(input)
        return packet.calcVersion()
    }

    override fun part2(input: String): Long {
        val packet = parsePacket(input)
        return packet.evaluate()
    }

}

fun main() {
    println("Day 16")
    Day16().run()
}