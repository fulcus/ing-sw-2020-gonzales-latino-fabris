package it.polimi.ingsw.model;

import java.util.ArrayList;

public class WorkerMap {
    private Board board;
    private Worker worker;
    private boolean[][] matrix;
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

    public void DomeCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i,j) != null && getAbsolutePosition(i, j).hasDome())
                    matrix[i][j] = false;
            }
        }
    }

    public void WorkerCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i,j) != null && getAbsolutePosition(i, j).hasWorker())
                    matrix[i][j] = false;
            }
        }
    }

    public void OccupiedCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i,j) != null && getAbsolutePosition(i, j).isOccupied())
                    matrix[i][j] = false;
            }
        }
    }

    public void FriendlyWorkerCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i,j) != null && getAbsolutePosition(i, j).hasWorker() &&
                        getAbsolutePosition(i, j).getWorker().getPlayer() == worker.getPlayer())
                    matrix[i][j] = false;
            }
        }
    }

    public void centerPositionFalse() {
        matrix[1][1] = false;
    }

    public void workersPositionTrue() {
        matrix[1][1] = true;
    }

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

    public boolean getBooleanCellWorkerMap(int i, int j) {
        return matrix[i][j];
    }

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
     * Returns Cell of Board given the relative coordinates of the worker.
     *
     * @param i Relative coordinate of MoveMatrix.
     * @param j Relative coordinate of MoveMatrix.
     * @return Returns Cell of Board given the relative coordinates of the worker,
     * returns null if the Cell is out of the boundaries of the board.
     */
    public Cell getAbsolutePosition(int i, int j) {
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        if (!board.isInBoard(workersX, workersY) || !board.isInBoard(i,j))
            return null;

        return worker.getPlayer().getGame().getBoard().findCell(workersX - 1 + i, workersY - 1 + j);
    }

    /**
     * Sets false cells not contained in Board.
     */
    //useless because getBooleanMap returns false if cell is out of board
    //called in updateMove/BuildMap of God
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

    public void cellsInPerimeterFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //null pointer exception
                if (getAbsolutePosition(i, j) != null && getAbsolutePosition(i, j).isInPerimeter())
                    matrix[i][j] = false;
            }
        }
    }

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
     * Finds all neighboring enemy workers.
     * @return Returns all enemy workers adjacent to the worker.
     */
    public ArrayList<Worker> neighboringEnemyWorkers() {

        ArrayList<Worker> neighboringWorkers = new ArrayList<Worker>(6);

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
