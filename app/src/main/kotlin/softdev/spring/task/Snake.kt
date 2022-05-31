package softdev.spring.task

class Snake(var length: Int, val number: Int) {

    var up = false
    var down = false
    var left = false
    var right = true

    fun checkEachBump(snake1: Snake, snake2: Snake, list1: MutableList<CellS>, list2: MutableList<CellS>) : Boolean {
        for (i in 0 until snake2.length) if (list1[0].getX() == list2[i].getX() && (list1[0].getY() == list2[i].getY())) return false

        for (i in 0 until snake1.length) if (list2[0].getX() == list1[i].getX() && (list2[0].getY() == list1[i].getY())) return false

        return true
    }

    fun checkEaten(food0: Food, list: MutableList<CellS>): Boolean {
        if (list[0].getX() == food0.getX() && list[0].getY() == food0.getY()) {
            changeSize(1, list)
            //food = createFood()
            return true
        }
        return false
    }

    fun changeSize(num: Int, list: MutableList<CellS>) {
        var i = num
        while (i > 0) {
            val snakeX = list[length - 1].getX()
            val snakeY = list[length - 1].getY()
            val cellV = list[length - 1].getV()
            when {
                cellV == 3 && snakeX + sizeOfOne < fieldSize -> list += CellS(snakeX + sizeOfOne, snakeY,3)
                cellV == 4 && snakeX - sizeOfOne > 0 -> list += CellS(snakeX - sizeOfOne, snakeY,4)
                cellV == 1 && snakeY - sizeOfOne < fieldSize -> list += CellS(snakeX, snakeY - sizeOfOne,1)
                cellV == 2 && snakeY - sizeOfOne > 0 -> list += CellS(snakeX, snakeY + sizeOfOne,2)
                else -> alive = false
            }
            length += 1
            i--
        }
    }

    fun move(a: MutableList<CellS>) {
        var i = length - 1
        while (i > 0) {
            a[i] = a[i - 1]
            i--
        }
        when {
            up -> a[0] = CellS(a[0].getX(), a[0].getY() - sizeOfOne, 1)
            down -> a[0] = CellS(a[0].getX(), a[0].getY() + sizeOfOne, 2)
            left -> a[0] = CellS(a[0].getX() - sizeOfOne, a[0].getY(), 3)
            right -> a[0] = CellS(a[0].getX() + sizeOfOne, a[0].getY(), 4)
        }
    }

    fun checkBump(list: MutableList<CellS>): Boolean {
        for (i in length - 1 downTo 1) {
            if (list[0].getX() == list[i].getX() && list[0].getY() == list[i].getY()) return false
        }
        return !(list[0].getX() >= fieldSize || list[0].getX() < 0 || list[0].getY() >= fieldSize || list[0].getY() < 0)
    }
}