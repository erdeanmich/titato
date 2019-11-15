package de.erdeanmich.titato


data class BoardPosition(
    val xPosition: Int,
    val yPosition: Int
) {
    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if (other?.javaClass != javaClass) return false

        other as BoardPosition

        return other.xPosition == xPosition && other.yPosition == yPosition
    }

    override fun hashCode(): Int {
        var result = xPosition
        result = 31 * result + yPosition
        return result
    }
}

