package controller;

import core.Logic;
import javafx.ViewManager;

public class GameListener {
    private final ViewManager viewManager;
    private final Logic logic;

    public GameListener(ViewManager viewManager, Logic logic) {
        this.logic = logic;
        this.viewManager = viewManager;
    }

    public void click() {
        logic.click();
        viewManager.getCombo().increase();
        int score = logic.getScore();
        int burn = logic.getBurn();
        int combo = logic.getCombo();
        int activationTime = logic.getActivationTime();
        boolean speedChange = logic.getSpeedChange();
        boolean stageChange = logic.getStageChange();
        int difMultiplier = logic.getDifMultiplier();
        int stageLevel = logic.getStageLevel();
        viewManager.click(logic.getScore());
        if (score > difMultiplier * 300) {
            logic.increaseDifMultiplier();
            if (burn < 17) {
                viewManager.setBurn(burn);
            }
            if (burn >= 19) {
                viewManager.setBurn(20);
            }
        }
        if (combo % 10 == 0 && activationTime > 100 && speedChange) {
            viewManager.increaseSpeed();
        }
        if (score > stageLevel * 500 && stageLevel < 3 && stageChange) {
            nextStage();
        }
        if (combo % 10 == 0 && activationTime > 100 && speedChange)
            viewManager.increaseSpeed();
    }

    public void miss() {
        logic.miss();
        viewManager.miss(logic.getScore());
    }

    public void nextStage() {
        logic.nextStage();
        viewManager.nextStage();
    }

    public int getActivationTime() {
        return logic.getActivationTime();
    }

    public int getScore() {
        return logic.getScore();
    }
}
