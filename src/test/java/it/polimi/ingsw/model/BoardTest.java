package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private int x, y;

    @Before
    public void setUp() {
        board = new Board();
    }

    @After
    public void tearDown(){
        board = null;
    }

    @Test
    public void testGetBoard() {
        for(int i=0; i<5; i++){
            for(int j=0; j<5;j++){
                Assert.assertEquals(board.findCell(i,j), board.getBoard()[i][j]);
            }
        }
    }

    @Test
    public void testFindCell() {
        x=1; y=2;
        Assert.assertEquals(board.getBoard()[x][y], board.findCell(x,y));
        x=7; y=2;
        assertNull(board.findCell(x, y));
    }

    @Test
    public void testIsInMap() {
        x=1; y=3;
        Assert.assertTrue(board.isInBoard(x,y));
        x=7; y=-1;
        Assert.assertFalse(board.isInBoard(x,y));
        x=3; y=7;
        Assert.assertFalse(board.isInBoard(x,y));
    }
}