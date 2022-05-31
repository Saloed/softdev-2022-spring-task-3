import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Test {

    @Test
    fun testCentreSearchX() {
        val centreX = 258.0
        val centreY = 588.0
        assertEquals(Logic().centreSearchX(237.0, 546.0), centreX to centreY)
    }

    @Test
    fun testStepsForCheckers1() {
        val l = listOf(Pair(368.0, 478.0), Pair(148.0, 478.0))
        assertEquals(Logic().stepsForCheckers1(258.0 to 588.0, true, queen = false), l)
    }


}