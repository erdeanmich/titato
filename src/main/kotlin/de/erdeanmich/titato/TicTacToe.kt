package de.erdeanmich.titato

import de.erdeanmich.titato.board.PlayingBoard
import de.erdeanmich.titato.player.AIPlayer
import de.erdeanmich.titato.player.HumanPlayer
import de.erdeanmich.titato.player.Player
import kotlin.random.Random


class TicTacToe {
    private var playingBoard = PlayingBoard(listOf(HUMAN_PLAYER_SYMBOL, AI_PLAYER_SYMBOL))
    private val humanPlayer = HumanPlayer(playingBoard, HUMAN_PLAYER_SYMBOL)
    private val aiPlayer = AIPlayer(playingBoard, AI_PLAYER_SYMBOL, HUMAN_PLAYER_SYMBOL)
    private var activePlayer : Player? = null


    init {
        run()
    }

    private fun printStartMessage() {
        println("Welcome to TicTacToe!")
        println("You are Player ${humanPlayer.symbol}.")
        println("Try to place three symbols in a row on the grid to win the game.")
        println("Prevent that your opponent is doing the same.")
        println("A random player begins...")
    }

    private fun run() {
        printStartMessage()
        pickAStartingPlayer()
        runMainGameLoop()
        printGameEndMessage()
    }

    private fun runMainGameLoop() {
        while (!gameIsOver()) {
            toggleActivePlayer()
            playingBoard.printToStOut()
            activePlayer?.makeNextMove()
        }
    }

    private fun pickAStartingPlayer() {
        activePlayer = pickRandomPlayer()
    }

    private fun printGameEndMessage() {
        println("The final state of the game:")
        playingBoard.printToStOut()
        if (playingBoard.containsThreeSymbolsInARow()) {
            println("Player ${activePlayer?.symbol} won.")
        } else {
            println("The game ended in draw.")
        }
        println("Game ended!")
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

        println("It's the turn of Player ${activePlayer?.symbol}.")
    }

    private fun pickRandomPlayer(): Player {
        return if(Random.nextBoolean()) {
            humanPlayer
        } else {
            aiPlayer
        }
    }

    companion object {
        const val HUMAN_PLAYER_SYMBOL = '☓'
        private const val AI_PLAYER_SYMBOL = '◯'
    }
}
