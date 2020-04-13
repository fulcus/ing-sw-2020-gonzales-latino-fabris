package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WorkerMoveMapTest {

    private Worker worker;
    private Worker worker2;
    private Worker enemyWorker;
    private Worker enemyWorker2;

    private Player player;
    private Player player2;
    private Game game;
    private Board board;
    private WorkerMoveMap moveMap;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("nickname1");
        game.addPlayer("nickname2");
        board = game.getBoard();
        player = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);
        worker = player.getWorkers().get(0);
        worker2 = player.getWorkers().get(1);
        enemyWorker = player2.getWorkers().get(0);
        enemyWorker2 = player2.getWorkers().get(1);
        worker.setPosition(0, 0);
        worker2.setPosition(0, 1);
        enemyWorker.setPosition(0, 0);
        enemyWorker2.setPosition(0, 1);
        moveMap = worker.getMoveMap();
    }

    @After
    public void tearDown() {
        game = null;
        board = null;
        player = null;
        worker = null;
        moveMap = null;
    }

    @Test
    public void testCannotMoveInDomeCell() {
        worker.setPosition(2, 1);
        worker.buildDome(2, 2);
        moveMap.cannotMoveInDomeCell();
        assertFalse(moveMap.isAllowedToMoveBoard(2, 2));
    }

    @Test
    public void testCannotMoveInWorkerCell() {
        worker.setPosition(2, 1);
        worker2.setPosition(2, 2);
        moveMap.cannotMoveInWorkerCell();
        assertFalse(moveMap.isAllowedToMoveBoard(2, 2));
    }

    @Test
    public void testCannotMoveInOccupiedCell() {
        worker.setPosition(2, 1);
        worker2.setPosition(2, 2);
        worker.buildDome(2, 0);
        moveMap.cannotMoveInOccupiedCell();
        assertFalse(moveMap.isAllowedToMoveBoard(2, 2));
        assertFalse(moveMap.isAllowedToMoveBoard(2, 0));
    }

    @Test
    public void testCannotMoveInFriendlyWorkerCell() {
        worker.setPosition(2, 1);
        worker2.setPosition(2, 2);
        enemyWorker.setPosition(2, 0);
        moveMap.cannotMoveInFriendlyWorkerCell();
        assertFalse(moveMap.isAllowedToMoveBoard(2, 2));  //frendly worker
        assertTrue(moveMap.isAllowedToMoveBoard(2, 0));//enemyworker
    }

    @Test
    public void testCannotStayStill() {
        worker.setPosition(2, 1);
        moveMap.cannotStayStill();
        assertFalse(moveMap.isAllowedToMoveBoard(2, 1));
        moveMap.canStayStill();
        assertTrue(moveMap.isAllowedToMoveBoard(2, 1));
    }

    @Test
    public void testCannotMoveInPerimeter() {
        worker.setPosition(3, 3);
        moveMap.cannotMoveInPerimeter();
        assertFalse(moveMap.isAllowedToMoveBoard(4, 3));
    }

    @Test
    public void testGetWorker() {
        assertEquals(worker, moveMap.getWorker());
    }

    @Test
    public void testCannotMoveUpMoveThanOneLevel() {
        worker.setPosition(3, 3);

        moveMap.updateMoveUpRestrictions();

        worker.buildBlock(3, 4);
        //can move up one level, can move up == true
        assertTrue(player.getCanMoveUp() && moveMap.isAllowedToMoveBoard(3, 4));

        worker.buildBlock(3, 4);

        moveMap.updateMoveUpRestrictions();
        //cant move up two levels, canMoveUp == true
        assertTrue(player.getCanMoveUp());
        assertFalse(moveMap.isAllowedToMoveBoard(3, 4));

        player.setPermissionToMoveUp(false);

        worker.buildBlock(3, 2);
        moveMap.updateMoveUpRestrictions();

        //worker cannot move up one level, canMoveUp == false
        assertFalse(player.getCanMoveUp());
        assertFalse(moveMap.isAllowedToMoveBoard(3, 2));
    }

    @Test
    public void testIsAllowedToMoveOutOfMaps() {
        worker.setPosition(3, 3);
        //out of workers board
        assertFalse(moveMap.isAllowedToMoveBoard(3, 5));
        worker.setPosition(4, 4);
        //out of board
        assertFalse(moveMap.isAllowedToMoveBoard(5, 4));

        assertNull(moveMap.getAbsolutePosition(5, 4));

    }

    @Test
    public void testGetBooleanCellWorkerMap() {
        worker.setPosition(3, 3);
        worker.buildDome(2, 2);
        moveMap.cannotMoveInOccupiedCell();
        //relative to workers board
        assertFalse(moveMap.isAllowedToMoveWorkersMap(0, 0));
    }

    @Test
    public void testAnyAvailableMovePosition() {
        worker.setPosition(3, 3);

        assertTrue(moveMap.anyAvailableMovePosition());

        //set whole map false
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                moveMap.setBooleanCellWorkerMap(i, j, false);
            }
        }


        //relative to workers board
        assertFalse(moveMap.anyAvailableMovePosition());
    }

    @Test
    public void testSetBooleanCellBoard() {
        worker.setPosition(4, 4);
        assertEquals(board.findCell(4,4),moveMap.getAbsolutePosition(1,1));

        //access non existing cell
        moveMap.setBooleanCellBoard(6, 0, false);
        assertFalse(moveMap.getBooleanCellBoard(6, 0));

        //out of map input
        assertNull(moveMap.getAbsolutePosition(1,2));
    }

    @Test
    public void testNeighboringEnemyWorkers() {
        worker.setPosition(2, 1);
        enemyWorker.setPosition(4,4);

        assertTrue(moveMap.neighboringEnemyWorkers().isEmpty());

        enemyWorker.setPosition(2,2);
        moveMap.neighboringEnemyWorkers();

        assertEquals(enemyWorker,moveMap.neighboringEnemyWorkers().get(0));

    }


}