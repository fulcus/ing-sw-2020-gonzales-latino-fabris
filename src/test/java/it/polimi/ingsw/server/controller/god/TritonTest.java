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

public class TritonTest {

    private Triton triton;

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
    private Cell cell;


    @Before
    public void setUp() {
        godController = mock(GodController.class);
        triton = new Triton(godController);
        game = mock(Game.class);
        player = mock(Player.class);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);
        cell = mock(Cell.class);

    }


    @After
    public void tearDown() {
        godController = null;
        triton = null;
        game = null;
        worker = null;
        workerMoveMap = null;
        player = null;
        cell = null;
    }


    private void settingUsualParameters() {
        when(worker.getPosition()).thenReturn(cell);
        doNothing().when(godController).errorMoveScreen();

        //setting the behaviour for the updateMoveMap
        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        doNothing().when(workerMoveMap).reset();
        doNothing().when(workerMoveMap).updateCellsOutOfMap();
        doNothing().when(workerMoveMap).updateMoveUpRestrictions();
        doNothing().when(workerMoveMap).cannotStayStill();
        doNothing().when(workerMoveMap).cannotMoveInOccupiedCell();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true, true, false);

        //setting the behaviour of the move
        int[] move = {1, 1};
        when(godController.getInputMove()).thenReturn(move);
        //many return because of the many calls of the function
        when(cell.getX()).thenReturn(2, 3);
        when(cell.getY()).thenReturn(2, 3);
        when(cell.isInPerimeter()).thenReturn(true);
        when(godController.wantToMoveAgain()).thenReturn(true, false);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(true, false);
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();
        when(godController.errorMoveDecisionScreen()).thenReturn(true, true, false);

        //setting the behaviour of the win condition
        when(worker.getLevel()).thenReturn(2);
        when(worker.getLevelVariation()).thenReturn(1);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getCanWinInPerimeter()).thenReturn(true);

        //setting the update build map matrix behaviour
        WorkerBuildMap workerBuildMap = mock(WorkerBuildMap.class);
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        //setting the build behaviour
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

        //Parameters have been set to simulate one turn of the player that holds Triton.
        settingUsualParameters();

        triton.evolveTurn(worker);

        verify(godController, times(2)).wantToMoveAgain();
        //counting all the times the method is invoked inside the evolve turn
        //and also considering the displaying errors, it turns out to be that the
        //num of calls of the getPosition() of the selected worker is 14.
        verify(worker, times(14)).getPosition();
    }


    @Test
    public void evolveTurnCatchException() throws UnableToMoveException, UnableToBuildException, WinException {
        //Different from the previous one because allows to see if the exception inside the
        //move again method is been handled correctly.

        settingUsualParameters();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true, false);

        triton.evolveTurn(worker);

    }


    @Test
    public void getGodController() {

        assertEquals(triton.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("Each time your Worker moves into a perimeter space, it may immediately move again.", triton.getDescription());
    }
}