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

public class HephaestusTest {

    private Hephaestus hephaestus;

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
        hephaestus = new Hephaestus(godController);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);
        workerBuildMap = mock(WorkerBuildMap.class);
        cell2 = mock(Cell.class);
    }


    @After
    public void tearDown() {
        godController = null;
        hephaestus = null;
        worker = null;
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
    public void evolveTurn() throws UnableToMoveException, UnableToBuildException, WinException {

        //here only checking if the method gives some errors,
        //operations inside are already tested in their specific classes.

        settingUsualParameters();

        when(godController.wantToBuildAgain(hephaestus)).thenReturn(true);
        when(godController.errorBuildDecisionScreen()).thenReturn(true);

        hephaestus.evolveTurn(worker);

    }


    @Test
    public void firstBuild() throws UnableToBuildException {

        //setting the update build map matrix behaviour
        WorkerBuildMap workerBuildMap = mock(WorkerBuildMap.class);
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).resetMap();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        //setting the build behaviour
        Player player = mock(Player.class);
        when(worker.getPlayer()).thenReturn(player);
        when(worker.getPlayer()).thenReturn(player);
        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);
        int[] build = {0, 1};
        when(godController.getBuildingInput()).thenReturn(build, build);
        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(false, true);
        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2, cell2);
        when(cell2.getLevel()).thenReturn(2);
        doNothing().when(worker).buildBlock(any(int.class), any(int.class));
        doNothing().when(godController).errorBuildScreen();

        assertNotNull(hephaestus.firstBuild(worker));


        when(cell2.getLevel()).thenReturn(3);
        assertNotNull(hephaestus.firstBuild(worker));

    }


    @Test
    public void secondBuild() {

        Cell firstBuild = mock(Cell.class);
        hephaestus.firstBuildCell = firstBuild;
        when(firstBuild.getLevel()).thenReturn(2);
        when(firstBuild.getX()).thenReturn(2);
        when(firstBuild.getY()).thenReturn(2);
        when(godController.wantToBuildAgain(hephaestus)).thenReturn(true);
        doNothing().when(worker).buildBlock(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        hephaestus.secondBuild(worker);


        when(firstBuild.getLevel()).thenReturn(3);
        when(godController.wantToBuildAgain(hephaestus)).thenReturn(true);

        hephaestus.secondBuild(worker);


        when(firstBuild.getLevel()).thenReturn(2);
        when(godController.wantToBuildAgain(hephaestus)).thenReturn(false);

        hephaestus.secondBuild(worker);
    }


    @Test
    public void getGodController() {

        assertEquals(hephaestus.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("Your Worker may build one additional block (not dome) on top of your first block.", hephaestus.getDescription());
    }
}