package com.k00ntz.aoc2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day18Test {

    val day18 = Day18()
    val pairs = Day18.Pairs

    val input1 = "[1,2]"
    val input2 = "[[3,4],5]"
    val answer12 = "[[1,2],[[3,4],5]]"

    val additionInputString = """[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
[7,[5,[[3,8],[1,4]]]]
[[2,[2,2]],[8,[8,1]]]
[2,9]
[1,[[[9,3],9],[[9,0],[0,7]]]]
[[[5,[7,4]],7],1]
[[[[4,2],2],6],[8,7]]"""

    val part1InputString = """[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
[[[5,[2,8]],4],[5,[[9,9],0]]]
[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
[[[[5,4],[7,7]],8],[[8,3],8]]
[[9,3],[[9,9],[6,[4,9]]]]
[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"""

    val part1Input = part1InputString.split("\n")

    @Test
    fun parsePairs1() {
        assertEquals(listOf(Pair(1, 1), Pair(2, 1)), pairs.parsePair(input1))
        assertEquals(listOf(Pair(3, 2), Pair(4, 2), Pair(5, 1)), pairs.parsePair(input2))
        assertEquals(
            listOf(Pair(1, 2), Pair(2, 2), Pair(3, 3), Pair(4, 3), Pair(5, 2)),
            pairs.treePlus(pairs.parsePair(input1), pairs.parsePair(input2))
        )
    }

    fun pairsReduceTest(output: String, input: String) {
        val m = pairs.parsePair(input).toMutableList()
        pairs.pairReduce(m)
        assertEquals(pairs.parsePair(output), m)
    }

    @Test
    fun pairsReduce1() {
        pairsReduceTest("[[[[0,9],2],3],4]", "[[[[[9,8],1],2],3],4]")
        pairsReduceTest("[7,[6,[5,[7,0]]]]", "[7,[6,[5,[4,[3,2]]]]]")
        pairsReduceTest("[[6,[5,[7,0]]],3]", "[[6,[5,[4,[3,2]]]],1]")
        pairsReduceTest("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
    }

    fun pairsReduce(strs: List<String>): List<Pair<Int, Int>> =
        pairs.parsePairs(strs)
            .reduce { acc: List<Pair<Int, Int>>, pair: List<Pair<Int, Int>> ->
                pairs.treePlus(acc, pair)
            }

    fun testPairReduce(expectedOutput: String, input: List<String>) {
        assertEquals(pairs.parsePair(expectedOutput), pairsReduce(input))
    }

    @Test
    fun pairsPlus() {
        testPairReduce(
            "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", listOf(
                "[[[[4,3],4],4],[7,[[8,4],9]]]",
                "[1,1]"
            )
        )
        testPairReduce(
            "[[[[1,1],[2,2]],[3,3]],[4,4]]", listOf(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]",
            )
        )
        testPairReduce(
            "[[[[3,0],[5,3]],[4,4]],[5,5]]", listOf(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]",
                "[5,5]"
            )
        )
        testPairReduce(
            "[[[[5,0],[7,4]],[5,5]],[6,6]]", listOf(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]",
                "[5,5]",
                "[6,6]"
            )
        )
        testPairReduce("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]",
        """[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
[7,[5,[[3,8],[1,4]]]]
[[2,[2,2]],[8,[8,1]]]
[2,9]
[1,[[[9,3],9],[[9,0],[0,7]]]]
[[[5,[7,4]],7],1]
[[[[4,2],2],6],[8,7]]""".split("\n")
            )
    }

    fun magnitude(str: String): Int =
        pairs.magnitude(pairs.parsePair(str))

    @Test
    fun pairMagnitude(){
//        assertEquals(143, pairs.magnitude(listOf(Pair(1, 2), Pair(2, 2), Pair(3, 3), Pair(4, 3), Pair(5, 2))))
        assertEquals(1384 , magnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"))
        assertEquals(445, magnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]"))
        assertEquals(791, magnitude("[[[[3,0],[5,3]],[4,4]],[5,5]]"))
        assertEquals(1137, magnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]"))
        assertEquals(3488, magnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"))
    }

    @Test
    fun part1Pair(){
        val i = """[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
[[[5,[2,8]],4],[5,[[9,9],0]]]
[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
[[[[5,4],[7,7]],8],[[8,3],8]]
[[9,3],[[9,9],[6,[4,9]]]]
[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]""".split("\n")
        val r = pairsReduce(i)
        assertEquals( pairs.parsePair("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]"),r)
        assertEquals(4140, pairs.magnitude(r))
    }

    @Test
    fun parse1() {
        val rn1 = Day18.RegularNumber(1)
        val rn2 = Day18.RegularNumber(2)
        rn1.next = rn2
        rn2.previous = rn1
        val input = Day18.SnailNumberParser().parse(input1)
        assertEquals(Day18.SnailPair(rn1, rn2), input)
    }

    @Test
    fun parse2() {
        val rn3 = Day18.RegularNumber(3)
        val rn4 = Day18.RegularNumber(4)
        val rn5 = Day18.RegularNumber(5)
        rn3.next = rn4
        rn4.next = rn5
        rn4.previous = rn3
        rn5.previous = rn4
        val input = Day18.SnailNumberParser().parse(input2)
        assertEquals(Day18.SnailPair(Day18.SnailPair(rn3, rn4), rn5), input)
    }

    @Test
    fun plus() {
        val input1 = Day18.SnailNumberParser().parse(input1)
        val input2 = Day18.SnailNumberParser().parse(input2)
        val rn1 = Day18.RegularNumber(1)
        val rn2 = Day18.RegularNumber(2)
        val rn3 = Day18.RegularNumber(3)
        val rn4 = Day18.RegularNumber(4)
        val rn5 = Day18.RegularNumber(5)
        rn2.previous = rn1
        rn3.previous = rn2
        rn4.previous = rn3
        rn5.previous = rn4
        rn1.next = rn2
        rn2.next = rn3
        rn3.next = rn4
        rn4.next = rn5
        val ans = Day18.SnailPair(Day18.SnailPair(rn1, rn2), Day18.SnailPair(Day18.SnailPair(rn3, rn4), rn5))
        assertEquals(ans, input1 + input2)
    }

    @Test
    fun reduce0() {
        val r = (Day18.SnailNumberParser().parse("[[[[[9,8],1],2],3],4]") as Day18.SnailPair)
        r.reduce()
        assertEquals(Day18.SnailNumberParser().parse("[[[[0,9],2],3],4]"), r)
    }

    @Test
    fun reduce1() {
        assertEquals(
            Day18.SnailNumberParser().parse("[[[[1,1],[2,2]],[3,3]],[4,4]]"), reduce(
                """[1,1]
[2,2]
[3,3]
[4,4]""".split("\n")
            )
        )
    }

    @Test
    fun reduce2() {
        assertEquals(
            Day18.SnailNumberParser().parse("[[[[3,0],[5,3]],[4,4]],[5,5]]"), reduce(
                """[1,1]
[2,2]
[3,3]
[4,4]
[5,5]""".split("\n")
            )
        )
    }

    @Test
    fun reduce3() {
        assertEquals(
            parse("[[[[5,0],[7,4]],[5,5]],[6,6]]"), reduce(
                """[1,1]
[2,2]
[3,3]
[4,4]
[5,5]
[6,6]""".split("\n")
            )
        )
    }

    @Test
    fun reduce4() {
        assertEquals(
            parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"),
            reduce(additionInputString.split("\n"))
        )
    }

    fun parse(str: String): Day18.SnailNumber {
        return Day18.SnailNumberParser().parse(str)
    }

    @Test
    fun plus1() {
        val l = "[[[[4,3],4],4],[7,[[8,4],9]]]"
        val r = "[1,1]"
        val result = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
        assertEquals(parse(result), parse(l).plus(parse(r)))
    }

    fun reduce(str: List<String>): Day18.SnailNumber =
        day18.parse(str).reduce { acc, snailNumber -> acc + snailNumber }

    @Test
    fun part1() {
        assertEquals(
            Day18.SnailNumberParser().parse("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]"),
            reduce(part1Input)
        )
        assertEquals(4140, day18.part1(part1Input))
    }
}