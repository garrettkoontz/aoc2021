package com.k00ntz.aoc2021

import com.k00ntz.aoc.utils.Day
import com.k00ntz.aoc.utils.groupSeparatedByEmpty
import com.k00ntz.aoc.utils.measureAndPrintTime
import com.k00ntz.aoc.utils.parseFile
import java.util.*

class Day4 : Day<Day4.BingoInput, Int, Int> {

    data class BingoInput(
        val draws: List<Int>, val boards: List<Board>
    ) {
        private val numberMap: Map<Int, List<BingoSlot>> = boards.flatMap { it.flatten() }.groupBy { it.number }
        private val boardSize = boards.first().first().size

        companion object {
            fun make(input: List<List<String>>): BingoInput {
                val draws = input.first().first().split(",").map { it.toInt() }
                val boards = input.drop(1).map { boardInputList ->
                    boardInputList.map { boardInputLine ->
                        boardInputLine.trim().split("\\s+".toRegex()).map { spotNumber ->
                            BingoSlot(spotNumber.toInt(), false)
                        }
                    }
                }
                return BingoInput(draws, boards)
            }
        }

        private fun checkBoards(): Int =
            boards.indexOfFirst { checkBoard(it) }

        private fun anyRemaining(): Boolean =
            boards.any { !checkBoard(it) }

        private fun checkBoard(board: Board): Boolean {
            val size = board.size
            for (i in (0 until size)) {
                if (board[i].all { it.chosen } || board.all { it[i].chosen })
                    return true
            }
            return false
        }

        private fun drawNumber(i: Int) {
            val slots = numberMap[i]
            slots?.forEach { it.chosen = true }
        }

        fun runGame(): Pair<Board, Int> {
            val firstDraws = draws.take(boardSize)
            var restDraws: List<Int> = draws.drop(boardSize)
            firstDraws.forEach { drawNumber(it) }
            var draw = draws[boardSize]
            var check = checkBoards()
            while (check == -1) {
                draw = restDraws.first()
                restDraws = restDraws.drop(1)
                drawNumber(draw)
                check = checkBoards()
            }
            return Pair(boards[check], draw)
        }

        fun runGameToEnd(): Pair<Board, Int> {
            val firstDraws = draws.take(boardSize)
            var restDraws: List<Int> = draws.drop(boardSize)
            firstDraws.forEach { drawNumber(it) }
            var draw = draws[boardSize]
            lateinit var remainingBoard: Board
            while (anyRemaining()) {
                remainingBoard = boards.first { !checkBoard(it) }
                draw = restDraws.first()
                restDraws = restDraws.drop(1)
                drawNumber(draw)
            }
            return Pair(remainingBoard, draw)
        }
    }

    private fun Board.score(lastDraw: Int): Int =
        this.flatten().filterNot { it.chosen }.sumOf { it.number } * lastDraw

    data class BingoSlot(
        val number: Int,
        var chosen: Boolean
    )

    override fun run() {
        val inputFile =
            parseFile("${this.javaClass.simpleName.lowercase(Locale.getDefault())}.txt") { it }
        val input = BingoInput.make(groupSeparatedByEmpty(inputFile))
        measureAndPrintTime { print(part1(input)) }
        measureAndPrintTime { print(part2(input)) }
    }


    override fun part1(input: BingoInput): Int {
        val (board, lastDraw) = input.runGame()
        return board.score(lastDraw)
    }

    override fun part2(input: BingoInput): Int {
        val (board, lastDraw) = input.runGameToEnd()
        return board.score(lastDraw)
    }

}

typealias Board = List<List<Day4.BingoSlot>>

fun main() {
    println("Day 4")
    Day4().run()
}