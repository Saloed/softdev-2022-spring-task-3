import kotlin.math.abs

enum class Turn {
    White, Black, P
}

enum class Coordinates {
    B8, D8, F8, H8,
    A7, C7, E7, G7,
    B6, D6, F6, H6,
    A5, C5, E5, G5,
    B4, D4, F4, H4,
    A3, C3, E3, G3,
    B2, D2, F2, H2,
    A1, C1, E1, G1,
    No
}

fun coordinatesOfAllCells(): Map<Coordinates, Pair<Double, Double>> {
    val result = mutableMapOf<Coordinates, Pair<Double, Double>>()
    Coordinates.values().forEach {
        if (it != Coordinates.No) {
            val ordinal = it.ordinal
            val x = if (((ordinal / 4) % 8 % 2) == 1) {
                if (ordinal % 4 > 0) (ordinal % 4 * 2) * 70.0 + 22 else (ordinal % 4) * 70.0 + 22
            } else {
                if (ordinal % 4 > 0) (ordinal % 4 * 2) * 70.0 + 70 + 22 else (ordinal % 4) * 70.0 + 70 + 22
            }
            val y = ((ordinal / 4) % 8) * 70.0 + 22
            result[it] = x to y
        }
    }
    return result
}

val setOfAllCoordinates = coordinatesOfAllCells()

val listOfOccupiedCells: MutableList<Coordinates> = mutableListOf(
    Coordinates.A1, Coordinates.C1, Coordinates.E1, Coordinates.G1,
    Coordinates.B2, Coordinates.D2, Coordinates.F2, Coordinates.H2,
    Coordinates.A3, Coordinates.C3, Coordinates.E3, Coordinates.G3,
    Coordinates.B8, Coordinates.D8, Coordinates.F8, Coordinates.H8,
    Coordinates.A7, Coordinates.C7, Coordinates.E7, Coordinates.G7,
    Coordinates.B6, Coordinates.D6, Coordinates.F6, Coordinates.H6,
)

fun makeLocation(): Map<Coordinates, Coordinates> {
    val loc = mutableMapOf<Coordinates, Coordinates>()
    listOfOccupiedCells.forEach {
        loc[it] = it
    }
    return loc
}

fun step(newCord: Coordinates, oldCord: Coordinates, cord: Coordinates, loc: Map<Coordinates, Coordinates>): Map<Coordinates, Coordinates> {
    val l = loc.toMutableMap()
    l.remove(oldCord)
    l[newCord] = cord
    return l
}

fun eatenChecker(
    cord: Coordinates,
    oldCord: Coordinates,
    loc: Map<Coordinates, Coordinates>,
    white: Turn,
    queen: Boolean
): Pair<Coordinates, Coordinates> {
    return eat(oldCord, cord, loc, white, queen)
}

fun removeEaten(
    cord: Coordinates,
    oldCord: Coordinates,
    loc: Map<Coordinates, Coordinates>,
    white: Turn,
    queen: Boolean
): Map<Coordinates, Coordinates> {
    val l = loc.toMutableMap()
    val new = eatenChecker(cord, oldCord, loc, white, queen).second
    if (l.keys.contains(new)) {
        l.remove(new)
    }
    return l
}

fun getActiveCh(
    turn: List<Turn>,
    actives: Set<Coordinates>,
    active: Coordinates,
    queen: Boolean,
    listOfQueen: Set<Coordinates>,
    loc: Map<Coordinates, Coordinates>
): Set<Coordinates> {
    var a = active
    val c = if (turn.size > 1) turn.last()
    else turn.first()
    if (loc.containsKey(a)) {
        a = if (loc[a]!! !in actives) Coordinates.No else a
    }
    return checkDelete(loc, c, a, listOfQueen, queen)
}

fun changeTurn(actives: Set<Coordinates>, turn: List<Turn>, defCord: Coordinates): List<Turn> {
    val newTurn = turn.toMutableList()
    if (actives.isNotEmpty()) {
        if (Turn.P !in newTurn) newTurn.add(0, Turn.P)
        if (defCord !in actives) {
            if (newTurn[1] == Turn.White) newTurn.add(Turn.Black)
            if (newTurn[1] == Turn.Black) newTurn.add(Turn.White)
            newTurn.removeAt(1)
        }
    } else {
        if (Turn.P in newTurn) newTurn.remove(Turn.P)
        if (newTurn[0] == Turn.White) newTurn.add(Turn.Black)
        if (newTurn[0] == Turn.Black) newTurn.add(Turn.White)
        newTurn.removeAt(0)
    }
    return newTurn
}

fun checkGameOver(loc: Map<Coordinates, Coordinates>, listOfQueen: Set<Coordinates>, updateOpen: (Boolean) -> Unit, updateText: (String) -> Unit) {
    val t = gameOver(loc, listOfQueen)
    if (t != "") {
        updateOpen(true)
        updateText(t)
    }
}

fun isQueen(white: Turn, y: Coordinates) =
    (white == Turn.White && y.ordinal < 4) || (white == Turn.Black && y.ordinal in 28..31)

fun checkDelete(
    loc: Map<Coordinates, Coordinates>,
    turn: Turn,
    active: Coordinates,
    queenList: Set<Coordinates>,
    queen: Boolean
): Set<Coordinates> {
    val result = mutableSetOf<Coordinates>()
    if (active != Coordinates.No) {
        val wh = if (loc[active] in listOfWhite) Turn.White else Turn.Black
        val list = allowedCells(active, wh, loc, queen)
        if (hasDeleted(list, active)) {
            result.add(active)
        }
    }
    if (result.isEmpty()) {
        loc.keys.forEachIndexed { _, it ->
            if (it != Coordinates.No) {
                val wh = if (loc[it]!! in listOfWhite) Turn.White else Turn.Black
                if (wh != turn) {
                    val list = allowedCells(it, wh, loc, queenList.contains(loc[it]))
                    if (hasDeleted(list, it)) {
                        result.add(loc[it]!!)
                    }
                }
            }
        }
    }
    return result
}

fun hasDeleted(list: Set<Coordinates>, x: Coordinates): Boolean {
    val cordX = setOfAllCoordinates[x]!!.first
    list.forEach {
        val newCordX = setOfAllCoordinates[it]!!.first
        if (abs(cordX - newCordX) > 70) return true
    }
    return false
}

fun gameOver(loc: Map<Coordinates, Coordinates>, listOfQueen: Set<Coordinates>): String {
    var alert = ""
    var countB = 0
    var countW = 0
    var countStepB = 0
    var countStepW = 0
    loc.keys.forEachIndexed { _, s ->
        if (loc[s]!! in listOfWhite && s != Coordinates.No) countW++
        if (loc[s]!! in listOfBlack && s != Coordinates.No) countB++
        if (loc[s]!! in listOfWhite && s != Coordinates.No && allowedCells(
                s,
                Turn.White,
                loc,
                listOfQueen.contains(loc[s]!!)
            ).isNotEmpty()
        )
            countStepW++
        if (loc[s]!! in listOfBlack && s != Coordinates.No && allowedCells(
                s,
                Turn.Black,
                loc,
                listOfQueen.contains(loc[s]!!)
            ).isNotEmpty()
        )
            countStepB++
    }
    if (countB == 0 || countStepB == 0) alert = "Победа белых!"
    if (countW == 0 || countStepW == 0) alert = "Победа черных!"
    return alert
}

fun eat(
    oldCord: Coordinates,
    newCord: Coordinates,
    loc: Map<Coordinates, Coordinates>,
    white: Turn,
    queen: Boolean
): Pair<Coordinates, Coordinates> {
    val oldX = oldCord.ordinal % 4
    val newX = newCord.ordinal % 4
    val oldY = oldCord.ordinal / 4
    val newY = newCord.ordinal / 4
    if (white == Turn.White && newY - oldY < -1 || queen && newY - oldY < -1) {
        if (newX - oldX >= 1) {
            val ordNew = if (newY % 2 == 0) newCord.ordinal + 4 else newCord.ordinal + 3
            val new = setOfAllCoordinates.keys.toList()[ordNew]
            return Pair(loc[new]!!, new)
        }
        if (newX - oldX <= -1) {
            val ordNew = if (newY % 2 == 0) newCord.ordinal + 5 else newCord.ordinal + 4
            val new = setOfAllCoordinates.keys.toList()[ordNew]
            return Pair(loc[new]!!, new)
        }
    }
    if (white == Turn.Black && newY - oldY > 1 || queen && newY - oldY > 1) {
        if (newX - oldX >= 1) {
            val ordNew = if (newY % 2 == 0) newCord.ordinal - 4 else newCord.ordinal - 5
            val new = setOfAllCoordinates.keys.toList()[ordNew]
            return Pair(loc[new]!!, new)
        }
        if (newX - oldX <= -1) {
            val ordNew = if (newY % 2 == 0) newCord.ordinal - 3 else newCord.ordinal - 4
            val new = setOfAllCoordinates.keys.toList()[ordNew]
            return Pair(loc[new]!!, new)
        }
    }
    return Pair(Coordinates.No, Coordinates.No)
}

fun getNextStep(cord: Coordinates, white: Turn, queen: Boolean): Set<Coordinates> {
    val result = mutableSetOf<Coordinates>()
    if (white == Turn.White || queen && cord != Coordinates.No) {
        val ord = cord.ordinal
        val cordY = ord / 4
        if ((ord % 4 != 3 || cordY % 2 != 0) && (ord % 4 != 0 || cordY % 2 == 0) && ord / 4 != 0) {
            val nextStepX1: Coordinates
            val nextStepX2: Coordinates
            if (cordY % 2 == 0) {
                nextStepX1 = setOfAllCoordinates.keys.toList()[ord - 3]
                nextStepX2 = setOfAllCoordinates.keys.toList()[ord - 4]
            } else {
                nextStepX1 = setOfAllCoordinates.keys.toList()[ord - 4]
                nextStepX2 = setOfAllCoordinates.keys.toList()[ord - 5]
            }
            result.addAll(setOf(nextStepX1, nextStepX2))
        }
        if ((ord % 4 != 3 || cordY % 2 != 0) && ord / 4 != 0) {
            val nextStep = setOfAllCoordinates.keys.toList()[ord - 4]
            result.addAll(setOf(nextStep))
        }
        if ((ord % 4 != 0 || cordY % 2 == 0) && ord / 4 != 0) {
            val nextStep = setOfAllCoordinates.keys.toList()[ord - 4]
            result.addAll(setOf(nextStep))
        }
    }
    if (white == Turn.Black || queen && cord != Coordinates.No) {
        val ord = cord.ordinal
        val cordY = ord / 4
        if ((ord % 4 != 3 || cordY % 2 != 0) && (ord % 4 != 0 || cordY % 2 == 0) && ord / 4 != 7) {
            val nextStepX1: Coordinates
            val nextStepX2: Coordinates
            if (cordY % 2 == 0) {
                nextStepX1 = setOfAllCoordinates.keys.toList()[ord + 4]
                nextStepX2 = setOfAllCoordinates.keys.toList()[ord + 5]
            } else {
                nextStepX1 = setOfAllCoordinates.keys.toList()[ord + 3]
                nextStepX2 = setOfAllCoordinates.keys.toList()[ord + 4]
            }
            result.addAll(setOf(nextStepX1, nextStepX2))
        }
        if ((ord % 4 != 3 || cordY % 2 != 0) && ord / 4 != 7) {
            val nextStep = setOfAllCoordinates.keys.toList()[ord + 4]
            result.addAll(setOf(nextStep))
        }
        if ((ord % 4 != 0 || cordY % 2 == 0) && ord / 4 != 7) {
            val nextStep = setOfAllCoordinates.keys.toList()[ord + 4]
            result.addAll(setOf(nextStep))
        }
    }
    return result
}

fun allowedCells(cord: Coordinates, white: Turn, loc: Map<Coordinates, Coordinates>, queen: Boolean): Set<Coordinates> {
    val allowed = mutableSetOf<Coordinates>()
    val result = getNextStep(cord, white, queen)
    result.forEach {
        if (it in loc) {
            if ((white == Turn.White && loc[it]!! in listOfBlack && cord.ordinal / 4 > it.ordinal / 4)
                || (white == Turn.Black && queen && cord.ordinal / 4 > it.ordinal / 4 && loc[it]!! in listOfWhite)) {
                val ordCord = cord.ordinal
                val ordIt = it.ordinal
                if (ordCord % 4 != 3 && ordCord / 4 > 1) {
                    val i = if ((ordIt / 4) % 2 == 0) ordIt - 3 else ordIt - 4
                    val newCord = setOfAllCoordinates.keys.toList()[i]
                    if (ordCord % 4 < newCord.ordinal % 4 && !loc.keys.contains(newCord)) allowed.add(
                        newCord
                    )
                }
                if (ordCord % 4 != 0 && ordCord / 4 > 1) {
                    val i = if ((ordIt / 4) % 2 == 0) ordIt - 4 else ordIt - 5
                    val newCord = setOfAllCoordinates.keys.toList()[i]
                    if (ordCord % 4 > newCord.ordinal % 4 && !loc.contains(newCord)) allowed.add(
                        newCord
                    )
                }
            }
            if ((white == Turn.Black && loc[it]!! in listOfWhite && cord.ordinal / 4 < it.ordinal / 4)
                || (white == Turn.White && queen && cord.ordinal / 4 < it.ordinal / 4 && loc[it]!! in listOfBlack)) {
                val ordCord = cord.ordinal
                val ordIt = it.ordinal
                if (ordCord % 4 != 3 && ordCord / 4 < 6) {
                    val i = if ((ordIt / 4) % 2 == 0) ordIt + 5 else ordIt + 4
                    val newCord = setOfAllCoordinates.keys.toList()[i]
                    if (ordCord % 4 < newCord.ordinal % 4 && !loc.contains(newCord)) allowed.add(
                        newCord
                    )
                }
                if (ordCord % 4 != 0 && ordCord / 4 < 6) {
                    val i = if ((ordIt / 4) % 2 == 0) ordIt + 4 else ordIt + 3
                    val newCord = setOfAllCoordinates.keys.toList()[i]
                    if (ordCord % 4 > newCord.ordinal % 4 && !loc.contains(newCord)) allowed.add(
                        newCord
                    )
                }
            }
        }
    }
    if (allowed.isEmpty()) {
        result.forEach {
            if (it !in loc.keys) allowed.add(it)
        }
    }
    return allowed
}
