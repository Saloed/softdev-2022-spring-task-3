package backgammon;

import java.awt.Color;
import java.util.LinkedList;

public class Triangle implements java.io.Serializable {

    public int[] x; // Координата x для треугольника
    public int[] y; // Координата y для треугольника
    public int id; //Номер треугольника
    public int width; //Ширина основания треугольника
    public Color color; //Цвет треугольника

    public LinkedList<Piece> pieces = new LinkedList<>(); // Фишки в треугольниках

    public Triangle(int[] x, int[] y, int id, Color color) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.color = color;
        this.width = x[2] - x[0];
    }
    
    // Добавление фишки в треугольник
    public void add(Piece p) {
        if (pieces.isEmpty()) {
            if (this.id < 12) {
                p.setCoordinate(this.x[0] + 10, this.y[0]);
            } else {
                p.setCoordinate(this.x[0] + 10, this.y[0] - p.r);
            }
        } else {
            if (pieces.size() >= 5) {
                p.setCoordinate(pieces.getLast().x, pieces.getLast().y);
            } else if (this.id < 12) {
                p.setCoordinate(pieces.getLast().x, pieces.getLast().y + p.r);
            } else {
                p.setCoordinate(pieces.getLast().x, pieces.getLast().y - p.r);
            }
        }

        pieces.add(p);

    }

    //Получение последней фишки из треугольника
    public Piece getLast() {
        return pieces.getLast();
    }

    //Удаление фишки из треугольника
    public Piece remove() {
        return pieces.removeLast();
    }

    //Получение количества фишек в треугольнике
    public int size() {
        return pieces.size();
    }
    
    //Проверка на пустоту треугольника
    public boolean isEmpty(){
        return pieces.isEmpty();
    }

}
