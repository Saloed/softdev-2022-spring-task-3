package logic;

import graph.Game;

import java.util.concurrent.ThreadLocalRandom;

public class Logic {

    public int[] getTiles() {
        return tiles;
    }

    private final int[] tiles;
    private final int nbTiles;
    private int blankPos;
    private int size;

    public Logic(int size) {
        nbTiles = size * size - 1;
        tiles = new int[size * size];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = i;
        }
        blankPos = tiles.length - 1;
        this.size = size;
    }

    public void newGame() {
        do {
            shuffle();
        } while (!isSolvable());

    }


    private void shuffle() {

        int n = nbTiles;
        while (n > 1) {
            int r = ThreadLocalRandom.current().nextInt(0, n--);
            int tmp = tiles[r];
            tiles[r] = tiles[n];
            tiles[n] = tmp;
        }
    }

    public boolean isSolvable() {
        int countInversions = 0;

        for (int i = 0; i < nbTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i])
                    countInversions++;
            }
        }

        return countInversions % 2 == 0;
    }

    public boolean isSolved() {
        if (tiles[tiles.length - 1] != size * size - 1)
            return false;

        for (int i = nbTiles - 1; i >= 0; i--) {
            if (tiles[i] != i)
                return false;
        }

        return true;
    }


    public void onClick(int x, int y) {
        int gridSize = Game.STAGE_SIZE;
        int tileSize = gridSize / size;
        if (x < 0 || x > gridSize || y < 0 || y > gridSize)
            return;
        int c1 = x / tileSize;
        int r1 = y / tileSize;
        int c2 = blankPos % size;
        int r2 = blankPos / size;
        int clickPos = r1 * size + c1;
        int dir = 0;
        if (c1 == c2 && Math.abs(r1 - r2) == 1)
            dir = (r1 - r2) > 0 ? size : -size;
        else if (r1 == r2 && Math.abs(c1 - c2) == 1)
            dir = (c1 - c2) > 0 ? 1 : -1;
        if (dir != 0) {
            do {
                int newBlankPos = blankPos + dir;
                tiles[blankPos] = tiles[newBlankPos];
                blankPos = newBlankPos;
            } while (blankPos != clickPos);
            tiles[blankPos] = size * size - 1;
        }
    }
}