package model

class GameLogic(private val size: Int, private val init: Init) : Game {
    val board = createGameBoard(size)
    private val numToWin = when (size) {
        3 -> 32
        4 -> 2048
        5 -> 524288
        else -> 536870912
    }

    override fun hasWon() = board.getAllCells().any { get(it.i, it.j) == numToWin }

    override fun hasLost() = board.getAllCells().all { get(it.i, it.j ) != get(it.i + 1, it.j )
            && get(it.i, it.j ) != get(it.i, it.j + 1) && get(it.i, it.j ) != null }

    override fun initialize() = repeat(2) { board.addNewValue(init) }

    override fun processMove(direction: Direction) { if (board.moveBoard(direction)) board.addNewValue(init) }

    override fun get(i: Int, j: Int): Int? = board.run { getCellOrNull(i, j)?.let { get(it) } }

    override fun canMove(direction: Direction): Boolean {
        val notEmptyCells = board.getAllCells().filter { get(it.i, it.j) != null }
        return when(direction) {
            Direction.UP -> notEmptyCells.any { get(it.i, it.j) == get(it.i - 1, it.j) || (it.i != 1 && get(it.i - 1, it.j) == null) }
            Direction.DOWN -> notEmptyCells.any { get(it.i, it.j) == get(it.i + 1, it.j) || (it.i != size && get(it.i + 1, it.j) == null) }
            Direction.RIGHT -> notEmptyCells.any { get(it.i, it.j) == get(it.i, it.j + 1) || (it.j != size && get(it.i, it.j + 1) == null) }
            Direction.LEFT -> notEmptyCells.any { get(it.i, it.j) == get(it.i, it.j - 1) || ((it.j != 1 && get(it.i, it.j - 1) == null)) }
            else -> false
        }
    }
}
