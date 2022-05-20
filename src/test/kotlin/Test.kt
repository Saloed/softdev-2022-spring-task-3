import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Test {

    @Test
    fun testNextStep() {
        val res = setOf(Coordinates.B4, Coordinates.D4)
        val s = getNextStep(Coordinates.C3, Turn.White, false)
        assertEquals(res, s)
    }

    @Test
    fun testAllowedCells() {
        val loc = (listOfOccupiedCells).toMutableList()
        val ind = loc.indexOf(Coordinates.D6)
        loc.removeAt(ind)
        loc.add(ind, Coordinates.F4)
        val res = setOf(Coordinates.E5, Coordinates.G5)
        val s = allowedCells(Coordinates.F4, Turn.White, loc, false)
        assertEquals(res, s)
    }

    @Test
    fun testEat() {
        val loc = (listOfOccupiedCells).toMutableList()
        val ind = loc.indexOf(Coordinates.D6)
        loc.removeAt(ind)
        loc.add(ind, Coordinates.F4)
        val res = Pair(Coordinates.D6, Coordinates.F4)
        val oldPos = Coordinates.G3
        val pos = Coordinates.E5
        val s = eat(oldPos, pos, loc, Turn.White)
        assertEquals(res, s)
    }
}