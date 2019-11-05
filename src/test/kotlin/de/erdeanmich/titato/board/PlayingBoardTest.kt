package de.erdeanmich.titato.board

import de.erdeanmich.titato.BoardPosition
import org.mockito.internal.util.reflection.FieldSetter
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class PlayingBoardTest {

    @Test
    fun testIsFull() {
        val playingBoard = PlayingBoard()
        assertFalse(playingBoard.isFull())

        FieldSetter.setField(playingBoard,playingBoard.javaClass.getDeclaredField("board"), Array(3) {
            Array(3) {
                'X'
            }
        })
        assertTrue(playingBoard.isFull())
    }

    @Test
    fun testPlaceSymbolOnBoard() {
        val playingBoard = PlayingBoard()
        playingBoard.placeSymbolOnBoard('X', BoardPosition(1,1))

        assertFailsWith(InvalidPositionException::class) {
            playingBoard.placeSymbolOnBoard('X',BoardPosition(10,10))
        }
    }

    @Test
    fun testCanSymbolBePlacedOn() {
        val playingBoard = PlayingBoard()
        assertTrue(playingBoard.canSymbolBePlacedOn('X', BoardPosition(1,1)))
        assertFalse(playingBoard.canSymbolBePlacedOn('X',BoardPosition(1,10)))
        assertFalse(playingBoard.canSymbolBePlacedOn('X',BoardPosition(10,1)))
        assertFalse(playingBoard.canSymbolBePlacedOn('Ä',BoardPosition(1,1)))
    }
}
