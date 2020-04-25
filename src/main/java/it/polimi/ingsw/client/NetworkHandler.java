package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLIMainView;
import it.polimi.ingsw.client.view.GodView;
import it.polimi.ingsw.serializableObjects.Message;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;


public class NetworkHandler implements Runnable {

    private enum Commands {

    }

    private Commands nextCommand;
    private String convertStringParam;

    private Socket server;
    private ObjectOutputStream outputStm;
    private ObjectInputStream inputStm;
    private final List<ServerObserver> observers = new ArrayList<>();


    public NetworkHandler(Socket server, Client client) {
        this.server = server;
        addObserver(client);
    }

    public void init() {

        try {
            outputStm = new ObjectOutputStream(server.getOutputStream());
            inputStm = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException e) {
            System.out.println("protocol violation");
        }


    }


    public void addObserver(ServerObserver observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }


    public void removeObserver(ServerObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }


    public synchronized void stop() {
        nextCommand = Commands.STOP;
        notifyAll();
    }


    public synchronized void requestConversion(String input) {
        nextCommand = Commands.CONVERT_STRING;
        convertStringParam = input;
        notifyAll();
    }


    @Override
    public void run() {

        init();

        while(true){
            handleServerRequest();
            handleClientResponse();
        }


        try {
            server.close();
        } catch (IOException e) {
        }
    }


    private synchronized void handleServerRequest() throws IOException, ClassNotFoundException {

        Message receivedMessage = null;

        receivedMessage = (Message) inputStm.readObject();

        if(receivedMessage != null) {

            for (ServerObserver observer : observers)
                observer.update(receivedMessage);
        }

    }


    private synchronized void handleClientResponse() throws IOException, ClassNotFoundException {
        /* wait for commands */
        while (true) {

            //TODO ONCE THE CLIENT FULFILL INFORMATION----->NOTIFYALL();
            //Wait until the clients gives information the server requested before
            try {
                wait();
            } catch (InterruptedException e) {
            }

            if (nextCommand == null)
                continue;

            // NEI CASE DI QUESTO SWITCH DOBBIAMO METTERE I METODI delle richieste del server
            switch (nextCommand) {
                case CONVERT_STRING:
                    doStringConversion();
                    break;

                case STOP:
                    return;
            }
        }
    }


    private synchronized void doStringConversion() throws IOException, ClassNotFoundException {
        /* send the string to the server and get the new string back */
        outputStm.writeObject(convertStringParam);
        String newStr = (String) inputStm.readObject();

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.didReceiveConvertedString(convertStringParam, newStr);
        }
    }

}
