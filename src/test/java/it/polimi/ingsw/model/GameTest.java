package it.polimi.ingsw.model;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    Game game;

    @Before
    public void setUp() {
        game = new Game(3);
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void testAddPlayer() {
        game.addPlayer("Gianni");
        assertEquals(1, game.getPlayers().size());
    }


    @Test
    public void testRandomChallenger() {
        game.addPlayer("Pippo");
        game.addPlayer("Pluto");
        game.addPlayer("Paperino");
        assertNotNull(game.getChallenger());
    }


    @Test
    public void testGetMap() {
        assertNotNull(game.getBoard());
    }

    @Test
    public void testGetNumberOfPlayers() {
        assertEquals(3, game.getNumberOfPlayers());
    }

    @Test
    public void testGetPlayers() {
        game.addPlayer("pluto");
        game.addPlayer("donald");
        assertEquals(2, game.getPlayers().size());
    }

    @Test
    public void testGetChallenger() {
        assertNull(game.getChallenger());
        game.addPlayer("Pippo");
        game.addPlayer("Pluto");
        game.addPlayer("Paperino");
        assertNotNull(game.getChallenger());
    }

    @Test
    public void testGetChosenGods() {
        assertEquals(0, game.getChosenGods().size());
    }
}