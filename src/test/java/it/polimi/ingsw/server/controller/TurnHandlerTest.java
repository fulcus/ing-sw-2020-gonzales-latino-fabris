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
import org.mockito.Spy;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TurnHandlerTest {

    private TurnHandler turnHandler;

    @Mock
    private ViewClient client1;

    @Mock
    private ViewClient client2;

    @Mock
    private Game game;

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    @Mock
    private GameController gameController;


    @Before
    public void setUp() {
        gameController = mock(GameController.class);
        game = mock(Game.class);
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        client1 = mock(ViewClient.class);
        client2 = mock(ViewClient.class);

        ArrayList<Player> players = new ArrayList<>(2);
        players.add(player1);
        players.add(player2);
        when(game.getPlayers()).thenReturn(players);
        when(game.getNumberOfPlayers()).thenReturn(2);
        when(gameController.getGame()).thenReturn(game);
        //when(gameController.getTurnHandler()).thenReturn(turnHandler);

        turnHandler = new TurnHandler(game, gameController);
    }


    @After
    public void tearDown() {
        gameController = null;
        game = null;
        client1 = null;
        client2 = null;
        player1 = null;
        player2 = null;
        turnHandler = null;
    }

    @Test
    public void run() {
    }

    @Test
    public void setUpTurns() {

        ArrayList<God> godDeck = new ArrayList<>();
        GodController godController = mock(GodController.class);
        when(gameController.getGodController()).thenReturn(godController);
        Zeus zeus = new Zeus(gameController.getGodController());
        godDeck.add(zeus);
        when(gameController.getGodsDeck()).thenReturn(godDeck);

        when(game.getChallenger()).thenReturn(player1);
        when(player1.getClient()).thenReturn(client1);
        when(player2.getClient()).thenReturn(client2);
        when(player1.getNickname()).thenReturn("Nick1");
        when(player2.getNickname()).thenReturn("Nick2");

        when(client1.getGodFromChallenger(any(int.class), any(int.class))).thenReturn("Apollo", "Apollo", "Pan");
        ArrayList<God> chosenGods = mock(ArrayList.class);
        when(game.getChosenGods()).thenReturn(chosenGods);
        when(chosenGods.contains(any())).thenReturn(false);

        chosenGods.add(new Apollo(gameController.getGodController()));
        chosenGods.add(new Pan(gameController.getGodController()));

        when(client2.askPlayerGod()).thenReturn("Apollo");
        when(client1.askPlayerGod()).thenReturn("Pan");

        when(client1.challengerChooseStartPlayer()).thenReturn(null, "Nick1");

        Worker maleWorker1 = mock(Worker.class);
        Worker femaleWorker1 = mock(Worker.class);
        Worker maleWorker2 = mock(Worker.class);
        Worker femaleWorker2 = mock(Worker.class);
        ArrayList<Worker> workers1 = new ArrayList();
        workers1.add(maleWorker1);
        workers1.add(femaleWorker1);
        ArrayList<Worker> workers2 = new ArrayList();
        workers2.add(maleWorker2);
        workers2.add(femaleWorker2);
        when(player1.getWorkers()).thenReturn(workers1);
        when(player2.getWorkers()).thenReturn(workers2);
        int[] input = {2, 2};
        when(client2.askInitialWorkerPosition(anyString())).thenReturn(input);
        Board board = mock(Board.class);
        Cell cell = mock(Cell.class);
        when(game.getBoard()).thenReturn(board);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell);
        when(cell.isOccupied()).thenReturn(true, false);
        doNothing().when(maleWorker1).setPosition(any(int.class), any(int.class));
        doNothing().when(maleWorker2).setPosition(any(int.class), any(int.class));
        doNothing().when(femaleWorker1).setPosition(any(int.class), any(int.class));
        doNothing().when(femaleWorker2).setPosition(any(int.class), any(int.class));


        turnHandler.setUpTurns();


        verify(client1, times(1)).printAllGods(any());
        verify(client2, times(1)).printAllGods(any());
        verify(player1, times(1)).setGod(any(God.class));
        verify(player2, times(1)).setGod(any(God.class));
        verify(client1, times(2)).otherPlayerChoseGod(anyString(), anyString());

        verify(client2, times(1)).waitChallengerStartPlayer();
        verify(client1, times(2)).challengerChooseStartPlayer();
    }

    @Test
    public void startTurnFlow() {
    }

    @Test
    public void handleCyclicalCounter() {
    }

    @Test
    public void chooseWorker() {
    }

    @Test
    public void turn() {
    }

    @Test
    public void handleGameChange() {
    }

    @Test
    public void getCurrentPlayer() {
        assertNull(turnHandler.getCurrentPlayer());
    }

    @Test
    public void stopTurnFlow() {
        assertTrue(turnHandler.getGameAlive());
        turnHandler.stopTurnFlow();
        assertFalse(turnHandler.getGameAlive());
    }

    @Test
    public void getGameAlive() {
        assertTrue(turnHandler.getGameAlive());
    }

    @Test
    public void setNumberOfPLayersHasChanged() {
        turnHandler.setNumberOfPLayersHasChanged(true);
        assertTrue(turnHandler.numberOfPLayersHasChanged());
    }

    @Test
    public void numberOfPLayersHasChanged() {
        assertFalse(turnHandler.numberOfPLayersHasChanged());
        turnHandler.setNumberOfPLayersHasChanged(true);
        assertTrue(turnHandler.numberOfPLayersHasChanged());

    }

    @Test
    public void setNumberOfPlayers() {
        turnHandler.setNumberOfPlayers(3);
        assertEquals(turnHandler.getNumberOfPlayers(), 3);
    }
}