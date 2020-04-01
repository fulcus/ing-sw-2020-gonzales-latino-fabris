package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Before;

public class GameTest extends TestCase {

    Game game;

    @Before
    public void setUp() {
        game = new Game(3);
    }

    public void testAddPlayer() {
        game.addPlayer("Gianni");
        assertEquals(1, game.getPlayers().size());
    }

    public void testAddChosenGod() {
    }

    public void testGetMap() {
    }

    public void testGetNumberOfPlayers() {
    }

    public void testGetPlayers() {
    }

    public void testGetChallenger() {
    }

    public void testGetChosenGods() {
    }
}