package it.polimi.ingsw.model;

/**
 * Matrix that represents the adjacent positions the player can move to.
 */
public class WorkerBuildMap extends WorkerMap {

    public WorkerBuildMap(Worker worker) {
        super(worker);
    }

    public void cannotBuildInWorkerCell() {
        WorkerCellFalse();
    }

    public void cannotBuildInDomeCell() {
        DomeCellFalse();
    }

    public void cannotBuildInOccupiedCell() {
        OccupiedCellFalse();
    }

    public void cannotBuildUnderneath() {
        centerPositionFalse();
    }

    public void canBuildUnderneath() {
        centerPositionTrue();
    }

    /*
    public void setAllowedToBuildBoard(int i, int j) {
        setBooleanCellBoard(i, j,true);
    }

    public void setNotAllowedToBuildBoard(int i, int j) {
        setBooleanCellBoard(i, j,false);
    }
    */

    public boolean isAllowedToBuildBoard(int i, int j) {
        return getBooleanCellBoard(i, j);
    }

    public void cannotBuildInPerimeter() {
        cellsInPerimeterFalse();
    }

    /**
     *
     * @return Returns true if there is a cell the worker can build in, false otherwise.
     */
    public boolean anyAvailableBuildPosition() {
        return anyTrueCell();
    }

}
