package de.erdeanmich.titato.player

import de.erdeanmich.titato.board.PlayingBoard


abstract class Player(protected val playingBoard: PlayingBoard, val symbol: Char) {
    abstract fun makeNextMove()
}
