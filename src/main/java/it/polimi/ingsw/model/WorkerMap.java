package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * A 3x3 matrix that represents the cells adjacent to the worker.
 * The worker is in the central cell of the matrix.
 * This matrix is extended by WorkerMoveMap and WorkerBuildMap to represent
 * the positions in which the worker may or may not respectively move or build.
 */
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
        }
    }

    protected Worker getWorker() {
        return worker;
    }


    /**
     * Sets false any cell containing a dome.
     */
    protected void DomeCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i, j) != null && getAbsolutePosition(i, j).hasDome())
                    matrix[i][j] = false;
            }
        }
    }

    /**
     * Sets false any occupied cell, i.e. any cell containing a worker or a dome.
     */
    protected void occupiedCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i, j) != null && getAbsolutePosition(i, j).isOccupied())
                    matrix[i][j] = false;
            }
        }
    }

    /**
     * Sets false any cell containing the other worker of the same player.
     */
    protected void friendlyWorkerCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i, j) != null && getAbsolutePosition(i, j).hasWorker() &&
                        getAbsolutePosition(i, j).getWorker().getPlayer() == worker.getPlayer())
                    matrix[i][j] = false;
            }
        }
    }

    /**
     * Sets true or false the central cell of the WorkersMap,
     * i.e. the position of the worker.
     */
    protected void setCenterPosition(boolean center) {
        matrix[1][1] = center;
    }

    /**
     * Returns a cell of the WorkerMap given its relative coordinates.
     *
     * @param i Coordinate relative to the WorkerMap.
     * @param j Coordinate relative to the WorkerMap.
     * @return Selected cell of the WorkerMap.
     */
    protected boolean getBooleanCellWorkerMap(int i, int j) {
        return matrix[i][j];
    }

    /**
     * Returns a cell of the WorkerMap given its absolute coordinates
     * i.e the coordinates of the Board.
     *
     * @param i Coordinate relative to the Board.
     * @param j Coordinate relative to the Board.
     * @return Selected cell of the WorkerMap.
     */
    protected boolean getBooleanCellBoard(int i, int j) {
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        //if not in boolean board or not in board board return false
        int relativeX = i - workersX + 1;
        int relativeY = j - workersY + 1;

        if (relativeX < 0 || relativeX > 2 || relativeY < 0 || relativeY > 2 || !board.isInBoard(i, j))
            return false;

        return matrix[i - workersX + 1][j - workersY + 1];
    }

    /**
     * Returns the cell of the Board given its coordinates relative to the WorkerMap.
     *
     * @param i Relative coordinate of MoveMatrix.
     * @param j Relative coordinate of MoveMatrix.
     * @return Returns Cell of Board given the relative coordinates of the worker,
     * returns null if the Cell is out of the boundaries of the board.
     */
    protected Cell getAbsolutePosition(int i, int j) {
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        return board.findCell(workersX - 1 + i, workersY - 1 + j);
    }

    /**
     * Sets false cells that are above the worker more than x levels.
     *
     * @param x Maximum level difference between any given cell of the WorkerMap and the Worker's cell.
     */
    public void levelDifferenceLessEqualThanX(int x) {
        int workersLevel = worker.getPosition().getLevel();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                if (getAbsolutePosition(i, j) != null &&
                        getAbsolutePosition(i, j).getLevel() - workersLevel > x)
                    matrix[i][j] = false;
            }
        }
    }


    /**
     * Says if there is any near Cell 1 level higher than the worker's.
     *
     * @return True if there is at least one cell 1 level higher, false otherwise.
     */
    public boolean reachNearHigherLevel() {
        int workersLevel = worker.getPosition().getLevel();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                if (getAbsolutePosition(i, j) != null &&
                        getAbsolutePosition(i, j).getLevel() - workersLevel == 1)
                    return true;
            }
        }
        return false;
    }


    /**
     * Finds all neighboring enemy Workers.
     *
     * @return Returns all enemy Workers adjacent to the worker.
     */
    public ArrayList<Worker> neighboringEnemyWorkers() {

        ArrayList<Worker> neighboringWorkers = new ArrayList<>(6);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Cell adjacentCell = getAbsolutePosition(i, j);

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
    protected boolean anyTrueCell() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j])
                    return true;
            }
        }
        return false;
    }

    /**
     * Sets the whole map true.
     */
    public void resetMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                matrix[i][j] = true;

            }
        }

    }

    /**
     * Sets false cells not contained in Board.
     */
    //necessary for unableToMove/Build exceptions to work correctly.
    public void updateCellsOutOfMap() {
        Cell workersCell = worker.getPosition();
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        if (workersCell.isInPerimeter()) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {

                    if (!board.isInBoard(workersX - 1 + i, workersY - 1 + j))
                        matrix[i][j] = false;

                }
            }
        }
    }

    /**
     * Temporary. Prints the map. Useful for debugging.
     */
    public void printMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (matrix[i][j])
                    System.out.print("T ");
                else
                    System.out.print("F ");
            }
            System.out.println();
        }
    }

}
