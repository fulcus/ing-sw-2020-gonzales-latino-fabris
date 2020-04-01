package it.polimi.ingsw.model;
import org.junit.*;
import org.junit.Before;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PlayerTest {

   private Player player;
    private Game game;
    private Worker worker;


    @Before
    public void setupPlayer()
    {
        game = new Game(2);
        player = new Player(game,"player");
        worker = new Worker(player,Sex.FEMALE);

    }

    @After
    public void clean()
    {
        game = null;
        player = null;
        worker = null;
    }

    @Test
    public void testGetNickname() {

        Assert.assertEquals("player" ,player.getNickname());
    }

    @Test
    public void testSetChosenWorker() {

        player.setChosenWorker(worker);

        Assert.assertEquals(worker,player.getChosenWorker());
    }

    @Test
    public void testGetGod() {

        Assert.assertNull(player.getGod());


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

        Assert.assertEquals(Color.BLUE,player.getColor());

        player.setColor(Color.WHITE);

        Assert.assertEquals(Color.WHITE,player.getColor());

        player.setColor(Color.BEIGE);

        Assert.assertEquals(Color.BEIGE,player.getColor());

    }

    @Test
    public void testSetColor() {

        player.setColor(Color.BLUE);

        Assert.assertEquals(Color.BLUE,player.getColor());

        player.setColor(Color.WHITE);

        Assert.assertEquals(Color.WHITE,player.getColor());

        player.setColor(Color.BEIGE);

        Assert.assertEquals(Color.BEIGE,player.getColor());
    }

    @Test
    public void testCanWinInPerimeter() {

        player.setPermissionToWinInPerimeter(false);
        Assert.assertFalse(player.canWinInPerimeter());


    }

    @Test
    public void testCanMoveUp() {

        player.setPermissionToMoveUp(false);

        Assert.assertFalse(player.canMoveUp());
    }


    @Test
    public void testGetWorkers() {

    }

    @Test
    public void testGetGame() {

        Assert.assertEquals(game, player.getGame());
    }
}