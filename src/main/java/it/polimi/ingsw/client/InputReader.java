package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;

public class InputReader implements Runnable {

    private boolean connected;
    private ObjectInputStream inputStm;
    private volatile SynchronousQueue<Object> receivedObjectsQueue;
    private final Client client;


    public InputReader(Socket server, Client client) {

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

                Message readMessage = (Message) readObject;


                if (readMessage.getMethod().equals("shutdownClient") || readMessage.getMethod().equals("notifyOtherPlayerDisconnection")) {

                    System.out.println("received" + readMessage.getMethod());

                    if(readMessage.getMethod().equals("notifyOtherPlayerDisconnection"))
                        client.update(readMessage);

                    else{
                        connected = false;
                        client.disconnect();
                    }

                    //Che succede se ho messo false, quidni non aggiungero piu niente alla coda ma network handler, è su handle server request?
                    //STOP NETWORK HANDLER THREAD?


                } else {

                    try {
                        receivedObjectsQueue.put(readObject);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            } catch (IOException | ClassNotFoundException e) {

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
