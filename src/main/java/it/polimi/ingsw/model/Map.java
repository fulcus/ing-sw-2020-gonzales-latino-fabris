package it.polimi.ingsw.model;

/**
 * This class represents the structure where the players can play their game
 */

public class Map {
    private Game game;
    private Cell[][] map;
    private final int borderLength = 5;

    public Map(Game game) {
        this.game = game;
        this.map = new Cell[borderLength][borderLength];
        for(int i=0; i<borderLength; i++){
            for(int j=0; j<borderLength; j++){
                map[i][j] = new Cell(i, j);
            }
        }
    }

    public int getBorderLength(){
        return borderLength;
    }

    public Cell[][] getMap(){
        return map;
    }

    /**
     * used to find one specific cell on the map
     * @param x row of the map
     * @param y column of the map
     * @return  cell which I specified with the parameters
     */
    public Cell findCell(int x, int y) {
        return map[x][y];
    }
}
