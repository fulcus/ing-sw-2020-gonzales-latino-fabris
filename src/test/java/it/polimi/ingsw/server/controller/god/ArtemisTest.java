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
import static org.mockito.Mockito.*;


public class ArtemisTest {

    private Artemis artemis;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private WorkerMoveMap workerMoveMap;


    @Before
    public void setUp() {
        godController = mock(GodController.class);
        artemis = new Artemis(godController);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);

    }


    @After
    public void tearDown() {
        godController = null;
        artemis = null;
        workerMoveMap = null;
    }


    private void settingUsualParameters() {
        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);

        when(godController.wantToMoveAgain()).thenReturn(true);
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
        int[] moveAgain = {0, -1};
        when(godController.getInputMove()).thenReturn(move, move, moveAgain, moveAgain);
        when(worker.getPosition()).thenReturn(cell);
        //many return because of the many calls of the function
        when(cell.getX()).thenReturn(2, 2, 3, 3, 1, 1);
        when(cell.getY()).thenReturn(2, 2, 3, 3, 1, 1);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(false, true);
        //doNothing().when(godController).errorMoveScreen();   //(already set above, but leave it here because could be useful for other classes)
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        //setting the behaviour of the win condition
        when(worker.getLevel()).thenReturn(1);
        when(worker.getLevelVariation()).thenReturn(0);
        Player player = mock(Player.class);
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
        when(worker.getPlayer()).thenReturn(player);
        Game game = mock(Game.class);
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
    public void evolveTurn() throws UnableToBuildException, UnableToMoveException, WinException {

        settingUsualParameters();

        artemis.evolveTurn(worker);

        verify(worker, times(11)).getPosition();
        verify(godController, times(3)).getInputMove();
        verify(godController, times(1)).wantToMoveAgain();
        verify(workerMoveMap, times(3)).isAllowedToMoveBoard(any(int.class), any(int.class));
        verify(worker, times(2)).setPosition(any(int.class), any(int.class));
        verify(godController, times(3)).displayBoard();
    }


    @Test
    public void evolveTurnWOMoveAgain() throws UnableToBuildException, UnableToMoveException, WinException {

        settingUsualParameters();
        when(godController.wantToMoveAgain()).thenReturn(false);

        artemis.evolveTurn(worker);

        verify(worker, times(9)).getPosition();
        verify(godController, times(2)).getInputMove();
        verify(godController, times(1)).wantToMoveAgain();
        assertFalse(godController.wantToMoveAgain());
        verify(workerMoveMap, times(2)).isAllowedToMoveBoard(any(int.class), any(int.class));
        verify(worker, times(1)).setPosition(any(int.class), any(int.class));
        verify(godController, times(2)).displayBoard();
    }


    @Test
    public void evolveTurnErrorMoveAgain() throws UnableToBuildException, UnableToMoveException, WinException {

        settingUsualParameters();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true, false);
        when(godController.errorMoveDecisionScreen()).thenReturn(false);

        artemis.evolveTurn(worker);

        verify(worker, times(9)).getPosition();
        verify(godController, times(2)).getInputMove();
        verify(workerMoveMap, times(2)).isAllowedToMoveBoard(any(int.class), any(int.class));
        verify(worker, times(1)).setPosition(any(int.class), any(int.class));
        verify(godController, times(2)).displayBoard();

    }


    @Test
    public void getGodController() {

        assertEquals(artemis.getGodController(), godController);
    }

    @Test
    public void getDescription() {

        assertEquals("Your Worker may move one additional time, but not back to its initial space.", artemis.getDescription());
    }
}