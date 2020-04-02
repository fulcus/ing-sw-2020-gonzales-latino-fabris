package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CellTest {


    private Cell cell;
    private Worker worker;
    private Player player;
    private Game game;
    private Map map;


    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("nickname1");
        game.addPlayer("nickname2");
        map = game.getMap();
        player = game.getPlayers().get(0);
        worker = player.getWorkers().get(0);
        cell = map.findCell(3,2);
    }

    @After
    public void tearDown() {
        game = null;
        map = null;
        player = null;
        worker = null;
    }

    @Test
    public void testGetLevel() {


        cell.buildBlock();

        assertEquals(1, cell.getLevel());
    }

    @Test
    public void testBuildBlock() {

        cell.buildBlock();

        assertEquals(1, cell.getLevel());

        cell.buildBlock();

        assertEquals(2, cell.getLevel());

        cell.buildBlock();

        assertEquals(3, cell.getLevel());


    }

    @Test
    public void testHasDome() {

        assertFalse(cell.hasDome());

        cell.buildDome();

        assertTrue(cell.hasDome());
    }

    @Test
    public void testBuildDome() {

        assertFalse(cell.hasDome());

        cell.buildDome();

        assertTrue(cell.hasDome());

    }

    @Test
    public void testHasWorker() {

        assertFalse(cell.hasWorker());

    }

    @Test
    public void testMoveIn() {

        assertNull(cell.getWorker());

        cell.moveIn(worker);

        assertEquals(worker, cell.getWorker());

    }

    @Test
    public void testMoveOut() {

        cell.moveIn(worker);
        cell.moveOut();

        assertNull(cell.getWorker());


    }

    @Test
    public void testIsInPerimeter() {

        assertTrue((new Cell(4, 2).isInPerimeter()));

        assertFalse((new Cell(3, 2).isInPerimeter()));


    }

    @Test
    public void testIsOccupied() {

        assertFalse(cell.isOccupied());

        cell.moveIn(worker);

        assertTrue(cell.isOccupied());
    }

    @Test
    public void testGetWorker() {

        assertNull(cell.getWorker());

        cell.moveIn(worker);

        assertEquals(worker, cell.getWorker());
    }

    @Test
    public void testGetX() {

        assertEquals(3, cell.getX());

    }

    @Test
    public void testGetY() {

        assertEquals(2, cell.getY());
    }
}