package de.erdeanmich.titato.player

import de.erdeanmich.titato.BoardPosition
import de.erdeanmich.titato.board.PlayingBoard
import kotlin.random.Random


class AIPlayer(playingBoard: PlayingBoard, symbol: Char, private val humanPlayerSymbol: Char) : Player(playingBoard,symbol) {

    private val allSymbols = listOf(symbol,humanPlayerSymbol)

    override fun makeNextMove() {
        println("Computer makes it's move!")
        playingBoard.placeSymbolOnBoard(symbol,chooseWisestMove())

    }

    private fun chooseWisestMove() : BoardPosition{
        val boardPosition : BoardPosition
        val possibleWinningMoveOfAi = getWinningMoveOfPlayer(symbol)
        val possibleWinningMoveOfHuman = getWinningMoveOfPlayer(humanPlayerSymbol)

        boardPosition = when {
            middlePositionIsAvailable() -> MIDDLE_BOARD_POSITION
            possibleWinningMoveOfAi != null -> possibleWinningMoveOfAi
            possibleWinningMoveOfHuman != null -> possibleWinningMoveOfHuman

            else -> chooseRandomPosition()
        }

        return boardPosition
    }

    private fun chooseRandomPosition(): BoardPosition {
        var boardPosition: BoardPosition
        do {
            boardPosition = BoardPosition(Random.nextInt(playingBoard.getMaxPositionValue()), playingBoard.getMaxPositionValue())
        } while (!playingBoard.canSymbolBePlacedOn(symbol, boardPosition))
        return boardPosition
    }

    private fun getWinningMoveOfPlayer(playerSymbol: Char): BoardPosition? {
        val opponentMoves = playingBoard.getPositionsMarkedWithSymbol(playerSymbol)
        val freePositions  = playingBoard.getFreePositions()
        val fakeBoard = PlayingBoard(allSymbols)

        var winningMove: BoardPosition? = null
        opponentMoves.forEach { fakeBoard.placeSymbolOnBoard(playerSymbol,it) }

        freePositions.forEach { position ->
            playingBoard.placeSymbolOnBoard(playerSymbol,position)
            if(playingBoard.containsThreeSymbolsInARow()) {
                winningMove = position
            }
            playingBoard.erasePosition(position)
        }
        return winningMove
    }

    private fun middlePositionIsAvailable(): Boolean {
        return playingBoard.canSymbolBePlacedOn(symbol, MIDDLE_BOARD_POSITION)
    }

    companion object {
        private val MIDDLE_BOARD_POSITION = BoardPosition(1,1)
    }
}
