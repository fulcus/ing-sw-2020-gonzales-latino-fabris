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

    public void allowedToMoveInPosition(int i, int j) {
        setCellTrue(i,j);
    }

    public void notAllowedToMoveInPosition(int i, int j) {
        setCellFalse(i,j);
    }

    public boolean isAllowedToMove(int i, int j) {
        return getBooleanCell(i,j);
    }

    public void cannotMoveInPerimeter() {
        cellsInPerimeterFalse();
    }


}
