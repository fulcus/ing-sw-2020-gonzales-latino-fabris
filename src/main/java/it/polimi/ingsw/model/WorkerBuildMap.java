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

    public void cannotBuildInOccupiedCell() {
        OccupiedCellFalse();
    }

    public void cannotBuildUnderneath() {
        centerPositionFalse();
    }

    public void canBuildUnderneath() {
        workersPositionTrue();
    }

    public void setAllowedToBuildInPosition(int i, int j) {
        setBooleanCellBoard(i, j,true);
    }

    public void setNotAllowedToBuildInPosition(int i, int j) {
        setBooleanCellBoard(i, j,false);
    }

    public boolean isAllowedToBuildBoard(int i, int j) {
        return getBooleanCellBoard(i, j);
    }

    public void cannotBuildInPerimeter() {
        cellsInPerimeterFalse();
    }

}
