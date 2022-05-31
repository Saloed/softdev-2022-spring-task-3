import org.junit.jupiter.api.Test;
import softdev.spring.task.core.Board;
import softdev.spring.task.core.Cell;
import softdev.spring.task.view.BoardPanel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReversiTests {

    Board board1 = new Board(750, 550, 8, 8);
    BoardPanel boardPanel1 = new BoardPanel(new Cell(0, 0), board1);

    Board board2 = new Board(1000, 1350, 15, 15);
    BoardPanel boardPanel2 = new BoardPanel(new Cell(0, 0), board2);

    @Test
    public void correctMovesTest1() {

        List<Cell> result = new ArrayList<>();

        Cell cell1 = new Cell(3, 3);
        Cell cell2 = new Cell(3, 4);
        Cell cell3 = new Cell(4, 3);
        Cell cell4 = new Cell(4, 4);
        Cell cell5 = new Cell(5, 5);

        result.add(cell1);
        result.add(cell2);
        result.add(cell3);
        result.add(cell4);

        assertEquals(result, boardPanel1.getAllCorrectMoves());
        assertTrue(boardPanel1.isCorrectMove(cell1));
        assertFalse(boardPanel1.isCorrectMove(cell5));

    }

    @Test
    public void correctMovesTest2() {

        List<Cell> result = new ArrayList<>();

        Cell cell1 = new Cell(6, 6);
        Cell cell2 = new Cell(6, 7);
        Cell cell3 = new Cell(7, 6);
        Cell cell4 = new Cell(7, 7);
        Cell cell5 = new Cell(8, 8);

        result.add(cell1);
        result.add(cell2);
        result.add(cell3);
        result.add(cell4);

        assertEquals(result, boardPanel2.getAllCorrectMoves());
        assertTrue(boardPanel2.isCorrectMove(cell1));
        assertFalse(boardPanel2.isCorrectMove(cell5));

    }

    @Test
    public void sizeOfCellsTest() {

        assertEquals(93, boardPanel1.getCellWidth());
        assertEquals(68, boardPanel1.getCellHeight());

        assertEquals(66, boardPanel2.getCellWidth());
        assertEquals(90, boardPanel2.getCellHeight());

    }

    @Test
    public void resultOfGameTest() {

        assertEquals(3, boardPanel1.getWinner(false));
        assertEquals(2, boardPanel1.getWinner(true));

    }

}
