package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.model.Board;


/**
 * Representation of the game Board client-side.
 */
public class BoardClient {

    private final CellClient[][] board;
    private WorkerClient bluemale;
    private WorkerClient bluefemale;
    private WorkerClient whitemale;
    private WorkerClient whitefemale;
    private WorkerClient beigemale;
    private WorkerClient beigefemale;


    public BoardClient() {
        this.board = new CellClient[Board.SIDE][Board.SIDE];

        for (int i = 0; i < Board.SIDE; i++) {
            for (int j = 0; j < Board.SIDE; j++) {
                board[i][j] = new CellClient(i, j);
            }
        }
    }


    /**
     * Finds a specific cell in the board, based on the received Cartesian input.
     *
     * @param x Cartesian coordinate that refers to the row of the board.
     * @param y Cartesian coordinate that refers to the column of the board.
     * @return The cell that has that specific position on the board.
     */
    public CellClient findCell(int x, int y) {
        if (isInBoard(x, y))
            return board[x][y];
        return null;
    }


    /**
     * Calculates position of cell relatively to worker position.
     *
     * @param x Cartesian coordinate that refers to the row of the board.
     * @param y Cartesian coordinate that refers to the column of the board.
     * @return relative position
     */
    private int[] workerCellRelativePosition(int xWorker, int yWorker, int x, int y) {

        System.out.println("worker position: " + xWorker + "," + yWorker);

        int[] position = new int[2];

        position[0] = x - xWorker;
        position[1] = y - yWorker;

        return position;
    }


    /**
     * Calculates the relative position selected with respect to the chosen worker.
     *
     * @param selectedWorker The chosen worker from which the computation will be referred to.
     * @param xTo            The row delta with respect to the chosen worker.
     * @param yTo            The column delta with respect to the chosen worker.
     * @return The relative position with respect to the chosen worker, expressed in compass coordinates.
     */
    public String workerCellRelativePositionCompass(WorkerClient selectedWorker, int xTo, int yTo) {
        int[] position = workerCellRelativePosition(selectedWorker.getXPosition(), selectedWorker.getYPosition(), xTo, yTo);

        int relativeX = position[0];
        int relativeY = position[1];
        System.out.println("relativeX,Y: " + relativeX + "," + relativeY);

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

        if (result.equals(""))
            return "U";

        return result;
    }


    /**
     * Allows to know if the input position stays on or off the board.
     *
     * @param x Cartesian coordinate that refers to the row of the board.
     * @param y Cartesian coordinate that refers to the column of the board.
     * @return True if the input is inside the board, false otherwise.
     */
    public boolean isInBoard(int x, int y) {
        return x >= 0 && x < Board.SIDE && y >= 0 && y < Board.SIDE;
    }


    /**
     * Updates the clientBoard of the view after receiving a cell from the server.
     *
     * @param cellFromServer cell received from the server.
     */
    public void update(CellClient cellFromServer) {
        //finds cell of clientBoard
        CellClient cellInClient = findCell(cellFromServer.getX(), cellFromServer.getY());

        //updates level and dome of cell
        cellInClient.updateBuildingCell(cellFromServer);

        //updates local worker AND worker attribute of local cell
        updateWorkerInCell(cellFromServer);

    }


    /**
     * The worker state is updated to the cellFromServer.
     *
     * @param cellFromServer It is the cell where the update needs to be done
     */
    private void updateWorkerInCell(CellClient cellFromServer) {

        WorkerClient workerFromServer = cellFromServer.getWorkerClient();
        CellClient workerCell = findCell(cellFromServer.getX(), cellFromServer.getY());

        //if cell from server doesn't contain worker, remove worker from corresponding local cell
        if (workerFromServer == null) {
            workerCell.removeWorker();
            return;
        }

        //else update local worker and local cell
        String sex = workerFromServer.getWorkerSex().toLowerCase();
        String color = workerFromServer.getWorkerColor().toLowerCase();


        if (sex.equals("male")) {
            switch (color) {
                case "white":
                    if (whitemale == null)
                        whitemale = new WorkerClient(workerFromServer);
                    else
                        whitemale.updateWorkerPosition(workerFromServer);

                    workerCell.addWorker(whitemale);
                    break;
                case "blue":
                    if (bluemale == null)
                        bluemale = new WorkerClient(workerFromServer);
                    else
                        bluemale.updateWorkerPosition(workerFromServer);

                    workerCell.addWorker(bluemale);
                    break;
                case "beige":
                    if (beigemale == null)
                        beigemale = new WorkerClient(workerFromServer);
                    else
                        beigemale.updateWorkerPosition(workerFromServer);

                    workerCell.addWorker(beigemale);
                    break;
            }

        } else if (sex.equals("female")) {
            switch (color) {
                case "white":
                    if (whitefemale == null)
                        whitefemale = new WorkerClient(workerFromServer);
                    else
                        whitefemale.updateWorkerPosition(workerFromServer);

                    workerCell.addWorker(whitefemale);
                    break;
                case "blue":
                    if (bluefemale == null)
                        bluefemale = new WorkerClient(workerFromServer);
                    else
                        bluefemale.updateWorkerPosition(workerFromServer);

                    workerCell.addWorker(bluefemale);
                    break;
                case "beige":
                    if (beigefemale == null)
                        beigefemale = new WorkerClient(workerFromServer);
                    else
                        beigefemale.updateWorkerPosition(workerFromServer);

                    workerCell.addWorker(beigefemale);
                    break;
            }
        }

    }

    /**
     * Finds instance of worker in boardClient
     *
     * @param color color of the worker to find
     * @param sex   sex of the worker to find
     */
    public WorkerClient findWorker(String color, String sex) {

        color = color.toLowerCase();
        sex = sex.toLowerCase();

        if (sex.equals("male")) {
            switch (color) {
                case "white":
                    return whitemale;
                case "blue":
                    return bluemale;
                case "beige":
                    return beigemale;
            }

        } else if (sex.equals("female")) {
            switch (color) {
                case "white":
                    return whitefemale;
                case "blue":
                    return bluefemale;
                case "beige":
                    return beigefemale;
            }
        }

        return null;
    }

}
