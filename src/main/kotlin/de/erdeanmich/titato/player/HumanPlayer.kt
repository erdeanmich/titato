package de.erdeanmich.titato.player

import de.erdeanmich.titato.BoardPosition
import de.erdeanmich.titato.board.PlayingBoard


class HumanPlayer(playingBoard: PlayingBoard, symbol: Char) : Player(playingBoard, symbol) {
    override fun makeNextMove() {
        playingBoard.placeSymbolOnBoard(symbol,getMoveFromInput())
    }

    private fun getMoveFromInput(): BoardPosition {
        println("Please choose the row and the column where you want to place your symbol.")

        var boardPosition: BoardPosition?
        do {
            val y = getYPositionByInput()
            val x = getXPositionByInput()
            boardPosition = BoardPosition(x,y)
        } while (!playingBoard.canSymbolBePlacedOn(symbol,boardPosition!!))

        return boardPosition
    }

    private fun getYPositionByInput(): Int {
        var yInput: Int?
        do {
            println("Please enter the row:")
            yInput = readNumberMinusOneFromStdIn()
        } while (!yPositionIsValid(yInput))

        return yInput!!
    }

    private fun getXPositionByInput(): Int {
        var xInput: Int?
        do {
            println("Please enter the column:")
            xInput = readNumberMinusOneFromStdIn()
        } while (!xPositionIsValid(xInput))

        return xInput!!
    }

    private fun readNumberMinusOneFromStdIn() = readLine()?.toIntOrNull()?.minus(1)

    private fun yPositionIsValid(yPosition : Int?) : Boolean {
        return yPosition != null && playingBoard.yPositionIsValid(yPosition)
    }

    private fun xPositionIsValid(xPosition: Int?) : Boolean {
        return xPosition != null && playingBoard.xPositionIsValid(xPosition)
    }

    companion object {
        private const val PLAYER_SYMBOL = 'X'
    }
}
