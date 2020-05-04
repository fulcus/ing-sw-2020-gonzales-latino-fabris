package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.serializableObjects.Message;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import org.junit.*;
import it.polimi.ingsw.server.ViewClient;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class GameControllerTest {

    private GameController gameController;

    @Mock
    private ViewClient client;

    @Mock
    private ViewClient client2;

    @Mock
    private TurnHandler turnHandler;

    @Mock
    private Player player1;

    @Mock
    private Player player2;


    @Before
    public void setUp() {
        gameController = new GameController();
        client = mock(ViewClient.class);

    }

    @After
    public void tearDown() {
        gameController = null;

    }

    @Test
    public void setUpGame() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        assertNotNull(gameController.getGodController());
        assertNotNull(gameController.getGame());
        assertNotNull(gameController.getTurnHandler());
        assertEquals(gameController.getGodsDeck().size(), 14);
    }


    @Test
    public void addPlayer() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        doNothing().when(client).connected();
        doNothing().when(client).beginningView();

        when(client.askPlayerNickname()).thenReturn("Nick1", "");
        when(client.askPlayerColor()).thenReturn("BLUE");
        //when(gameController.nicknameIsAvailable("Nick1")).thenReturn(true);
        //when(gameController.nicknameIsAvailable("")).thenReturn(false);
        doNothing().when(client).notAvailableNickname();

        gameController.addPlayer(client);

        verify(client).connected();
        verify(client).beginningView();

        //assertEquals(gameController.getPlayersConnected(), 1);
        assertTrue(gameController.getGame().getPlayers().size() > 0);

    }

    @Test
    public void winGame() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        client = mock(ViewClient.class);
        client2 = mock(ViewClient.class);


        //when(gameController.getGame().addPlayer(anyString(), client)).
        player1 = gameController.getGame().addPlayer("Nick1", client);
        player2 = gameController.getGame().addPlayer("Nick2", client2);

        //when(gameController.getTurnHandler()).thenReturn(turnHandler);
        //when(gameController.getTurnHandler().getCurrentPlayer()).thenReturn(player1);
        when(player1.getClient()).thenReturn(client);
        when(player2.getClient()).thenReturn(client2);
        when(client.winningView()).thenReturn(true);
        doNothing().when(client).killClient();
        when(client2.losingView("Nick1")).thenReturn(true);

        gameController.winGame(player1);

        assertNull(gameController);

    }


    @Test
    public void getGodsDeck() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getGodsDeck());
    }

    @Test
    public void getGame() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getGame());
    }

    @Test
    public void getGodController() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getGodController());

    }

    @Test
    public void getTurnHandler() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getTurnHandler());
    }

/*
    @Test
    public void getPlayersConnected() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getPlayersConnected());
    }
*/

    @Test
    public void getExecutorPlayerAdder() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getExecutorPlayerAdder());
    }


}