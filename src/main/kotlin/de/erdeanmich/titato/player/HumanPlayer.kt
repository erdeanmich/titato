package de.erdeanmich.titato.player

import de.erdeanmich.titato.BoardPosition


class HumanPlayer : Player() {
    override fun getNextMove(): BoardPosition {
        //TODO: read move from console in
        return BoardPosition(0,0)
    }
}
