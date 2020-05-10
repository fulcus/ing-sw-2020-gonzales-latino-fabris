package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;
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
        doNothing().when(workerBuildMap).resetMap();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        doNothing().when(workerBuildMap).canBuildUnderneath();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);
        doNothing().when(godController).allowBuildUnderneath();

        assertEquals(workerBuildMap, zeus.updateBuildMap(worker));
        verify(workerBuildMap, times(1)).resetMap();
        verify(workerBuildMap, times(1)).updateCellsOutOfMap();
        verify(workerBuildMap, times(1)).cannotBuildInOccupiedCell();
        verify(workerBuildMap, times(1)).canBuildUnderneath();
        verify(workerBuildMap, times(1)).anyAvailableBuildPosition();
        verify(godController, times(1)).allowBuildUnderneath();

    }


    @Test (expected = UnableToBuildException.class)
    public void updateBuildMapFail() throws UnableToBuildException {

        when(worker.getBuildMap()).thenReturn(workerBuildMap);
        doNothing().when(workerBuildMap).resetMap();
        doNothing().when(workerBuildMap).updateCellsOutOfMap();
        doNothing().when(workerBuildMap).cannotBuildInOccupiedCell();
        doNothing().when(workerBuildMap).canBuildUnderneath();
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(false);

        assertEquals(workerBuildMap, zeus.updateBuildMap(worker));
        verify(workerBuildMap, times(1)).resetMap();
        verify(workerBuildMap, times(1)).updateCellsOutOfMap();
        verify(workerBuildMap, times(1)).cannotBuildInOccupiedCell();
        verify(workerBuildMap, times(1)).canBuildUnderneath();
        verify(workerBuildMap, times(1)).anyAvailableBuildPosition();

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