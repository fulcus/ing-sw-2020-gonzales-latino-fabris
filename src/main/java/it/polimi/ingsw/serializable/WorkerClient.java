package it.polimi.ingsw.serializable;

import it.polimi.ingsw.server.model.Worker;

import java.io.Serializable;

/**
 * The worker representation client side.
 */
public class WorkerClient implements Serializable {

    private final String workerSex;
    private final String workerColor;
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


    //ONLY used in server
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


    /**
     * Updates the position of the local representation of the worker.
     *
     * @param workerFromServer Worker received from the server.
     */
    public void updateWorkerPosition(WorkerClient workerFromServer) {

        /*
        System.out.println("workerFromServer: "+workerFromServer.getWorkerColor()
        + "; coordinates: "+workerFromServer.getXPosition()+","+getYPosition());
        System.out.println("updatedWorker: " + getWorkerColor());
        System.out.println("old x,y: "+getXPosition() + "," + getYPosition());
        */

        this.xPosition = workerFromServer.getXPosition();
        this.yPosition = workerFromServer.getYPosition();

        /*
        System.out.println("new x,y: "
                + getXPosition() + "," + getYPosition());
        System.out.println("attributes: "
                + xPosition + "," + yPosition);*/

    }

}
