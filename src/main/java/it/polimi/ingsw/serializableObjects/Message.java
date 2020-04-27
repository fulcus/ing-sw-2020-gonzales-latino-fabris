package it.polimi.ingsw.serializableObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

    private final int type; //says which parameters the method takes
    private final String method;
    private String stringParam;
    private int intParam1;
    private int intParam2;
    private ArrayList<String> stringListParam;
    private CellClient toUpdateCell;
    private ArrayList<WorkerClient> workersParam;
    private WorkerClient worker;


    public Message(String method) {
        type = 1;
        this.method = method;
    }

    public Message(String method, String stringParam) {
        type = 2;
        this.method = method;
        this.stringParam = stringParam;
    }

    public Message(String method, ArrayList<String> stringListParam) {
        type = 3;
        this.method = method;
        this.stringListParam = stringListParam;
    }

    public Message(String method, int intParam1, int intParam2) {
        type = 4;
        this.method = method;
        this.intParam1 = intParam1;
        this.intParam2 = intParam2;
    }

    public Message(String method, CellClient toUpdateCell) {
        type = 5;
        this.method = method;
        this.toUpdateCell = toUpdateCell;
    }

    public Message(String method, ArrayList<WorkerClient> workersParam, WorkerClient worker) {
        type = 6;
        this.method = method;
        this.workersParam = workersParam;
        this.worker = worker;
    }

    public String getMethod() {
        return method;
    }

    public String getStringParam() {
        return stringParam;
    }

    public int getIntParam1() {
        return intParam1;
    }

    public int getIntParam2() {
        return intParam2;
    }

    public ArrayList<String> getStringListParam() {
        return stringListParam;
    }

    public CellClient getToUpdateCell() {
        return toUpdateCell;
    }

    public ArrayList<WorkerClient> getWorkersParam() {
        return workersParam;
    }

    public WorkerClient getWorker() {
        return worker;
    }

    public int getMessageType() {
        return type;
    }


}
