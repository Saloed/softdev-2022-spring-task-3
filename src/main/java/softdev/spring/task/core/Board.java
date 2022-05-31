package softdev.spring.task.core;

public class Board {

    private final int width;

    private final int height;

    private final int cellNumberX;

    private final int cellNumberY;

    public Board(int width, int height, int cellNumberX, int cellNumberY) {
        this.width = width;
        this.height = height;
        this.cellNumberX = cellNumberX;
        this.cellNumberY = cellNumberY;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellNumberX() {
        return cellNumberX;
    }

    public int getCellNumberY() {
        return cellNumberY;
    }

}
