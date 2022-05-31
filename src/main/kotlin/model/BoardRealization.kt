package model

class BoardCondition(override val size: Int, override val cells: MutableList<MutableList<Cell>>): Board {
    private val cellValues = mutableMapOf<Cell, Int?>()

    private fun getRow(i: Int, jRange: IntProgression): List<Cell> = jRange.map { j -> getCellOrNull(i, j)!! }.toList()

    private fun getColumn(iRange: IntProgression, j: Int): List<Cell> = iRange.map { i -> getCellOrNull(i, j)!! }.toList()

    private fun doubleSame(cells: List<Int?>): MutableList<Int> {
        val notNulls = cells.filterNotNull().toMutableList()
        with(notNulls) {
            var index = 1
            while(index < this.size) {
                if (this[index] == this[index - 1]) {
                    this[index - 1] = 2 * (this[index])
                    this.removeAt(index)
                }
                ++index
            }
        }
        return notNulls
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        when {
            i > size || j > size || i == 0 || j == 0 -> null
            else -> cells[i - 1][j - 1]
        }

    override fun getAllCells(): List<Cell> {
        val result = mutableListOf<Cell>()
        for (i in 1..size) {
            for (j in 1..size)
                result.add(getCellOrNull(i, j)!!)
        }
        return result
    }

    override fun get(cell: Cell): Int? = cellValues[cell]

    override fun set(cell: Cell, value: Int?) { cellValues[cell] = value }

    override fun moveRowOrColumn(rowOrColumn: List<Cell>): Boolean {
        val list: List<Int> = doubleSame(rowOrColumn.map { this[it] })
        rowOrColumn.forEachIndexed { index, cell -> this[cell] = if (index < list.size) list[index] else null }
        return list.isNotEmpty()
    }

    override fun moveBoard(direction: Direction): Boolean {
        val directionRange = if (direction in listOf(Direction.UP, Direction.LEFT)) 1..size else size downTo 1
        var valuesMoved = false
        when (direction) {
            Direction.LEFT, Direction.RIGHT -> {
                for (i in 1..size) {
                    val movement = moveRowOrColumn(getRow(i, directionRange))
                    valuesMoved = valuesMoved || movement
                }
            }
            Direction.UP, Direction.DOWN -> {
                for (i in 1..size) {
                    val movement = moveRowOrColumn(getColumn(directionRange, i))
                    valuesMoved = valuesMoved || movement
                }
            }
            else -> valuesMoved = false
        }
        return valuesMoved
    }

    override fun addNewValue(init: BoardInit) {
        val nextValue = init.nextValue(this)
        nextValue?.let { this.getAllCells().filter { it == nextValue.first }.forEach { this[it] = nextValue.second } }
    }

    companion object {
        fun gameBoard(size: Int): BoardCondition {
            val board = mutableListOf<MutableList<Cell>>()
            for (i in 1..size) {
                val listOfCells = mutableListOf<Cell>()
                for (j in 1..size)
                    listOfCells.add(CellCondition(i, j))
                board.add(listOfCells)
            }
            return BoardCondition(size, board)
        }
    }
}
