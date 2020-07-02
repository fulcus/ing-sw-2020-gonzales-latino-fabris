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

public class PrometheusTest {

    private Prometheus prometheus;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private WorkerMoveMap workerMoveMap;

    @Mock
    private Player player;

    @Mock
    private WorkerBuildMap workerBuildMap;


    @Before
    public void setUp() {
        godController = mock(GodController.class);
        prometheus = new Prometheus(godController);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);
        player = mock(Player.class);
        workerBuildMap = mock(WorkerBuildMap.class);
    }


    @After
    public void tearDown() {
        godController = null;
        prometheus = null;
        worker = null;
        workerMoveMap = null;
        player = null;
        workerBuildMap = null;
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
        //many return because of the many calls of the function
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(false, true);
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        //setting the behaviour of the win condition
        when(worker.getLevel()).thenReturn(1);
        when(worker.getLevelVariation()).thenReturn(0);

        when(player.getCanWinInPerimeter()).thenReturn(true);

        //setting the update build map matrix behaviour
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();

        //setting the build behaviour
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
    public void evolveTurn() throws UnableToMoveException, UnableToBuildException, WinException {

        when(worker.getPlayer()).thenReturn(player);
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);
        settingUsualParameters();

        doNothing().when(player).setPermissionToMoveUp(anyBoolean());
        when(player.getCanMoveUp()).thenReturn(true);
        when(godController.wantToBuildAgain(prometheus)).thenReturn(true, false);

        prometheus.evolveTurn(worker);

        verify(godController, times(3)).getBuildingInput();
        verify(godController, times(1)).wantToBuildAgain(prometheus);
        verify(player, times(2)).setPermissionToMoveUp(anyBoolean());
    }


    @Test (expected = UnableToBuildException.class)
    public void evolveTurnFail() throws UnableToMoveException, UnableToBuildException, WinException {

        when(worker.getPlayer()).thenReturn(player);
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(false);
        settingUsualParameters();

        doNothing().when(player).setPermissionToMoveUp(anyBoolean());
        when(player.getCanMoveUp()).thenReturn(true);
        when(godController.wantToBuildAgain(prometheus)).thenReturn(true, false);

        prometheus.evolveTurn(worker);

        //the following method will be called only 1 time because of the exception
        verify(player, times(1)).setPermissionToMoveUp(anyBoolean());
        //but the player is asked to build again if he wants
        verify(godController, times(0)).wantToBuildAgain(prometheus);
    }


    @Test (expected = UnableToBuildException.class)
    public void evolveTurnSecondFail() throws UnableToMoveException, UnableToBuildException, WinException {

        when(worker.getPlayer()).thenReturn(player);
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true, false);
        settingUsualParameters();

        doNothing().when(player).setPermissionToMoveUp(anyBoolean());
        when(player.getCanMoveUp()).thenReturn(true);
        when(godController.wantToBuildAgain(prometheus)).thenReturn(true, false);

        prometheus.evolveTurn(worker);

        //the following method will be called only 1 time because of the exception
        verify(player, times(1)).setPermissionToMoveUp(anyBoolean());
        //but the player is asked to build again if he wants
        verify(godController, times(1)).wantToBuildAgain(prometheus);
    }


    @Test
    public void move() throws UnableToMoveException {
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
        when(godController.getInputMove()).thenReturn(move);
        when(worker.getPosition()).thenReturn(cell);
        //many return because of the many calls of the function
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(false, true);
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        when(worker.getPlayer()).thenReturn(player);
        doNothing().when(player).setPermissionToMoveUp(anyBoolean());

        prometheus.move(worker);

        //this method is also tested during the evolveTurn testing method.
        //Here it has been reproposed for a complete explanation of the method,
        //showing that also the setPermissionToMoveUp method has been called.
        verify(player, times(1)).setPermissionToMoveUp(anyBoolean());
    }


    @Test
    public void getGodController() {

        assertEquals(prometheus.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("If your Worker does not move up, it may build both before and after moving.", prometheus.getDescription());
    }
}