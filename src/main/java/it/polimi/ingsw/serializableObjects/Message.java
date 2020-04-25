package it.polimi.ingsw.serializableObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

    private int typeOfMessage;
    private String method;
    private String stringParam;
    private int intParam1;
    private int intParam2;
    private ArrayList<String> stringListParam;
    private ClientCell toUpdateCell;
    private ArrayList<WorkerClient> workersParam;
    private WorkerClient worker;


    public Message(String method) {
        typeOfMessage = 1;
        this.method = method;
    }

    public Message(String method, String stringParam) {
        typeOfMessage = 2;
        this.method = method;
        this.stringParam = stringParam;
    }

    public Message(String method, ArrayList<String> stringListParam) {
        typeOfMessage = 3;
        this.method = method;
        this.stringListParam = stringListParam;
    }

    public Message(String method, int intParam1, int intParam2) {
        typeOfMessage = 4;
        this.method = method;
        this.intParam1 = intParam1;
        this.intParam2 = intParam2;
    }

    public Message(String method, ClientCell toUpdateCell) {
        typeOfMessage = 5;
        this.method = method;
        this.toUpdateCell = toUpdateCell;
    }

    public Message(String method, ArrayList<WorkerClient> workersParam, WorkerClient worker) {
        typeOfMessage = 6;
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

    public ClientCell getToUpdateCell() {
        return toUpdateCell;
    }

    public ArrayList<WorkerClient> getWorkersParam() {
        return workersParam;
    }

    public WorkerClient getWorker() {
        return worker;
    }

    public int getTypeOfMessage() {
        return typeOfMessage;
    }



}
