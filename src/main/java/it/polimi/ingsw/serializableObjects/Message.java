package it.polimi.ingsw.serializableObjects;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String method;
    private String stringParam;
    private int intParam1;
    private int intParam2;
    private ArrayList<String> stringListParam;
    private ClientCell toUpdateCell;
    private ArrayList<WorkerClient> workersParam;
    private WorkerClient worker;


    public Message(String method) {
        this.method = method;
    }

    public Message(String method, String stringParam) {
        this.method = method;
        this.stringParam = stringParam;
    }

    public Message(String method, ArrayList<String> stringListParam) {
        this.method = method;
        this.stringListParam = stringListParam;
    }

    public Message(String method, int intParam1, int intParam2) {
        this.method = method;
        this.intParam1 = intParam1;
        this.intParam2 = intParam2;
    }

    public Message(String method, ClientCell toUpdateCell) {
        this.method = method;
        this.toUpdateCell = toUpdateCell;
    }

    public Message(String method, ArrayList<WorkerClient> workersParam, WorkerClient worker) {
        this.method = method;
        this.workersParam = workersParam;
        this.worker = worker;

    }


}
