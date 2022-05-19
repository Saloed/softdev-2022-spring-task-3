package core;

import controller.GameListener;
import javafx.application.Platform;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Logic {
    private int score;
    private int combo;
    private int burn;
    private int activationTime;
    private int difMultiplier;
    private int stageLevel;
    private final boolean speedChange;
    private final boolean stageChange;
    private GameListener listener;
    private int squareCount;
    private Timer glowTimer;

    public Logic(boolean speedChange, boolean stageChange) {
        this.speedChange = speedChange;
        this.stageChange = stageChange;
        squareCount = 3;
        score = 0;
        combo = 0;
        activationTime = 500;
        difMultiplier = 1;
        stageLevel = 1;
        burn = 1;
    }

    public void assignListener(GameListener listener) {
        this.listener = listener;
        listener.setBurn(burn);
    }

    public void click() {
        combo += 1;
        score += combo;
        if (score > difMultiplier * 300) {
            difMultiplier += 1;
            if (burn < 17) {
                burn += 1;
                if (listener != null) listener.setBurn(burn);
            }
            if (burn >= 19) {
                burn = 20;
                if (listener != null) listener.setBurn(burn);
            }
        }
        if (combo % 10 == 0 && activationTime > 100 && speedChange) {
            activationTime -= 50;
            glowTimer.cancel();
            activateGlow();
        }
        if (score > stageLevel * 500 && stageLevel < 3 && stageChange) {
            if (listener != null) listener.stopGlow();
            glowTimer.cancel();
            squareCount += 1;
            if (listener != null) listener.nextStage();
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

    public void activateGlow() {
        Integer[] squares = new Integer[squareCount * squareCount];
        TimerTask glowTask = new TimerTask() {
            @Override
            public void run() {
                listener.activateGLow(random(squares));
            }
        };
        glowTimer = new Timer();
        glowTimer.schedule(glowTask, 300, activationTime);
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

    public int getStageLevel() {
        return stageLevel;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setActivationTime(int activationTime) {
        this.activationTime = activationTime;
    }

    public int getSquareCount() {
        return squareCount;
    }

}
