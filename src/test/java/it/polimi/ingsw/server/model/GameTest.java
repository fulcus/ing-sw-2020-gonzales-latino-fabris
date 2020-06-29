package it.polimi.ingsw.server.model;


import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.god.*;
import org.junit.After;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.net.Socket;


public class GameTest {

    Game game;

    @Mock
    private VirtualView clientView;

    GameController gameController;
    Socket socket;
    GodController godController;

    @Before
    public void setUp() {
        socket = new Socket();
        gameController = new GameController();
        game = new Game(2);
        godController = new GodController(gameController);
    }


    @After
    public void tearDown() {
        game = null;
        clientView = null;
    }


    @Test
    public void testAddPlayer() {
        assertEquals(0, game.getPlayers().size());
        game.addPlayer("Gianni", clientView);
        assertEquals(1, game.getPlayers().size());
    }


    @Test
    public void testRandomChallenger() {
        VirtualView clientView1 = mock(VirtualView.class);

        game.addPlayer("Pippo", clientView);
        game.addPlayer("Pluto", clientView1);
        assertNotNull(game.getChallenger());
    }


    @Test
    public void testAddGodChosenByChallenger(){
        God godTest = new Pan(godController);

        assertEquals(0, game.getChosenGods().size());

        game.addGodChosenByChallenger(godTest);
        assertEquals(1, game.getChosenGods().size());

    }


    @Test
    public void testGetBoard() {
        assertNotNull(game.getBoard());
    }


    @Test
    public void testGetNumberOfPlayers() {
        assertTrue(game.getNumberOfPlayers()==2 || game.getNumberOfPlayers()==3);
    }


    @Test
    public void testGetPlayers() {
        game.addPlayer("Pluto", clientView);
        assertEquals(1, game.getPlayers().size());
    }


    @Test
    public void testGetChallenger() {
        VirtualView clientView1 = mock(VirtualView.class);
        //VirtualView clientView2 = new VirtualView(socket, gameController);

        assertNull(game.getChallenger());
        game.addPlayer("Pippo", clientView);
        game.addPlayer("Pluto", clientView1);

        assertNotNull(game.getChallenger());
    }


    @Test
    public void testSetNumberOfPlayers() {
        game.setNumberOfPlayers(3);
        assertEquals(game.getNumberOfPlayers(), 3);
    }


    @Test
    public void testGetChosenGods() {
        assertEquals(0, game.getChosenGods().size());
    }


    @Test
    public void testRemovePlayer() {
        game.addPlayer("Pippo", clientView);

        VirtualView clientView1 = mock(VirtualView.class);
        game.addPlayer("Pluto", clientView1);

        game.removePlayer(game.getPlayers().get(0));

        assertEquals(1, game.getPlayers().size());

    }
}