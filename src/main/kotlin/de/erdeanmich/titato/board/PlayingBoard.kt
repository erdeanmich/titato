package de.erdeanmich.titato.board

import de.erdeanmich.titato.BoardPosition


class PlayingBoard {
    private val board = getEmptyPlayingBoard()

    private fun getEmptyPlayingBoard(): Array<Array<Char>> {
        return Array(MAX_WIDTH) {
            Array(MAX_HEIGHT) {
                ' '
            }
        }
    }

    fun containsThreeSymbolsInARow() : Boolean {
        return true
    }

    fun isFull() : Boolean {
        return board.all { column -> column.all { it != ' ' } }
    }

    fun placeSymbolOnBoard(symbol: Char, boardPosition: BoardPosition) {
        try {
            board[boardPosition.xPosition][boardPosition.yPosition] = symbol
        } catch (exception: ArrayIndexOutOfBoundsException) {
            throw InvalidPositionException("the position $boardPosition is invalid")
        }
    }

    fun canSymbolBePlacedOn(symbol: Char, boardPosition: BoardPosition): Boolean {
        return symbolIsAllowed(symbol)
                && boardPositionIsAllowed(boardPosition)
                && boardPositionIsFree(boardPosition)
    }

    private fun boardPositionIsFree(boardPosition: BoardPosition): Boolean {
        return board[boardPosition.xPosition][boardPosition.yPosition] == ' '
    }

    private fun boardPositionIsAllowed(boardPosition: BoardPosition) : Boolean {
        return boardPosition.xPosition in 0 until MAX_WIDTH
                && boardPosition.yPosition in 0 until MAX_HEIGHT
    }

    private fun symbolIsAllowed(symbol: Char): Boolean {
        return ALLOWED_PLAYER_SYMBOLS.contains(symbol)
    }

    companion object {
        private const val MAX_WIDTH = 3
        private const val MAX_HEIGHT = 3
        private val ALLOWED_PLAYER_SYMBOLS = listOf('X','O')
    }


}

class InvalidPositionException(s: String) : Throwable(s)
