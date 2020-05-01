package it.polimi.ingsw.server.model;

/**
 * Matrix that represents positions adjacent to the worker, in which he
 * may or may not move, depending on the boolean value of the cell.
 */
public class WorkerMoveMap extends WorkerMap {

    public WorkerMoveMap(Worker worker) {
        super(worker);
    }

    /**
     * Forbids worker to move in a cell occupied by a dome.
     */
    public void cannotMoveInDomeCell() {
        domeCellFalse();
    }

    /**
     * Forbids worker to move in a cell occupied by a dome or another worker.
     */
    public void cannotMoveInOccupiedCell() {
        occupiedCellFalse();
    }

    /**
     * Forbids worker to move in a cell occupied by the other worker of the same player.
     */
    public void cannotMoveInFriendlyWorkerCell() {
        friendlyWorkerCellFalse();
    }

    /**
     * Forbids worker to not move.
     */
    public void cannotStayStill() {
        setCenterPosition(false);
    }

    /**
     * Finds out if worker is allowed to move in a given cell of the board.
     *
     * @param i Board coordinate X.
     * @param j Board coordinate Y.
     * @return True if it can move in cell, false otherwise.
     */
    public boolean isAllowedToMoveBoard(int i, int j) {
        return getBooleanCellBoard(i, j);
    }

    /**
     * Finds out if worker is allowed to move in a given cell of the moveMap.
     *
     * @param i moveMap coordinate X.
     * @param j moveMap coordinate Y.
     * @return True if it can move in position, false otherwise.
     */
    //maybe useless
    public boolean isAllowedToMoveWorkersMap(int i, int j) {
        return getBooleanCellWorkerMap(i, j);
    }

    /**
     * Allows or forbids player to move up based on the canMoveUp attribute of Player.
     */
    public void updateMoveUpRestrictions() {

        if (getWorker().getPlayer().getCanMoveUp())
            levelDifferenceLessEqualThanX(1);
        else
            levelDifferenceLessEqualThanX(0);
    }

    /**
     * Checks if the worker can move.
     *
     * @return Returns true if there is a cell the worker can move in, false otherwise.
     */
    public boolean anyAvailableMovePosition() {
        return anyTrueCell();
    }


}
