package it.polimi.ingsw.client;

import it.polimi.ingsw.serializable.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.LinkedBlockingQueue;


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
                    connected = false;
                    client.disconnect();
                } else if (readMessage.getMethod().equals("notifyOtherPlayerDisconnection")) {
                    client.update(readMessage);
                } else {
                    try {
                        receivedObjectsQueue.put(readObject);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (SocketTimeoutException te) {
                connected = false;
                Message notifyDisconnection = new Message("notifyOtherPlayerDisconnection", "YOU");
                client.update(notifyDisconnection);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("\n\nServer has died");
                connected = false;
                System.exit(1);
            } catch (Exception e) {
                System.out.println("\n\nAn error occurred\n\n");
                e.printStackTrace();
                System.exit(1);
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
