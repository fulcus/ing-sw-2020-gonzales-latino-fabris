package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.ViewClient;
import it.polimi.ingsw.server.controller.god.Apollo;
import it.polimi.ingsw.server.controller.god.God;
import it.polimi.ingsw.server.controller.god.Pan;
import it.polimi.ingsw.server.controller.god.Zeus;
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
    private ViewClient client1;

    @Mock
    private ViewClient client2;

    @Mock
    private Player player1;

    @Mock
    private Player player2;


    @Before
    public void setUp() {

        gameController = new GameController();
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        client1 = mock(ViewClient.class);
        client2 = mock(ViewClient.class);

        when(client1.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client1);

        when(client1.askPlayerNickname()).thenReturn("Nick1");
        when(client2.askPlayerNickname()).thenReturn("Nick2");
        when(client1.askPlayerColor()).thenReturn("BEIGE");
        when(client2.askPlayerColor()).thenReturn("BLUE");

        doNothing().when(client1).setPlayer(any(Player.class));
        doNothing().when(client2).setPlayer(any(Player.class));

        when(client1.getPlayer()).thenReturn(player1);
        when(client2.getPlayer()).thenReturn(player2);
        when(player1.getClient()).thenReturn(client1);
        when(player2.getClient()).thenReturn(client2);

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
    public void runMoveEx() {

/*
        ViewClient client3 = mock(ViewClient.class);
        Player player3 = mock(Player.class);
        //when(client1.askNumberOfPlayers()).thenReturn(3);
        when(client3.askPlayerNickname()).thenReturn("Nick3");
        when(client3.askPlayerColor()).thenReturn("WHITE");
        when(client3.getPlayer()).thenReturn(player3);
        when(player3.getClient()).thenReturn(client3);
        doNothing().when(player3).setColor(any(Color.class));
        doNothing().when(player3).setColor(any(Color.class));
        gameController.addPlayer(client3);*/


        doNothing().when(client1).waitChallengerChooseGods(anyString());
        doNothing().when(client2).waitChallengerChooseGods(anyString());

        when(client1.getGodFromChallenger(any(int.class), any(int.class))).thenReturn("Apollo", "Apollo", "Pan");
        when(client2.getGodFromChallenger(any(int.class), any(int.class))).thenReturn("Pan", "Pan", "Apollo");
        //when(client3.getGodFromChallenger(any(int.class), any(int.class))).thenReturn("Zeus", "Pan", "Apollo");


        //da qui comincia il playersChooseGods
        when(client2.askPlayerGod()).thenReturn("Apollo");
        when(client1.askPlayerGod()).thenReturn("Pan");
        //when(client3.askPlayerGod()).thenReturn("Zeus");


        //
        when(client1.challengerChooseStartPlayer()).thenReturn("Nick1");
        when(client2.challengerChooseStartPlayer()).thenReturn("Nick2");
        //when(client3.challengerChooseStartPlayer()).thenReturn("Nick3");

        int[] input1 = {0, 0};
        int[] input2 = {0, 1};
        int[] input3 = {3, 3};
        int[] input4 = {4, 4};
        //int[] input5 = {4, 2};
        //int[] input6 = {3, 1};
        when(client1.askInitialWorkerPosition(anyString())).thenReturn(input1, input2);
        when(client2.askInitialWorkerPosition(anyString())).thenReturn(input3, input4);
        //when(client3.askInitialWorkerPosition(anyString())).thenReturn(input5, input6);


        when(client1.askChosenWorker()).thenReturn("FEMALE", "MALE");
        when(client2.askChosenWorker()).thenReturn("MALE", "FEMALE");
        //when(client3.askChosenWorker()).thenReturn("MALE", "FEMALE");

        when(client1.askMovementDirection()).thenReturn("N");
        when(client2.askMovementDirection()).thenReturn("N");
        //when(client3.askMovementDirection()).thenReturn("N");

        when(client1.askBuildingDirection()).thenReturn("E");
        when(client2.askBuildingDirection()).thenReturn("E");
      //  when(client3.askBuildingDirection()).thenReturn("E");

        Worker usefulWorker = gameController.getGame().getPlayers().get(0).getWorkers().get(0);
        usefulWorker.buildBlock(0, 2);
        usefulWorker.buildBlock(0, 2);
        usefulWorker.buildBlock(1, 2);
        usefulWorker.buildBlock(1, 2);
        usefulWorker.buildBlock(1, 0);
        usefulWorker.buildBlock(1, 0);
        usefulWorker.buildBlock(1, 1);
        usefulWorker.buildBlock(1, 1);

        //starting thread of turnhandler
        new Thread(gameController.getTurnHandler()).start();

        //check periodically that the turnhandler thread has played a turn
        //ie has looped at least one time in startTurnFlow
        //then issues command to exit the loop with stopTurnFlow
        do {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(gameController.getTurnHandler().getTurnCounter() < 1);

        gameController.getTurnHandler().stopTurnFlow();
    }


    @Test
    public void runBuildEx() {

        doNothing().when(client1).waitChallengerChooseGods(anyString());
        doNothing().when(client2).waitChallengerChooseGods(anyString());

        when(client1.getGodFromChallenger(any(int.class), any(int.class))).thenReturn("Apollo", "Apollo", "Pan");
        when(client2.getGodFromChallenger(any(int.class), any(int.class))).thenReturn("Pan", "Pan", "Apollo");

        //da qui comincia il playersChooseGods
        when(client2.askPlayerGod()).thenReturn("Apollo");
        when(client1.askPlayerGod()).thenReturn("Pan");

        //
        when(client1.challengerChooseStartPlayer()).thenReturn("Nick1");
        when(client2.challengerChooseStartPlayer()).thenReturn("Nick2");

        int[] input1 = {0, 0};
        int[] input2 = {3, 2};
        int[] input3 = {0, 1};
        int[] input4 = {0, 4};
        when(client1.askInitialWorkerPosition(anyString())).thenReturn(input1, input2);
        when(client2.askInitialWorkerPosition(anyString())).thenReturn(input3, input4);


        when(client1.askChosenWorker()).thenReturn("FEMALE", "MALE");
        when(client2.askChosenWorker()).thenReturn("MALE", "FEMALE");

        when(client1.askMovementDirection()).thenReturn("N");
        when(client2.askMovementDirection()).thenReturn("W");

        when(client1.askBuildingDirection()).thenReturn("S");
        when(client2.askBuildingDirection()).thenReturn("E");

        //It's useful because in this way I can easily test the
        //Unable to build Exception and so to collect other lines of for the coverage.
        Worker usefulWorker = gameController.getGame().getPlayers().get(0).getWorkers().get(0);
        usefulWorker.buildDome(0, 2);
        usefulWorker.buildDome(0, 3);
        usefulWorker.buildDome(1, 0);
        usefulWorker.buildDome(1, 1);
        usefulWorker.buildDome(1, 2);
        usefulWorker.buildDome(1, 3);
        usefulWorker.buildDome(1, 4);



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
        } while(gameController.getTurnHandler().getTurnCounter() < 1);

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

        gameController.getTurnHandler().handleGameChange("Nick1");

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