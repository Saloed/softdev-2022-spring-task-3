package javafx;

import core.Logic;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class ViewManager {

    private int width = 300;
    private int height = 300;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private int squareCount;
    private int activationTime;
    private final Logic logic;
    LifeBar lifeBar;
    Combo combo;
    private final int glowTime;
    private Timer glowTimer;
    private Square[] squares;

    public ViewManager(boolean speedChange, boolean stageChange) {
        this.gamePane = new AnchorPane();
        this.gameScene = new Scene(gamePane, width, height);
        this.gameStage = new Stage();
        this.lifeBar = new LifeBar(this);
        this.combo = new Combo(this);
        this.logic = new Logic(this, speedChange, stageChange);
        gameStage.setScene(gameScene);
        gameStage.show();
        squareCount = 3;
        glowTime = 1;
        activationTime = 500;
        createSquares(squareCount);
        combo.activateCombo(gamePane);
    }

    public void nextStage() {
        gameStage.close();
        this.gamePane = new AnchorPane();
        width += 98;
        height += 55;
        this.gameScene = new Scene(gamePane, width, height);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        combo.activateCombo(gamePane);
        lifeBar.deactivate();
        lifeBar.activate();
        glowTimer.cancel();
        gameStage.show();
        squareCount += 1;
        createSquares(squareCount);
    }

    public void createSquares(int squareCount) {
        double x = 55 + 15 * (squareCount - 3);
        double y = 55;
        this.squares = new Square[squareCount * squareCount];
        int count = 0;
        for (int k = 1; k <= squareCount; k++) {
            for (int i = 1; i <= squareCount; i++) {
                squares[count] = new Square(x, y, logic);
                gamePane.getChildren().add(squares[count]);
                x += 70;
                count += 1;
            }
            x = 55 + 15 * (squareCount - 3);
            y += 70;
        }
        activateGlow(squares);
    }

    public void click(int score) {
        combo.getText().setText(score + "   " + combo.getCombo() + "X");
        lifeBar.increase();
    }

    public AnchorPane getPane() {
        return gamePane;
    }

    public void close() {
        gameStage.close();
    }

    public void setBurn(int burn) {
        lifeBar.setBurn(burn);
    }

    public void miss(int score) {
        combo.nullify();
        activationTime = 500;
        Platform.runLater(() -> combo.getText().setText(score + "   " + "0X"));
    }

    public void increaseSpeed() {
        activationTime -= 50;
        glowTimer.cancel();
        activateGlow(squares);
    }

    public void activateGlow(Square[] squares) {
        TimerTask glowTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> squares[Logic.random(squares)].glow(glowTime));
            }
        };
        glowTimer = new Timer();
        glowTimer.schedule(glowTask, 0, activationTime);
    }

    public int getScore() {
        return logic.getScore();
    }

    public Combo getCombo() {
        return combo;
    }
}
