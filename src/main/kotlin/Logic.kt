import kotlin.math.abs
import kotlin.math.roundToInt

val listOfNumbers = listOf("8", "7", "6", "5", "4", "3", "2", "1")
val listOfLetters = listOf("A", "B", "C", "D", "E", "F", "G", "H")
val turn = mutableListOf("black")
val listOfOccupiedCells: MutableList<String> =
    (coordinatesW + coordinatesB).toMutableList()

fun checkOccupiedCells(old: Pair<Double, Double>, new: Pair<Double, Double>, queen: Boolean): MutableList<String> {
    val letterOld = listOfLetters[(old.first / 70).roundToInt()]
    val numberOld = listOfNumbers[(old.second / 70).roundToInt()]
    val letterNew = listOfLetters[(new.first / 70).roundToInt()]
    val numberNew = listOfNumbers[(new.second / 70).roundToInt()]
    val add = if (queen) "q" else ""
    return mutableListOf(letterOld + numberOld + add, letterNew + numberNew + add)
}

fun isQueen(white: String, y: Double): Boolean {
    val b = if (white == "white" && y < 70) true
    else (white == "black") && (y > 480)
    return b
}

fun resetCoordinates(cord: String, loc: List<String>): Pair<Double, Double> {
    val ind = (coordinatesW + coordinatesB).indexOf(cord)
    val x = Cell(loc[ind]).centerX
    val y = Cell(loc[ind]).centerY
    return Pair(x, y)
}

fun checkDelete(loc: List<String>, turn: String, active: String, queen: Boolean): Set<String> {
    val result = mutableSetOf<String>()
    if (active != "") {
        val ind = (coordinatesW + coordinatesB).indexOf(active)
        val x = Cell(loc[ind]).centerX
        val y = Cell(loc[ind]).centerY
        val wh = if (active in coordinatesW) "white" else "black"
        val list = allowedCells(Pair(x, y), wh, loc, queen)
        if (hasDeleted(list, x)) {
            result.add(active)
        }
    }
    if (result.isEmpty()) {
        loc.forEachIndexed { index, it ->
            if (it != "") {
                val wh = if (index in 0..11) "white" else "black"
                if (wh != turn) {
                    val x = Cell(it).centerX
                    val y = Cell(it).centerY
                    val list = allowedCells(Pair(x, y), wh, loc, it.contains("q"))
                    if (hasDeleted(list, x)) {
                        result.add((coordinatesW + coordinatesB)[index])
                    }
                }
            }
        }
    }
    return result
}

fun hasDeleted(list: Set<String>, x: Double): Boolean {
    list.forEach {
        if (abs(Cell(it).centerX - x) > 80) return true
    }
    return false
}

fun getCord(posX: Double, posY: Double) =
    if (posX > 0 && posX < 530 && posY > 0 && posY < 540)
        listOfLetters[((posX - 20) / 70).roundToInt()] + listOfNumbers[((posY - 20) / 70).roundToInt()]
    else ""

fun allowedStep(cord: String, set: Set<String>): Boolean {
    return cord in set
}

fun repeat(letter: String, number: String, loc: List<String>): Pair<String, String> {
    var ind = loc.indexOf(letter + number)
    if (ind == -1) ind = loc.indexOf(letter + number + "q")
    val new = if (loc[ind].contains("q")) letter + number + "q" else letter + number
    return Pair((coordinatesW + coordinatesB)[ind], new)
}

fun eatForQueen(oldPosX: Double, oldPosY: Double, posX: Double, posY: Double, loc: List<String>, white: String): Pair<String, String> {
    if (posX - oldPosX > 80 && posY - oldPosY > 80) {
        val letter = listOfLetters[((posX - 70.0) / 70).roundToInt()]
        val number = listOfNumbers[((posY - 70.0) / 70).roundToInt()]
        return repeat(letter, number, loc)
    }
    if (posX - oldPosX < -80 && posY - oldPosY > 80) {
        val letter = listOfLetters[((posX + 70.0) / 70).roundToInt()]
        val number = listOfNumbers[((posY - 70.0) / 70).roundToInt()]
        return repeat(letter, number, loc)
    }
    if (posX - oldPosX > 80 && posY - oldPosY < -80) {
        val letter = listOfLetters[((posX - 70.0) / 70).roundToInt()]
        val number = listOfNumbers[((posY + 70.0) / 70).roundToInt()]
        return repeat(letter, number, loc)
    }
    if (posX - oldPosX < -80 && posY - oldPosY < -80) {
        val letter = listOfLetters[((posX + 70.0) / 70).roundToInt()]
        val number = listOfNumbers[((posY + 70.0) / 70).roundToInt()]
        return repeat(letter, number, loc)
    }
    return Pair("", "")
}

fun gameOver(loc: List<String>): String {
    var alert = ""
    var countB = 0
    var countW = 0
    var countStepB = 0
    var countStepW = 0
    loc.forEachIndexed { index, s ->
        if (index < 12 && s != "") countW++
        if (index > 11 && s != "") countB++
        if (index < 12 && s != "" && allowedCells(Cell(s.replace("q", "")).centerX to Cell(s.replace("q", "")).centerY, "white", loc, s.contains("q")).isNotEmpty())
            countStepW ++
        if (index > 11 && s != "" && allowedCells(Cell(s.replace("q", "")).centerX to Cell(s.replace("q", "")).centerY, "black", loc, s.contains("q")).isNotEmpty())
            countStepB ++
    }
    if (countB == 0 || countStepB == 0) alert = "Победа белых!"
    if (countW == 0 || countStepW == 0) alert = "Победа черных!"
    return alert
}

fun restart() {
    activeCh.value = setOf()
    location.value = (coordinatesW + coordinatesB).toMutableList()
    delete.value = mutableSetOf()
    turnWhite.value = mutableListOf("black")
    restart.value = true
}

fun eat(
    oldPosX: Double,
    oldPosY: Double,
    posX: Double,
    posY: Double,
    loc: List<String>,
    white: String,
): Pair<String, String> {
    if (white == "white") {
        if (posX - oldPosX > 80) {
            val letter = listOfLetters[((posX - 70.0) / 70).roundToInt()]
            val number = listOfNumbers[((posY + 70.0) / 70).roundToInt()]
            return repeat(letter, number, loc)
        }
        if (posX - oldPosX < -80) {
            val letter = listOfLetters[((posX + 70.0) / 70).roundToInt()]
            val number = listOfNumbers[((posY + 70.0) / 70).roundToInt()]
            return repeat(letter, number, loc)
        }
    }
    if (white == "black") {
        if (posX - oldPosX > 80) {
            val letter = listOfLetters[((posX - 70.0) / 70).roundToInt()]
            val number = listOfNumbers[((posY - 70.0) / 70).roundToInt()]
            return repeat(letter, number, loc)
        }
        if (posX - oldPosX < -80) {
            val letter = listOfLetters[((posX + 70.0) / 70).roundToInt()]
            val number = listOfNumbers[((posY - 70.0) / 70).roundToInt()]
            return repeat(letter, number, loc)
        }
    }
    return Pair("", "")
}

fun getNextStep(cord: Pair<Double, Double>, white: String, queen: Boolean): Set<String> {
    val result = mutableSetOf<String>()
    if (white == "white" || queen) {
        if (cord.first + 70 <= 540.0 && cord.first - 70 >= 0.0 && cord.second - 70.0 >= 0) {
            val nextStepX1 = cord.first + 70
            val nextStepX2 = cord.first - 70
            val nextStepY = cord.second - 70
            val cord1 = listOfLetters[(nextStepX1 / 70).roundToInt()] + listOfNumbers[(nextStepY / 70).roundToInt()]
            val cord2 = listOfLetters[(nextStepX2 / 70).roundToInt()] + listOfNumbers[(nextStepY / 70).roundToInt()]
            result.addAll(setOf(cord1, cord2))
        }
        if (cord.first + 70 >= 560.0 && cord.second > 80) {
            val cor =
                listOfLetters[((cord.first - 70) / 70).roundToInt()] + listOfNumbers[((cord.second - 70) / 70).roundToInt()]
            result.addAll(setOf(cor))
        }
        if (cord.first - 70 < 0) {
            val cor =
                listOfLetters[((cord.first + 70) / 70).roundToInt()] + listOfNumbers[((cord.second - 70) / 70).roundToInt()]
            result.addAll(setOf(cor))
        }
    }
    if (white == "black" || queen) {
        if (cord.first + 70 <= 540.0 && cord.first - 70 >= 0.0 && cord.second + 70 <= 540.0) {
            val nextStepX1 = cord.first + 70
            val nextStepX2 = cord.first - 70
            val nextStepY = cord.second + 70
            val cord1 = listOfLetters[(nextStepX1 / 70).roundToInt()] + listOfNumbers[(nextStepY / 70).roundToInt()]
            val cord2 = listOfLetters[(nextStepX2 / 70).roundToInt()] + listOfNumbers[(nextStepY / 70).roundToInt()]
            result.addAll(setOf(cord1, cord2))
        }
        if (cord.first + 70 >= 560.0) {
            val cor =
                listOfLetters[((cord.first - 70) / 70).roundToInt()] + listOfNumbers[((cord.second + 70) / 70).roundToInt()]
            result.addAll(setOf(cor))
        }
        if (cord.first - 70 < 0 && cord.second < 400) {
            val cor =
                listOfLetters[((cord.first + 70) / 70).roundToInt()] + listOfNumbers[((cord.second + 70) / 70).roundToInt()]
            result.addAll(setOf(cor))
        }
    }
    return result
}

fun allowedCells(cord: Pair<Double, Double>, white: String, loc: List<String>, queen: Boolean): Set<String> {
    val allowed = mutableSetOf<String>()
    val result = getNextStep(cord, white, queen)
    result.forEach {
        var index = loc.indexOf(it)
        if (index == -1) index = loc.indexOf(it + "q")
        if (index != -1) {
            if ((white == "white" && index > 11) || (white == "black" && queen && index < 12)) {
                val futureCord1: String
                val futureCord2: String
                if (cord.first < 400 && cord.second > 150) {
                    futureCord1 =
                        (listOfLetters[listOfLetters.indexOf(it[0].toString()) + 1] + listOfNumbers[listOfNumbers.indexOf(
                            it[1].toString()
                        ) - 1])
                    if (cord.first < Cell(futureCord1).centerX && !loc.contains(futureCord1) && !loc.contains(futureCord1 + "q") && Cell(futureCord1).centerX < 560 && cord.second > Cell(futureCord1).centerY) allowed.add(
                        futureCord1
                    )
                }
                if (cord.first > 150 && cord.second > 150) {
                    futureCord2 =
                        (listOfLetters[listOfLetters.indexOf(it[0].toString()) - 1] + listOfNumbers[listOfNumbers.indexOf(
                            it[1].toString()
                        ) - 1])
                    if (cord.first > Cell(futureCord2).centerX && !loc.contains(futureCord2) && !loc.contains(futureCord2 + "q") && Cell(futureCord2).centerX > 0 && cord.second > Cell(futureCord2).centerY) allowed.add(
                        futureCord2
                    )
                }
            }
            if ((white == "black" && index < 12) || (white == "white" && queen && index > 11)) {
                val futureCord1: String
                val futureCord2: String
                if (cord.first < 400 && cord.second < 400) {
                    futureCord1 =
                        (listOfLetters[listOfLetters.indexOf(it[0].toString()) + 1] + listOfNumbers[listOfNumbers.indexOf(
                            it[1].toString()
                        ) + 1])
                    if (cord.first < Cell(futureCord1).centerX && !loc.contains(futureCord1) && !loc.contains(futureCord1 + "q") && Cell(futureCord1).centerX < 560 && cord.second < Cell(futureCord1).centerY) allowed.add(
                        futureCord1
                    )
                }
                if (cord.first > 150 && cord.second < 400) {
                    futureCord2 =
                        (listOfLetters[listOfLetters.indexOf(it[0].toString()) - 1] + listOfNumbers[listOfNumbers.indexOf(
                            it[1].toString()
                        ) + 1])
                    if (cord.first > Cell(futureCord2).centerX && !loc.contains(futureCord2) && !loc.contains(futureCord2 + "q") && Cell(futureCord2).centerX > 0 && cord.second < Cell(futureCord2).centerY) allowed.add(
                        futureCord2
                    )
                }
            }
        }
    }
    if (allowed.isEmpty()) {
        result.forEach {
            if (it !in loc && it + "q" !in loc) allowed.add(it)
        }
    }
    return allowed
}
