package it.polimi.ingsw.server.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    private Board board;


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
                Assert.assertEquals(board.findCell(i,j), board.findCell(i,j));
            }
        }
    }

    @Test
    public void testFindCell() {
        assertNotNull(board.findCell(1,2));
        assertNull(board.findCell(7, 2));
    }


}