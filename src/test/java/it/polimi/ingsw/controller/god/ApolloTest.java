package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ApolloTest {


    //testing Apollo AND God behaviors
    //ie also methods that Apollo doesn't modify

    private Worker worker, worker2;
    private Player player, player2;
    private Game game;
    private Board board;
    private ArrayList<Player> players;
    Integer numOfPlayers;
    GodController godController;

    @Before
    public void setUp() {
        game = new Game(2);
        game.addPlayer("nick1");
        game.addPlayer("nick2");
        board = game.getBoard();
        players = game.getPlayers();
        numOfPlayers = game.getNumberOfPlayers();



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
        worker = null;
    }


    @Test
    public void move() {
    }

    @Test
    public void updateMoveMap() {
    }

    @Test
    public void getGodController() {
    }

    @Test
    public void getDescription() {
    }
}