package minesweep.logic;

import minesweep.Coordinates;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Logic {

    public static final int DEFAULT_SIZE = 10;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    public final int height;
    public final int width;

    public final Set<Tile> bombs;
    public Set<Tile> openTileViews;

    public final Map<Coordinates, Tile> tileMap;

    public final int bombsQuantity;

    private Listener listener;
    private Tile.Listener tileListener = new Tile.Listener() {
        @Override
        public void onTileFlagged(Tile tile, boolean flagged) {
            if (listener != null) listener.onTileFlagged(tile, flagged);
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

        @Override
        public void onTileOpen(Tile tile) {
            if (listener != null) listener.onTileOpen(tile);
            tile.setBombsAround(countBombsAroundForTile(tile));
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

    public Logic(int size, int bombsQuantity) {
        this(size, size, bombsQuantity);
    }

    public Logic(int bombsQuantity) {
        this(DEFAULT_SIZE, bombsQuantity);
    }

    public void plantBombs(int bombQuantity, Coordinates exceptOf) {
        for (int i = 0; i < bombQuantity; i++) {
            int col = ThreadLocalRandom.current().nextInt(0, width - 1);
            int row = ThreadLocalRandom.current().nextInt(0, height - 1);
            Coordinates coordinates = new Coordinates(col, row);
            Tile tile = tileMap.get(coordinates);
            if (tile == null || bombs.contains(tile) || coordinates.equals(exceptOf)) i -= 1;
            else tileToBomb(tile);
        }
    }

    public int countBombsAroundForTile(Tile tile) {
        if (tile.getBombsAround() == -1) {
            int bombsAround = (int) getNeighboursOf(tile).stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(Tile::isBomb)
                    .count();
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
        getNeighboursOf(tile)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(neighbour -> threadPool.submit(() -> openTile(neighbour)));
    }

    public Set<Optional<Tile>> getNeighboursOf(Tile tile) {
        Set<Optional<Tile>> neighbours = new HashSet<>();
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

    private Optional<Tile> getTile(int row, int col) {
        return Optional.ofNullable(tileMap.get(Coordinates.getCoordinates(col, row)));
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
        void onTileOpen(Tile tile);
    }

}
