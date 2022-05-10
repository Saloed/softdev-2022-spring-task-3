package javafx;

import core.Logic;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewManager {

    private int width = 300;
    private int height = 300;
    AnchorPane gamePane;
    Scene gameScene;
    Stage gameStage;
    int squareCount;
    Logic logic;

    public ViewManager(boolean speedChange, boolean stageChange) {
        this.gamePane = new AnchorPane();
        this.gameScene = new Scene(gamePane, width, height);
        this.gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.show();
        squareCount = 3;
        this.logic = new Logic(gamePane, gameStage, this, speedChange, stageChange);
        squareCount = 3;
        createSquares(squareCount);
    }

    public void nextStage() {
        gameStage.close();
        this.gamePane = new AnchorPane();
        width += 98;
        height += 55;
        this.gameScene = new Scene(gamePane, width, height);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        logic.setPane(this.gamePane);
        logic.setStage(this.gameStage);
        gameStage.show();
        squareCount += 1;
        createSquares(squareCount);
    }

    public void createSquares(int squareCount) {
        double x = 55 + 15 * (squareCount - 3);
        double y = 55;
        Square[] squares = new Square[squareCount * squareCount];
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
        logic.activateGlow(squares);
    }

    public void close() {
        gameStage.close();
    }
}
