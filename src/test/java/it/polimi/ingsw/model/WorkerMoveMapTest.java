package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WorkerMoveMapTest {

    private Worker worker;
    private Worker worker2;
    private Worker enemyWorker;
    private Worker enemyWorker2;

    private Player player;
    private Player player2;
    private Game game;
    private Map map;
    private WorkerMoveMap moveMap;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("nickname1");
        game.addPlayer("nickname2");
        map = game.getMap();
        player = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);
        worker = player.getWorkers().get(0);
        worker2 = player.getWorkers().get(1);
        enemyWorker = player2.getWorkers().get(0);
        enemyWorker2 = player2.getWorkers().get(1);
        worker.setPosition(0,0);
        worker2.setPosition(0,1);
        enemyWorker.setPosition(0,0);
        enemyWorker2.setPosition(0,1);
        moveMap = worker.getMoveMap();
    }

    @After
    public void tearDown() {
        game = null;
        map = null;
        player = null;
        worker = null;
        moveMap = null;
    }

    @Test
    public void testCannotMoveInDomeCell() {
        worker.setPosition(2,1);
        worker.buildDome(2,2);
        moveMap.cannotMoveInDomeCell();
        assertFalse(moveMap.isAllowedToMoveBoard(2,2));
    }

    @Test
    public void testCannotMoveInWorkerCell() {
        worker.setPosition(2,1);
        worker2.setPosition(2,2);
        moveMap.cannotMoveInWorkerCell();
        assertFalse(moveMap.isAllowedToMoveBoard(2,2));
    }

    @Test
    public void testCannotMoveInOccupiedCell() {
        worker.setPosition(2,1);
        worker2.setPosition(2,2);
        worker.buildDome(2,0);
        moveMap.cannotMoveInOccupiedCell();
        assertFalse(moveMap.isAllowedToMoveBoard(2,2));
        assertFalse(moveMap.isAllowedToMoveBoard(2,0));
    }

    @Test
    public void testCannotMoveInFriendlyWorkerCell() {
        worker.setPosition(2,1);
        worker2.setPosition(2,2);
        enemyWorker.setPosition(2,0);
        moveMap.cannotMoveInFriendlyWorkerCell();
        assertFalse(moveMap.isAllowedToMoveBoard(2,2));  //frendly worker
        assertTrue(moveMap.isAllowedToMoveBoard(2,0));//enemyworker
    }

    @Test
    public void testCannotStayStill() {
        worker.setPosition(2,1);
        moveMap.cannotStayStill();
        assertFalse(moveMap.isAllowedToMoveBoard(2,1));
        moveMap.canStayStill();
        assertTrue(moveMap.isAllowedToMoveBoard(2,1));
    }

    @Test
    public void testCannotMoveInPerimeter() {
        worker.setPosition(3,3);
        moveMap.cannotMoveInPerimeter();
        assertFalse(moveMap.isAllowedToMoveBoard(4,3));
    }

    @Test
    public void testGetWorker() {
        assertEquals(worker,moveMap.getWorker());
    }

    @Test
    public void testCannotMoveUpMoveThanOneLevel() {
        worker.setPosition(3,3);
        moveMap.cannotMoveUpMoreThanOneLevel();

        worker.buildBlock(3,4);
        assertTrue(moveMap.isAllowedToMoveBoard(3,4));

        worker.buildBlock(3,4);

        moveMap.cannotMoveUpMoreThanOneLevel();
        assertFalse(moveMap.isAllowedToMoveBoard(3,4));
    }

    @Test
    public void testIsAllowedToMoveOutOfMaps() {
        worker.setPosition(3,3);
        //out of workers map
        assertFalse(moveMap.isAllowedToMoveBoard(3,5));
        worker.setPosition(4,4);
        //out of board
        assertFalse(moveMap.isAllowedToMoveBoard(5,4));

        assertNull(moveMap.getAbsolutePosition(5,4));

    }

    @Test
    public void testGetBooleanCellWorkerMap() {
        worker.setPosition(3,3);
        worker.buildDome(2,2);
        moveMap.cannotMoveInOccupiedCell();
        //relative to workers map
        assertFalse(moveMap.isAllowedToMoveWorkersMap(0,0));
    }

}