package com.example.demo.tests;

import com.example.demo.logic.Matrix;
import com.example.demo.logic.WinLine;
import org.junit.Test;

import static org.junit.Assert.*;

public class WinLineTest {
    int rows = 15;
    int columns = 15;
    String[][] data = new String[rows][columns];
    Matrix matrix = new Matrix(15, 15, data);

    @Test
    public void redWin() {
        matrix.setElement(0,0, "red");
        matrix.setElement(1,0, "red");
        matrix.setElement(2,0, "red");
        matrix.setElement(3,0, "red");
        matrix.setElement(4,0, "red");
        String winner = "";
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                WinLine winLine = new WinLine(matrix);
                if (winLine.redWin(matrix, i, j) == "Победил красный") {
                    winner = winLine.redWin(matrix, i, j);
                }
            }
        }
        assertEquals("Победил красный", winner);
    }

    @Test
    public void greenWin() {
        matrix.setElement(10,10, "green");
        matrix.setElement(11,11, "green");
        matrix.setElement(12,12, "green");
        matrix.setElement(13,13, "green");
        matrix.setElement(14,14, "green");
        String winner = "";
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                WinLine winLine = new WinLine(matrix);
                if (winLine.greenWin(matrix, i, j) == "Победил зелёный") {
                    winner = winLine.greenWin(matrix, i, j);
                }
            }
        }
        assertEquals("Победил зелёный", winner);
    }
}