package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PlayerTest {

    private Worker worker;
    private Worker worker2;
    private Player player;
    private Game game;
    private Map map;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("player");
        game.addPlayer("player2");
        map = game.getMap();
        player = game.getPlayers().get(0);
        worker = player.getWorkers().get(0);
        worker2 = player.getWorkers().get(1);
    }

    @After
    public void tearDown() {
        game = null;
        map = null;
        player = null;
        worker = null;
    }

    @Test
    public void testGetNickname() {

        assertEquals("player", player.getNickname());
    }

    @Test
    public void testSetChosenWorker() {

        player.setChosenWorker(worker);

        assertEquals(worker, player.getChosenWorker());
    }

    @Test
    public void testGetGod() {

        assertNull(player.getGod());


    }

    @Test
    public void testChooseGod() {

    }

    @Test
    public void testChooseWorker() {


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
    public void testCanWinInPerimeter() {

        player.setPermissionToWinInPerimeter(false);
        assertFalse(player.canWinInPerimeter());


    }

    @Test
    public void testCanMoveUp() {

        player.setPermissionToMoveUp(false);

        assertFalse(player.canMoveUp());
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
}