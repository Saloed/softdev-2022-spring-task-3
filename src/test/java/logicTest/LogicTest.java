package logicTest;

import logic.Logic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LogicTest {

    @Test
    public void sizeTest() {
        Logic logic3 = new Logic(3);
        assertEquals(9, logic3.getTiles().length);
        Logic logic4 = new Logic(4);
        assertEquals(16, logic4.getTiles().length);
        Logic logic5 = new Logic(5);
        assertEquals(25, logic5.getTiles().length);
    }

    @Test
    public void solvedTest() {
        Logic logic = new Logic(3);
        assertTrue(logic.isSolved());
    }

    @Test
    public void clickTest() {
        Logic logic3 = new Logic(3);
        logic3.onClick(400, 200);
        int[] expected3 = {0, 1, 2, 3, 4, 8, 6, 7, 5};
        assertArrayEquals(expected3, logic3.getTiles());

        Logic logic4 = new Logic(4);
        logic4.onClick(400, 300);
        int[] expected4 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 12, 13, 14, 11};
        assertArrayEquals(expected4, logic4.getTiles());

        Logic logic5 = new Logic(5);
        logic5.onClick(420, 350);
        int[] expected5 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24, 20, 21, 22, 23, 19};
        assertArrayEquals(expected5, logic5.getTiles());
    }

    @Test
    public void solvableTest() {
        Logic logic = new Logic(4);
        logic.newGame();
        assertTrue(logic.isSolvable());
    }

}
