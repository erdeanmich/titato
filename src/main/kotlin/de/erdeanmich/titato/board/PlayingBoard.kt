package de.erdeanmich.titato.board

import de.erdeanmich.titato.BoardPosition


class PlayingBoard(private val allowedPlayerSymbols: List<Char>) {
    private var board = getEmptyPlayingBoard()

    private fun getEmptyPlayingBoard(): Array<Array<Char>> {
        return Array(MAX_BOARD_LENGTH) {
            Array(MAX_BOARD_LENGTH) {
                FREE_BOARD_POSITION_SYMBOL
            }
        }
    }

    fun containsThreeSymbolsInARow() : Boolean {
        return containsAHorizontalRow()
                || containsAVerticalRow()
                || containsADiagonalRow()
    }

    fun erasePosition(boardPosition: BoardPosition) {
        board[boardPosition.xPosition][boardPosition.yPosition] = FREE_BOARD_POSITION_SYMBOL
    }

    private fun containsADiagonalRow() : Boolean {
        return descendingDiagonalRowContainsTheSameSymbols()
                || ascendingDiagonalRowContainsTheSameSymbols()
    }

    private fun descendingDiagonalRowContainsTheSameSymbols(): Boolean {
        return rowConsistsOfTheSameSymbol(getSymbolsOfDescendingDiagonalRow())
    }

    private fun ascendingDiagonalRowContainsTheSameSymbols(): Boolean {
        return  rowConsistsOfTheSameSymbol(getSymbolsOfAscendingDiagonalRow())
    }

    private fun getSymbolsOfAscendingDiagonalRow(): List<Char> {
        val symbols = ArrayList<Char>()
        (0 until MAX_BOARD_LENGTH).forEach { x ->
            (0 until MAX_BOARD_LENGTH).reversed().forEach { y ->
                symbols.add(board[x][y])
            }
        }
        return symbols
    }

    private fun getSymbolsOfDescendingDiagonalRow(): List<Char> {
        return (0 until MAX_BOARD_LENGTH).map { board[it][it]}
    }

    private fun containsAHorizontalRow() : Boolean {
        return (0 until MAX_BOARD_LENGTH).any { y -> rowConsistsOfTheSameSymbol(getSymbolsAtYPosition(y)) }
    }

    private fun getSymbolsAtYPosition(yPosition: Int): List<Char> {
        return listOf(board[0][yPosition], board[1][yPosition], board[2][yPosition])
    }

    private fun containsAVerticalRow() : Boolean {
        return board.any { columns -> rowConsistsOfTheSameSymbol(columns.toList()) }
    }

    private fun rowConsistsOfTheSameSymbol(row: List<Char>) : Boolean {
        return row.none { isFreeSymbol(it) }
                && row.distinct().size == 1
    }

    private fun isFreeSymbol(symbol: Char) : Boolean {
        return symbol == FREE_BOARD_POSITION_SYMBOL
    }

    fun isFull() : Boolean {
        return board.all { column -> column.all { it != FREE_BOARD_POSITION_SYMBOL } }
    }

    fun printToStOut() {
        (0 until MAX_BOARD_LENGTH).forEach { y ->
            (0 until MAX_BOARD_LENGTH).forEach { x ->
                print("|\t${board[x][y]}\t")
            }
            println("|")
        }
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

    fun getFreePositions(): List<BoardPosition> {
        return getPositionsMarkedWithSymbol(FREE_BOARD_POSITION_SYMBOL)
    }

    fun getPositionsMarkedWithSymbol(symbol: Char): List<BoardPosition> {
        return ArrayList<BoardPosition>().apply {
            board.forEachIndexed{xPosition, column ->
                column.forEachIndexed { yPosition, boardSymbol ->
                    if (boardSymbol == symbol)
                        add(BoardPosition(xPosition,yPosition))
                }
            }
        }
    }

    private fun boardPositionIsFree(boardPosition: BoardPosition): Boolean {
        return board[boardPosition.xPosition][boardPosition.yPosition] == FREE_BOARD_POSITION_SYMBOL
    }

    private fun boardPositionIsAllowed(boardPosition: BoardPosition) : Boolean {
        return boardPosition.xPosition in 0 until MAX_BOARD_LENGTH
                && boardPosition.yPosition in 0 until MAX_BOARD_LENGTH
    }

    private fun symbolIsAllowed(symbol: Char): Boolean {
        return allowedPlayerSymbols.contains(symbol)
    }

    fun yPositionIsValid(row: Int) : Boolean {
        return row in 0 until MAX_BOARD_LENGTH
    }

    fun xPositionIsValid(column: Int) : Boolean {
        return column in 0 until MAX_BOARD_LENGTH
    }

    fun getMaxPositionValue(): Int {
        return MAX_BOARD_LENGTH - 1
    }

    companion object {
        private const val MAX_BOARD_LENGTH = 3
        private const val FREE_BOARD_POSITION_SYMBOL = ' '
    }
}

class InvalidPositionException(s: String) : Throwable(s)
