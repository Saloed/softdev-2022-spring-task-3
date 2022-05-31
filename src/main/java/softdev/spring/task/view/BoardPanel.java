package softdev.spring.task.view;

import softdev.spring.task.core.Board;
import softdev.spring.task.core.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel {

    private final Cell cell;

    private final CellPanel[][] cells;

    private int firstFourMovesIndicator;

    private final List<Cell> correctMoves;

    private final int cellHeight;

    private final int cellWidth;

    private final Board board;

    public BoardPanel(Cell cell, Board board) {
        this.cell = cell;
        this.board = board;
        int fieldWidth = board.getCellNumberX();
        int fieldHeight = board.getCellNumberY();

        cells = new CellPanel[fieldWidth][fieldHeight];

        this.cellWidth = board.getWidth()/fieldWidth;
        this.cellHeight = board.getHeight()/fieldHeight;

        for (int x = 0; x < fieldWidth; x++) {
            for (int y = 0; y < fieldHeight; y++) {
                cells[x][y] = new CellPanel(new Cell(x*cellWidth, y*cellHeight), cellWidth, cellHeight);
            }
        }

        firstFourMovesIndicator = 0;
        correctMoves = new ArrayList<>();
        updateCorrectMoves(0);
    }

    public void reset() {
        for (CellPanel[] cellPanels : cells) {
            for (int y = 0; y < cells[0].length; y++) {
                cellPanels[y].reset();
            }
        }
        firstFourMovesIndicator = 0;
        updateCorrectMoves(0);
    }

    public void playMove(Cell cell, int player) {
        firstFourMovesIndicator++;
        cells[cell.x][cell.y].setCellState(player);
        List<Cell> changeCellPositions = getChangedPositionsForMove(cell, player);
        for (Cell swapPosition : changeCellPositions) {
            cells[swapPosition.x][swapPosition.y].setCellState(player);
        }
        updateCorrectMoves(player == 0 ? 1 : 0);
    }

    public Cell convertMouseToCell(Cell mousePosition) {
        int fieldX = (mousePosition.x - cell.x)/cellWidth;
        int fieldY = (mousePosition.y - cell.y)/cellHeight;
        if (fieldX >= cells.length || fieldX < 0 || fieldY >= cells[0].length || fieldY < 0) {
            return new Cell(-1,-1);
        }
        return new Cell(fieldX,fieldY);
    }

    public boolean isCorrectMove(Cell cell) {
        return getAllCorrectMoves().contains(cell);
    }

    public int getBlackCounts() {
        int[] counts = new int[3];
        for (int y = 0; y < cells[0].length; y++) {
            for (int x = 0; x < cells.length; x++) {
                counts[cells[x][y].getCellState()]++;
            }
        }

        return counts[0];
    }

    public int getWhiteCounts() {
        int[] counts = new int[3];
        for (int y = 0; y < cells[0].length; y++) {
            for (int x = 0; x < cells.length; x++) {
                counts[cells[x][y].getCellState()]++;
            }
        }

        return counts[1];
    }

    public int getWinner(boolean currentCorrectMoves) {
        int[] counts = new int[3];
        for (int y = 0; y < cells[0].length; y++) {
            for (int x = 0; x < cells.length; x++) {
                counts[cells[x][y].getCellState()]++;
            }
        }

        if (currentCorrectMoves && counts[2] > 0) return 2;
        else if (counts[0] == counts[1]) return 3;
        else return counts[0] > counts[1] ? 0 : 1;
    }

    public void paint(Graphics g) {
        drawBoard(g);
        for (CellPanel[] gridCells : cells) {
            for (int y = 0; y < cells[0].length; y++) {
                gridCells[y].paint(g);
            }
        }
    }

    private void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);

        int y2 = cell.y + board.getHeight();
        int y1 = cell.y;
        for (int x = 0; x < cells.length+1; x++)
            g.drawLine(cell.x + x * cellWidth, y1, cell.x + x * cellWidth, y2);

        int x2 = cell.x + board.getWidth();
        int x1 = cell.x;
        for (int y = 0; y < cells[0].length+1; y++)
            g.drawLine(x1, cell.y + y * cellHeight, x2, cell.y + y * cellHeight);
    }

    public void updateCorrectMoves(int playerID) {
        correctMoves.clear();

        if (firstFourMovesIndicator < 4) {
            int midX = cells.length/2 - 1;
            int midY = cells[0].length/2 - 1;
            for (int x = midX; x < midX + 2; x++) {
                for (int y = midY; y < midY+2; y++) {
                    if (cells[x][y].getCellState() == 2) {
                        correctMoves.add(new Cell(x, y));
                    }
                }
            }
        } else {
            for (int x = 0; x < cells.length; x++) {
                for (int y = 0; y < cells[0].length; y++) {
                    if (cells[x][y].getCellState() == 2 && getChangedPositionsForMove(new Cell(x,y),playerID).size()>0) {
                        correctMoves.add(new Cell(x, y));
                    }
                }
            }
        }
    }

    public List<Cell> getChangedPositionsForMove(Cell cell, int playerID) {
        List<Cell> result = new ArrayList<>();
        result.addAll(getChangedPositionsForMoveInDirection(cell, playerID, softdev.spring.task.core.Cell.DOWN));
        result.addAll(getChangedPositionsForMoveInDirection(cell, playerID, softdev.spring.task.core.Cell.LEFT));
        result.addAll(getChangedPositionsForMoveInDirection(cell, playerID, softdev.spring.task.core.Cell.UP));
        result.addAll(getChangedPositionsForMoveInDirection(cell, playerID, softdev.spring.task.core.Cell.RIGHT));
        result.addAll(getChangedPositionsForMoveInDirection(cell, playerID, softdev.spring.task.core.Cell.UP_RIGHT));
        result.addAll(getChangedPositionsForMoveInDirection(cell, playerID, softdev.spring.task.core.Cell.UP_LEFT));
        result.addAll(getChangedPositionsForMoveInDirection(cell, playerID, softdev.spring.task.core.Cell.DOWN_RIGHT));
        result.addAll(getChangedPositionsForMoveInDirection(cell, playerID, softdev.spring.task.core.Cell.DOWN_lEFT));
        return result;
    }

    private List<Cell> getChangedPositionsForMoveInDirection(Cell cell, int playerID, Cell direction) {
        List<Cell> result = new ArrayList<>();
        Cell movingCell = new Cell(cell);
        int otherPlayer = playerID == 0 ? 1 : 0;
        movingCell.add(direction);

        while (isInBounds(movingCell) && cells[movingCell.x][movingCell.y].getCellState() == otherPlayer) {
            result.add(new Cell(movingCell));
            movingCell.add(direction);
        }

        if (!isInBounds(movingCell) || cells[movingCell.x][movingCell.y].getCellState() != playerID) {
            result.clear();
        }
        return result;
    }

    private boolean isInBounds(Cell cell) {
        return !(cell.x < 0 || cell.y < 0 || cell.x >= cells.length || cell.y >= cells[0].length);
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public List<Cell> getAllCorrectMoves() {
        return correctMoves;
    }

}
