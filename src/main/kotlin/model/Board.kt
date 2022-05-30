package model

enum class Direction { UP, DOWN, RIGHT, LEFT, STATIC }

interface Cell {
    val i: Int
    val j: Int
}

data class CellCondition(override val i: Int, override val j: Int): Cell

interface Board {
   val size: Int

   operator fun get(cell: Cell): Int?

   operator fun set(cell: Cell, value: Int?)

    fun getCellOrNull(i: Int, j: Int): Cell?

    fun getAllCells(): List<Cell>

    fun addNewValue(init: Init)

    fun moveRowOrColumn(rowOrColumn: List<Cell>): Boolean

    fun moveBoard(direction: Direction): Boolean
}

