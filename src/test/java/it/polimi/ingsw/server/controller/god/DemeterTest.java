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

public class DemeterTest {

    private Demeter demeter;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private WorkerMoveMap workerMoveMap;

    @Mock
    private WorkerBuildMap workerBuildMap;

    @Mock
    private Cell cell2;



    @Before
    public void setUp() {
        godController = mock(GodController.class);
        demeter = new Demeter(godController);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);
        workerBuildMap = mock(WorkerBuildMap.class);
        cell2 = mock(Cell.class);
    }


    @After
    public void tearDown() {
        godController = null;
        demeter = null;
    }


    private void settingUsualParameters() {
        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);

        when(godController.wantToMoveAgain()).thenReturn(true);
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
        when(godController.getInputMove()).thenReturn(move);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(true);
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        //setting the behaviour of the win condition
        when(worker.getLevel()).thenReturn(1);
        when(worker.getLevelVariation()).thenReturn(0);
        Player player = mock(Player.class);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getCanWinInPerimeter()).thenReturn(true);

        //setting the update build map matrix behaviour
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        //setting the build behaviour
        when(worker.getPlayer()).thenReturn(player);
        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);
        int[] build = {0, 1};
        when(godController.getBuildingInput()).thenReturn(build);
        //The getPosition of the worker and the getX and getY are already defined in the move part above
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(true, false, true);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2, cell2, cell, cell);
        when(cell2.getLevel()).thenReturn(2);
        doNothing().when(worker).buildBlock(any(int.class), any(int.class));
        doNothing().when(worker).buildDome(any(int.class), any(int.class));
        doNothing().when(godController).errorBuildScreen();
    }


    @Test
    public void evolveTurn() throws UnableToBuildException, UnableToMoveException, WinException {

        settingUsualParameters();

        when(godController.wantToBuildAgain(demeter)).thenReturn(true);
        when(godController.errorBuildDecisionScreen()).thenReturn(true);

        demeter.evolveTurn(worker);

        verify(godController, times(1)).wantToBuildAgain(demeter);
        verify(godController, times(2)).errorBuildDecisionScreen();

        //now changing the option about the errorBuildDecisionScreen
        settingUsualParameters();
        when(godController.wantToBuildAgain(demeter)).thenReturn(true);
        when(godController.errorBuildDecisionScreen()).thenReturn(false);
        when(cell2.getLevel()).thenReturn(3);

        demeter.evolveTurn(worker);

    }


    @Test
    public void evolveTurnBuildOnce() throws UnableToBuildException, UnableToMoveException, WinException {

        settingUsualParameters();
        when(godController.wantToBuildAgain(demeter)).thenReturn(false);
        when(godController.errorBuildDecisionScreen()).thenReturn(true);

        demeter.evolveTurn(worker);

        verify(godController, times(1)).wantToBuildAgain(demeter);


        settingUsualParameters();

        when(godController.wantToBuildAgain(demeter)).thenReturn(false);
        when(godController.errorBuildDecisionScreen()).thenReturn(true);

        demeter.evolveTurn(worker);
    }


    @Test
    public void evolveTurnFail() throws UnableToBuildException, UnableToMoveException, WinException {

        settingUsualParameters();
        when(godController.wantToBuildAgain(demeter)).thenReturn(true);
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true, false);

        demeter.evolveTurn(worker);

        verify(godController, times(1)).wantToBuildAgain(demeter);

    }


    @Test
    public void firstBuild() throws UnableToBuildException {

        //setting the update build map matrix behaviour
        WorkerBuildMap workerBuildMap = mock(WorkerBuildMap.class);
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        //setting the build behaviour
        Player player = mock(Player.class);
        when(worker.getPlayer()).thenReturn(player);
        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);
        int[] build = {0, 1};
        when(godController.getBuildingInput()).thenReturn(build, build);
        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(false, true);
        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2, cell2);
        when(cell2.getLevel()).thenReturn(2);
        doNothing().when(worker).buildBlock(any(int.class), any(int.class));
        doNothing().when(godController).errorBuildScreen();

        //todo ciao
        //assertNotNull(demeter.firstBuild(worker));

    }


    @Test
    public void getGodController() {

        assertEquals(demeter.getGodController(), godController);

    }


    @Test
    public void getDescription() {

        assertEquals("Your Worker may build one additional time, but not on the same space.", demeter.getDescription());
    }
}