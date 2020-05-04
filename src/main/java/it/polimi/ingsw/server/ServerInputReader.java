package it.polimi.ingsw.server;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

public class ServerInputReader implements Runnable {

    private final Socket clientSocket;
    private volatile SynchronousQueue<Object> receivedObjects;
    private boolean connected;
    private ObjectInputStream input;
    private int n;

    public ServerInputReader(Socket clientSocket) {

        this.clientSocket = clientSocket;
        receivedObjects = new SynchronousQueue<>();
        connected = true;



        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
        }

    }

    public SynchronousQueue<Object> getObjectsQueue() {
        return receivedObjects;
    }


    @Override
    public void run() {


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
                    }

                    else
                        System.out.println("Received message different from ping");

                } else {


                   // System.out.println("Thread reader received an object");
                    receivedObjects.add(readObject);

                //    System.out.println("Received:"+ receivedObject);

                }


            } catch (IOException e) {

                connected = false;

                //TODO REQUISITI SPECIFICA,PIAZZA, SHUT DOWN,(ATHENA e simili),
                //TODO WAIT FRA
                //Se scade timeout.
                //gestione disconnessione
                //reset game senza il giocatore
                //se il gioco è in 3, si riorganizza, notifica agli altri players
                //se il gioco è in 2, il gioco non puo andare avanti


                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


}
