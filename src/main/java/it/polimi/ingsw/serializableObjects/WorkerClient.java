package it.polimi.ingsw.serializableObjects;

import it.polimi.ingsw.server.model.Worker;

import java.io.Serializable;

public class WorkerClient implements Serializable {

    private String workerSex;
    private String workerColor;
    private int xPosition;
    private int yPosition;


    public String getWorkerSex() {
        return workerSex;
    }

    public String getWorkerColor() {
        return workerColor;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }


    public WorkerClient(Worker worker) {

        workerSex = worker.getSex().toString();
        workerColor = worker.getPlayer().getColor().toString();
        xPosition = worker.getPosition().getX();
        yPosition = worker.getPosition().getY();

    }

    public WorkerClient(WorkerClient workerFromServer) {

        this.workerColor = workerFromServer.getWorkerColor();
        this.workerSex = workerFromServer.getWorkerSex();
        this.xPosition = workerFromServer.getXPosition();
        this.yPosition = workerFromServer.getYPosition();
    }


}
