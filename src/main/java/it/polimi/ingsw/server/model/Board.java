package it.polimi.ingsw.server.model;

/**
 * Represents the Board of the game.
 */
public class Board {
    private final Cell[][] board;
    public static final int SIDE = 5;

    public Board() {
        this.board = new Cell[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Used to find one specific cell on the board.
     *
     * @param x Row of the board.
     * @param y Column of the board.
     * @return Returns cell if contained in board, null otherwise.
     */
    public Cell findCell(int x, int y) {
        if (isInBoard(x, y))
            return board[x][y];
        return null;
    }

    /**
     * Finds out whether a position is in the board or not
     *
     * @param x Coordinate x of the board.
     * @param y Coordinate y of the board
     * @return true if contained, false otherwise.
     */
    public boolean isInBoard(int x, int y) {
        return x >= 0 && x < SIDE && y >= 0 && y < SIDE;
    }

}
