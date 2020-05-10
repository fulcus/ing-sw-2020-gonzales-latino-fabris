package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class AthenaTest {

    private Athena athena;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private WorkerMoveMap workerMoveMap;

    @Mock
    private Player player;

    @Mock
    private Game game;


    @Before
    public void setUp() {
        godController = mock(GodController.class);
        athena = new Athena(godController);
        game = mock(Game.class);
        player = mock(Player.class);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);

    }


    @After
    public void tearDown() {
        godController = null;
        athena = null;
        worker = null;
        workerMoveMap = null;
        player = null;
    }


    private void settingUsualParameters() {
        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);

        doNothing().when(godController).errorMoveScreen();

        //setting the behaviour for the updateMoveMap
        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        doNothing().when(workerMoveMap).resetMap();
        doNothing().when(workerMoveMap).updateCellsOutOfMap();
        doNothing().when(workerMoveMap).updateMoveUpRestrictions();
        doNothing().when(workerMoveMap).cannotStayStill();
        doNothing().when(workerMoveMap).cannotMoveInOccupiedCell();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true);

        //setting the behaviour of the move
        int[] move = {1, 1};
        when(godController.getInputMove()).thenReturn(move, move);
        when(worker.getPosition()).thenReturn(cell);
        //many return because of the many calls of the function
        when(cell.getX()).thenReturn(2, 2);
        when(cell.getY()).thenReturn(2, 2);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(true);
        //doNothing().when(godController).errorMoveScreen();   //(already set above, but leave it here because could be useful for other classes)
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        //setting the behaviour of the win condition
        when(worker.getLevel()).thenReturn(2);
        when(worker.getLevelVariation()).thenReturn(1);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getCanWinInPerimeter()).thenReturn(true);

        //setting the update build map matrix behaviour
        WorkerBuildMap workerBuildMap = mock(WorkerBuildMap.class);
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).resetMap();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        //setting the build behaviour
        //when(worker.getPlayer()).thenReturn(player);
        //Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);
        int[] build = {0, 1};
        when(godController.getBuildingInput()).thenReturn(build, build);
        //The getPosition of the worker and the getX and getY are already defined in the move part above
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(false, true);
        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2, cell2);
        when(cell2.getLevel()).thenReturn(2);
        doNothing().when(worker).buildBlock(any(int.class), any(int.class));
        doNothing().when(godController).errorBuildScreen();
    }



    @Test
    public void evolveTurn() throws UnableToMoveException, UnableToBuildException, WinException {
        settingUsualParameters();

        Player player2 = mock(Player.class);
        //Creating a mock arraylist of players of the game.
        //It will behave in the same way as if created during the game.
        ArrayList<Player> gamePlayers = new ArrayList<>(2);
        gamePlayers.add(player);
        gamePlayers.add(player2);
        when(game.getPlayers()).thenReturn(gamePlayers);
        doNothing().when(player).setPermissionToMoveUp(anyBoolean());
        doNothing().when(player2).setPermissionToMoveUp(anyBoolean());

        athena.evolveTurn(worker);

        verify(worker, times(2)).getLevelVariation();
        verify(worker, times(5)).getPlayer();
        verify(player, times(1)).setPermissionToMoveUp(anyBoolean());
        verify(player2, times(1)).setPermissionToMoveUp(anyBoolean());

    }

    @Test
    public void getGodController() {

        assertEquals(athena.getGodController(), godController);
    }

    @Test
    public void getDescription() {

        assertEquals("If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", athena.getDescription());
    }
}