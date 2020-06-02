package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeoutException;

public class InputReader implements Runnable {

    private boolean connected;
    private ObjectInputStream inputStm;
    private final SynchronousQueue<Object> receivedObjectsQueue;
    private final Client client;
    private final Socket serverSocket;


    public InputReader(Socket server, Client client) {

        serverSocket = server;
        this.client = client;
        connected = true;
        receivedObjectsQueue = new SynchronousQueue<>();

        try {

            InputStream input = server.getInputStream();
            inputStm = new ObjectInputStream(input);

        } catch (IOException e) {
            System.out.println("server has died");
        }
    }


    @Override
    public void run() {

        while (connected) {

            try {

                Object readObject = inputStm.readObject();

                serverSocket.setSoTimeout(15000);


                Message readMessage = (Message) readObject;


                if (readMessage.getMethod().equals("shutdownClient")) {

                    System.out.println("received shutdownClient");
                    connected = false;
                    client.disconnect();

                    //Che succede se ho messo false, quindi non aggiungero piu niente alla coda ma network handler, è su handle server request?
                    //STOP NETWORK HANDLER THREAD?

                } else if (readMessage.getMethod().equals("notifyOtherPlayerDisconnection")) {
                    
                    System.out.println("received notifyOtherPlayerDisconnection");
                    client.update(readMessage);

                } else if (readMessage.getMethod().equals("PONG")) {

                    System.out.println("PONG from Server");

                } else {

                    try {
                        receivedObjectsQueue.put(readObject);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
            catch (SocketTimeoutException te) {

                connected = false;
                Message notifyDisconnection = new Message("notifyOtherPlayerDisconnection");
                client.update(notifyDisconnection);


            }
            catch (IOException | ClassNotFoundException e) {
                connected = false;
                e.printStackTrace();
            }
        }
    }

    public SynchronousQueue<Object> getObjectsQueue() {
        return receivedObjectsQueue;
    }

    public boolean isConnected() {
        return connected;
    }
}
