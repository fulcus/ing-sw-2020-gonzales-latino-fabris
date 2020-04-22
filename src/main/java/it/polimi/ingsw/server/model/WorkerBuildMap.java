package it.polimi.ingsw.server.model;

/**
 * Matrix that represents positions adjacent to the worker, in which he
 * may or may not build, depending on the boolean value of the cell.
 */
public class WorkerBuildMap extends WorkerMap {

    public WorkerBuildMap(Worker worker) {
        super(worker);
    }

    /**
     * Forbids worker to build in a cell occupied by a dome or another worker.
     */
    public void cannotBuildInOccupiedCell() {
        occupiedCellFalse();
    }

    /**
     * Forbids worker to build underneath himself.
     */
    public void cannotBuildUnderneath() {
        setCenterPosition(false);
    }

    /**
     * Allows worker to build underneath himself.
     */
    public void canBuildUnderneath() {
        setCenterPosition(true);
    }

    /**
     * Finds out if worker is allowed to build in a given cell of the board.
     * @param i Board coordinate X.
     * @param j Board coordinate Y.
     * @return True if it can build in cell, false otherwise.
     */
    public boolean isAllowedToBuildBoard(int i, int j) {
        return getBooleanCellBoard(i, j);
    }

    /**
     * Checks if the worker can build.
     * @return Returns true if there is a cell the worker can build in, false otherwise.
     */
    public boolean anyAvailableBuildPosition() {
        return anyTrueCell();
    }

}
