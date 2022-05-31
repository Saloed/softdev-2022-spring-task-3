package softdev.spring.task

import javafxTornado.twoPlayersCondition
import kotlin.random.Random

    const val fieldSize = 320
    const val sizeOfOne = 16
    const val allCells = 400

    var alive = true

    val cellsSnake: MutableList<CellS> = mutableListOf()

    val cellsSnake2: MutableList<CellS> = mutableListOf()

    val freeCellsMap: MutableMap<CellFree, Boolean> = mutableMapOf()

    var freeCells: MutableList<CellFree> = mutableListOf()

    fun createFood(): Food {
        reinitialize()
        val freeCellNumber = freeCells.size - 1
        val random = Random.nextInt(freeCellNumber)
        val newFood = freeCells[random]
        return Food(newFood.getX(), newFood.getY())
    }

    fun reinitialize() {
        freeCells = mutableListOf()
        for (i in freeCellsMap.keys) freeCellsMap[i] = true
        for (cell in cellsSnake) freeCellsMap[CellFree(cell.getX(), cell.getY())] = false
        for (cell in cellsSnake2) freeCellsMap[CellFree(cell.getX(), cell.getY())] = false
        if (food != null) freeCellsMap[CellFree(food!!.getX(), food!!.getY())] = false
        if (bonus != null) freeCellsMap[CellFree(bonus!!.getX(), bonus!!.getY())] = false
        for (i in freeCellsMap.keys) {
            if (freeCellsMap[i] == true) freeCells += i
        }
    }

    fun createBonus(snake1: Snake, snake2: Snake): Bonus {
        reinitialize()
        val freeCellNumber = allCells - snake1.length - snake2.length
        val random = Random.nextInt(freeCellNumber)
        val newBonus = freeCells[random]
        return when (random % 4) {
            0 -> Bonus.IncreaseSize(newBonus.getX(), newBonus.getY())
            1 -> Bonus.DecreaseSize(newBonus.getX(), newBonus.getY())
            2 -> Bonus.IncreaseSpeed(newBonus.getX(), newBonus.getY())
            else -> Bonus.DecreaseSpeed(newBonus.getX(), newBonus.getY())
        }
    }

    val firstSnake = Snake(3, 1)

    val wasdLength = if (twoPlayersCondition) 3 else 0

    val wasdSnake = Snake(wasdLength, 2)

    var food: Food? = null

    var bonus: Bonus? = null

    var speed1 = 200L

    var speed2 = 200L





