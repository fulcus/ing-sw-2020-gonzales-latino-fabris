package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
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
        buildMap.setBooleanCellBoard(2,4,false);
        assertFalse(buildMap.isAllowedToBuildBoard(2,4));

        buildMap.setBooleanCellBoard(2,4,true);
        assertTrue(buildMap.isAllowedToBuildBoard(2,4));

    }

    @Test
    public void testGetWorker() {
        assertEquals(worker,buildMap.getWorker());
    }

    @Test
    public void testCannotBuildInWorkerCell() {
        worker.setPosition(2,1);
        worker2.setPosition(2,2);
        buildMap.cannotBuildInWorkerCell();
        assertFalse(buildMap.isAllowedToBuildBoard(2,2));

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
    public void testCannotBuildInDomeCell() {
        worker.setPosition(2,1);
        worker2.setPosition(2,2);
        worker.buildDome(2,0);
        buildMap.cannotBuildInDomeCell();

        //can build on worker
        assertTrue(buildMap.isAllowedToBuildBoard(2,2));
        //cannot build on dome
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
    public void testCannotBuildInPerimeter() {
        worker.setPosition(3,3);
        buildMap.cannotBuildInPerimeter();
        assertFalse(buildMap.isAllowedToBuildBoard(4,3));

    }

    @Test
    public void testAnyAvailableBuildPosition() {
        worker.setPosition(3, 3);

        assertTrue(buildMap.anyAvailableBuildPosition());

        //set whole map false
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buildMap.setBooleanCellWorkerMap(i, j, false);
            }
        }

        //relative to workers board
        assertFalse(buildMap.anyAvailableBuildPosition());

    }



}