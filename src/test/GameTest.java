package test;

import backgammon.Bar;
import backgammon.Piece;
import backgammon.Triangle;
import backgammon.Backgammon;
import static org.junit.Assert.assertEquals;
import java.awt.Color;
import org.junit.Test;

public class GameTest {
    @Test
    public void BarTest() {
        int actualHeight = 450;
        int triangleW = 60;
        int middleBar = 50 * (triangleW / 60);

        Bar bar = new Bar();
        bar.setBounds(6 * triangleW, 0, middleBar, actualHeight);

        assertEquals(360, bar.x);
        assertEquals(0, bar.y);
        assertEquals(50, bar.width);
        assertEquals(450, bar.height);
    }

    @Test
    public void PieceTest(){
        Piece piece = new Piece(4, 5, 10, Color.black);
        assertEquals(4, piece.x);
        assertEquals(5, piece.y);
        assertEquals(300, (int)3.14 * piece.r * piece.r);
        assertEquals(Color.black, piece.color);

        piece.setCoordinate(10, 15);
        assertEquals(10, piece.x);
        assertEquals(15, piece.y);
    }

    @Test
    public void TriangleTest(){
        Triangle triangle = new Triangle(new int[]{1,5,3}, new int[]{1,5,3}, 1, Color.black);
        assertEquals(2, triangle.width);
        assertEquals(Color.black, triangle.color);
        assertEquals(1, triangle.x[0]);
        assertEquals(1, triangle.y[0]);
    }

    @Test
    public void GiveUpTest(){
        Backgammon backgammon = new Backgammon();
        Backgammon.giveUp();
        assertEquals("WIN!", Backgammon.giveUpLabel.getText());
    }
}

