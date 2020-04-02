package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WorkerMoveMapTest {

    private Worker worker;
    private Player player;
    private Game game;
    private Map map;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("nickname1");
        //game.addPlayer("nickname2");
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
    public void testCannotMoveInDomeCell() {

    }

    @Test
    public void testCannotMoveInWorkerCell() {
    }

    @Test
    public void testCannotMoveInOccupiedCell() {
    }

    @Test
    public void testCannotMoveInFriendlyWorkerCell() {
    }

    @Test
    public void testCannotStayStill() {
    }

    @Test
    public void testAllowedToMoveInPosition() {
    }

    @Test
    public void testNotAllowedToMoveInPosition() {
    }

    @Test
    public void testGetAllowedToMove() {
    }

    @Test
    public void testGetAbsolutePosition() {
    }

    @Test
    public void testUpdateCellsNotInMap() {
    }

    @Test
    public void testGetWorker() {
    }
}