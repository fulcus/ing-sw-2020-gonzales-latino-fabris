package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PanTest {

    private Pan pan;

    @Mock
    private Worker worker;

    @Mock
    private GodController godController;

    @Mock
    private Player player;



    @Before
    public void setUp() {
        godController = mock(GodController.class);
        pan = new Pan(godController);
        worker = mock(Worker.class);
        player = mock(Player.class);
    }


    @After
    public void tearDown() {
        godController = null;
        pan = null;
    }


    @Test (expected = WinException.class)
    public void winExcep() throws WinException {
        when(worker.getLevel()).thenReturn(3);
        when(worker.getLevelVariation()).thenReturn(1);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getCanWinInPerimeter()).thenReturn(true);

        pan.win(worker);

    }


    @Test
    public void win() throws WinException {
        when(worker.getLevel()).thenReturn(2);
        when(worker.getLevelVariation()).thenReturn(1);
        when(worker.getPlayer()).thenReturn(player);
        when(player.getCanWinInPerimeter()).thenReturn(false);
        Cell cell = mock(Cell.class);
        when(worker.getPosition()).thenReturn(cell);
        when(cell.isInPerimeter()).thenReturn(false);

        pan.win(worker);

        verify(worker, times(1)).getLevel();
        verify(worker, times(1)).getLevelVariation();

    }


    @Test
    public void getGodController() {

        assertEquals(pan.getGodController(), godController);
    }


    @Test
    public void getDescription() {

        assertEquals("You also win if your Worker moves down two or more levels.", pan.getDescription());

    }
}