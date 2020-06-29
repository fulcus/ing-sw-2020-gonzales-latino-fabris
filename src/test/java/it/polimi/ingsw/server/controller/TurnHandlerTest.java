package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.server.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class TurnHandlerTest {

    private GameController gameController;

    @Mock
    private VirtualView client1;

    @Mock
    private VirtualView client2;

    @Mock
    private Player player1;

    @Mock
    private Player player2;


    @Before
    public void setUp() {

        gameController = new GameController();
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        client1 = mock(VirtualView.class);
        client2 = mock(VirtualView.class);

        when(client1.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client1);

        when(client1.askPlayerNickname()).thenReturn("Nick1");
        when(client2.askPlayerNickname()).thenReturn("Nick2");
        when(client1.askPlayerColor(any())).thenReturn("BEIGE");
        when(client2.askPlayerColor(any())).thenReturn("BLUE");

        doNothing().when(client1).setPlayer(any(Player.class));
        doNothing().when(client2).setPlayer(any(Player.class));

        when(client1.getPlayer()).thenReturn(player1);
        when(client2.getPlayer()).thenReturn(player2);
        when(player1.getClient()).thenReturn(client1);
        when(player2.getClient()).thenReturn(client2);
        when(player1.getColor()).thenReturn(Color.BEIGE);
        when(player2.getColor()).thenReturn(Color.BLUE);

        doNothing().when(player1).setColor(any(Color.class));
        doNothing().when(player2).setColor(any(Color.class));

        gameController.addPlayer(client1);
        gameController.addPlayer(client2);
        
    }


    @After
    public void tearDown() {
        gameController = null;
        client1 = null;
        client2 = null;
        player1 = null;
        player2 = null;
    }


    @Test
    public void run() {


        doNothing().when(client1).waitChallengerChooseGods(anyString());
        doNothing().when(client2).waitChallengerChooseGods(anyString());

        when(client1.getGodFromChallenger(any(int.class), any(int.class))).thenReturn("Apollo", "Apollo", "Pan");
        when(client2.getGodFromChallenger(any(int.class), any(int.class))).thenReturn("Pan", "Pan", "Apollo");

        //da qui comincia il playersChooseGods
        when(client2.askPlayerGod()).thenReturn("Zeus", "Apollo");
        when(client1.askPlayerGod()).thenReturn("Pan");

        //
        when(client1.challengerChooseStartPlayer()).thenReturn("Nick1");
        when(client2.challengerChooseStartPlayer()).thenReturn("Nick2");

        int[] input1 = {2, 2};
        int[] input2 = {3, 3};
        int[] input3 = {1, 1};
        int[] input4 = {4, 4};
        when(client1.askInitialWorkerPosition(anyString())).thenReturn(input1, input2);
        when(client2.askInitialWorkerPosition(anyString())).thenReturn(input3, input4);


        when(client1.askChosenWorker()).thenReturn("FEMALE", "MALE");
        when(client2.askChosenWorker()).thenReturn("MALE", "FEMALE");

        when(client1.askMovementDirection()).thenReturn("N");
        when(client2.askMovementDirection()).thenReturn("N");

        when(client1.askBuildingDirection()).thenReturn("E");
        when(client2.askBuildingDirection()).thenReturn("E");


        //starting thread of turnhandler
        new Thread(gameController.getTurnHandler()).start();

        //check periodically that the turnhandler thread has played a turn
        //ie has looped at least one time in startTurnFlow
        //then issues command to exit the loop with stopTurnFlow
        do {
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(gameController.getTurnHandler().getTurnCounter() < 2);

        gameController.getTurnHandler().stopTurnFlow();
    }


    @Test
    public void handleCyclicalCounter() {
        assertEquals(gameController.getTurnHandler().handleCyclicalCounter(3), 0);
        assertEquals(gameController.getTurnHandler().handleCyclicalCounter(2), 1);
        assertEquals(gameController.getTurnHandler().handleCyclicalCounter(1), 0);

    }


    @Test
    public void handleGameChange() {

        assertEquals(gameController.getGame().getNumberOfPlayers(), 2);
        gameController.getTurnHandler().handleGameChange("Nick1");
        assertTrue(gameController.getTurnHandler().numberOfPLayersHasChanged());
    }


    @Test
    public void getCurrentPlayer() {
        assertNull(gameController.getTurnHandler().getCurrentPlayer());
    }


    @Test
    public void stopTurnFlow() {
        assertTrue(gameController.getTurnHandler().getGameAlive());
        gameController.getTurnHandler().stopTurnFlow();
        assertFalse(gameController.getTurnHandler().getGameAlive());
    }


    @Test
    public void getGameAlive() {
        assertTrue(gameController.getTurnHandler().getGameAlive());
    }


    @Test
    public void setNumberOfPLayersHasChanged() {
        gameController.getTurnHandler().setNumberOfPLayersHasChanged(true);
        assertTrue(gameController.getTurnHandler().numberOfPLayersHasChanged());
    }


    @Test
    public void numberOfPLayersHasChanged() {
        assertFalse(gameController.getTurnHandler().numberOfPLayersHasChanged());
        gameController.getTurnHandler().setNumberOfPLayersHasChanged(true);
        assertTrue(gameController.getTurnHandler().numberOfPLayersHasChanged());

    }


    @Test
    public void setNumberOfPlayers() {
        gameController.getTurnHandler().setNumberOfPlayers(3);
        assertEquals(gameController.getTurnHandler().getNumberOfPlayers(), 3);
    }


    @Test
    public void getTurnCounter() {
        assertEquals(gameController.getTurnHandler().getTurnCounter(), 0);
    }


    @Test
    public void getNumberOfPlayers() {
        assertEquals(gameController.getTurnHandler().getNumberOfPlayers(), 2);
    }


}