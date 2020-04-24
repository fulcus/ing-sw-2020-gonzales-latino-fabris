package it.polimi.ingsw.serializableObjects;

import it.polimi.ingsw.server.model.Worker;

import java.io.Serializable;

public class WorkerClient implements Serializable {

    String workerSex;
    String workerColor;
    int xPosition;
    int yPosition;


    public String getWorkerSex() {
        return workerSex;
    }

    public String getWorkerColor() {
        return workerColor;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }


    public WorkerClient(Worker worker) {

        workerSex = worker.getSex().toString();
        workerColor = worker.getPlayer().getColor().toString();
        xPosition = worker.getPosition().getX();
        yPosition = worker.getPosition().getY();

    }
}
