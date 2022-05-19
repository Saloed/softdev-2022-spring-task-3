import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Test {

    @Test
    fun testNextStep() {
        val res = setOf("B4", "D4")
        val s = getNextStep(Pair(Cell("C3").centerX, Cell("C3").centerY), "white", false)
        assertEquals(res, s)
    }

    @Test
    fun testAllowedCells() {
        val loc = (coordinatesW + coordinatesB).toMutableList()
        val ind = loc.indexOf("D6")
        loc.removeAt(ind)
        loc.add(ind, "F4")
        val res = setOf("E5", "G5")
        val s = allowedCells(Pair(Cell("F4").centerX, Cell("F4").centerY), "white", loc, false)
        assertEquals(res, s)
    }

    @Test
    fun testEat() {
        val loc = (coordinatesW + coordinatesB).toMutableList()
        val ind = loc.indexOf("D6")
        loc.removeAt(ind)
        loc.add(ind, "F4")
        val res = Pair("D6", "F4")
        val oldPos = Pair(Cell("G3").centerX, Cell("G3").centerY)
        val pos = Pair(Cell("E5").centerX, Cell("E5").centerY)
        val s = eat(oldPos.first, oldPos.second, pos.first, pos.second, loc, "white")
        assertEquals(res, s)
    }
}