package backgammon;

import java.awt.Color;

public class Piece implements java.io.Serializable {

    public int x; //Координата x фишки
    public int y; //Координата y фишки
    public int r; // Радиус фишки
    public Color color; //Цвет фишки

    public Piece(int x, int y, int r, Color color) {

        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
    }

    // Метод для изменения координаты фишки.
    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
