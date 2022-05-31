package model


interface BoardInit {
    fun nextValue(board: Board): Pair<Cell,Int?>?
}

object Random: BoardInit {
    override fun nextValue(board: Board): Pair<Cell, Int>? {
        val randomValue = listOf(2, 2, 2, 2, 2, 2, 2, 2, 2, 4).random()
        val emptyCells = board.getAllCells().filter { board[it] == null }.toList()
        return if (emptyCells.isNotEmpty()) Pair(emptyCells[emptyCells.indices.random()], randomValue)
        else null
    }
}
