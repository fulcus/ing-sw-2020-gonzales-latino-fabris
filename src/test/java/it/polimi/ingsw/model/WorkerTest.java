package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class WorkerTest {

    private Worker worker;
    private Player player;
    private Game game;
    private Board board;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("nick1");
        game.addPlayer("nick2");
        board = game.getBoard();
        player = game.getPlayers().get(0);
        worker = player.getWorkers().get(0);
    }

    @After
    public void tearDown() {
        game = null;
        board = null;
        player = null;
        worker = null;
    }

    @Test
    public void testSetPositionCoordinates() {
        worker.setPosition(2,3);
        assertEquals(2,worker.getPosition().getX());
        assertEquals(3,worker.getPosition().getY());

        worker.setPosition(4,2);
        assertEquals(4,worker.getPosition().getX());
        assertEquals(2,worker.getPosition().getY());

    }

    @Test
    public void testSetPositionCell() {
        Cell firstCell = board.findCell(2,3);
        worker.setPosition(firstCell);
        assertEquals(2,worker.getPosition().getX());
        assertEquals(3,worker.getPosition().getY());

        Cell secondCell = board.findCell(3,1);
        worker.setPosition(secondCell);
        assertEquals(3,worker.getPosition().getX());
        assertEquals(1,worker.getPosition().getY());
    }

    @Test
    public void testBuildBlock() {
        worker.buildBlock(1,2);
        assertEquals(1, board.findCell(1,2).getLevel());
    }

    @Test
    public void testBuildDome() {
        worker.buildDome(4,4);
        assertTrue(board.findCell(4,4).hasDome());
    }

    @Test
    public void testGetPlayer() {
        assertEquals(player,worker.getPlayer());
    }

    @Test
    public void testGetPosition() {
        worker.setPosition(4,3);
        assertEquals(4,worker.getPosition().getX());
        assertEquals(3,worker.getPosition().getY());

        assertEquals(board.findCell(4,3),worker.getPosition());
    }

    @Test
    public void testGetLevel() {
        worker.buildBlock(3,4);
        worker.buildBlock(3,4);

        worker.setPosition(3,4);

        assertEquals(2,worker.getLevel());
        assertEquals(2, board.findCell(3,4).getLevel());
    }

    @Test
    public void testGetLevelVariation() {
        worker.setPosition(0,1);
        worker.buildBlock(0,0);
        worker.setPosition(0,0);
        assertEquals(1, worker.getLevelVariation());
    }

    @Test
    public void testGetSex() {
        assertEquals(Sex.MALE,worker.getSex());
    }

    @Test
    public void testGetAllowedMoveMatrix() {
        //edge case of worker in perimeter
        worker.setPosition(4,4);
        WorkerMoveMap matrix = worker.getMoveMap();

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(i == 1 && j == 1 || !board.isInBoard(i,j))
                    assertFalse(matrix.isAllowedToMoveWorkersMap(i,j));
                else
                    assertTrue(matrix.isAllowedToMoveWorkersMap(i,j));
            }
        }

    }

    @Test
    public void testGetAllowedBuildMatrix() {
        worker.setPosition(3,3);
        WorkerBuildMap matrix = worker.getBuildMap();

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(i == 1 && j == 1 || !board.isInBoard(i,j))
                    assertFalse(matrix.getBooleanCellWorkerMap(i,j));
                else
                    assertTrue(matrix.getBooleanCellWorkerMap(i,j));
            }
        }


    }

}