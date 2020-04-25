package it.polimi.ingsw.client.view;

import it.polimi.ingsw.serializableObjects.ClientCell;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.model.Board;

public class ClientBoard  {

    private final ClientCell[][] board;

    public ClientBoard() {
        this.board = new ClientCell[Board.SIDE][Board.SIDE];

        for (int i = 0; i < Board.SIDE; i++) {
            for (int j = 0; j < Board.SIDE; j++) {
                board[i][j] = new ClientCell(i, j);
            }
        }
    }

    public ClientCell findCell(int x, int y) {
        if (isInBoard(x, y))
            return board[x][y];
        return null;
    }

    public boolean isInBoard(int x, int y) {
        return x >= 0 && x < Board.SIDE && y >= 0 && y < Board.SIDE;
    }


    /**
     * Updates the clientBoard of the view after receiving a cell from the server.
     * @param cellFromServer cell received from the server.
     */

    public void update(ClientCell cellFromServer) {
        //find cell position in clientBoard
        ClientCell cellInClient = this.findCell(cellFromServer.getX(),cellFromServer.getY());
        //update cell
        cellInClient.updateCell(cellInClient);
    }

}
