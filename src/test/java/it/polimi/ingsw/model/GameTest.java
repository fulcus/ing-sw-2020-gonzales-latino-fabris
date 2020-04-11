package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
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
        Assert.assertEquals(1, game.getPlayers().size());
    }

    /*@Test
    public void testAddChosenGod() {
        Assert.assertEquals(0, game.getChosenGods().size());
        game.addPlayer("Pippo");
        game.addPlayer("Pluto");
        game.addPlayer("Paperino");
        game.addChosenGods();
        Assert.assertEquals(3, game.getChosenGods().size());
    }*/

    @Test
    public void testRandomChallenger() {
        game.addPlayer("Pippo");
        game.addPlayer("Pluto");
        game.addPlayer("Paperino");
        Assert.assertNotNull(game.getChallenger());
    }


    @Test
    public void testGetMap() {
        Assert.assertNotNull(game.getBoard());
    }

    @Test
    public void testGetNumberOfPlayers() {
        Assert.assertEquals(3, game.getNumberOfPlayers());
    }

    @Test
    public void testGetPlayers() {
        game.addPlayer("pluto");
        game.addPlayer("donald");
        Assert.assertEquals(2, game.getPlayers().size());
    }

    @Test
    public void testGetChallenger() {
        Assert.assertNull(game.getChallenger());
        game.addPlayer("Pippo");
        game.addPlayer("Pluto");
        game.addPlayer("Paperino");
        Assert.assertNotNull(game.getChallenger());
    }

    @Test
    public void testGetChosenGods() {
        Assert.assertEquals(0, game.getChosenGods().size());
    }
}