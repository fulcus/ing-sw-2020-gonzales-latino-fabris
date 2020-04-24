package it.polimi.ingsw.serializableObjects;

import it.polimi.ingsw.server.model.Cell;

import java.io.Serializable;

public class ClientCell implements Serializable {


    private final int x;
    private final int y;

    WorkerClient worker;

    boolean hasDome;
    int cellLevel;

    public ClientCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.worker = null;
        hasDome = false;
        cellLevel = 0;
    }

    public ClientCell(Cell observedCell) {
        this.x = observedCell.getX();
        this.y = observedCell.getY();

        if (observedCell.getWorker() != null)
            this.worker = new WorkerClient(observedCell.getWorker());
        else
            this.worker = null;

        this.hasDome = observedCell.hasDome();
        this.cellLevel = observedCell.getLevel();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setWorker(WorkerClient worker) {
        this.worker = worker;
    }

    public WorkerClient getWorkerClient(){ return this.worker; }

    public boolean HasWorker() {
        return worker != null;
    }

    public boolean HasDome() {
        return hasDome;
    }

    public int getCellLevel() {
        return cellLevel;
    }

    public void setHasDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

    public void setCellLevel(int cellLevel) {
        this.cellLevel = cellLevel;
    }

}
