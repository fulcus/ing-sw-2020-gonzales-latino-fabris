package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;


public class AtlasTest {

    private Atlas atlas;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private WorkerBuildMap workerBuildMap;


    @Before
    public void setUp() {
        godController = mock(GodController.class);
        atlas = new Atlas(godController);
        worker = mock(Worker.class);
        workerBuildMap = mock(WorkerBuildMap.class);

    }


    @After
    public void tearDown() {
        godController = null;
        atlas = null;
        worker = null;
        workerBuildMap = null;
    }


    @Test
    public void buildAtlasBlock() throws UnableToBuildException {

        //setting the update build map matrix behaviour
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        Player player = mock(Player.class);
        Game game = mock(Game.class);
        Board board = mock(Board.class);
        Cell cell = mock(Cell.class);

        when(worker.getPlayer()).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        int[] build = {0, 1, 0};
        int[] buildFail = {0, 1, 3};
        when(godController.getBuildingInputAtlas()).thenReturn(build, buildFail, build);
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(false, true);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell);
        when(cell.getLevel()).thenReturn(2);
        doNothing().when(worker).buildBlock(any(int.class), any(int.class));
        doNothing().when(godController).errorBuildScreen();
        doNothing().when(godController).displayBoard();

        atlas.build(worker);

        //verifying the methods for building the block are as many times as the cycle
        //for the test has been done, also taking into account that the permissions to build
        //were not set from the first building cycle
        verify(godController, times(3)).getBuildingInputAtlas();
        verify(worker, times(1)).buildBlock(any(int.class), any(int.class));
    }


    @Test
    public void buildAtlasDome() throws UnableToBuildException {
        //setting the update build map matrix behaviour
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildUnderneath();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

        Player player = mock(Player.class);
        Game game = mock(Game.class);
        Board board = mock(Board.class);
        Cell cell = mock(Cell.class);

        when(worker.getPlayer()).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        int[] build = {0, 1, 1};
        int[] buildFail = {0, 1, 3};
        when(godController.getBuildingInputAtlas()).thenReturn(buildFail, build);
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(true);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell);
        when(cell.getLevel()).thenReturn(2);
        doNothing().when(worker).buildDome(any(int.class), any(int.class));
        doNothing().when(godController).errorBuildScreen();
        doNothing().when(godController).displayBoard();

        atlas.build(worker);

        verify(godController, times(2)).getBuildingInputAtlas();
        verify(worker, times(1)).buildDome(any(int.class), any(int.class));
    }


    @Test
    public void getGodController() {

        assertEquals(atlas.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("Your Worker may build a dome at any level.", atlas.getDescription());
    }
}