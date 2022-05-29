package logic;

import java.util.concurrent.ThreadLocalRandom;

public class Logic {
    public int[] getTiles() {
        return tiles;
    }

    private final int[] tiles;
    private final int nbTiles;
    private int blankPos;

    public Logic(int size) {
        nbTiles = size * size - 1;
        tiles = new int[size * size];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = (i + 1) % tiles.length;
        }
        blankPos = tiles.length - 1;
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

    private boolean isSolvable() {
        int countInversions = 0;

        for (int i = 0; i < nbTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i])
                    countInversions++;
            }
        }

        return countInversions % 2 == 0;
    }

    private boolean isSolved() {
        if (tiles[tiles.length - 1] != 0)
            return false;

        for (int i = nbTiles - 1; i >= 0; i--) {
            if (tiles[i] != i + 1)
                return false;
        }

        return true;
    }


//    public void onClick(int x, int y, int gridSize, int tileSize){
//        // get position of the click
//        int ex = x - margin;
//        int ey = y - margin;
//
//// click in the grid ?
//        if (ex < 0 || ex > gridSize  || ey < 0  || ey > gridSize)
//            return;
//
//// get position in the grid
//        int c1 = ex / tileSize;
//        int r1 = ey / tileSize;
//
//// get position of the blank cell
//        int c2 = blankPos % size;
//        int r2 = blankPos / size;
//
//// we convert in the 1D coord
//        int clickPos = r1 * size + c1;
//
//        int dir = 0;
//
//// we search direction for multiple tileView moves at once
//        if (c1 == c2  &&  Math.abs(r1 - r2) > 0)
//            dir = (r1 - r2) > 0 ? size : -size;
//        else if (r1 == r2 && Math.abs(c1 - c2) > 0)
//            dir = (c1 - c2) > 0 ? 1 : -1;
//
//        if (dir != 0) {
//            // we move tiles in the direction
//            do {
//                int newBlankPos = blankPos + dir;
//                tiles[blankPos] = tiles[newBlankPos];
//                blankPos = newBlankPos;
//            } while(blankPos != clickPos);
//
//            tiles[blankPos] = 0;
//    }
//
//
}
