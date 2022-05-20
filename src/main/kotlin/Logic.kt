import kotlin.math.abs

val turn = mutableListOf(Turn.Black)
val listOfNumbers1 = listOf(8, 7, 6, 5, 4, 3, 2, 1)
enum class Letters {
    A, B, C, D, E, F, G, H
}
val listOfLetters1 = listOf(Letters.A, Letters.B, Letters.C, Letters.D, Letters.E, Letters.F, Letters.G, Letters.H)
enum class Coordinates {
    A1, C1, E1, G1, B2, D2, F2, H2, A3, C3, E3, G3,
    B8, D8, F8, H8, A7, C7, E7, G7, B6, D6, F6, H6,
    B4, D4, F4, H4, A5, C5, E5, G5,
    No
}
val listOfOccupiedCells: List<Coordinates> = listOf(
    Coordinates.A1, Coordinates.C1, Coordinates.E1, Coordinates.G1,
    Coordinates.B2, Coordinates.D2, Coordinates.F2, Coordinates.H2,
    Coordinates.A3, Coordinates.C3, Coordinates.E3, Coordinates.G3,
    Coordinates.B8, Coordinates.D8, Coordinates.F8, Coordinates.H8,
    Coordinates.A7, Coordinates.C7, Coordinates.E7, Coordinates.G7,
    Coordinates.B6, Coordinates.D6, Coordinates.F6, Coordinates.H6,
)

fun update(cord: Coordinates, queen: Boolean, oldCord: Coordinates, white: Turn, defCord: Coordinates) {
    var active = Coordinates.No
    val ind = location.value.indexOf(oldCord)
    location.value.removeAt(ind)
    location.value.add(ind, cord)
    val old: Coordinates
    val new: Coordinates
    if (!queen) {
        old = eat(oldCord, cord, location.value, white).first
        new = eat(oldCord, cord, location.value, white).second
    } else {
        old = eatForQueen(oldCord, cord, location.value).first
        new = eatForQueen(oldCord, cord, location.value).second
    }
    val index = location.value.indexOf(new)
    delete.value += old
    if (index != -1) location.value.add(index, Coordinates.No)
    location.value.remove(new)
    val c = if (turnWhite.value.size > 1) turnWhite.value[1]
    else turnWhite.value[0]
    if (defCord in activeCh.value) active = defCord
    activeCh.value = checkDelete(location.value, c, active, listOfQueen.value, queen)
    if (activeCh.value.isNotEmpty()) {
        if (Turn.P !in turnWhite.value) turnWhite.value.add(0, Turn.P)
        if (defCord !in activeCh.value) {
            if (turnWhite.value[1] == Turn.White) turnWhite.value.add(Turn.Black)
            if (turnWhite.value[1] == Turn.Black) turnWhite.value.add(Turn.White)
            turnWhite.value.removeAt(1)
        }
    } else {
        active = Coordinates.No
        if (Turn.P in turnWhite.value) turnWhite.value.remove(Turn.P)
        if (turnWhite.value[0] == Turn.White) turnWhite.value.add(Turn.Black)
        if (turnWhite.value[0] == Turn.Black) turnWhite.value.add(Turn.White)
        turnWhite.value.removeAt(0)
    }
    val t = gameOver(location.value)
    if (t != "") {
        openDialog.value = true
        text.value = t
    }
}

fun isQueen(white: Turn, y: Coordinates) =
    (white == Turn.White && y.toString()[1] == '8') || (white == Turn.Black && y.toString()[1] == '1')

fun checkDelete(loc: List<Coordinates>, turn: Turn, active: Coordinates, queenList: Set<Coordinates>, queen: Boolean): Set<Coordinates> {
    val result = mutableSetOf<Coordinates>()
    if (active != Coordinates.No) {
        val ind = (listOfOccupiedCells).indexOf(active)
        val wh = if (ind < 12) Turn.White else Turn.Black
        val list = allowedCells(loc[ind], wh, loc, queen)
        if (hasDeleted(list, loc[ind])) {
            result.add(active)
        }
    }
    if (result.isEmpty()) {
        loc.forEachIndexed { index, it ->
            if (it != Coordinates.No) {
                val wh = if (index in 0..11) Turn.White else Turn.Black
                if (wh != turn) {
                    println(it)
                    println(queenList.contains(it))
                    val list = allowedCells(it, wh, loc, queenList.contains(listOfOccupiedCells[index]))
                    if (hasDeleted(list, it)) {
                        result.add(listOfOccupiedCells[index])
                    }
                }
            }
        }
    }
    return result
}

fun hasDeleted(list: Set<Coordinates>, x: Coordinates): Boolean {
    val indX = listOfLetters1.indexOf(Letters.valueOf(x.toString()[0].toString()))
    list.forEach {
        val indNew = listOfLetters1.indexOf(Letters.valueOf(it.toString()[0].toString()))
        if (abs(indX - indNew) > 1) return true
    }
    return false
}

fun repeat(letter: String, number: String, loc: List<Coordinates>): Pair<Coordinates, Coordinates> {
    val ind = loc.indexOf(Coordinates.valueOf(letter + number))
    println(Coordinates.valueOf(letter + number))
    val new = Coordinates.valueOf(letter + number)
    return Pair((listOfOccupiedCells)[ind], new)
}

fun eatForQueen(oldCord: Coordinates, newCord: Coordinates, loc: List<Coordinates>): Pair<Coordinates, Coordinates> {
    val oldX = listOfLetters1.indexOf(Letters.valueOf(oldCord.toString()[0].toString()))
    val newX = listOfLetters1.indexOf(Letters.valueOf(newCord.toString()[0].toString()))
    val newY = listOfNumbers1.indexOf(newCord.toString()[1].digitToInt())
    val oldY = listOfNumbers1.indexOf(oldCord.toString()[1].digitToInt())
    if (newX - oldX > 1 && newY - oldY < -1) {
        val letter = listOfLetters1[newX - 1].toString()
        val number = listOfNumbers1[newY + 1]
        println(letter + number)
        return repeat(letter, number.toString(), loc)
    }
    if (newX - oldX < -1 && newY - oldY < -1) {
        val letter = listOfLetters1[newX + 1].toString()
        val number = listOfNumbers1[newY + 1]
        println(letter + number)
        return repeat(letter, number.toString(), loc)
    }
    if (newX - oldX > 1 && newY - oldY > 1) {
        val letter = listOfLetters1[newX - 1].toString()
        val number = listOfNumbers1[newY - 1]
        println(letter + number)
        return repeat(letter, number.toString(), loc)
    }
    if (newX - oldX < -1 && newY - oldY > 1) {
        val letter = listOfLetters1[newX + 1].toString()
        val number = listOfNumbers1[newY - 1]
        println(letter + number)
        return repeat(letter, number.toString(), loc)
    }
    return Pair(Coordinates.No, Coordinates.No)
}

fun gameOver(loc: List<Coordinates>): String {
    var alert = ""
    var countB = 0
    var countW = 0
    var countStepB = 0
    var countStepW = 0
    loc.forEachIndexed { index, s ->
        if (index < 12 && s != Coordinates.No) countW++
        if (index > 11 && s != Coordinates.No) countB++
        if (index < 12 && s != Coordinates.No && allowedCells(
                s,
                Turn.White,
                loc,
                listOfQueen.value.contains(listOfOccupiedCells[index])
            ).isNotEmpty()
        )
            countStepW++
        if (index > 11 && s != Coordinates.No && allowedCells(
                s,
                Turn.Black,
                loc,
                listOfQueen.value.contains(listOfOccupiedCells[index])
            ).isNotEmpty()
        )
            countStepB++
    }
    if (countB == 0 || countStepB == 0) alert = "Победа белых!"
    if (countW == 0 || countStepW == 0) alert = "Победа черных!"
    return alert
}

fun restart() {
    activeCh.value = setOf()
    location.value = (listOfOccupiedCells).toMutableList()
    delete.value = mutableSetOf()
    turnWhite.value = mutableListOf(Turn.Black)
    restart.value = true
    listOfQueen.value = mutableSetOf()
}

fun eat(
    oldCord: Coordinates,
    newCord: Coordinates,
    loc: List<Coordinates>,
    white: Turn,
): Pair<Coordinates, Coordinates> {
    val oldX = listOfLetters1.indexOf(Letters.valueOf(oldCord.toString()[0].toString()))
    val newX = listOfLetters1.indexOf(Letters.valueOf(newCord.toString()[0].toString()))
    val newY = listOfNumbers1.indexOf(newCord.toString()[1].digitToInt())
    if (white == Turn.White) {
        if (newX - oldX > 1) {
            val letter = listOfLetters1[newX - 1].toString()
            val number = listOfNumbers1[newY + 1]
            return repeat(letter, number.toString(), loc)
        }
        if (newX - oldX < -1) {
            val letter = listOfLetters1[newX + 1].toString()
            val number = listOfNumbers1[newY + 1]
            return repeat(letter, number.toString(), loc)
        }
    }
    if (white == Turn.Black) {
        if (newX - oldX > 1) {
            val letter = listOfLetters1[newX - 1].toString()
            val number = listOfNumbers1[newY - 1]
            return repeat(letter, number.toString(), loc)
        }
        if (newX - oldX < -1) {
            val letter = listOfLetters1[newX + 1].toString()
            val number = listOfNumbers1[newY - 1]
            return repeat(letter, number.toString(), loc)
        }
    }
    return Pair(Coordinates.No, Coordinates.No)
}

fun getNextStep(cord: Coordinates, white: Turn, queen: Boolean): Set<Coordinates> {
    val result = mutableSetOf<Coordinates>()
    if (white == Turn.White || queen) {
        if (cord.toString()[0] != 'H' && cord.toString()[0] != 'A' && cord.toString()[1] != '8') {
            val i = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
            val nextStepX1 = listOfLetters1[i + 1]
            val nextStepX2 = listOfLetters1[i - 1]
            val nextStepY = cord.toString()[1].digitToInt() + 1 // +1?
            val cord1 = Coordinates.valueOf(nextStepX1.toString() + nextStepY.toString())
            val cord2 = Coordinates.valueOf(nextStepX2.toString() + nextStepY.toString())
            result.addAll(setOf(cord1, cord2))
        }
        if (cord.toString()[0] == 'H' && cord.toString()[1] != '8') {
            val i = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
            val x = listOfLetters1[i - 1]
            val y = cord.toString()[1].digitToInt() + 1 // +1?
            val cor =
                Coordinates.valueOf(x.toString() + y.toString())
            result.addAll(setOf(cor))
        }
        if (cord.toString()[0] == 'A' && cord.toString()[1] != '8') {
            val i = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
            val x = listOfLetters1[i + 1]
            val y = cord.toString()[1].digitToInt() + 1 // +1?
            val cor =
                Coordinates.valueOf(x.toString() + y.toString())
            result.addAll(setOf(cor))
        }
    }
    if (white == Turn.Black || queen) {
        if (cord.toString()[0] != 'H' && cord.toString()[0] != 'A' && cord.toString()[1] != '1') {
            val i = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
            val nextStepX1 = listOfLetters1[i + 1]
            val nextStepX2 = listOfLetters1[i - 1]
            val nextStepY = cord.toString()[1].digitToInt() - 1 // -1?
            val cord1 = Coordinates.valueOf(nextStepX1.toString() + nextStepY.toString())
            val cord2 = Coordinates.valueOf(nextStepX2.toString() + nextStepY.toString())
            result.addAll(setOf(cord1, cord2))
        }
        if (cord.toString()[0] == 'H' && cord.toString()[1] != '1') {
            val i = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
            val x = listOfLetters1[i - 1]
            val y = cord.toString()[1].digitToInt() - 1 // -1?
            val cor =
                Coordinates.valueOf(x.toString() + y.toString())
            result.addAll(setOf(cor))
        }
        if (cord.toString()[0] == 'A' && cord.toString()[1] != '1') {
            val i = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
            val x = listOfLetters1[i + 1]
            val y = cord.toString()[1].digitToInt() - 1 // -1?
            val cor =
                Coordinates.valueOf(x.toString() + y.toString())
            result.addAll(setOf(cor))
        }
    }
    return result
}

fun allowedCells(cord: Coordinates, white: Turn, loc: List<Coordinates>, queen: Boolean): Set<Coordinates> {
    val allowed = mutableSetOf<Coordinates>()
    val result = getNextStep(cord, white, queen)
    result.forEach {
        val index = loc.indexOf(it)
        if (index != -1) {
            if ((white == Turn.White && index > 11) || (white == Turn.Black && queen && index < 12)) {
                val futureCord1: Coordinates
                val futureCord2: Coordinates
                if (cord.toString()[0] != 'G' && cord.toString()[0] != 'H' && cord.toString()[1] != '7' && cord.toString()[1] != '8') {
                    val i = listOfLetters1.indexOf(Letters.valueOf(it.toString()[0].toString()))
                    val x = listOfLetters1[i + 1]
                    val y = it.toString()[1].digitToInt() + 1 // -1?
                    futureCord1 = Coordinates.valueOf(x.toString() + y.toString())
                    val iOld = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
                    if (iOld < i && !loc.contains(futureCord1) /*&& !loc.contains(futureCord1 + "q") && Cell(futureCord1).centerX < 560 && cord.second > Cell(futureCord1).centerY*/) allowed.add(
                        futureCord1
                    )
                }
                if (cord.toString()[0] != 'A' && cord.toString()[0] != 'B' && cord.toString()[1] != '7' && cord.toString()[1] != '8') {
                    val i = listOfLetters1.indexOf(Letters.valueOf(it.toString()[0].toString()))
                    val x = listOfLetters1[i - 1]
                    val y = it.toString()[1].digitToInt() + 1 // -1?
                    futureCord2 = Coordinates.valueOf(x.toString() + y.toString())
                    val iOld = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
                    if (iOld > i && !loc.contains(futureCord2) /*&& !loc.contains(futureCord1 + "q") && Cell(futureCord1).centerX < 560 && cord.second > Cell(futureCord1).centerY*/) allowed.add(
                        futureCord2
                    )
                }
            }
            if ((white == Turn.Black && index < 12) || (white == Turn.White && queen && index > 11)) {
                val futureCord1: Coordinates
                val futureCord2: Coordinates
                if (cord.toString()[0] != 'G' && cord.toString()[0] != 'H' && cord.toString()[1] != '1' && cord.toString()[1] != '2') {
                    val i = listOfLetters1.indexOf(Letters.valueOf(it.toString()[0].toString()))
                    val x = listOfLetters1[i + 1]
                    val y = it.toString()[1].digitToInt() - 1 // -1?
                    futureCord1 = Coordinates.valueOf(x.toString() + y.toString())
                    val iOld = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
                    if (iOld < i && !loc.contains(futureCord1) /*&& !loc.contains(futureCord1 + "q") && Cell(futureCord1).centerX < 560 && cord.second > Cell(futureCord1).centerY*/) allowed.add(
                        futureCord1
                    )
                }
                if (cord.toString()[0] != 'A' && cord.toString()[0] != 'B' && cord.toString()[1] != '1' && cord.toString()[1] != '2') {
                    val i = listOfLetters1.indexOf(Letters.valueOf(it.toString()[0].toString()))
                    val x = listOfLetters1[i - 1]
                    val y = it.toString()[1].digitToInt() - 1 // -1?
                    futureCord2 = Coordinates.valueOf(x.toString() + y.toString())
                    val iOld = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString()))
                    if (iOld > i && !loc.contains(futureCord2) /*&& !loc.contains(futureCord1 + "q") && Cell(futureCord1).centerX < 560 && cord.second > Cell(futureCord1).centerY*/) allowed.add(
                        futureCord2
                    )
                }
            }
        }
    }
    if (allowed.isEmpty()) {
        result.forEach {
            if (it !in loc) allowed.add(it)
        }
    }
    return allowed
}
