package it.polimi.ingsw.client;

import java.util.Scanner;
import java.util.TimerTask;

public class OutOfTurn extends TimerTask {

    private Scanner input;
    private String clientWrites;

    public OutOfTurn() {
        input = new Scanner(System.in);
        clientWrites = null;
    }

    @Override
    public void run() {
        while(true)
            clientWrites = writeOutOfTurn();
    }

    public String writeOutOfTurn() {
        return input.nextLine();
    }

    public String getClientWrites() {
        return clientWrites;
    }

    public void setClientWritesNull() {
        this.clientWrites = null;
    }
}


