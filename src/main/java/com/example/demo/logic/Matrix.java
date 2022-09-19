package com.example.demo.logic;

public class Matrix {

    public int xsize;
    public int ysize;
    String [][]Data;

    public Matrix(int xsize, int ysize, String [][]Data) {
        this.xsize = xsize;
        this.ysize = ysize;
        this.Data = Data;
    }

    public void setElement(int xsize, int ysize, String value) {
        this.Data[xsize][ysize] = value;
    }

    public String getElement(int xsize, int ysize) {
        return Data[xsize][ysize];
    }
}
