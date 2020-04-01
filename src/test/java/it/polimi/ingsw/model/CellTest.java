package it.polimi.ingsw.model;

import org.junit.*;

public class CellTest {


    private Cell cell;
    private Worker worker;

    @Before
    public void setupCell() {
        Game game = new Game(2);
        Player player = new Player(game, "player");
        cell = new Cell(3, 2);
        worker = new Worker(player, Sex.MALE);

    }

    @After
    public void cleancell() {
        cell = null;
    }

    @Test
    public void testGetLevel() {


        cell.buildBlock();

        Assert.assertEquals(1, cell.getLevel());
    }

    @Test
    public void testBuildBlock() {

        cell.buildBlock();

        Assert.assertEquals(1, cell.getLevel());

        cell.buildBlock();

        Assert.assertEquals(2, cell.getLevel());

        cell.buildBlock();

        Assert.assertEquals(3, cell.getLevel());


    }

    @Test
    public void testHasDome() {

        Assert.assertFalse(cell.hasDome());

        cell.buildDome();

        Assert.assertTrue(cell.hasDome());
    }

    @Test
    public void testBuildDome() {

        Assert.assertFalse(cell.hasDome());

        cell.buildDome();

        Assert.assertTrue(cell.hasDome());

    }

    @Test
    public void testHasWorker() {

        Assert.assertFalse(cell.hasWorker());
    }

    @Test
    public void testMoveIn() {

        Assert.assertNull(cell.getWorker());

        cell.moveIn(worker);

        Assert.assertEquals(worker, cell.getWorker());

    }

    @Test
    public void testMoveOut() {

        cell.moveIn(worker);
        cell.moveOut();

        Assert.assertNull(cell.getWorker());


    }

    @Test
    public void testIsInPerimeter() {

        Assert.assertTrue((new Cell(4, 2).isInPerimeter()));

        Assert.assertFalse((new Cell(3, 2).isInPerimeter()));


    }

    @Test
    public void testIsOccupied() {

        Assert.assertFalse(cell.isOccupied());

        cell.moveIn(worker);

        Assert.assertTrue(cell.isOccupied());
    }

    @Test
    public void testGetWorker() {

        Assert.assertNull(cell.getWorker());

        cell.moveIn(worker);

        Assert.assertEquals(worker, cell.getWorker());
    }

    @Test
    public void testGetX() {

        Assert.assertEquals(3, cell.getX());

    }

    @Test
    public void testGetY() {

        Assert.assertEquals(2, cell.getY());
    }
}