package de.erdeanmich.titato

import de.erdeanmich.titato.board.PlayingBoard
import de.erdeanmich.titato.player.AIPlayer
import de.erdeanmich.titato.player.HumanPlayer
import de.erdeanmich.titato.player.Player


class TicTacToe {
    private var playingBoard = PlayingBoard(listOf(HUMAN_PLAYER_SYMBOL, AI_PLAYER_SYMBOL))
    private val humanPlayer = HumanPlayer(playingBoard, HUMAN_PLAYER_SYMBOL)
    private val aiPlayer = AIPlayer(playingBoard, AI_PLAYER_SYMBOL)
    private var activePlayer : Player? = null


    init {
        println("Welcome to TicTacToe!")
        run()
    }

    private fun run() {
        activePlayer = humanPlayer
        while (!gameIsOver() ) {
            toggleActivePlayer()
            playingBoard.printToStOut()
            activePlayer?.makeNextMove()
        }

        if(playingBoard.containsThreeSymbolsInARow()) {
            println("Player ${activePlayer?.symbol} won.")
        } else {
            println("REMIS!")
        }
    }

    private fun gameIsOver() : Boolean {
        return playingBoard.isFull() || playingBoard.containsThreeSymbolsInARow()
    }

    private fun toggleActivePlayer() {
        activePlayer = if(activePlayer == humanPlayer) {
            aiPlayer
        } else {
            humanPlayer
        }
    }

    companion object {
        private const val HUMAN_PLAYER_SYMBOL = 'X'
        private const val AI_PLAYER_SYMBOL = 'O'
    }
}
