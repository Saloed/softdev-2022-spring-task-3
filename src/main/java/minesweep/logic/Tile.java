package minesweep.logic;

import minesweep.Coordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Tile {

    private final Coordinates coordinates;

    private boolean isFlagged;
    private boolean isBomb;
    private boolean isEmpty;

    private int bombsAround = -1;

    private Listener listener;

    public Tile(int row, int col) {
        this(new Coordinates(col, row));
    }

    public Tile(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
        if (listener != null) listener.onTileFlagged(this, flagged);

    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
        if (listener != null) listener.onTileBomb(this, bomb);
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
        if (listener != null) listener.onTileEmpty(this, empty);
    }

    public int getBombsAround() {
        return bombsAround;
    }

    public void setBombsAround(int bombsAround) {
        this.bombsAround = bombsAround;
        if (listener != null) listener.onTileSetBombsAround(this, bombsAround);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return isFlagged == tile.isFlagged &&
                isBomb == tile.isBomb &&
                isEmpty == tile.isEmpty &&
                bombsAround == tile.bombsAround &&
                Objects.equals(coordinates, tile.coordinates) &&
                Objects.equals(listener, tile.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, isFlagged, isBomb, isEmpty, bombsAround, listener);
    }

    public interface Listener {
        void onTileFlagged(Tile tile, boolean flagged);
        void onTileEmpty(Tile tile, boolean empty);
        void onTileBomb(Tile tile, boolean bomb);
        void onTileSetBombsAround(Tile tile, int bombsAround);
    }
}
