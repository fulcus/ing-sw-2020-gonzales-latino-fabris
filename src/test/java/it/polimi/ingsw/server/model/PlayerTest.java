package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.ViewClient;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.god.Apollo;
import it.polimi.ingsw.server.controller.god.Pan;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class PlayerTest {

    private Worker worker, worker2;
    private Player player, player2;
    private Game game;
    private Board board;
    private GameController gameController;
    private Socket socket, socket1;
    private ViewClient viewClient, viewClient1;
    private ArrayList<Player> players;
    Integer numOfPlayers;

    @Before
    public void setUp() {
        socket = new Socket();
        socket1 = new Socket();
        gameController = new GameController();
        viewClient = new ViewClient(socket, gameController);
        viewClient1 = new ViewClient(socket, gameController);
        gameController.setUpGame(viewClient);
        game = gameController.getGame();

        game.addPlayer("nick1", viewClient);
        game.addPlayer("nick2", viewClient1);
        board = game.getBoard();
        players = game.getPlayers();
        // numOfPlayers = game.getNumberOfPlayers();

        player = players.get(0);
        player2 = players.get(1);
        worker = player.getWorkers().get(0);
        worker2 = player.getWorkers().get(1);
        worker.setPosition(1,1);
        worker2.setPosition(2,2);
    }

    @After
    public void tearDown() {
        game = null;
        board = null;
        player = null;
        player2 = null;
        worker = null;
        worker2 = null;
        gameController = null;
    }

    @Test
    public void testGetNickname() {
        //challenger is set as last in players
        assertEquals(game.getChallenger().getNickname(),
                players.get(numOfPlayers -1).getNickname());
    }


    @Test
    public void testPlayerGod() {
        Apollo apollo = new Apollo(new GodController(new GameController()));
        player.setGod(apollo);
        assertEquals(apollo, player.getGod());

    }


    @Test
    public void testGetColor() {

        player.setColor(Color.BLUE);

        assertEquals(Color.BLUE, player.getColor());

        player.setColor(Color.WHITE);

        assertEquals(Color.WHITE, player.getColor());

        player.setColor(Color.BEIGE);

        assertEquals(Color.BEIGE, player.getColor());

    }

    @Test
    public void testSetColor() {

        player.setColor(Color.BLUE);

        assertEquals(Color.BLUE, player.getColor());

        player.setColor(Color.WHITE);

        assertEquals(Color.WHITE, player.getColor());

        player.setColor(Color.BEIGE);

        assertEquals(Color.BEIGE, player.getColor());
    }


    @Test
    public void testGetWorkers() {
        assertEquals(worker,player.getWorkers().get(0));
        assertEquals(worker2,player.getWorkers().get(1));
    }

    @Test
    public void testGetGame() {

        assertEquals(game, player.getGame());
    }

    @Test
    public void testColor() {
        player.setColor(Color.StringToColor("BLUE"));
        assertEquals(player.getColor(),Color.StringToColor("BLUE"));

        player.setColor(Color.StringToColor("WHITE"));
        assertEquals(player.getColor(),Color.StringToColor("WHITE"));

        player.setColor(Color.StringToColor("BEIGE"));
        assertEquals(player.getColor(),Color.StringToColor("BEIGE"));


    }

    @Test
    public void testPermissionToWinInPerimeter() {
        player.setPermissionToWinInPerimeter(true);
        assertTrue(player.getCanWinInPerimeter());

        player.setPermissionToWinInPerimeter(false);
        assertFalse(player.getCanWinInPerimeter());

    }

    @Test
    public void testLose() {
        Apollo apollo = new Apollo(new GodController(new GameController()));
        Pan pan = new Pan(new GodController(new GameController()));

        game.addGodChosenByChallenger(apollo);
        game.addGodChosenByChallenger(pan);

        player.setGod(apollo);
        player2.setGod(pan);
        assertTrue(game.getChosenGods().contains(apollo));
        assertTrue(game.getChosenGods().contains(pan));
        assertTrue(players.contains(player));
        int nPlayersBefore = numOfPlayers;

        player.lose();

        for(Worker worker : player.getWorkers()) {
            //removed workers from board
            Cell workerCell = worker.getPosition();
            assertFalse(workerCell.hasWorker());
        }

        assertFalse(game.getChosenGods().contains(apollo));
        assertFalse(players.contains(player));
        assertEquals(nPlayersBefore - 1,game.getNumberOfPlayers());
    }


}