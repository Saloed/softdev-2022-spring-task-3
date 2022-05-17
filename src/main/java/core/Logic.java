package core;

import java.util.Random;

public class Logic {
    private int score;
    private int combo;
    private int burn;
    private int activationTime;
    private int difMultiplier;
    private int stageLevel;
    private final boolean speedChange;
    private final boolean stageChange;

    public Logic(boolean speedChange, boolean stageChange) {
        this.speedChange = speedChange;
        this.stageChange = stageChange;
        score = 0;
        combo = 0;
        activationTime = 500;
        difMultiplier = 1;
        stageLevel = 1;
        burn = 1;
    }

    public void click() {
        combo += 1;
        score += combo;
        if (score > difMultiplier * 300) {
            if (burn < 17) {
                burn += 1;
            }
            if (burn >= 19) {
                burn = 20;
            }
        }
        if (combo % 10 == 0 && activationTime > 100 && speedChange) {
            activationTime -= 50;
        }
    }

    public void nextStage() {
        stageLevel += 1;
    }

    public void miss() {
        combo = 0;
        activationTime = 500;
    }


    public static <T> int random(T[] array) {
        return new Random().nextInt(array.length);
    }

    public int getScore() {
        return score;
    }

    public int getActivationTime() {
        return activationTime;
    }

    public void setBurn(int burn) {
        this.burn = burn;
    }

    public int getBurn() {
        return burn;
    }

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public boolean getSpeedChange() {
        return speedChange;
    }

    public boolean getStageChange() {
        return stageChange;
    }

    public int getDifMultiplier() {
        return difMultiplier;
    }

    public int getStageLevel() {
        return stageLevel;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public void increaseDifMultiplier() {
        difMultiplier += 1;
    }

    public void setActivationTime(int activationTime) {
        this.activationTime = activationTime;
    }
}
