package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * This class allows to handle the connection with the server
 */
public class NetworkHandler implements Runnable {

    private enum Commands {

    }

    private Commands nextCommand;
    private String convertStringParam;

    private boolean keepConnected;
    private Socket server;
    private ObjectOutputStream outputStm;
    private ObjectInputStream inputStm;
    private final List<ServerObserver> observers = new ArrayList<>();


    public NetworkHandler(Socket server, Client client) {
        this.server = server;
        keepConnected = true;
        addObserver(client);
    }

    public void shutDown() {
        this.keepConnected = false;
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



    @Override
    public void run() {

        init();

        while (keepConnected) {

            Object returnedValue;

            try {

                returnedValue = handleServerRequest();

                handleClientResponse(returnedValue);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }


        try {
            server.close();
        } catch (IOException e) {
        }
    }

    /**
     * Allows to manage the requests received.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private synchronized Object handleServerRequest() throws IOException, ClassNotFoundException {

        Message receivedMessage = null;

        receivedMessage = (Message) inputStm.readObject();

        if(receivedMessage.getMethod().equals("shutdownClient")) {
            keepConnected = false;
            return null;
        }

        if (receivedMessage != null) {

            return observers.get(0).update(receivedMessage);
        }

        return null;

    }


    private synchronized void handleClientResponse(Object clientResponse) throws IOException, ClassNotFoundException {

        if(clientResponse == null)
            return;

        outputStm.writeObject(clientResponse);


      /*  while (true) {

            //TODO ONCE THE CLIENT FULFILL INFORMATION----->NOTIFYALL();
            //Wait until the clients gives information the server requested before
            try {
                wait();
            } catch (InterruptedException e) {
            }


        }*/
    }


}
