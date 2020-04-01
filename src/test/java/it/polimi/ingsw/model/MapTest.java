package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapTest {

    private Map map;
    private int x, y;

    @Before
    public void setUp() {
        map = new Map();
    }

    @After
    public void tearDown(){
        map = null;
    }

    @Test
    public void testGetBoard() {
        for(int i=0; i<5; i++){
            for(int j=0; j<5;j++){
                Assert.assertEquals(map.findCell(i,j), map.getBoard()[i][j]);
            }
        }
    }

    @Test
    public void testFindCell() {
        x=1; y=2;
        Assert.assertEquals(map.getBoard()[x][y], map.findCell(x,y));
        x=7; y=2;
        Assert.assertEquals(null, map.findCell(x,y));
    }

    @Test
    public void testIsInMap() {
        x=1; y=3;
        Assert.assertTrue(map.isInMap(x,y));
        x=7; y=-1;
        Assert.assertFalse(map.isInMap(x,y));
        x=3; y=7;
        Assert.assertFalse(map.isInMap(x,y));
    }
}