package core;

import javafx.Combo;
import javafx.LifeBar;
import javafx.Square;
import javafx.ViewManager;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Logic {
    private int score;
    private final Combo combo;
    private Stage stage;
    private final LifeBar lifeBar;
    private int glowTime;
    private Timer glowTimer;
    private Square[] squares;
    private AnchorPane gamePane;
    private int activationTime;
    private int difMultiplier;
    private int stageLevel;
    private final ViewManager viewManager;
    private final boolean speedChange;
    private final boolean stageChange;

    public Logic(AnchorPane gamePane, Stage stage, ViewManager viewManager, boolean speedChange, boolean stageChange) {
        this.speedChange = speedChange;
        this.stageChange = stageChange;
        this.viewManager = viewManager;
        this.gamePane = gamePane;
        this.stage = stage;
        lifeBar = new LifeBar(this);
        score = 0;
        combo = new Combo(this);
        combo.activateCombo(gamePane);
        glowTime = 1;
        activationTime = 500;
        difMultiplier = 1;
        stageLevel = 1;
    }

    public void click() {
        combo.increase();
        score += combo.getCombo();
        combo.getText().setText(score + "   " + combo.getCombo() + "X");
        lifeBar.increase();
        if (score > difMultiplier * 300) {
            if (lifeBar.getBurn() < 17) lifeBar.increaseBurn();
            if (lifeBar.getBurn() >= 19) lifeBar.setBurn(20);
            difMultiplier += 1;
        }
        if (combo.getCombo() % 10 == 0 && activationTime > 100 && speedChange) {
            activationTime -= 50;
            glowTimer.cancel();
            activateGlow(squares);
        }
        if (score > stageLevel * 500 && stageLevel < 3 && stageChange) {
            nextStage();
        }
    }

    private void nextStage() {
        glowTimer.cancel();
        viewManager.nextStage();
        stageLevel += 1;
        combo.activateCombo(gamePane);
        lifeBar.deactivate();
        lifeBar.activate();
    }

    public void miss() {
        combo.nullify();
        activationTime = 500;
        Platform.runLater(() -> combo.getText().setText(score + "   " + "0X"));
    }

    public void close() {
        stage.close();
        System.exit(0);
    }

    public void activateGlow(Square[] squares) {
        this.squares = squares;
        TimerTask glowTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> squares[Logic.random(squares)].glow(glowTime));
            }
        };
        glowTimer = new Timer();
        glowTimer.schedule(glowTask, 0, activationTime);
    }

    public static <T> int random(T[] array) {
        return new Random().nextInt(array.length);
    }

    public AnchorPane getPane() {
        return gamePane;
    }

    public int getScore() {
        return score;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPane(AnchorPane pane) {
        this.gamePane = pane;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

}
