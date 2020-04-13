package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    private Board board;


    @Before
    public void setUp() {
        Game game = new Game(2);
        game.addPlayer("nickname1");
        game.addPlayer("nickname2");
        board = game.getBoard();
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

    @Test
    public void testIsInMap() {
        assertTrue(board.isInBoard(1,3));
        assertFalse(board.isInBoard(7,-1));
        assertFalse(board.isInBoard(3,7));
    }
}