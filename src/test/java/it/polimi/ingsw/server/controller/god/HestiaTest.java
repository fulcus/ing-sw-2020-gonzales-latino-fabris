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
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;


public class HestiaTest {

    private Hestia hestia;

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

    @Mock
    private WorkerBuildMap workerBuildMap;


    @Before
    public void setUp() {
        godController = mock(GodController.class);
        hestia = new Hestia(godController);
        game = mock(Game.class);
        player = mock(Player.class);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);
        workerBuildMap = mock(WorkerBuildMap.class);

    }


    @After
    public void tearDown() {
        godController = null;
        hestia = null;
        game = null;
        worker = null;
        workerMoveMap = null;
        workerBuildMap = null;
        player = null;
    }


    private void settingUsualParameters() {
        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);
        doNothing().when(godController).errorMoveScreen();

        //setting the behaviour for the updateMoveMap
        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        doNothing().when(workerMoveMap).reset();
        doNothing().when(workerMoveMap).updateCellsOutOfMap();
        doNothing().when(workerMoveMap).updateMoveUpRestrictions();
        doNothing().when(workerMoveMap).cannotStayStill();
        doNothing().when(workerMoveMap).cannotMoveInOccupiedCell();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true);

        //setting the behaviour of the move
        int[] move = {1, 1};
        when(godController.getMoveInput()).thenReturn(move, move);
        when(worker.getPosition()).thenReturn(cell);
        //many return because of the many calls of the function
        when(cell.getX()).thenReturn(2, 2);
        when(cell.getY()).thenReturn(2, 2);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(true);
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        //setting the behaviour of the win condition
        when(worker.getLevel()).thenReturn(2);
        when(worker.getLevelVariation()).thenReturn(1);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getCanWinInPerimeter()).thenReturn(true);

        //setting the update build map matrix behaviour
        //WorkerBuildMap workerBuildMap = mock(WorkerBuildMap.class);
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();

        //setting the build behaviour,
        //only few lines here because differentiating the behaviour in different tests
        when(player.getGame()).thenReturn(game);
        int[] build = {0, 1};
        when(godController.getBuildInput()).thenReturn(build, build);
        //The getPosition of the worker and the getX and getY are already defined in the move part above
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(true, false, true);
        doNothing().when(worker).buildBlock(any(int.class), any(int.class));
        doNothing().when(worker).buildDome(any(int.class), any(int.class));
        doNothing().when(godController).errorBuildScreen();
    }


    @Test
    public void evolveTurnLowerLevels() throws UnableToMoveException, UnableToBuildException, WinException {

        settingUsualParameters();

        when(godController.wantToBuildAgain(hestia)).thenReturn(true);

        //because of updateBuildMapHestia
        doNothing().when(workerBuildMap).cannotBuildInPerimeter();

        //overridden this behaviour because otherwise an exception would be raised
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(true, false, true);
        when(godController.errorBuildDecisionScreen()).thenReturn(true);

        //Setting the specific build behaviour
        Board board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2);
        when(cell2.getLevel()).thenReturn(2);
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        hestia.evolveTurn(worker);

        verify(workerBuildMap, times(2)).cannotBuildInPerimeter();
        verify(worker, times(2)).buildBlock(any(int.class), any(int.class));
    }


    @Test
    public void evolveTurnNotBuildAgain() throws UnableToMoveException, UnableToBuildException, WinException {

        settingUsualParameters();

        when(godController.wantToBuildAgain(hestia)).thenReturn(false);

        //Setting the specific build behaviour
        Board board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2);
        when(cell2.getLevel()).thenReturn(2);
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        hestia.evolveTurn(worker);

        verify(godController, times(1)).wantToBuildAgain(hestia);
        //instead of before (2), now the build block is called one time.
        verify(worker, times(1)).buildBlock(any(int.class), any(int.class));

    }


    @Test
    public void evolveTurnFailHandled() throws UnableToMoveException, UnableToBuildException, WinException {

        settingUsualParameters();

        doNothing().when(workerBuildMap).cannotBuildInPerimeter();

        when(godController.wantToBuildAgain(hestia)).thenReturn(true);
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true, false);

        //Setting the specific build behaviour
        Board board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2);
        when(cell2.getLevel()).thenReturn(2);

        hestia.evolveTurn(worker);

        verify(godController, times(1)).wantToBuildAgain(hestia);
        verify(workerBuildMap, times(1)).cannotBuildInPerimeter();

    }


    @Test
    public void getGodController() {

        assertEquals(hestia.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("Your Worker may build one additional time, but this cannot be on a perimeter space.", hestia.getDescription());
    }
}