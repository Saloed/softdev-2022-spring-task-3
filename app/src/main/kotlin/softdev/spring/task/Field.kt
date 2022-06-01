package softdev.spring.task

import javafxTornado.twoPlayersCondition
import kotlin.concurrent.timer
import kotlin.random.Random

const val fieldSize = 320
const val sizeOfOne = 16
const val allCells = 400

var alive = true

val cellsSnake: MutableList<CellS> = mutableListOf()

val cellsSnake2: MutableList<CellS> = mutableListOf()

val freeCellsMap: MutableMap<CellFree, Boolean> = mutableMapOf()

var freeCells: MutableList<CellFree> = mutableListOf()

fun checkBonus(snake: Snake, list: MutableList<CellS>, snake2: Snake) {
    if (list[0].getX() == bonus!!.getX() && list[0].getY() == bonus!!.getY()) {
        if (bonus is Bonus.DecreaseSpeed && snake == firstSnake) speed1 *= 2 else if (snake == wasdSnake && twoPlayersCondition) speed2 *= 2
        if (bonus is Bonus.DecreaseSize) {
            snake.length -= 3
            if (snake.length > 0) {
                var i = 3
                while (i > 0) {
                    list.removeLast()
                    i--
                }
            } else alive = false
        }
        if (bonus is Bonus.IncreaseSpeed) if (bonus is Bonus.IncreaseSpeed && snake == firstSnake) speed1 /= 2 else if (snake == wasdSnake && twoPlayersCondition) speed2 /= 2
        if (bonus is Bonus.IncreaseSize) snake.changeSize(3, list)

        bonus = createBonus(snake, snake2)
    }
}

fun eatToLive(snake: Snake, mutableList: MutableList<CellS>) {
    timer(period = 7000, daemon = true, initialDelay = 7000) {
        snake.length -= 1
        if (snake.length > 0) mutableList.removeLast() else {
            alive = false
        }
    }
}

fun createFood(): Food {
    reinitialize()
    val freeCellNumber = freeCells.size - 1
    val random = Random.nextInt(freeCellNumber)
    val newFood = freeCells[random]
    return Food(newFood.getX(), newFood.getY())
}

fun checks(snake: Snake, cells: MutableList<CellS>, snake2: Snake) {
    snake.move(cells)
    if (food != null) snake.checkEaten(food!!, cells)
    if (snake.checkEaten(food!!, cells)) food = createFood()
    checkBonus(snake, cells, snake2)
    if (alive) alive = snake.checkBump(cells)
    if (twoPlayersCondition && alive) alive = snake.checkEachBump(firstSnake, wasdSnake, cellsSnake, cellsSnake2)
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





