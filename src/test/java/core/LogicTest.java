package core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogicTest {
    @Test
    public void click() {
        Logic logic = new Logic(true, true);
        logic.setScore(300);
        logic.click();
        assertEquals(2, logic.getBurn());
        logic.setScore(300);
        logic.setBurn(20);
        logic.click();
        assertEquals(20, logic.getBurn());
        assertEquals(500, logic.getActivationTime());
        logic.setCombo(9);
        logic.click();
        assertEquals(450, logic.getActivationTime());
    }

    @Test
    public void miss() {
        Logic logic = new Logic(true, true);
        logic.setCombo(500);
        logic.setActivationTime(100);
        assertEquals(500, logic.getCombo());
        assertEquals(100, logic.getActivationTime());
        logic.miss();
        assertEquals(0, logic.getCombo());
        assertEquals(500, logic.getActivationTime());
    }

    @Test
    public void nextStage() {
        Logic logic = new Logic(true, true);
        assertEquals(1, logic.getStageLevel());
        logic.nextStage();
        assertEquals(2, logic.getStageLevel());
    }
}
