package it.polimi.ingsw.server.model;


import it.polimi.ingsw.server.ClientView;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.god.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.net.Socket;


public class GameTest {

    Game game;
    ClientView clientView;
    GameController gc;
    Socket socket;

    @Before
    public void setUp() {
        socket = new Socket();
        gc = new GameController();
        clientView = new ClientView(socket, gc);
        gc.setUpGame(clientView);
        game = gc.getGame();
    }


    @After
    public void tearDown() {
        game = null;
    }


    @Test
    public void testAddPlayer() {
        assertEquals(0, game.getPlayers().size());
        game.addPlayer("Gianni", clientView);
        assertEquals(1, game.getPlayers().size());
    }


    @Test
    public void testRandomChallenger() {
        ClientView clientView1 = new ClientView(socket, gc);
        ClientView clientView2 = new ClientView(socket, gc);

        game.addPlayer("Pippo", clientView);
        game.addPlayer("Pluto", clientView1);
        game.addPlayer("Indiana", clientView2);
        assertNotNull(game.getChallenger());
    }


    @Test
    public void testAddGodChosenByChallenger(){
        God godTest = new Pan(gc.getGodController());

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
        ClientView clientView1 = new ClientView(socket, gc);
        ClientView clientView2 = new ClientView(socket, gc);

        assertNull(game.getChallenger());
        game.addPlayer("Pippo", clientView);
        game.addPlayer("Pluto", clientView1);
        game.addPlayer("Indiana", clientView2);
        assertNotNull(game.getChallenger());
    }


    @Test
    public void testGetChosenGods() {
        assertEquals(0, game.getChosenGods().size());
    }


    @Test
    public void testRemovePlayer() {
        game.addPlayer("Pippo", clientView);

        ClientView clientView1 = new ClientView(socket, gc);
        game.addPlayer("Pluto", clientView1);

        assertEquals(2, game.getPlayers().size());

        int numOfPlayers = game.getNumberOfPlayers();

        game.removePlayer(game.getPlayers().get(0));

        assertEquals(1, game.getPlayers().size());
        assertTrue(game.getNumberOfPlayers() < numOfPlayers);
    }
}