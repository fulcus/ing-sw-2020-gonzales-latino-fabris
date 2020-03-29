package it.polimi.ingsw.model;

/**
 * This class represents the structure where the players can play their game
 */

public class Map {
    private Game game;
    private Cell[][] map;
    public static final int BORDERLENGTH = 5;

    public Map(Game game) {
        this.game = game;
        this.map = new Cell[BORDERLENGTH][BORDERLENGTH];
        for(int i=0; i<BORDERLENGTH; i++){
            for(int j=0; j<BORDERLENGTH; j++){
                map[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getMap(){
        return map;
    }

    /**
     * Used to find one specific cell on the map
     * @param x Row of the map
     * @param y Column of the map
     * @return  Cell which I specified with the parameters
     */
    public Cell findCell(int x, int y) {
        return map[x][y];
    }
}
