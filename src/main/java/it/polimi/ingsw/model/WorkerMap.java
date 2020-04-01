package it.polimi.ingsw.model;

public class WorkerMap {
    private Map map;
    private Worker worker;
    private boolean[][] matrix;
    private static final int N = 3;

    public WorkerMap(Worker worker) {
        this.worker = worker;
        matrix = new boolean[N][N];
        this.map = worker.getPlayer().getGame().getMap();

        //initialized standard matrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = true;
            }
            centerPositionFalse();
        }
    }

    public void DomeCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i, j).hasDome())
                    matrix[i][j] = false;
            }
        }
    }

    public void WorkerCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i, j).hasWorker())
                    matrix[i][j] = false;
            }
        }
    }

    public void OccupiedCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i, j).isOccupied())
                    matrix[i][j] = false;
            }
        }
    }

    public void FriendlyWorkerCellFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getAbsolutePosition(i, j).hasWorker() &&
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

    public void setCellTrue(int i, int j) {
        matrix[i][j] = true;
    }

    public void setCellFalse(int i, int j) {
        matrix[i][j] = false;
    }

    public boolean getBooleanCell(int i, int j) {
        return matrix[i][j];
    }

    /**
     * Returns Cell of Map given the relative coordinates of the worker.
     *
     * @param i Relative coordinate of MoveMatrix.
     * @param j Relative coordinate of MoveMatrix.
     * @return Returns Cell of Map given the relative coordinates of the worker,
     * returns null if the Cell is out of the boundaries of the map.
     */
    public Cell getAbsolutePosition(int i, int j) {
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();

        if (!map.isInMap(workersX, workersY))
            return null;

        return worker.getPlayer().getGame().getMap().findCell(workersX - 1 + i, workersY - 1 + j);
    }

    /**
     * Sets false cells not contained in Map.
     */
    public void updateCellsOutofMap() {
        Cell workersCell = worker.getPosition();

        if (workersCell.isInPerimeter()) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {

                    if (!map.isInMap(i, j))
                        matrix[i][j] = false;

                }
            }
        }
    }

    public void cellsInPerimeterFalse() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                if (getAbsolutePosition(i, j).isInPerimeter())
                    matrix[i][j] = false;
            }
        }
    }

    public Worker getWorker() {
        return worker;
    }
}
