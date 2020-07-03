package it.polimi.ingsw.client;

import it.polimi.ingsw.serializable.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Handles connection to the server.
 * Receives and sends game messages.
 */
public class NetworkHandler implements Runnable {

    private volatile boolean connected;
    private ObjectOutputStream outputStm;
    private final Client client;
    private final Socket server;
    private ObjectInputStream inputStream;
    private final LinkedBlockingQueue<Object> receivedObjectsQueue;


    public NetworkHandler(Socket server, Client client) {
        connected = true;
        this.client = client;
        this.server = server;
        receivedObjectsQueue = new LinkedBlockingQueue<>();

        try {
            outputStm = new ObjectOutputStream(server.getOutputStream());
            inputStream = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException e) {
            System.out.println("protocol violation");
        }

        new Thread(this::readInputStream).start();

    }


    @Override
    public void run() {

        while (connected) {
            try {
                Object returnedValue = handleServerRequest();
                handleClientResponse(returnedValue);
            } catch (IOException e) {
                System.out.println("network handler exception");
                e.printStackTrace();
            }
        }
        connected = false;
    }

    /**
     * Reads the input stream from the server.
     * It adds game messages to a queue and resets timeout whenever it receives data from the server.
     */
    private void readInputStream() {

        while (connected) {
            try {
                Object readObject = inputStream.readObject();
                server.setSoTimeout(15000);
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
            } catch (IOException e) {
                System.out.println("\n\nServer has died\n");
                connected = false;
                System.exit(1);
            } catch (Exception e) {
                System.out.println("\n\nAn error occurred\n\n");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }


    /**
     * Forwards requests read by readInputStream and forwards them to the client.
     */
    private Object handleServerRequest() {

        Message receivedMessage = null;

        try {
            receivedMessage = (Message) receivedObjectsQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (receivedMessage == null)
            return null;

        return client.update(receivedMessage);
    }


    /**
     * Handles the answer of the client to send to the server.
     *
     * @param clientResponse What the client wants to send to teh server.
     * @throws IOException If the write object gives some problems.
     */
    public synchronized void handleClientResponse(Object clientResponse) throws IOException {
        if (clientResponse != null)
            outputStm.writeObject(clientResponse);
    }

    public boolean isConnected() {
        return connected;
    }

}
