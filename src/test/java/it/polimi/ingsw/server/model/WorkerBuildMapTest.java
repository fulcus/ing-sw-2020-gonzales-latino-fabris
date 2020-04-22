package it.polimi.ingsw.server.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WorkerBuildMapTest {
    private Worker worker;
    private Worker worker2;

    private Player player;
    private Game game;
    private WorkerBuildMap buildMap;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("nickname1");
        game.addPlayer("nickname2");
        player = game.getPlayers().get(0);
        worker = player.getWorkers().get(0);
        worker2 = player.getWorkers().get(1);
        worker.setPosition(0,0);
        worker2.setPosition(0,1);
        buildMap = worker.getBuildMap();
    }

    @After
    public void tearDown() {
        game = null;
        player = null;
        worker = null;
    }

    @Test
    public void testAllowedToBuildInPosition() {
        worker.setPosition(2,3);
        worker.buildDome(2,4);
        buildMap.cannotBuildInOccupiedCell();
        //cant build in cell occupied by dome
        assertFalse(buildMap.isAllowedToBuildBoard(2,4));

        //can build in free cell
        assertTrue(buildMap.isAllowedToBuildBoard(2,2));

    }

    @Test
    public void testGetWorker() {
        assertEquals(worker,buildMap.getWorker());
    }

    @Test
    public void testCannotBuildInOccupiedCell() {
        worker.setPosition(2,1);
        worker2.setPosition(2,2);
        worker.buildDome(2,0);
        buildMap.cannotBuildInOccupiedCell();
        assertFalse(buildMap.isAllowedToBuildBoard(2,2));
        assertFalse(buildMap.isAllowedToBuildBoard(2,0));

    }

    @Test
    public void testCannotBuildUnderneath() {
        worker.setPosition(2,1);
        buildMap.cannotBuildUnderneath();
        assertFalse(buildMap.isAllowedToBuildBoard(2,1));
        buildMap.canBuildUnderneath();
        assertTrue(buildMap.isAllowedToBuildBoard(2,1));

    }

    @Test
    public void testAnyAvailableBuildPosition() {
        worker.setPosition(0, 0);

        assertTrue(buildMap.anyAvailableBuildPosition());

        //set all neighboring cells false. some already false bc out of map.
        worker.buildDome(1,0);
        worker.buildDome(1,1);
        worker.buildDome(0,1);
        buildMap.cannotBuildInOccupiedCell();
        buildMap.updateCellsOutOfMap();

        //relative to workers board
        assertFalse(buildMap.anyAvailableBuildPosition());

    }



}