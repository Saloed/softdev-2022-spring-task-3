package com.example.demo.tests;

import com.example.demo.logic.Matrix;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {
    int rows = 15;
    int columns = 15;
    String[][] data = new String[rows][columns];
    Matrix matrix = new Matrix(15, 15, data);

    @Test
    public void setElement() {
        matrix.setElement(0,0,"red");
        assertEquals("red", matrix.getElement(0,0));
    }

    @Test
    public void getElement() {
        matrix.setElement(14,14, "GREENISTHEBESTPLAYER");
        assertEquals("GREENISTHEBESTPLAYER", matrix.getElement(14,14));
    }
}