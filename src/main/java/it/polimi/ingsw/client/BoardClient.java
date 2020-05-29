package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.WorkerClient;
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

    /**
     * Calculates position of cell relative to worker position.
     * @param x x coordinate of cell
     * @param y y coordinate of cell
     * @return relative position
     */
    private int[] workerCellRelativePosition(int xWorker, int yWorker, int x, int y) {

        System.out.println("worker position: "+ xWorker +","+ yWorker);

        int[] position = new int[2];

        position[0] = x - xWorker;
        position[1] = y - yWorker;

        return position;
    }

    public String workerCellRelativePositionCompass(int xWorker, int yWorker, int xTo, int yTo) {
        int[] position = workerCellRelativePosition(xWorker,yWorker,xTo,yTo);

        int relativeX = position[0];
        int relativeY = position[1];
        System.out.println("relativeX,Y: "+relativeX+","+relativeY);

        String resultX;
        String resultY;

        switch (relativeX) {
            case -1:
                resultX = "N";
                break;
            case 0:
                resultX = "";
                break;
            case 1:
                resultX = "S";
                break;

            default:
                return "FALSE";
        }

        switch (relativeY) {
            case -1:
                resultY = "W";
                break;
            case 0:
                resultY = "";
                break;
            case 1:
                resultY = "E";
                break;

            default:
                return "FALSE";
        }

        String result = resultX + resultY;

        if(result.equals(""))
            return "U";

        return result;
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
