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
    private GameController gameController;
    private ViewClient viewClient;
    private ArrayList<Player> players;

    @Before
    public void setUp() {
        Socket socket, socket1;
        socket = new Socket();
        socket1 = new Socket();
        gameController = new GameController();
        viewClient = new ViewClient(socket, gameController);
        ViewClient viewClient1 = new ViewClient(socket1, gameController);
        //gameController.setUpGame(viewClient);
        game = new Game(2);

        player = new Player(game, "nick1", viewClient);
        player2 = new Player(game, "nick2", viewClient1);
        players = new ArrayList<Player>(2);
        players.add(player);
        players.add(player2);

        game.getPlayers().add(player);
        game.getPlayers().add(player2);
        //players = game.getPlayers();
        //player = players.get(0);
        //player2 = players.get(1);
        worker = player.getWorkers().get(0);
        worker2 = player.getWorkers().get(1);
        worker.setPosition(1,1);
        worker2.setPosition(2,2);
    }

    @After
    public void tearDown() {
        game = null;
        player = null;
        player2 = null;
        worker = null;
        worker2 = null;
        gameController = null;
        viewClient = null;
    }


    @Test
    public void testGetClient() {
        assertEquals(players.get(0).getClient(), viewClient);
    }


    @Test
    public void testGetNickname() {
        assertEquals(players.get(0).getNickname(), "nick1");
    }


    @Test
    public void testSetAndGetPlayerGod() {
        Apollo apollo = new Apollo(new GodController(gameController));

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
    public void testPermissionToMoveUp() {
        player.setPermissionToMoveUp(true);
        assertTrue(player.getCanMoveUp());

        player.setPermissionToMoveUp(false);
        assertFalse(player.getCanMoveUp());
    }


    @Test
    public void testLose() {
        /*
        //Apollo apollo = new Apollo(new GodController(gameController));
        //Pan pan = new Pan(new GodController(gameController));

        //game.addGodChosenByChallenger(apollo);
        //game.addGodChosenByChallenger(pan);

        //player.setGod(apollo);
        //player2.setGod(pan);
        //assertTrue(game.getChosenGods().contains(apollo));
        //assertTrue(game.getChosenGods().contains(pan));
        assertTrue(players.contains(player));
        int nPlayersBefore = game.getNumberOfPlayers();


        game.getPlayers().add(player);
        game.getPlayers().add(player2);
        assertTrue(players.contains(player));

        player.lose();

        for(Worker worker : player.getWorkers()) {
            //removed workers from board
            Cell workerCell = worker.getPosition();
            assertFalse(workerCell.hasWorker());
        }

        //assertFalse(game.getChosenGods().contains(apollo));
        //assertFalse(players.contains(player));
        //assertEquals(nPlayersBefore - 1,game.getNumberOfPlayers());
        */

    }


}