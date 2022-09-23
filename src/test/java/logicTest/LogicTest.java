package logicTest;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import minesweep.MinesweeperApplication;
import minesweep.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LogicTest {
    @Test
    public void bombTest(){
        Tile tile = new Tile(0,0);
        ImagePattern img = new ImagePattern(new Image(String.valueOf(getClass().getResource(String.format("%s%s", "cover", ".png")))));
        MinesweeperApplication test = new MinesweeperApplication();
        test.tileToBomb(tile);
        assertEquals(tile.isBomb(),true);
    }
}
