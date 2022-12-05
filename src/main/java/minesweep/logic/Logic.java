package minesweep.logic;

import minesweep.Coordinates;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Logic {

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
//кэширующий пул потоков, который создает потоки по мере необходимости,
// но переиспользует неактивные потоки (и подчищает потоки, которые были неактивные некоторое время)
    public final int height; //высота
    public final int width;//ширина

    public final Set<Tile> bombs;
    public Set<Tile> openTileViews;

    public final Map<Coordinates, Tile> tileMap;

    public final int bombsQuantity;

    private Listener listener;
    private final Tile.Listener tileListener = new Tile.Listener() {
        @Override
        public void onTileFlagged(Tile tile, boolean flagged) {
            if (listener != null) {
                listener.onTileFlagged(tile, flagged);
            }
        }

        @Override
        public void onTileEmpty(Tile tile, boolean empty) {
            if (listener != null) listener.onTileEmpty(tile, empty);
        }

        @Override
        public void onTileBomb(Tile tile, boolean bomb) {
            if (listener != null) listener.onTileBomb(tile, bomb);
        }

        @Override
        public void onTileSetBombsAround(Tile tile, int bombsAround) {
            if (listener != null) listener.onTileSetBombsAround(tile, bombsAround);
        }

    };

    public Logic(
            int height,
            int width,
            int bombsQuantity) {
        this.bombsQuantity = bombsQuantity;
        this.height = height;
        this.width = width;
        tileMap = new HashMap<>(height * width);
        bombs = new HashSet<>(bombsQuantity);
        openTileViews = new HashSet<>();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile tile = new Tile(row, col);
                tile.setListener(tileListener);
                Coordinates coordinates = new Coordinates(col, row);
                tileMap.put(coordinates, tile);
            }
        }
    }


    public void plantBombs(int bombQuantity, Coordinates exceptOf) {
        for (int i = 0; i < bombQuantity; i++) {
            int col = ThreadLocalRandom.current().nextInt(0, width - 1);
            int row = ThreadLocalRandom.current().nextInt(0, height - 1);
            Coordinates coordinates = new Coordinates(col, row);
            Tile tile = tileMap.get(coordinates);
            if (tile == null || bombs.contains(tile) || coordinates.equals(exceptOf)) {
                i -= 1;
            } else tileToBomb(tile);
        }
    }

    public int countBombsAroundForTile(Tile tile) {
        if (tile.getBombsAround() == -1) {
            long count = 0L;
            for (Tile t : getNeighboursOf(tile)) {
                if (t != null) {
                    if (t.isBomb()) {
                        count++;
                    }
                }
            }
            int bombsAround = (int) count;
            tile.setBombsAround(bombsAround);
        }
        return tile.getBombsAround();
    }

    public void tileToBomb(Tile tile) {
        tile.setBomb(true);
        bombs.add(tile);
    }

    public void openTile(Tile tile) {
        if (openTileViews.size() == 0) {
            plantBombs(bombsQuantity, tile.getCoordinates());
        }
        if (openTileViews.contains(tile)) {
            return;
        }
        if (tile.isBomb()) {
            lose();
        } else if (countBombsAroundForTile(tile) == 0) {
            openTilesAroundOf(tile);
        }
        openTileViews.add(tile);
        if (openTileViews.size() == width * height - bombsQuantity) {
            win();
        }
    }

    private void openTilesAroundOf(Tile tile) {
        for (Tile t : getNeighboursOf(tile)) {
            if (t != null) {
                threadPool.submit(() -> openTile(t));
            }
        }
    }

    public Set<Tile> getNeighboursOf(Tile tile) {
        Set<Tile> neighbours = new HashSet<>();
        int col = tile.getCoordinates().getX();
        int row = tile.getCoordinates().getY();


        neighbours.add(getTile(row + 1, col));
        neighbours.add(getTile(row - 1, col));
        neighbours.add(getTile(row, col + 1));
        neighbours.add(getTile(row, col - 1));

        if (row % 2 == 0) {
            neighbours.add(getTile(row - 1, col - 1));
            neighbours.add(getTile(row + 1, col - 1));
        } else {
            neighbours.add(getTile(row - 1, col + 1));
            neighbours.add(getTile(row + 1, col + 1));
        }
        return neighbours;
    }

    private Tile getTile(int row, int col) {
        return (tileMap.get(Coordinates.getCoordinates(col, row)));
    }

    private void lose() {
        if (listener != null) listener.onLost();
    }

    private void win() {
        if (listener != null) listener.onWon();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onLost();
        void onWon();
        void onTileFlagged(Tile tile, boolean flagged);
        void onTileEmpty(Tile tile, boolean empty);
        void onTileBomb(Tile tile, boolean bomb);
        void onTileSetBombsAround(Tile tile, int bombsAround);
    }

}
