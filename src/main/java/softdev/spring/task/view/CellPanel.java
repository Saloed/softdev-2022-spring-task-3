package softdev.spring.task.view;

import softdev.spring.task.core.Cell;
import softdev.spring.task.core.CellOwner;

import java.awt.*;

public class CellPanel {

    private final Cell cell;

    private CellOwner cellState; // 0 - black, 1 - white, 2 - none

    private final int width;

    private final int height;

    public CellPanel(Cell cell, int width, int height) {
        this.cell = cell;
        this.width = width;
        this.height = height;
        reset();
    }

    public void reset() {
        cellState = CellOwner.NONE;
    }

    public void setCellState(CellOwner newState) {
        this.cellState = newState;
    }

    public CellOwner getCellState() {
        return cellState;
    }

    public void paint(Graphics g) {
        if(cellState == CellOwner.NONE) return;
        g.setColor(cellState == CellOwner.BLACK ? Color.BLACK : Color.WHITE);
        g.fillOval(cell.x, cell.y, width, height);
    }
}
