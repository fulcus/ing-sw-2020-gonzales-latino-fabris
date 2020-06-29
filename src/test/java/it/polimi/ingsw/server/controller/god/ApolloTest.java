package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class ApolloTest {

    private Apollo apollo;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private WorkerMoveMap workerMoveMap;



    @Before
    public void setUp() {
        godController = mock(GodController.class);
        apollo = new Apollo(godController);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);
        //client = mock(ViewClient.class);

    }


    @After
    public void tearDown() {
        godController = null;
        apollo = null;
        //client = null;
    }


    @Test
    public void move() throws Exception{

        //Setting the updateMoveMap
        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        doNothing().when(workerMoveMap).reset();
        doNothing().when(workerMoveMap).updateCellsOutOfMap();
        doNothing().when(workerMoveMap).updateMoveUpRestrictions();
        doNothing().when(workerMoveMap).cannotStayStill();
        doNothing().when(workerMoveMap).cannotMoveInDomeCell();
        doNothing().when(workerMoveMap).cannotMoveInFriendlyWorkerCell();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true);

        Cell cell = mock(Cell.class);
        Cell moveCell = mock(Cell.class);
        //when(apollo.updateMoveMap(worker)).thenReturn(workerMoveMap);
        int[] move = {1, 1};
        when(godController.getInputMove()).thenReturn(move, move);
        when(worker.getPosition()).thenReturn(cell, cell, cell, cell);
        when(cell.getX()).thenReturn(2, 2);
        when(cell.getY()).thenReturn(2, 2);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(false, true);
        doNothing().when(godController).errorMoveScreen();

        //defining the cell where to move the worker
        Player mockPlayer = mock(Player.class);
        Game mockGame = mock(Game.class);
        Board mockBoard = mock(Board.class);
        when(worker.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getGame()).thenReturn(mockGame);
        when(mockGame.getBoard()).thenReturn(mockBoard);
        when(mockBoard.findCell(any(int.class), any(int.class))).thenReturn(moveCell);

        when(moveCell.hasWorker()).thenReturn(false);
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        apollo.move(worker);

        verify(godController, times(2)).getInputMove();
        verify(godController, times(1)).errorMoveScreen();
        verify(worker, times(4)).getPosition();
        verify(cell, times(2)).getX();
        verify(cell, times(2)).getY();
        verify(worker, times(1)).setPosition(any(int.class), any(int.class));
        verify(godController, times(1)).displayBoard();

    }


    @Test (expected = UnableToMoveException.class)
    public void updateMoveMapFail() throws Exception {

        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        doNothing().when(workerMoveMap).reset();
        doNothing().when(workerMoveMap).updateCellsOutOfMap();
        doNothing().when(workerMoveMap).updateMoveUpRestrictions();
        doNothing().when(workerMoveMap).cannotStayStill();
        doNothing().when(workerMoveMap).cannotMoveInDomeCell();
        doNothing().when(workerMoveMap).cannotMoveInFriendlyWorkerCell();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(false);

        apollo.updateMoveMap(worker);

        verify(workerMoveMap, times(1)).cannotMoveInFriendlyWorkerCell();
        verify(workerMoveMap, times(1)).reset();
        verify(workerMoveMap, times(1)).cannotMoveInDomeCell();
        verify(workerMoveMap, times(1)).updateMoveUpRestrictions();
        verify(workerMoveMap, times(1)).updateCellsOutOfMap();
        verify(workerMoveMap, times(1)).cannotStayStill();
    }


    @Test
    public void updateMoveMap() throws Exception{

        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        doNothing().when(workerMoveMap).reset();
        doNothing().when(workerMoveMap).updateCellsOutOfMap();
        doNothing().when(workerMoveMap).updateMoveUpRestrictions();
        doNothing().when(workerMoveMap).cannotStayStill();
        doNothing().when(workerMoveMap).cannotMoveInDomeCell();
        doNothing().when(workerMoveMap).cannotMoveInFriendlyWorkerCell();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true);

        assertEquals(apollo.updateMoveMap(worker), workerMoveMap);
    }


    @Test
    public void getGodController() {

        assertEquals(apollo.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.", apollo.getDescription());
    }

}