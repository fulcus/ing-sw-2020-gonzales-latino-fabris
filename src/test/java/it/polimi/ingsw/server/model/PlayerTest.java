package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.ViewClient;
import it.polimi.ingsw.server.controller.god.Apollo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


public class PlayerTest {

    private Worker worker, worker2;
    private Player player, player2;
    private Game game;

    @Mock
    private ViewClient viewClient;

    @Mock
    private ViewClient viewClient1;

    @Before
    public void setUp() {

        viewClient = mock(ViewClient.class);
        viewClient1 = mock(ViewClient.class);

        game = new Game(2);

        game.addPlayer("nick1", viewClient);
        game.addPlayer("nick2", viewClient1);

        player = game.getPlayers().get(0);
        player2 = game.getPlayers().get(1);
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
        viewClient = null;
        viewClient1 = null;
    }


    @Test
    public void testGetClient() {
        assertNotNull(game.getPlayers().get(0).getClient());
    }


    @Test
    public void testGetNickname() {

        assertNotNull(player.getNickname());
    }


    @Test
    public void testSetAndGetPlayerGod() {
        Apollo apollo = mock(Apollo.class);

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
        player.setColor(Color.stringToColor("BLUE"));
        assertEquals(player.getColor(),Color.stringToColor("BLUE"));

        player.setColor(Color.stringToColor("WHITE"));
        assertEquals(player.getColor(),Color.stringToColor("WHITE"));

        player.setColor(Color.stringToColor("BEIGE"));
        assertEquals(player.getColor(),Color.stringToColor("BEIGE"));
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

        player.lose();
        assertEquals(game.getPlayers().size(), 1);
    }


}