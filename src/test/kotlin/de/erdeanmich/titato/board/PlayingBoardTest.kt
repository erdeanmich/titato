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
        val playingBoard = PlayingBoard(listOf('X', 'O'))
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
        val playingBoard = PlayingBoard(listOf('X','O'))
        playingBoard.placeSymbolOnBoard('X', BoardPosition(1,1))

        assertFailsWith(InvalidPositionException::class) {
            playingBoard.placeSymbolOnBoard('X',BoardPosition(10,10))
        }
    }

    @Test
    fun testCanSymbolBePlacedOn() {
        val playingBoard = PlayingBoard(listOf('X','O'))
        assertTrue(playingBoard.canSymbolBePlacedOn('X', BoardPosition(1,1)))
        assertFalse(playingBoard.canSymbolBePlacedOn('X',BoardPosition(1,10)))
        assertFalse(playingBoard.canSymbolBePlacedOn('X',BoardPosition(10,1)))
        assertFalse(playingBoard.canSymbolBePlacedOn('Ä',BoardPosition(1,1)))
    }

    @Test
    fun testContainsThreeSymbolsInARow() {
        val playingBoard = PlayingBoard(listOf('X', 'O'))
        assertFalse(playingBoard.containsThreeSymbolsInARow())

        val withVerticalRow = listOf(
            listOf('O','X','O').toTypedArray(),
            listOf('O','O','O').toTypedArray(),
            listOf(' ', ' ', ' ').toTypedArray()
        ).toTypedArray()

        injectArrayIntoBoard(playingBoard, withVerticalRow)
        assertTrue(playingBoard.containsThreeSymbolsInARow())

        val withDiagonalRow = listOf(
            listOf('O','X','O').toTypedArray(),
            listOf('O','O','X').toTypedArray(),
            listOf(' ', ' ', 'O').toTypedArray()
        ).toTypedArray()

        injectArrayIntoBoard(playingBoard,withDiagonalRow)
        assertTrue(playingBoard.containsThreeSymbolsInARow())

        val withOtherDiagonalRow = listOf(
            listOf('X','X','O').toTypedArray(),
            listOf('O','O','X').toTypedArray(),
            listOf('O', ' ', 'X').toTypedArray()
        ).toTypedArray()

        injectArrayIntoBoard(playingBoard,withOtherDiagonalRow)
        assertTrue(playingBoard.containsThreeSymbolsInARow())

        val withHorizontalRow = listOf(
            listOf('O','X','O').toTypedArray(),
            listOf('O','O','X').toTypedArray(),
            listOf('O', ' ', 'X').toTypedArray()
        ).toTypedArray()
        injectArrayIntoBoard(playingBoard, withHorizontalRow)
        assertTrue(playingBoard.containsThreeSymbolsInARow())

        val withNoRow = listOf(
            listOf('O','X','O').toTypedArray(),
            listOf('O','O','X').toTypedArray(),
            listOf('X', ' ', 'X').toTypedArray()
        ).toTypedArray()
        injectArrayIntoBoard(playingBoard, withNoRow)
        assertFalse(playingBoard.containsThreeSymbolsInARow())

    }

    private fun injectArrayIntoBoard(playingBoard: PlayingBoard, newBoard: Array<Array<Char>>) {
        FieldSetter.setField(playingBoard,playingBoard.javaClass.getDeclaredField("board"), newBoard)
    }
}
