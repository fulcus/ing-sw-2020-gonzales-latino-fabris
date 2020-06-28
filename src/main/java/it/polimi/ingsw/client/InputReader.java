package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;


/**
 * Reads inputs that will receive, both game messages and heartbeat pings.
 */
public class InputReader implements Runnable {

    private boolean connected;
    private ObjectInputStream inputStream;
    private final LinkedBlockingQueue<Object> receivedObjectsQueue;
    private final Client client;
    private final Socket serverSocket;


    public InputReader(Socket server, Client client) {

        serverSocket = server;
        this.client = client;
        connected = true;
        receivedObjectsQueue = new LinkedBlockingQueue<>();

        try {

            InputStream input = server.getInputStream();
            inputStream = new ObjectInputStream(input);

        } catch (IOException e) {
            System.out.println("server has died");
        }
    }


    @Override
    public void run() {

        while (connected) {

            try {

                Object readObject = inputStream.readObject();

                serverSocket.setSoTimeout(15000);

                Message readMessage = (Message) readObject;


                if (readMessage.getMethod().equals("shutdownClient")) {

                    //System.out.println("received shutdownClient");
                    connected = false;
                    client.disconnect();

                    //todo Che succede se ho messo false, quindi non aggiungero piu niente alla coda ma network handler, è su handle server request?
                    //STOP NETWORK HANDLER THREAD?

                } else if (readMessage.getMethod().equals("notifyOtherPlayerDisconnection")) {

                  //  System.out.println("received notifyOtherPlayerDisconnection");
                    client.update(readMessage);

                } else if (readMessage.getMethod().equals("PONG")) {

                    System.out.println("PONG from Server");

                } else {

                   // System.out.println("Before PUT, IR, passed" + readMessage.getMethod());

                    try {
                        receivedObjectsQueue.put(readObject);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // System.out.println("AFTER PUT, IR, passed" + readMessage.getMethod());

                }

            }
            catch (SocketTimeoutException te) {
                System.out.println("PRIMA CATCH");
                connected = false;
                Message notifyDisconnection = new Message("notifyOtherPlayerDisconnection","me");
                client.update(notifyDisconnection);
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println("SECONDA CATCH");
                connected = false;
                e.printStackTrace();
            }

        }
    }


    public LinkedBlockingQueue<Object> getObjectsQueue() {
        return receivedObjectsQueue;
    }


    public boolean isConnected() {
        return connected;
    }
}
