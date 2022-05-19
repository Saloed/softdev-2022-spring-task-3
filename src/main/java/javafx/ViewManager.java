package javafx;

import controller.GameListener;
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
    LifeBar lifeBar;
    Combo combo;
    private final int glowTime;
    private Square[] squares;
    private GameListener listener;

    public ViewManager() {
        this.gamePane = new AnchorPane();
        this.gameScene = new Scene(gamePane, width, height);
        this.gameStage = new Stage();
        this.lifeBar = new LifeBar(this);
        this.combo = new Combo(this);
        gameStage.setScene(gameScene);
        gameStage.show();
        glowTime = 1;
        combo.activateCombo(gamePane);
    }

    public void assignListener(GameListener listener) {
        this.listener = listener;
        createSquares(listener.getSquareCount(), 100);
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
        gameStage.show();
        createSquares(listener.getSquareCount(), 300);
    }

    public void createSquares(int squareCount, int delay) {
        double x = 55 + 15 * (squareCount - 3);
        double y = 55;
        this.squares = new Square[squareCount * squareCount];
        int count = 0;
        for (int k = 1; k <= squareCount; k++) {
            for (int i = 1; i <= squareCount; i++) {
                squares[count] = new Square(x, y, listener);
                gamePane.getChildren().add(squares[count]);
                x += 70;
                count += 1;
            }
            x = 55 + 15 * (squareCount - 3);
            y += 70;
        }
        listener.startGame();
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
        Platform.runLater(() -> combo.getText().setText(score + "   " + "0X"));
    }

    public void activateGlow(int squareNumber) {
        squares[squareNumber].glow(glowTime);
    }

    public int getScore() {
        return listener.getScore();
    }

    public Combo getCombo() {
        return combo;
    }

    public void stopGlow() {
        for (int i = 0; i <= listener.getSquareCount() * listener.getSquareCount() - 1; i++) {
            squares[i].normalState();
        }
    }


}
