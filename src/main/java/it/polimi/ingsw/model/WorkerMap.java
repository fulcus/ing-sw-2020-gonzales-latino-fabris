package it.polimi.ingsw.model;

import java.util.ArrayList;

public class WorkerMap {
    private final Board board;
    private final Worker worker;
    private final boolean[][] matrix;
    public static final int N = 3;

    public WorkerMap(Worker worker) {
        this.worker = worker;
        matrix = new boolean[N][N];
        this.board = worker.getPlayer().getGame().getBoard();

        //initialized standard matrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = true;
            }
            centerPositionFalse();
        }
    }

    public Worker getWorker() {
        return worker;
    }


    /**
     * Sets false any cell containing a dome.
     */
    public void DomeCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i,j) != null && getAbsolutePosition(i, j).hasDome())
                    matrix[i][j] = false;
            }
        }
    }

    /**
     * Sets false any cell containing a worker.
     */
    public void workerCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i,j) != null && getAbsolutePosition(i, j).hasWorker())
                    matrix[i][j] = false;
            }
        }
    }

    /**
     * Sets false any occupied cell, i.e. any cell containing a worker or a dome.
     */
    public void occupiedCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i,j) != null && getAbsolutePosition(i, j).isOccupied())
                    matrix[i][j] = false;
            }
        }
    }

    /**
     * Sets false any cell containing the other worker of the same player.
     */
    public void friendlyWorkerCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i,j) != null && getAbsolutePosition(i, j).hasWorker() &&
                        getAbsolutePosition(i, j).getWorker().getPlayer() == worker.getPlayer())
                    matrix[i][j] = false;
            }
        }
    }

    /**
     * Sets false the central cell of the WorkersMap.
     */
    public void centerPositionFalse() {
        matrix[1][1] = false;
    }

    /**
     * Sets true the central cell of the WorkersMap.
     */
    public void centerPositionTrue() {
        matrix[1][1] = true;
    }

    //useless?
    public void setBooleanCellBoard(int i, int j, boolean value) {
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        //if not in boolean board return
        int relativeX = i - workersX + 1;
        int relativeY = j - workersY + 1;

        if(relativeX < 0 || relativeX > 2 || relativeY < 0 || relativeY > 2 || !board.isInBoard(i,j))
            return;

        matrix[i - workersX + 1][j - workersY + 1] = value;
    }

    /**
     * Returns a cell of the WorkerMap given its relative coordinates.
     * @param i Coordinate relative to the WorkerMap.
     * @param j Coordinate relative to the WorkerMap.
     * @return Selected cell of the WorkerMap.
     */
    public boolean getBooleanCellWorkerMap(int i, int j) {
        return matrix[i][j];
    }


    public void setBooleanCellWorkerMap(int i, int j, boolean value) {
        matrix[i][j] = value;
    }

    /**
     * Returns a cell of the WorkerMap given its absolute coordinates
     * i.e the coordinates of the Board.
     * @param i Coordinate relative to the Board.
     * @param j Coordinate relative to the Board.
     * @return Selected cell of the WorkerMap.
     */
    public boolean getBooleanCellBoard(int i, int j) {
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        //if not in boolean board or not in board board return false
        int relativeX = i - workersX + 1;
        int relativeY = j - workersY + 1;

        if(relativeX < 0 || relativeX > 2 || relativeY < 0 || relativeY > 2 || !board.isInBoard(i,j))
            return false;

        return matrix[i - workersX + 1][j - workersY + 1];
    }

    /**
     * Returns the cell of the Board given its coordinates relative to the WorkerMap.
     * @param i Relative coordinate of MoveMatrix.
     * @param j Relative coordinate of MoveMatrix.
     * @return Returns Cell of Board given the relative coordinates of the worker,
     * returns null if the Cell is out of the boundaries of the board.
     */
    public Cell getAbsolutePosition(int i, int j) {
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        if (!board.isInBoard(workersX, workersY))
            return null;

        return board.findCell(workersX - 1 + i, workersY - 1 + j);
    }

    /**
     * Sets false cells not contained in Board.
     */
    //useless because getBooleanMap returns false if cell is out of board
    //called in updateMap of God
    public void updateCellsOutOfMap() {
        Cell workersCell = worker.getPosition();
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        if (workersCell.isInPerimeter()) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {

                    if (!board.isInBoard(i - workersX + 1, j - workersY + 1))
                        matrix[i][j] = false;

                }
            }
        }
    }

    /**
     * Sets false the cells the cells in the perimeter of the Board.
     */
    public void cellsInPerimeterFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //null pointer exception
                if (getAbsolutePosition(i, j) != null && getAbsolutePosition(i, j).isInPerimeter())
                    matrix[i][j] = false;
            }
        }
    }

    /**
     * Sets false cells that are above the worker more than x levels.
     * @param x Maximum level difference between any given cell of the WorkerMap and the Worker's cell.
     */
    public void levelDifferenceLessEqualThanX(int x) {
        int workersLevel = worker.getPosition().getLevel();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                if (getAbsolutePosition(i,j) != null &&
                        getAbsolutePosition(i,j).getLevel() - workersLevel > x)
                    matrix[i][j] = false;
            }
        }
    }


    /**
     * Finds all neighboring enemy Workers.
     * @return Returns all enemy Workers adjacent to the worker.
     */
    public ArrayList<Worker> neighboringEnemyWorkers() {

        ArrayList<Worker> neighboringWorkers = new ArrayList<>(6);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                Cell adjacentCell = getAbsolutePosition(i,j);
                if (adjacentCell != null && adjacentCell.hasWorker() &&
                        adjacentCell.getWorker().getPlayer() != worker.getPlayer())
                    neighboringWorkers.add(adjacentCell.getWorker());
            }
        }

        return neighboringWorkers;
    }

    /**
     * Checks if there is any true cell in the matrix. Useful for lose conditions.
     */
    public boolean anyTrueCell() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(matrix[i][j])
                    return true;
            }
        }
        return false;
    }

}
