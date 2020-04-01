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

    public void allowedToBuildInPosition(int i, int j) {
        setCellTrue(i, j);
    }

    public void notAllowedToBuildInPosition(int i, int j) {
        setCellFalse(i, j);
    }

    public boolean isAllowedToBuild(int i, int j) {
        return getBooleanCell(i, j);
    }

    public void cannotBuildInPerimeter() {
        cellsInPerimeterFalse();
    }


}
