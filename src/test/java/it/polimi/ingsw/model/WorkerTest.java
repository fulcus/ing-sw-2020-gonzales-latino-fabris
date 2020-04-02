package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class WorkerTest {

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
    }

    @After
    public void tearDown() {
        game = null;
        map = null;
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
        Cell firstCell = map.findCell(2,3);
        worker.setPosition(firstCell);
        assertEquals(2,worker.getPosition().getX());
        assertEquals(3,worker.getPosition().getY());

        Cell secondCell = map.findCell(3,1);
        worker.setPosition(secondCell);
        assertEquals(3,worker.getPosition().getX());
        assertEquals(1,worker.getPosition().getY());
    }

    @Test
    public void testBuildBlock() {
        worker.buildBlock(1,2);
        assertEquals(1,map.findCell(1,2).getLevel());
    }

    @Test
    public void testBuildDome() {
        worker.buildDome(4,4);
        assertTrue(map.findCell(4,4).hasDome());
    }

    @Test
    public void testGetPlayer() {
        assertEquals("nickname1",worker.getPlayer().getNickname());
    }

    @Test
    public void testGetPosition() {
        worker.setPosition(4,3);
        assertEquals(4,worker.getPosition().getX());
        assertEquals(3,worker.getPosition().getY());

        assertEquals(map.findCell(4,3),worker.getPosition());
    }

    @Test
    public void testGetLevel() {
        worker.buildBlock(3,4);
        worker.buildBlock(3,4);

        worker.setPosition(3,4);

        assertEquals(2,worker.getLevel());
        assertEquals(2,map.findCell(3,4).getLevel());
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
                if(i == 1 && j == 1 || !map.isInMap(i,j))
                    assertFalse(matrix.isAllowedToMove(i,j));
                else
                    assertTrue(matrix.isAllowedToMove(i,j));
            }
        }

    }

    @Test
    public void testGetAllowedBuildMatrix() {
        worker.setPosition(3,3);
        WorkerBuildMap matrix = worker.getBuildMap();

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(i == 1 && j == 1 || !map.isInMap(i,j))
                    assertFalse(matrix.isAllowedToBuild(i,j));
                else
                    assertTrue(matrix.isAllowedToBuild(i,j));
            }
        }


    }


}