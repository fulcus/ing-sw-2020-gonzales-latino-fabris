package it.polimi.ingsw.model;

/**
 * Matrix that represents the adjacent positions the player can move to.
 */
public class WorkerMoveMap extends WorkerMap {

    public WorkerMoveMap(Worker worker) {
        super(worker);
    }

    public void cannotMoveInDomeCell() {
        DomeCellFalse();
    }

    public void cannotMoveInWorkerCell() {
        WorkerCellFalse();
    }

    public void cannotMoveInOccupiedCell() {
        OccupiedCellFalse();
    }

    public void cannotMoveInFriendlyWorkerCell() {
        FriendlyWorkerCellFalse();
    }


    public void cannotStayStill() {
        centerPositionFalse();
    }

    public void canStayStill() {
        workersPositionTrue();
    }

    /**
     * Returns if it is allowed to move in a given cell of the map.
     * @param i Board coordinate X.
     * @param j Board coordinate Y.
     * @return True if it can move in position, false otherwise.
     */
    public boolean isAllowedToMoveBoard(int i, int j) {
        return getBooleanCellBoard(i,j);
    }

    public boolean isAllowedToMoveWorkersMap(int i, int j) {
        return getBooleanCellWorkerMap(i, j);
    }

    public void cannotMoveInPerimeter() {
        cellsInPerimeterFalse();
    }

    /**
     * Sets if player can move up or not based on attribute of Player.
     */
    public void updateMoveUpRestrictions() {

        if(getWorker().getPlayer().getCanMoveUp())
            levelDifferenceLessEqualThanX(1);
        else
            levelDifferenceLessEqualThanX(0);
    }

    /**
     *
     * @return Returns true if there is a cell the worker can move in, false otherwise.
     */
    public boolean anyAvailableMovePosition() {
        return anyTrueCell();
    }


}
