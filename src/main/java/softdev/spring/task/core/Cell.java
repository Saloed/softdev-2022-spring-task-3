package softdev.spring.task.core;

public class Cell {

    public static final Cell DOWN = new Cell(0,1);

    public static final Cell UP = new Cell(0,-1);

    public static final Cell LEFT = new Cell(-1,0);

    public static final Cell RIGHT = new Cell(1,0);

    public static final Cell UP_LEFT = new Cell(-1, 1);

    public static final Cell UP_RIGHT = new Cell(1, 1);

    public static final Cell DOWN_RIGHT = new Cell(1, -1);

    public static final Cell DOWN_lEFT = new Cell(-1, -1);

    public int x;

    public int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Cell cell) {
        this.x += cell.x;
        this.y += cell.y;
    }

    public Cell(Cell cellToCopy) {
        this.x = cellToCopy.x;
        this.y = cellToCopy.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell position = (Cell) o;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
