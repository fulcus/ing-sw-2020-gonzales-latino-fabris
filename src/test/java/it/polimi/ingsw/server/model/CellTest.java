package it.polimi.ingsw.server.model;


import it.polimi.ingsw.server.ClientView;
import it.polimi.ingsw.server.controller.GameController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;


public class CellTest {

    private Cell cell;
    private Worker worker;
    private Player player;
    private Game game;
    private Board board;


    @Before
    public void setUp() {
        game = new Game(2);
        board = game.getBoard();
        player = game.getPlayers().get(0);
        worker = player.getWorkers().get(0);
        cell = board.findCell(3,2);

    }

    @After
    public void tearDown() {
        game = null;
        board = null;
        player = null;
        worker = null;
        cell = null;
    }


    @Test
    public void testGetLevel() {
        cell.buildBlock();
        assertEquals(1, cell.getLevel());
    }


    @Test
    public void testBuildBlock() {

        assertEquals(0, cell.getLevel());

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

        cell.moveIn(worker);
        assertTrue(cell.hasWorker());

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
        assertEquals(2, cell.getY());
    }


    @Test
    public void testGetY(){

        assertEquals(3, cell.getX());
        assertEquals(2, cell.getY());
    }


    @Test
    public void testSetLevel() {
        cell.setLevel(2);
        assertEquals(2, cell.getLevel());
    }


    @Test
    public void testSetDome() {
        cell.setDome(true);
        assertTrue(cell.hasDome());
        cell.setDome(false);
        assertFalse(cell.hasDome());
    }


    @Test
    public void testSetWorker() {
        cell.setWorker(worker);
        assertEquals(worker,cell.getWorker());
    }


    @Test
    public void testRegister() {

        GameController gc = new GameController();
        Socket socket = new Socket();
        ClientView client = new ClientView(socket, gc);

        assertEquals(0, cell.getCellObservers().size());

        cell.register(client);
        assertEquals(1, cell.getCellObservers().size());
    }


    @Test
    public void testUnregister() {

        GameController gc = new GameController();
        Socket socket = new Socket();
        ClientView client = new ClientView(socket, gc);

        cell.register(client);
        assertEquals(1, cell.getCellObservers().size());

        cell.unregister(client);
        assertEquals(0, cell.getCellObservers().size());
    }


    @Test
    public void testNotifyObservers() {

        //do nothing?
    }

}