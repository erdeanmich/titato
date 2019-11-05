package de.erdeanmich.titato.player

import de.erdeanmich.titato.BoardPosition


class AIPlayer : Player() {
    override fun getNextMove(): BoardPosition {
        //TODO: calc best move
        return BoardPosition(0,0)
    }
}
