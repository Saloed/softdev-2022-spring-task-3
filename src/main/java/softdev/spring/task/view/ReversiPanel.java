package softdev.spring.task.view;

import softdev.spring.task.core.Board;
import softdev.spring.task.core.Cell;
import softdev.spring.task.core.StateOfGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ReversiPanel extends JPanel implements MouseListener {

    private static final int PANEL_HEIGHT = 700;

    private static final int PANEL_WIDTH = 600;

    private final BoardPanel boardPanel;

    private StateOfGame stateOfGame;

    private String stateOfGameStr;

    private String countsBlackStr;

    private String countsWhiteStr;

    public ReversiPanel() {

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(new Color(30, 150, 50, 150));

        Board board = new Board(PANEL_WIDTH, PANEL_HEIGHT - 100, 8, 8);
        boardPanel = new BoardPanel(new Cell(0,0), board);
        setGameState(StateOfGame.BLACK_TURN);
        addMouseListener(this);

    }

    public void paint(Graphics g) {
        super.paint(g);
        boardPanel.paint(g);
        drawStateOfGame(g);
        drawGameCounts(g);
    }

    public void restart() {
        boardPanel.reset();
        setGameState(StateOfGame.BLACK_TURN);
    }

    public void handleInput(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if(keyCode == KeyEvent.VK_R) {
            restart();
            repaint();
        }
    }

    private void playTurn(Cell cell) {
        if(!boardPanel.isCorrectMove(cell)) {
            return;
        } else if(stateOfGame == StateOfGame.BLACK_TURN) {
            boardPanel.playMove(cell, 0);
            setGameState(StateOfGame.WHITE_TURN);
        } else if(stateOfGame == StateOfGame.WHITE_TURN) {
            boardPanel.playMove(cell, 1);
            setGameState(StateOfGame.BLACK_TURN);
        }
    }

    private void setGameState(StateOfGame newState) {
        stateOfGame = newState;
        switch (stateOfGame) {
            case WHITE_TURN:
                if(boardPanel.getAllCorrectMoves().size() > 0) {
                    stateOfGameStr = "White turn";
                } else {
                    boardPanel.updateCorrectMoves(0);
                    if(boardPanel.getAllCorrectMoves().size() > 0) {
                        setGameState(StateOfGame.BLACK_TURN);
                    } else {
                        hasGameEnded(false);
                    }
                }
                break;
            case BLACK_TURN:
                if(boardPanel.getAllCorrectMoves().size() > 0) {
                    stateOfGameStr = "Black turn";
                } else {
                    boardPanel.updateCorrectMoves(1);
                    if(boardPanel.getAllCorrectMoves().size() > 0) {
                        setGameState(StateOfGame.WHITE_TURN);
                    } else {
                        hasGameEnded(false);
                    }
                }
                break;
            case WHITE_WINS: stateOfGameStr = "White won! Press R"; break;
            case BLACK_WINS: stateOfGameStr = "Black won! Press R"; break;
            case DRAW: stateOfGameStr = "Draw! Press R"; break;
        }

        countsBlackStr = "Black's score: " + boardPanel.getBlackCounts();
        countsWhiteStr = "White's score: " + boardPanel.getWhiteCounts();
    }

    private void hasGameEnded(boolean currentCorrectMoves) {
        int gameResult = boardPanel.getWinner(currentCorrectMoves);

        if(gameResult == 0) {
            setGameState(StateOfGame.BLACK_WINS);
        } else if(gameResult == 1) {
            setGameState(StateOfGame.WHITE_WINS);
        } else if(gameResult == 3) {
            setGameState(StateOfGame.DRAW);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(stateOfGame == StateOfGame.WHITE_TURN || stateOfGame == StateOfGame.BLACK_TURN) {
            Cell cellPosition = boardPanel.convertMouseToCell(new Cell(e.getX(), e.getY()));
            playTurn(cellPosition);
            hasGameEnded(true);

        }
        repaint();
    }

    private void drawStateOfGame(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Default", Font.BOLD, 35));
        int strWidth = g.getFontMetrics().stringWidth(stateOfGameStr);
        g.drawString(stateOfGameStr, PANEL_WIDTH/2-strWidth/2, PANEL_HEIGHT-60);
    }

    private void drawGameCounts(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Default", Font.BOLD, 20));
        int strWidth = g.getFontMetrics().stringWidth(countsBlackStr + " " + countsWhiteStr);
        g.drawString(countsBlackStr + " " + countsWhiteStr, PANEL_WIDTH/2-strWidth/2, PANEL_HEIGHT-20);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
