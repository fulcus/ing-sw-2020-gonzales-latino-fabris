package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.ViewClient;
import it.polimi.ingsw.server.controller.GameController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.Socket;
import static org.junit.Assert.*;


public class WorkerTest {

    private Worker worker;
    private Worker enemyWorker;
    private Player player;
    private Game game;
    private Board board;

    private GameController gameController;


    @Before
    public void setUp() {
        Socket socket, socket1;
        ViewClient viewClient, viewClient1;
        socket = new Socket();
        socket1 = new Socket();
        //gameController = new GameController();
        game = new Game(2);
        viewClient = new ViewClient(socket, gameController);
        viewClient1 = new ViewClient(socket1, gameController);
        //gameController.setUpGame(viewClient);
        //game = gameController.getGame();

        game.addPlayer("nick1", viewClient);
        game.addPlayer("nick2", viewClient1);
        board = game.getBoard();
        player = game.getPlayers().get(0);
        worker = player.getWorkers().get(0);
        enemyWorker = game.getPlayers().get(1).getWorkers().get(0);
    }


    @After
    public void tearDown() {
        game = null;
        board = null;
        player = null;
        worker = null;
        gameController = null;
    }


    @Test
    public void testSetPositionCoordinates() {
        worker.setPosition(2, 3);
        assertEquals(2, worker.getPosition().getX());
        assertEquals(3, worker.getPosition().getY());

        worker.setPosition(4, 2);
        assertEquals(4, worker.getPosition().getX());
        assertEquals(2, worker.getPosition().getY());

    }


    @Test
    public void testSetPositionCell() {
        Cell firstCell = board.findCell(2, 3);
        worker.setPosition(firstCell);
        assertEquals(2, worker.getPosition().getX());
        assertEquals(3, worker.getPosition().getY());

        //Cell secondCell = board.findCell(3, 1);
        worker.setPosition(3, 1);
        assertEquals(3, worker.getPosition().getX());
        assertEquals(1, worker.getPosition().getY());
    }


    @Test
    public void testBuildBlock() {
        worker.buildBlock(1, 2);
        assertEquals(1, board.findCell(1, 2).getLevel());
    }


    @Test
    public void testBuildDome() {
        worker.buildDome(4, 4);
        assertTrue(board.findCell(4, 4).hasDome());
    }


    @Test
    public void testGetPlayer() {
        assertEquals(player, worker.getPlayer());
    }


    @Test
    public void testGetPosition() {
        worker.setPosition(4, 3);
        assertEquals(4, worker.getPosition().getX());
        assertEquals(3, worker.getPosition().getY());

        assertEquals(board.findCell(4, 3), worker.getPosition());
    }


    @Test
    public void testGetLevel() {
        worker.buildBlock(3, 4);
        worker.buildBlock(3, 4);

        worker.setPosition(3, 4);

        assertEquals(2, worker.getLevel());
        assertEquals(2, board.findCell(3, 4).getLevel());
    }


    @Test
    public void testGetLevelVariation() {
        worker.setPosition(0, 1);
        worker.buildBlock(0, 0);
        worker.setPosition(0, 0);
        assertEquals(1, worker.getLevelVariation());
    }


    @Test
    public void testGetSex() {
        assertEquals(Sex.MALE, worker.getSex());
    }


    @Test
    public void testSwap() {
        worker.setPosition(1,1);
        enemyWorker.setPosition(1,0);

        Cell workerPosition = worker.getPosition();
        Cell enemyPosition = enemyWorker.getPosition();

        worker.swapPosition(enemyPosition);

        assertEquals(enemyPosition, worker.getPosition());
        assertEquals(workerPosition, enemyWorker.getPosition());
    }


    @Test
    public void testGetMoveMap() {
        assertNotNull(worker.getMoveMap());
    }


    @Test
    public void testGetBuildMap() {
        assertNotNull(worker.getBuildMap());
    }
}