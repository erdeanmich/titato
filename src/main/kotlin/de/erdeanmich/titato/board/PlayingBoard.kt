package de.erdeanmich.titato.board

import de.erdeanmich.titato.BoardPosition


class PlayingBoard(private val allowedPlayerSymbols: List<Char>) {
    private val board = getEmptyPlayingBoard()

    private fun getEmptyPlayingBoard(): Array<Array<Char>> {
        return Array(MAX_WIDTH) {
            Array(MAX_HEIGHT) {
                ' '
            }
        }
    }



    fun containsThreeSymbolsInARow() : Boolean {
        return containsAHorizontalRow()
                || containsAVerticalRow()
                || containsADiagonalRow()
    }

    private fun containsADiagonalRow() : Boolean {
        return upperLeftToLowerRightContainsTheSameSymbols()
                || lowerLeftToUpperRightContainsTheSameSymbols()
    }

    private fun upperLeftToLowerRightContainsTheSameSymbols(): Boolean {
        return rowConsistsOfTheSameSymbol(listOf(board[0][0], board[1][1], board[2][2])) //TODO: make dynamic
    }

    private fun lowerLeftToUpperRightContainsTheSameSymbols(): Boolean {
        return  rowConsistsOfTheSameSymbol(listOf(board[0][2], board[1][1], board[2][0])) // TODO: make dynamic
    }

    private fun containsAHorizontalRow() : Boolean {
        return (0 until MAX_HEIGHT).any { y -> rowConsistsOfTheSameSymbol(getSymbolsAtYPosition(y)) }
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
        return board.all { column -> column.all { it != ' ' } }
    }

    fun printToStOut() {
        (0 until MAX_HEIGHT).forEach { y ->
            (0 until MAX_WIDTH).forEach { x ->
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

    private fun getPositionsMarkedWithSymbol(symbol: Char): List<BoardPosition> {
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
        return board[boardPosition.xPosition][boardPosition.yPosition] == ' '
    }

    private fun boardPositionIsAllowed(boardPosition: BoardPosition) : Boolean {
        return boardPosition.xPosition in 0 until MAX_WIDTH
                && boardPosition.yPosition in 0 until MAX_HEIGHT
    }

    private fun symbolIsAllowed(symbol: Char): Boolean {
        return allowedPlayerSymbols.contains(symbol)
    }

    fun yPositionIsValid(row: Int) : Boolean {
        return row in 0 until MAX_HEIGHT
    }

    fun xPositionIsValid(column: Int) : Boolean {
        return column in 0 until MAX_WIDTH
    }

    companion object {
        private const val MAX_WIDTH = 3
        private const val MAX_HEIGHT = 3
        private const val FREE_BOARD_POSITION_SYMBOL = ' '
    }
}

class InvalidPositionException(s: String) : Throwable(s)
