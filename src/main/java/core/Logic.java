package core;

import javafx.ViewManager;

import java.util.Random;

public class Logic {
    private int score;
    private int life;
    private int combo;
    private int burn;
    private int activationTime;
    private int difMultiplier;
    private int stageLevel;
    private final ViewManager viewManager;
    private final boolean speedChange;
    private final boolean stageChange;

    public Logic(ViewManager viewManager, boolean speedChange, boolean stageChange) {
        this.speedChange = speedChange;
        this.stageChange = stageChange;
        this.viewManager = viewManager;
        score = 0;
        combo = 0;
        activationTime = 500;
        difMultiplier = 1;
        stageLevel = 1;
    }

    public void click() {
        combo += 1;
        score += combo;
        viewManager.getCombo().increase();
        life += 10;
        if (score > difMultiplier * 300) {
            difMultiplier += 1;
            if (burn < 17) {
                burn += 1;
                viewManager.setBurn(burn);
            }
            if (burn >= 19) {
                burn = 20;
                viewManager.setBurn(20);
            }
        }
        if (combo % 10 == 0 && activationTime > 100 && speedChange) {
            activationTime -= 50;
            viewManager.increaseSpeed();
        }
        if (score > stageLevel * 500 && stageLevel < 3 && stageChange) {
            nextStage();
        }
        viewManager.click(score);
    }

    private void nextStage() {
        viewManager.nextStage();
        stageLevel += 1;
    }

    public void miss() {
        combo = 0;
        viewManager.miss(score);
    }


    public static <T> int random(T[] array) {
        return new Random().nextInt(array.length);
    }

    public int getScore() {
        return score;
    }


}
