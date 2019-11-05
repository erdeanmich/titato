package de.erdeanmich.titato.player

import de.erdeanmich.titato.BoardPosition


abstract class Player {
    abstract fun getNextMove() : BoardPosition
}
