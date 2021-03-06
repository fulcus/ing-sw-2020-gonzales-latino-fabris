package it.polimi.ingsw.serializable;

import it.polimi.ingsw.server.model.Cell;
import java.io.Serializable;


/**
 * Allows to represent the Cell object client-side.
 */
public class CellClient implements Serializable {

    private final int x;
    private final int y;
    private WorkerClient worker;
    private boolean hasDome;
    private int cellLevel;


    public CellClient(int x, int y) {
        this.x = x;
        this.y = y;
        worker = null;
        hasDome = false;
        cellLevel = 0;
    }

    //used ONLY in server
    public CellClient(Cell observedCell) {

        this.x = observedCell.getX();
        this.y = observedCell.getY();

        if (observedCell.getWorker() != null)
            this.worker = new WorkerClient(observedCell.getWorker());
        else
            this.worker = null;

        this.hasDome = observedCell.hasDome();
        this.cellLevel = observedCell.getLevel();
    }


    /**
     * Updates the clientCell of the view after receiving the cell from the server.
     * @param cellFromServer cell received from the server.
     */
    public void updateBuildingCell(CellClient cellFromServer) {

        this.cellLevel = cellFromServer.getCellLevel();
        this.hasDome = cellFromServer.hasDome();

    }


    /**
     * Removes worker from cell.
     */
    public void removeWorker() {
        this.worker = null;
    }


    /**
     * Updates worker in cell.
     * @param worker local workerClient instance.
     */
    public void addWorker(WorkerClient worker) {
        this.worker = worker;
    }


    public int getY() {
        return y;
    }


    public int getX() {
        return x;
    }


    public WorkerClient getWorkerClient() {
        return worker;
    }


    public boolean hasWorker() {
        return worker != null;
    }


    public boolean hasDome() {
        return hasDome;
    }


    public int getCellLevel() {
        return cellLevel;
    }


}
