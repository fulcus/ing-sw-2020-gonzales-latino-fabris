package it.polimi.ingsw.client.view;

import java.io.Serializable;

public class ClientCell implements Serializable {


    private final int x;
    private final int y;

    enum Sex{MALE,FEMALE;}
    enum Color{WHITE,BLUE,BEIGE}


    String workerSex;
    String workerColor;

    boolean hasWorker;
    boolean hasDome;
    int cellLevel;

    public ClientCell(int x, int y){
        this.x = x;
        this.y = y;
        workerSex = null;
        workerColor = null;
        hasWorker = false;
        hasDome = false;
        cellLevel = 0;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getWorkerSex() {
        return workerSex;
    }

    public String getWorkerColor() {
        return workerColor;
    }

    public boolean HasWorker() {
        return hasWorker;
    }

    public boolean HasDome() {
        return hasDome;
    }

    public int getCellLevel() {
        return cellLevel;
    }

    public void setWorkerSex(String workerSex) {
        this.workerSex = workerSex;
    }

    public void setWorkerColor(String workerColor) {
        this.workerColor = workerColor;
    }

    public void setHasWorker(boolean hasWorker) {
        this.hasWorker = hasWorker;
    }

    public void setHasDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

    public void setCellLevel(int cellLevel) {
        this.cellLevel = cellLevel;
    }

}
