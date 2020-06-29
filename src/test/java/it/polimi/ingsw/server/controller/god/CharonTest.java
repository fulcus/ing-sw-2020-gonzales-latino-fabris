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

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class CharonTest {

    private Charon charon;

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
        charon = new Charon(godController);
        worker = mock(Worker.class);
        workerMoveMap = mock(WorkerMoveMap.class);
        player = mock(Player.class);
        workerBuildMap = mock(WorkerBuildMap.class);
    }


    @After
    public void tearDown() {
        godController = null;
        charon = null;
        workerMoveMap = null;
    }


    private void settingUsualParameters() {
        Cell cell = mock(Cell.class);
        //when(worker.getPosition()).thenReturn(cell);

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
        when(workerBuildMap.anyAvailableBuildPosition()).thenReturn(true);

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

        settingUsualParameters();
        when(worker.getPlayer()).thenReturn(player);
        ArrayList<Worker> workerList = new ArrayList<>();
        when(workerMoveMap.neighboringEnemyWorkers()).thenReturn(workerList);

        charon.evolveTurn(worker);
    }


    @Test
    public void forceMoveEnemy() throws UnableToMoveException {

        //setting the behaviour for the updateMoveMap
        when(worker.getMoveMap()).thenReturn(workerMoveMap);
        doNothing().when(workerMoveMap).reset();
        doNothing().when(workerMoveMap).updateCellsOutOfMap();
        doNothing().when(workerMoveMap).updateMoveUpRestrictions();
        doNothing().when(workerMoveMap).cannotStayStill();
        doNothing().when(workerMoveMap).cannotMoveInOccupiedCell();
        when(workerMoveMap.anyAvailableMovePosition()).thenReturn(true);

        Game game = mock(Game.class);
        Board board = mock(Board.class);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getGame()).thenReturn(game);
        when(game.getBoard()).thenReturn(board);

        Worker enemy = mock(Worker.class);
        ArrayList<Worker> workerList = new ArrayList<>();
        workerList.add(enemy);
        when(workerMoveMap.neighboringEnemyWorkers()).thenReturn(workerList);

        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.getX()).thenReturn(2);
        when(cell.getY()).thenReturn(2);
        Cell enemyCell = mock(Cell.class);
        when(enemy.getPosition()).thenReturn(enemyCell);
        when(enemyCell.getX()).thenReturn(1);
        when(enemyCell.getY()).thenReturn(1);
        //The following result is not truly representative of the calculation,
        //but allows to not declare another local mocking variable.
        when(board.findCell(any(int.class), any(int.class))).thenReturn(cell);
        when(cell.isOccupied()).thenReturn(false);

        when(godController.wantToMoveEnemy()).thenReturn(true);

        when(godController.forceMoveEnemy(anyObject(), any(Worker.class))).thenReturn(enemy);

        //todo sorry again vitto <3
        //charon.forceMoveEnemy(worker);


        when(godController.forceMoveEnemy(anyObject(), any(Worker.class))).thenReturn(null);
        //charon.forceMoveEnemy(worker);


        when(godController.wantToMoveEnemy()).thenReturn(false);
        //charon.forceMoveEnemy(worker);


    }


    @Test
    public void getGodController() {

        assertEquals(charon.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if that space is unoccupied.", charon.getDescription());
    }
}