package de.erdeanmich.titato.player

import de.erdeanmich.titato.BoardPosition
import de.erdeanmich.titato.board.PlayingBoard
import kotlin.random.Random


class AIPlayer(playingBoard: PlayingBoard, symbol: Char) : Player(playingBoard,symbol) {
    override fun makeNextMove() {
        //TODO: calc best move
        println("Computer makes its move!")
        var boardPosition : BoardPosition
        do {
            boardPosition = BoardPosition(Random.nextInt(3),Random.nextInt(3))
        } while (!playingBoard.canSymbolBePlacedOn(symbol, boardPosition))
        playingBoard.placeSymbolOnBoard(symbol,boardPosition)
    }
}

