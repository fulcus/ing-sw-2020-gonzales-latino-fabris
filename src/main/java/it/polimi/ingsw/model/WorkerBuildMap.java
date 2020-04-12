package it.polimi.ingsw.model;

/**
 * Matrix that represents positions adjacent to the worker, in which he
 * may or may not build, depending on the boolean value of the cell.
 */
public class WorkerBuildMap extends WorkerMap {

    public WorkerBuildMap(Worker worker) {
        super(worker);
    }

    public void cannotBuildInWorkerCell() {
        workerCellFalse();
    }

    public void cannotBuildInDomeCell() {
        DomeCellFalse();
    }

    public void cannotBuildInOccupiedCell() {
        occupiedCellFalse();
    }

    public void cannotBuildUnderneath() {
        centerPositionFalse();
    }

    public void canBuildUnderneath() {
        centerPositionTrue();
    }

    public boolean isAllowedToBuildBoard(int i, int j) {
        return getBooleanCellBoard(i, j);
    }

    public void cannotBuildInPerimeter() {
        cellsInPerimeterFalse();
    }

    /**
     * Checks if the worker can build.
     * @return Returns true if there is a cell the worker can build in, false otherwise.
     */
    public boolean anyAvailableBuildPosition() {
        return anyTrueCell();
    }

}
