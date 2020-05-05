package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.ViewClient;
import it.polimi.ingsw.server.controller.god.Demeter;
import it.polimi.ingsw.server.controller.god.Hephaestus;
import it.polimi.ingsw.server.controller.god.Hestia;
import it.polimi.ingsw.server.controller.god.Prometheus;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class GodControllerTest {

    @Mock
    private ViewClient client;

    @Mock
    private Worker worker;

    @Mock
    private Player player;

    private GameController gameController;
    private GodController godController;


    @Before
    public void setUp() {
        gameController = new GameController();
        client = mock(ViewClient.class);
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        godController = new GodController(gameController);
    }


    @After
    public void tearDown() {
        gameController = null;
        godController = null;
    }


    @Test
    public void updateCurrentClient() {
        assertNull(godController.getCurrentClient());
        godController.updateCurrentClient(client);
        assertNotNull(godController.getCurrentClient());
        assertEquals(godController.getCurrentClient(), client);
    }


    @Test
    public void getCurrentClient() {
        assertNull(godController.getCurrentClient());
        godController.updateCurrentClient(client);
        assertNotNull(godController.getCurrentClient());
    }


    @Test
    public void getInputInCoordinates() {
        godController.getInputInCoordinates("NE");
        assertEquals(godController.getInputInCoordinates("N")[0], -1);
        assertEquals(godController.getInputInCoordinates("NE")[0], -1);
        assertEquals(godController.getInputInCoordinates("NW")[0], -1);
        assertEquals(godController.getInputInCoordinates("S")[0], 1);
        assertEquals(godController.getInputInCoordinates("SE")[0], 1);
        assertEquals(godController.getInputInCoordinates("SW")[0], 1);
        assertEquals(godController.getInputInCoordinates("W")[0], 0);
        assertEquals(godController.getInputInCoordinates("E")[0], 0);
        assertEquals(godController.getInputInCoordinates("U")[1], 0);

        assertNull(godController.getInputInCoordinates("notAvailable"));
    }


    @Test
    public void getInputMove() {
        godController.updateCurrentClient(client);
        when(client.askMovementDirection()).thenReturn("NE");

        assertNotNull(godController.getInputMove());
    }


    @Test
    public void wantToMoveAgain() {
        godController.updateCurrentClient(client);
        when(client.askMoveAgain()).thenReturn("Y");

        assertTrue(godController.wantToMoveAgain());
    }


    @Test
    public void wantToMoveUp() {
        godController.updateCurrentClient(client);
        when(client.askWantToMoveUp()).thenReturn("Y");

        assertTrue(godController.wantToMoveUp());
    }


    @Test
    public void wantToMoveEnemy() {
        godController.updateCurrentClient(client);
        when(client.askWantToMoveEnemy()).thenReturn("Y");

        assertTrue(godController.wantToMoveEnemy());
    }


    @Test
    public void forceMoveEnemy() {
        godController.updateCurrentClient(client);
        Player playerTest = new Player(gameController.getGame(), "nick", client);
        worker = mock(Worker.class);
        when(client.askWorkerToMove(playerTest.getWorkers(), worker)).thenReturn(null);

        assertTrue(godController.forceMoveEnemy(playerTest.getWorkers(), worker) == null);
    }


    @Test
    public void displayBoard() {

        //Setting one player in the game
        player = mock(Player.class);
        doNothing().when(client).connected();
        doNothing().when(client).beginningView();

        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");

        doNothing().when(client).setPlayer(any(Player.class));

        when(client.getPlayer()).thenReturn(player);
        when(player.getClient()).thenReturn(client);
        doNothing().when(player).setColor(any(Color.class));

        gameController.addPlayer(client);


        doNothing().when(client).printMap();

        godController.displayBoard();

        verify(client).printMap();
    }


    @Test
    public void getBuildingInputAtlas() {
        godController.updateCurrentClient(client);
        String[] valueAtlas = {"NE", "B"};
        when(client.askBuildingDirectionAtlas()).thenReturn(valueAtlas);

        assertNotNull(godController.getBuildingInputAtlas());

        String[] valueAtlas2 = {"S", "D"};
        when(client.askBuildingDirectionAtlas()).thenReturn(valueAtlas2);

        assertNotNull(godController.getBuildingInputAtlas());

    }


    @Test
    public void getBuildingInput() {
        godController.updateCurrentClient(client);
        when(client.askBuildingDirection()).thenReturn("N");

        assertNotNull(godController.getBuildingInput());
    }


    @Test
    public void wantToBuildAgain() {
        godController.updateCurrentClient(client);
        when(client.askBuildAgainDemeter()).thenReturn("Y");
        when(client.askBuildAgainHephaestus()).thenReturn("Y");
        when(client.askBuildAgainHestia()).thenReturn("Y");
        when(client.askBuildPrometheus()).thenReturn("Y");

        assertTrue(godController.wantToBuildAgain(new Demeter(godController)));
        assertTrue(godController.wantToBuildAgain(new Hestia(godController)));
        assertTrue(godController.wantToBuildAgain(new Hephaestus(godController)));
        assertTrue(godController.wantToBuildAgain(new Prometheus(godController)));

    }


    @Test
    public void allowBuildUnderneath() {
        godController.updateCurrentClient(client);
        assertNotNull(godController.getCurrentClient());

        doNothing().when(client).printBuildUnderneath();

        godController.allowBuildUnderneath();

        verify(client).printBuildUnderneath();
    }


    @Test
    public void errorMoveScreen() {
        godController.updateCurrentClient(client);
        assertNotNull(godController.getCurrentClient());

        doNothing().when(client).printMoveErrorScreen();

        godController.errorMoveScreen();

        verify(client).printMoveErrorScreen();
    }


    @Test
    public void errorMoveDecisionScreen() {
        godController.updateCurrentClient(client);
        when(client.printMoveDecisionError()).thenReturn("Y");

        assertTrue(godController.errorMoveDecisionScreen());
    }


    @Test
    public void errorBuildDecisionScreen() {
        godController.updateCurrentClient(client);
        when(client.printBuildDecisionError()).thenReturn("Y");

        assertTrue(godController.errorBuildDecisionScreen());
    }


    @Test
    public void errorBuildInSamePosition() {
        godController.updateCurrentClient(client);
        assertNotNull(godController.getCurrentClient());

        doNothing().when(client).printBuildInSamePositionScreen();

        godController.errorBuildInSamePosition();

        verify(client).printBuildInSamePositionScreen();
    }


    @Test
    public void errorBuildScreen() {
        godController.updateCurrentClient(client);
        assertNotNull(godController.getCurrentClient());

        doNothing().when(client).printBuildGeneralErrorScreen();

        godController.errorBuildScreen();

        verify(client).printBuildGeneralErrorScreen();
    }


    @Test
    public void errorBuildBlockScreen() {
        godController.updateCurrentClient(client);
        assertNotNull(godController.getCurrentClient());

        doNothing().when(client).printBuildBlockErrorScreen();

        godController.errorBuildBlockScreen();

        verify(client).printBuildBlockErrorScreen();
    }
    
}