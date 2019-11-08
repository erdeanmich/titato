package de.erdeanmich.titato.board

import org.junit.Test


class VariousTest {

    @Test
    fun testDistinct() {
        assert(listOf('X','X','X').distinct().size == 1)
    }
}
