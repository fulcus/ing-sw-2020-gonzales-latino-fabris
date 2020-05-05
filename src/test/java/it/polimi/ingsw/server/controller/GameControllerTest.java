package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Player;
import org.junit.*;
import it.polimi.ingsw.server.ViewClient;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class GameControllerTest {

    private GameController gameController;

    @Mock
    private ViewClient client;

    @Mock
    private ViewClient client2;

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
        client = null;
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

        when(client.askPlayerNickname()).thenReturn("", "Nick1");
        when(client.askPlayerColor()).thenReturn("BLACK", "BEIGE");

        doNothing().when(client).setPlayer(any(Player.class));

        player1 = mock(Player.class);
        when(client.getPlayer()).thenReturn(player1);
        doNothing().when(player1).setColor(any(Color.class));

        doNothing().when(client).notAvailableNickname();
        doNothing().when(client).notAvailableColor();

        gameController.addPlayer(client);

        verify(client, times(1)).connected();
        verify(client, times(1)).beginningView();
        verify(client, times(1)).setPlayer(any(Player.class));
        verify(client, times(1)).notAvailableNickname();
        verify(client, times(1)).notAvailableColor();

    }


    @Test
    public void winGame() {
        when(client.askNumberOfPlayers()).thenReturn(3);
        gameController.setUpGame(client);

        client2 = mock(ViewClient.class);
        player1 = mock(Player.class);
        player2 = mock(Player.class);

        when(client.winningView()).thenReturn(true);
        doNothing().when(client).killClient();
        doNothing().when(client2).killClient();
        when(client2.losingView(player1.getNickname())).thenReturn(true);

        when(player2.getClient()).thenReturn(client2);
        when(player1.getClient()).thenReturn(client, client);


        //Setting the configuration for 2 players of the game and adding them to the game
        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");
        when(client.getPlayer()).thenReturn(player1);
        when(player1.getNickname()).thenReturn("Nick1");
        doNothing().when(player1).setColor(any(Color.class));
        doNothing().when(client).setPlayer(any(Player.class));
        doNothing().when(client).connected();
        doNothing().when(client).beginningView();
        doNothing().when(client).setPlayer(any(Player.class));

        when(client2.askPlayerNickname()).thenReturn("Nick2");
        when(client2.askPlayerColor()).thenReturn("WHITE");
        when(client2.getPlayer()).thenReturn(player2);
        doNothing().when(player2).setColor(any(Color.class));
        doNothing().when(client2).setPlayer(any(Player.class));
        doNothing().when(client2).connected();
        doNothing().when(client2).beginningView();
        doNothing().when(client2).setPlayer(any(Player.class));

        gameController.addPlayer(client);
        gameController.addPlayer(client2);


        gameController.winGame(player1);

        assertFalse(gameController.getTurnHandler().getGameAlive());
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