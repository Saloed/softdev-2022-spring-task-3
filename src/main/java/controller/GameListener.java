package controller;

import core.Logic;
import javafx.GameEndScene;
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


    public int getScore() {
        return logic.getScore();
    }

    public int getSquareCount() {
        return logic.getSquareCount();
    }

    public void startGame() {
        logic.activateGlow(500);
    }

    public void stopGlow() {
        viewManager.stopGlow();
    }

    public void endGame() {
        viewManager.close();
        GameEndScene endScene = new GameEndScene();
        endScene.activate(viewManager);
    }

    public void setLifeBar(int life) {
        viewManager.setLife(life);
    }
}
