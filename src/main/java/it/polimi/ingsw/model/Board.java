package it.polimi.ingsw.model;

/**
 * This class represents the structure where the players can play their game
 */

public class Board {
    private final Cell[][] board;
    public static final int SIDE = 5;

    public Board() {
        this.board = new Cell[SIDE][SIDE];
        for(int i = 0; i < SIDE; i++){
            for(int j = 0; j < SIDE; j++){
                board[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getBoard() {
        return board;
    }


    /**
     * Used to find one specific cell on the map
     * @param x Row of the map
     * @param y Column of the map
     * @return  Returns cell if contained in map, null otherwise.
     */
    public Cell findCell(int x, int y) {
        if(isInBoard(x,y))
            return board[x][y];
        return null;
    }

    public boolean isInBoard(int x, int y) {
        return x >= 0 && x < SIDE && y >= 0 && y < SIDE;
    }

}
