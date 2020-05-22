package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.server.model.Board;

public class BoardClient {

    private final CellClient[][] board;

    public BoardClient() {
        this.board = new CellClient[Board.SIDE][Board.SIDE];

        for (int i = 0; i < Board.SIDE; i++) {
            for (int j = 0; j < Board.SIDE; j++) {
                board[i][j] = new CellClient(i, j);
            }
        }
    }

    public CellClient findCell(int x, int y) {
        if (isInBoard(x, y))
            return board[x][y];
        return null;
    }

    public boolean isInBoard(int x, int y) {
        return x >= 0 && x < Board.SIDE && y >= 0 && y < Board.SIDE;
    }


    /**
     * Updates the clientBoard of the view after receiving a cell from the server.
     *
     * @param cellFromServer cell received from the server.
     */

    public void update(CellClient cellFromServer) {
        //find cell position in clientBoard
        CellClient cellInClient = findCell(cellFromServer.getX(), cellFromServer.getY());

        //update cell in client with cell from server parameters
        cellInClient.updateCell(cellFromServer);
    }

}
