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
        viewManager.click(logic.getScore());
    }

    public void miss() {
        logic.miss();
        viewManager.miss(logic.getScore());
    }

    public void nextStage() {
        logic.nextStage();
        viewManager.nextStage();
    }

    public void activateGLow(int squareNumber) {
        viewManager.activateGlow(squareNumber);
    }

    public void setBurn(int burn) {
        viewManager.setBurn(burn);
    }


    public int getScore() {
        return logic.getScore();
    }

    public int getSquareCount() {
        return logic.getSquareCount();
    }

    public void startGame() {
        logic.activateGlow();
    }

    public void stopGlow() {
        viewManager.stopGlow();
    }
}
