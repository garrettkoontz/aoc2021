package com.k00ntz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class Day16Test {

    val day16 = Day16()
    val literalInput = day16.parseToBinary("D2FE28")
    val operatorInput1 = day16.parseToBinary("38006F45291200")
    val operatorInput2 = day16.parseToBinary("EE00D40C823060")
    val sample1 = day16.parseToBinary("8A004A801A8002F478")
    val sample2 = day16.parseToBinary("620080001611562C8802118E34")
    val sample3 = day16.parseToBinary("C0015000016115A2E0802F182340")
    val sample4 = day16.parseToBinary("A0016C880162017C3686B18A3D4780")

    @Test
    fun parseOperator() {
        val ops = day16.parsePacket(operatorInput1)
        assertEquals(1, ops.version)
        val ops2 = day16.parsePacket(operatorInput2)
        assertEquals(7, ops2.version)
    }

    @Test
    fun parseLiteralValue() {
        val expected = Day16.LiteralValue(6, 2021)
        val actual = day16.parsePacket(literalInput)
        assertTrue(actual is Day16.LiteralValue)
        actual as Day16.LiteralValue
        assertEquals(expected.value, actual.value)
        assertEquals(expected.version, actual.version)

    }

    @Test
    fun part1() {
        assertEquals(16, day16.part1(sample1))
        assertEquals(12, day16.part1(sample2))
        assertEquals(23, day16.part1(sample3))
        assertEquals(31, day16.part1(sample4))
    }

    @Test
    fun part2() {
        assertEquals(3L, day16.part2(day16.parseToBinary("C200B40A82")))
        assertEquals(54L, day16.part2(day16.parseToBinary("04005AC33890")))
        assertEquals(7L, day16.part2(day16.parseToBinary("880086C3E88112")))
        assertEquals(9L, day16.part2(day16.parseToBinary("CE00C43D881120")))
        assertEquals(1L, day16.part2(day16.parseToBinary("D8005AC2A8F0")))
        assertEquals(0L, day16.part2(day16.parseToBinary("F600BC2D8F")))
        assertEquals(0L, day16.part2(day16.parseToBinary("9C005AC2F8F0")))
        assertEquals(1, day16.part2(day16.parseToBinary("9C0141080250320F1802104A08")))
    }
}