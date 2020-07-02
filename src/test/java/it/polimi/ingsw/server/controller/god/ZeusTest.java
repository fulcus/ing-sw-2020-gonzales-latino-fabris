package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ZeusTest {

    private Zeus zeus;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private WorkerBuildMap workerBuildMap;



    @Before
    public void setUp() {
        godController = mock(GodController.class);
        zeus = new Zeus(godController);
        worker = mock(Worker.class);
        workerBuildMap = mock(WorkerBuildMap.class);

    }


    @After
    public void tearDown() {
        godController = null;
        zeus = null;
    }


    @Test
    public void updateBuildMap() throws UnableToBuildException {

        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        doNothing().when(workerBuildMap).canBuildUnderneath();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);
        //TODO VITTO SORRY METODO RIMOSSO doNothing().when(godController).allowBuildUnderneath();

        assertEquals(workerBuildMap, zeus.updateBuildMap(worker));
        verify(workerBuildMap, times(1)).reset();
        verify(workerBuildMap, times(1)).updateCellsOutOfMap();
        verify(workerBuildMap, times(1)).cannotBuildInOccupiedCell();
        verify(workerBuildMap, times(1)).canBuildUnderneath();
        verify(workerBuildMap, times(1)).anyAvailableBuildPosition();
       //TODO VITTO SORRY METODO RIMOSSOverify(godController, times(1)).allowBuildUnderneath();

    }


    @Test (expected = UnableToBuildException.class)
    public void updateBuildMapFail() throws UnableToBuildException {

        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).reset();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        doNothing().when(workerBuildMap).canBuildUnderneath();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(false);

        assertEquals(workerBuildMap, zeus.updateBuildMap(worker));
        verify(workerBuildMap, times(1)).reset();
        verify(workerBuildMap, times(1)).updateCellsOutOfMap();
        verify(workerBuildMap, times(1)).cannotBuildInOccupiedCell();
        verify(workerBuildMap, times(1)).canBuildUnderneath();
        verify(workerBuildMap, times(1)).anyAvailableBuildPosition();

    }


    @Test
    public void buildTest() throws UnableToBuildException {
        Player player = mock(Player.class);
        Cell cell = mock(Cell.class);
        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);

        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);

        int[] build = {0, 1};
        when(godController.getBuildingInput()).thenReturn(build);
        when(workerBuildMap.isAllowedToBuildBoard(any(int.class), any(int.class))).thenReturn(false, true);
        when(board.findCell(anyInt(), anyInt())).thenReturn(cell);
        when(cell.getLevel()).thenReturn(2);

        zeus.build(worker);

        verify(worker, times(1)).buildBlock(anyInt(), anyInt());
        verify(godController, times(1)).errorBuildScreen();
        verify(worker, times(1)).setPosition(any());

        Cell cell2 = mock(Cell.class);
        when(cell2.getLevel()).thenReturn(3);
        when(board.findCell(anyInt(), anyInt())).thenReturn(cell2);
        zeus.build(worker);

        verify(worker, times(1)).buildDome(anyInt(), anyInt());
    }


    @Test
    public void getGodController() {

        assertEquals(zeus.getGodController(), godController);

    }


    @Test
    public void getDescription() {

        assertEquals("Your Worker may build a block under itself.", zeus.getDescription());

    }
}