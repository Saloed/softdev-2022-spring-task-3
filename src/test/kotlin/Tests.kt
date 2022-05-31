import model.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Tests {

    @Test
    fun boardTest() {
        val gameBoard = BoardCondition.gameBoard(3)

        gameBoard[CellCondition(1, 1)] = 2
        gameBoard[CellCondition(3, 2)] = null
        assertTrue(2 == gameBoard[CellCondition(1, 1)])
        assertEquals(null, gameBoard[CellCondition(3, 2)])
        assertFalse(0 == gameBoard[CellCondition(3, 2)])
        assertEquals(9, gameBoard.getAllCells().count())
        assertEquals("[CellCondition(i=1, j=1), CellCondition(i=1, j=2), CellCondition(i=1, j=3)," +
                " CellCondition(i=2, j=1), CellCondition(i=2, j=2), CellCondition(i=2, j=3), CellCondition(i=3, j=1)," +
                " CellCondition(i=3, j=2), CellCondition(i=3, j=3)]", gameBoard.getAllCells().toString())

        val cell = gameBoard.getCellOrNull(2, 3)
        assertEquals(2, cell!!.i)
        assertEquals(3, cell.j)

        val nullCell = gameBoard.getCellOrNull(10, 10)
        assertEquals(null, nullCell)
    }

    @Test
    fun logicTest() {
        val game = GameLogic(4, Random)
        game.initialize()
        assertFalse(game.hasWon())
        assertFalse(game.hasLost())

        val move = listOf(game.canMove(Direction.UP), game.canMove(Direction.DOWN), game.canMove(Direction.LEFT), game.canMove(Direction.RIGHT))
        assertTrue(move.any())

        val winGame = GameLogic(6, Random)
        winGame.board[CellCondition(1, 1)] = 536870912
        assertTrue(winGame.hasWon())
        assertFalse(winGame.hasLost())

        val winMove = listOf(winGame.canMove(Direction.UP), winGame.canMove(Direction.DOWN), winGame.canMove(Direction.LEFT), winGame.canMove(Direction.RIGHT))
        assertTrue(winMove.any())

        val loseGame = GameLogic(3, Random)
        loseGame.board[CellCondition(1, 1)] = 1
        loseGame.board[CellCondition(1, 2)] = 2
        loseGame.board[CellCondition(1, 3)] = 3
        loseGame.board[CellCondition(2, 1)] = 4
        loseGame.board[CellCondition(2, 2)] = 5
        loseGame.board[CellCondition(2, 3)] = 6
        loseGame.board[CellCondition(3, 1)] = 7
        loseGame.board[CellCondition(3, 2)] = 8
        loseGame.board[CellCondition(3, 3)] = 9
        assertFalse(loseGame.hasWon())
        assertTrue(loseGame.hasLost())
    }
}