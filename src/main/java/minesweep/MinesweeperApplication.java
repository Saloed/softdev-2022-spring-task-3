package minesweep;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import minesweep.logic.Logic;
import minesweep.logic.Tile;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 Пулы потоков бывают:
 FixedThreadPool - при создании задается конкретное число потоков в этом пуле. При передачи задачи в пул - берется любой свободный поток оттуда.
 CachedThreadPool - его размер определяется автоматически JVM в зависимости от количества свободных ресурсов процессора и памяти.
 **/


public class MinesweeperApplication extends Application {

    private final static double r = 20; // the inner radius from hexagon center to outer corner
    private final static double innerRadius = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private static final int ROWS = 10; // how many rows of tiles should be created
    private static final int COLUMNS = 10; // the amount of tiles that are contained in each row
    private final static int WINDOW_WIDTH = ROWS * 50;
    private final static int WINDOW_HEIGHT = COLUMNS * 40;
    private final int BOMBS = ROWS * COLUMNS / 10;
    private AnchorPane boardView = new AnchorPane();
    private Map<Coordinates, TileView> tileViewMap;
    private Logic logic;
    private Scene content;
    private Stage root;
    private BorderPane borderPane = new BorderPane(boardView);

    public Logic.Listener logicListener = new Logic.Listener() {
        @Override
        public void onLost() {
            lose();
        }

        @Override
        public void onWon() {
            win();
        }

        @Override
        public void onTileFlagged(Tile tile, boolean flagged) {
            //tileViewMap.get(tile.getCoordinates()).setFlagged(flagged);
        }

        @Override
        public void onTileEmpty(Tile tile, boolean empty) {

        }

        @Override
        public void onTileBomb(Tile tile, boolean bomb) {

        }

        @Override
        public void onTileSetBombsAround(Tile tile, int bombsAround) {
            TileView tileView = tileViewMap.get(tile.getCoordinates());
            switch (bombsAround) {
                case 0:
                    tileView.setImage("empty");
                    break;
                case 1:
                    tileView.setImage("oneMineAround");
                    break;
                case 2:
                    tileView.setImage("twoMinesAround");
                    break;
                case 3:
                    tileView.setImage("threeMinesAround");
                    break;
                case 4:
                    tileView.setImage("fourMinesAround");
                    break;
                case 5:
                    tileView.setImage("fiveMinesAround");
                    break;
                case 6:
                    tileView.setImage("sixMinesAround");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onTileOpen(Tile tile) {
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage root) {
        tileViewMap = new HashMap<>(ROWS * COLUMNS);
        this.root = root;
        newGame();
    }

    private void newGame() {
        content = new Scene(borderPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        root.setScene(content);
        root.setTitle("hex-minesweeper");
        logic = new Logic(ROWS, COLUMNS, BOMBS);
        logic.setListener(logicListener);
        fillBoardWithTiles();
        root.show();
    }

    private void fillBoardWithTiles() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {

                Coordinates coordinates = new Coordinates(col, row);
                TileView tileView = new TileView(logic.tileMap.get(coordinates));
                tileViewMap.put(coordinates, tileView);
                tileView.setOnMouseClicked(new TileMouseClickHandler(tileView));
                boardView.getChildren().add(tileView);
            }
        }
    }

    private void lose() {
        logic.bombs.forEach(bomb -> tileViewMap.get(bomb.getCoordinates()).setImage("mine"));
        System.out.println("oh no you have died!");
        endGame();
    }

    private void win() {
        System.out.println("congratulations! you're the best minesweeper!");
        endGame();
    }

    private void endGame() {
        logic.tileMap.values().stream()
                .filter(tile -> tile.isFlagged() && !tile.isBomb())
                .forEach(tile -> tileViewMap.get(tile.getCoordinates()).setImage("incorrectFlag"));
        tileViewMap.values().forEach(tileView -> tileView.setOnMouseClicked(null));
    }

    private Set<Optional<TileView>> getNeighboursOf(TileView tileView) {
        Set<Optional<TileView>> neighbours = new HashSet<>();
        int col = tileView.getCoordinates().getX();
        int row = tileView.getCoordinates().getY();


        neighbours.add(getTileView(row + 1, col));
        neighbours.add(getTileView(row - 1, col));
        neighbours.add(getTileView(row, col + 1));
        neighbours.add(getTileView(row, col - 1));

        if (row % 2 == 0) {
            neighbours.add(getTileView(row - 1, col - 1));
            neighbours.add(getTileView(row + 1, col - 1));
        } else {
            neighbours.add(getTileView(row - 1, col + 1));
            neighbours.add(getTileView(row + 1, col + 1));
        }
        return neighbours;
    }

    private Optional<TileView> getTileView(int row, int col) {
        return Optional.ofNullable(tileViewMap.get(Coordinates.getCoordinates(col, row)));
    }

    private class TileMouseClickHandler implements EventHandler<MouseEvent> {
        private final TileView tileView;

        public TileMouseClickHandler(TileView tileView) {
            this.tileView = tileView;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                logic.openTile(tileView.tile);
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                tileView.setFlagged(!tileView.isFlagged());
            }
        }
    }
}