package it.polimi.ingsw.server;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerInputReader implements Runnable {

    private final Socket clientSocket;
    private volatile Object receivedObject;
    private boolean connected;
    private ObjectInputStream input;
    private int n;

    public ServerInputReader(Socket clientSocket) {

        this.clientSocket = clientSocket;
        receivedObject = null;
        connected = true;


        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
        }

    }

    public Object getReceivedObject() {
        return receivedObject;
    }

    public void resetReceivedObject() {
        receivedObject = null;
    }

    @Override
    public void run() {


        try {
            clientSocket.setSoTimeout(15000);
        } catch (SocketException e) {
            e.printStackTrace();
        }


        while (connected) {
/*
            while (receivedObject != null) {
                //if a message has been received, wait sendMessageWithreturn to get it
            }
*/

            try {

                //meglio mettere in una struttura tipo coda o stream


                Object readObject = input.readObject();


                if (readObject instanceof Message) {

                    Message readMessage = (Message) readObject;

                    if (readMessage.getMethod().toUpperCase().equals("PING")) {
                        clientSocket.setSoTimeout(15000);
                        System.out.println("Ping Received from " + clientSocket.getInetAddress());
                    }

                    else
                        System.out.println("Received message different from ping");

                } else {

                    clientSocket.setSoTimeout(15000);

                   // System.out.println("Thread reader received an object");
                    receivedObject = readObject;
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
