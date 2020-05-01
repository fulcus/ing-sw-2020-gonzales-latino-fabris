package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import org.junit.Assert;
import org.junit.Test;
import it.polimi.ingsw.server.ViewClient;
import org.junit.After;
import org.junit.Before;

import java.net.Socket;

import static org.junit.Assert.*;

public class GameControllerTest {

    private GameController gameController;


    @Before
    public void setUp() {
        gameController = new GameController();

    }

    @After
    public void tearDown() {
        gameController = null;

    }

    @Test
    public void setUpGame() {
        ViewClient viewClient = new ViewClient(new Socket(), gameController);

        gameController.setUpGame(viewClient);

        assertNotNull(gameController.getGodController());
        assertNotNull(gameController.getGame());
        assertNotNull(gameController.getTurnHandler());
        assertEquals(gameController.getGodsDeck().size(), 14);
    }


    @Test
    public void addPlayer() {
    }

    @Test
    public void winGame() {
    }

    @Test
    public void getGodsDeck() {
    }

    @Test
    public void getGame() {
    }

    @Test
    public void getGodController() {
    }

    @Test
    public void getTurnHandler() {
    }
}