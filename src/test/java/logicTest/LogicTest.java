package logicTest;

import minesweep.Coordinates;
import minesweep.logic.Logic;
import minesweep.logic.Tile;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class LogicTest {
    @Test
    public void bombTest(){
        Tile tile = new Tile(0, 0);
        tile.setBomb(true);
        assertTrue(tile.isBomb());
    }
    @Test
    public void emptyTest(){
        Tile tile = new Tile(0,0);
        tile.setEmpty(true);
        assertTrue(tile.isEmpty());
    }
    @Test
    public void flagTest() {
        Tile tile = new Tile(0, 0);
        tile.setFlagged(true);
        assertTrue(tile.isFlagged());
    }
    @Test
    public void neighboursTest() {
        Logic logic = new Logic(1,2,0);
        Tile tile = logic.tileMap.get(new Coordinates(0, 0));
        Set<Tile> set = Set.of(
                logic.tileMap.get(new Coordinates(1, 0))
        );
        assertEquals(set, logic.getNeighboursOf(tile).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet())
        );
    }
}
