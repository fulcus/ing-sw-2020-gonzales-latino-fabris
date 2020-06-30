package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MinotaurTest {

    private Minotaur minotaur;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private WorkerMoveMap workerMoveMap;

    @Mock
    private Player player;


    @Before
    public void setUp() {
        godController = mock(GodController.class);
        minotaur = new Minotaur(godController);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);
        player = mock(Player.class);
    }


    @After
    public void tearDown() {
        godController = null;
        minotaur = null;
    }


    @Test
    public void move() throws UnableToMoveException {

        Cell cell = mock(Cell.class);
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
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(false, true);
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);
        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2, cell2);
        when(cell2.hasWorker()).thenReturn(true);
        when(cell2.isOccupied()).thenReturn(true, false);
        Worker enemy = mock(Worker.class);
        when(cell2.getWorker()).thenReturn(enemy);
        doNothing().when(enemy).setPosition(any(int.class), any(int.class));


        minotaur.move(worker);

        //verifying the cycles of the while were effectively the ones we thought they were
        //to test the lines of code inside the method
        verify(godController, times(3)).getInputMove();
        verify(workerMoveMap, times(3)).isAllowedToMoveBoard(any(int.class), any(int.class));
        verify(cell2, times(2)).isOccupied();

    }


    @Test  //different from the previous one because now we assume we don't have any worker in our neighborhood
    public void moveWithoutEnemyNear() throws UnableToMoveException {

        Cell cell = mock(Cell.class);
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
        when(godController.getInputMove()).thenReturn(move, move, move);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        when(workerMoveMap.isAllowedToMoveBoard(any(int.class), any(int.class))).thenReturn(false, true);
        doNothing().when(worker).setPosition(any(int.class), any(int.class));
        doNothing().when(godController).displayBoard();

        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);
        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2, cell2);

        //This is the different assumption from the previous test.
        when(cell2.hasWorker()).thenReturn(false);

        minotaur.move(worker);


    }


    @Test
    public void updateMoveMap() throws UnableToMoveException {

        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        doNothing().when(workerMoveMap).reset();
        doNothing().when(workerMoveMap).updateCellsOutOfMap();
        doNothing().when(workerMoveMap).updateMoveUpRestrictions();
        doNothing().when(workerMoveMap).cannotStayStill();
        doNothing().when(workerMoveMap).cannotMoveInDomeCell();
        doNothing().when(workerMoveMap).cannotMoveInFriendlyWorkerCell();

        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);

        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);

        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2, cell2);
        when(cell2.hasWorker()).thenReturn(false);
        when(cell2.isOccupied()).thenReturn(false);
        Worker enemy = mock(Worker.class);
        ArrayList<Worker> enemies = new ArrayList<>();
        enemies.add(enemy);
        when(workerMoveMap.neighboringEnemyWorkers()).thenReturn(enemies);
        when(cell2.getWorker()).thenReturn(enemy);
        when(enemy.getPosition()).thenReturn(cell2);
        when(cell2.getX()).thenReturn(3);
        when(cell2.getY()).thenReturn(3);
        doNothing().when(enemy).setPosition(any(int.class), any(int.class));

        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true);

        assertEquals(workerMoveMap, minotaur.updateMoveMap(worker));

        //verifying the two different conditions from the interface's method
        verify(workerMoveMap, times(1)).cannotMoveInDomeCell();
        verify(workerMoveMap, times(1)).cannotMoveInFriendlyWorkerCell();

    }


    @Test (expected = UnableToMoveException.class)
    public void updateMoveMapFail() throws UnableToMoveException {

        //Useful to see if the move exception is correctly risen.
        //Calls in the method are quite similar to the previous test.

        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(false);

        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);

        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);

        Cell cell2 = mock(Cell.class);
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell2, cell2);
        when(cell2.hasWorker()).thenReturn(false);
        when(cell2.isOccupied()).thenReturn(false);
        Worker enemy = mock(Worker.class);
        ArrayList<Worker> enemies = new ArrayList<>();
        when(workerMoveMap.neighboringEnemyWorkers()).thenReturn(enemies);
        when(cell2.getWorker()).thenReturn(enemy);
        when(enemy.getPosition()).thenReturn(cell2);
        when(cell2.getX()).thenReturn(3);
        when(cell2.getY()).thenReturn(3);

        assertEquals(workerMoveMap, minotaur.updateMoveMap(worker));
    }


    @Test
    public void getGodController() {

        assertEquals(minotaur.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", minotaur.getDescription());
    }
}