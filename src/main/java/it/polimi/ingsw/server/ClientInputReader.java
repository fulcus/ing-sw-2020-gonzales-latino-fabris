package it.polimi.ingsw.server;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

public class ClientInputReader implements Runnable {

    private final ViewClient client;
    private volatile SynchronousQueue<Object> receivedObjects;
    private boolean connected;
    private ObjectInputStream input;

    public ClientInputReader(ViewClient client) {

        this.client = client;
        receivedObjects = new SynchronousQueue<>();
        connected = true;


        try {
            input = new ObjectInputStream(client.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SynchronousQueue<Object> getObjectsQueue() {
        return receivedObjects;
    }


    @Override
    public void run() {

        Socket clientSocket = client.getSocket();

        try {
            clientSocket.setSoTimeout(15000);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (connected) {

            try {

                Object readObject = input.readObject();

                clientSocket.setSoTimeout(15000);

                if (readObject instanceof Message) {

                    Message readMessage = (Message) readObject;

                    if (readMessage.getMethod().toUpperCase().equals("PING")) {
                        System.out.println("Ping Received from " + clientSocket.getInetAddress());
                    } else
                        System.out.println("Received message different from ping");

                } else {

                    receivedObjects.add(readObject);
                }


            } catch (IOException e) {

                //System.out.println("PRINTING EXCEPTION");
                //e.printStackTrace();

                connected = false;

                client.setInGame(false);

                if (client.getPlayer() != null) {
                    System.out.println(client.getPlayer().getNickname() + " disconnected");

                    client.getGameController().handleGameDisconnection(client.getPlayer().getNickname());
                }
                else {
                    System.out.println("Someone disconnected");
                    client.getGameController().handleGameDisconnection("Someone");
                }


                //TODO VERIFICARE SE RIMUOVERE LA SOUT PERCHè IN INIZIALIZZAZIONE DEL GIOCO, NULL POINTER PERCHè NICK ANCORA NON ESISTE.


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }


}
