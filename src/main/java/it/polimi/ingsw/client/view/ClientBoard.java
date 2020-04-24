package it.polimi.ingsw.client.view;

import it.polimi.ingsw.serializableObjects.ClientCell;
import it.polimi.ingsw.serializableObjects.WorkerClient;

public class ClientBoard implements ViewObserver {

    private final ClientCell[][] board;
    public static final int SIDE = 5;

    public ClientBoard() {
        this.board = new ClientCell[SIDE][SIDE];
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
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
        return x >= 0 && x < SIDE && y >= 0 && y < SIDE;
    }


    @Override
    public void update(ClientCell observedCell) {

        int xToUpdate = observedCell.getX();
        int yToUpdate = observedCell.getY();
        WorkerClient worker = observedCell.getWorkerClient();

        ClientCell BoardCellToUpdate = findCell(xToUpdate, yToUpdate);

        //update the level of the changed cell in the view
        BoardCellToUpdate.setCellLevel(observedCell.getCellLevel());
        //update dome of changed cell in the view
        BoardCellToUpdate.setHasDome(observedCell.HasDome());
        //update worker of the changed cell in the view
        if (worker != null)
            BoardCellToUpdate.setWorker(worker);

    }
}
