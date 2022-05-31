package softdev.spring.task.view;

import softdev.spring.task.core.Cell;

import java.awt.*;

public class CellPanel {

    private final Cell cell;

    private int cellState; // 0 - black, 1 - white, 2 - none

    private final int width;

    private final int height;

    public CellPanel(Cell cell, int width, int height) {
        this.cell = cell;
        this.width = width;
        this.height = height;
        reset();
    }

    public void reset() {
        cellState = 2;
    }

    public void setCellState(int newState) {
        this.cellState = newState;
    }

    public int getCellState() {
        return cellState;
    }

    public void paint(Graphics g) {
        if(cellState == 2) return;
        g.setColor(cellState == 0 ? Color.BLACK : Color.WHITE);
        g.fillOval(cell.x, cell.y, width, height);
    }
}
