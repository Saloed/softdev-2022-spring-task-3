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
    private int life;
    private Timer lifeTimer;

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
        life = 100;
    }

    public void assignListener(GameListener listener) {
        this.listener = listener;
    }

    public void click() {
        combo += 1;
        score += combo;
        increaseLife();
        if (score > difMultiplier * 300) {
            difMultiplier += 1;
            if (burn < 17) {
                burn += 1;
            }
            if (burn >= 19) {
                burn = 20;
            }
        }
        if (combo % 10 == 0 && activationTime > 100 && speedChange) {
            activationTime -= 50;
            if (glowTimer != null) glowTimer.cancel();
            activateGlow(0);
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
        if (glowTimer != null) glowTimer.cancel();
        activateGlow(100);
    }


    public static <T> int random(T[] array) {
        return new Random().nextInt(array.length);
    }

    public void activateGlow(int delay) {
        Integer[] squares = new Integer[squareCount * squareCount];
        TimerTask glowTask = new TimerTask() {
            @Override
            public void run() {
                if (listener != null) listener.activateGLow(random(squares));
            }
        };
        glowTimer = new Timer();
        glowTimer.schedule(glowTask, delay, activationTime);
        if (lifeTimer == null) activateLife();
    }

    public void activateLife() {
        if (lifeTimer != null) lifeTimer.cancel();
        this.lifeTimer = new Timer();
        TimerTask lifeTask = new TimerTask() {
            @Override
            public void run() {
                life -= burn;
                listener.setLifeBar(life);
                if (life <= 0) {
                    lifeTimer.cancel();
                    Platform.runLater(() -> listener.endGame());
                }
            }
        };
        lifeTimer.schedule(lifeTask, 1000, 100);
    }

    public void increaseLife() {
        if (life > 90) {
            life = 100;
        }
        else {
            life += 10;
        }

        if (listener != null) listener.setLifeBar(life);
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
