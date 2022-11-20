package backgammon;

import java.awt.Color;
import java.util.LinkedList;

public class Bar implements java.io.Serializable {
    public int  x, y, width, height;
    LinkedList<Piece> piecesYellow = new LinkedList<>(); // Список для фишек желтого
    LinkedList<Piece> piecesBlue = new LinkedList<>(); // Список для фишек синего
    
    // Добавляем фишку на панель треугольника по цвету игрока
    public void add(Piece p) {

        if (p.color.equals(Color.YELLOW)) {
            p.setCoordinate(x + 5, y);
            piecesYellow.add(p);
        } else {
            p.setCoordinate(x + 5, height - p.r);
            piecesBlue.add(p);
        }
    }

    //Устанавливаем границы центральной панели треугольника (координаты, ширина и высота)
    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //Количество фишек желтого игрока на центральной полосе треугольника
    public int sizeYellow() {
        return piecesYellow.size();
    }


    public int sizeBlue() {
        return piecesBlue.size();
    }
    
    // Удаление последней фишки по цвету
    public Piece remove(Color player) {
        if (player.equals(Color.YELLOW)) {
            return piecesYellow.removeLast();
        } else {
            return piecesBlue.removeLast();
        }
    }

    // Проверка на наличие фишки на центральной панели треугольника.
    public boolean hasPiece(Color player) {
        if (player.equals(Color.YELLOW) && sizeYellow() > 0) {
            return true;
        } else return player.equals(Color.BLUE) && sizeBlue() > 0;
    }

}
