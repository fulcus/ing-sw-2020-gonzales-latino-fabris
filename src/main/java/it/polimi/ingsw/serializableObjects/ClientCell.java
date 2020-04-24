package it.polimi.ingsw.serializableObjects;

import it.polimi.ingsw.server.model.Cell;

import java.io.Serializable;

public class ClientCell implements Serializable {


    private final int x;
    private final int y;

    private WorkerClient worker;

    private boolean hasDome;
    private int cellLevel;


    public ClientCell(int x, int y) {
        this.x = x;
        this.y = y;
        this.worker = null;
        hasDome = false;
        cellLevel = 0;
    }

    //used in server
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

    /**
     * Updates the clientCell of the view after receiving the cell from the server.
     * @param cellFromServer cell received from the server.
     */
    public void updateCell(ClientCell cellFromServer) {
        this.cellLevel = cellFromServer.getCellLevel();
        this.hasDome = cellFromServer.hasDome();

        if(cellFromServer.getWorkerClient() != null)
            this.worker = new WorkerClient(cellFromServer.getWorkerClient());
        else
            this.worker = null;
    }


    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public WorkerClient getWorkerClient(){ return this.worker; }

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
