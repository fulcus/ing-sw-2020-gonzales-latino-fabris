package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WorkerBuildMapTest {
    private Worker worker;
    private Worker worker2;
    private Worker enemyWorker;
    private Worker enemyWorker2;

    private Player player;
    private Player player2;
    private Game game;
    private WorkerBuildMap buildMap;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("nickname1");
        game.addPlayer("nickname2");
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
    public void cannotBuildInWorkerCell() {
        worker.setPosition(2,1);
        worker2.setPosition(2,2);
        buildMap.cannotBuildInWorkerCell();
        assertFalse(buildMap.isAllowedToBuildBoard(2,2));

    }

    @Test
    public void cannotBuildInOccupiedCell() {
        worker.setPosition(2,1);
        worker2.setPosition(2,2);
        worker.buildDome(2,0);
        buildMap.cannotBuildInOccupiedCell();
        assertFalse(buildMap.isAllowedToBuildBoard(2,2));
        assertFalse(buildMap.isAllowedToBuildBoard(2,0));

    }

    @Test
    public void cannotBuildUnderneath() {
        worker.setPosition(2,1);
        buildMap.cannotBuildUnderneath();
        assertFalse(buildMap.isAllowedToBuildBoard(2,1));
        buildMap.canBuildUnderneath();
        assertTrue(buildMap.isAllowedToBuildBoard(2,1));

    }

    @Test
    public void cannotBuildInPerimeter() {
        worker.setPosition(3,3);
        buildMap.cannotBuildInPerimeter();
        assertFalse(buildMap.isAllowedToBuildBoard(4,3));


    }
}