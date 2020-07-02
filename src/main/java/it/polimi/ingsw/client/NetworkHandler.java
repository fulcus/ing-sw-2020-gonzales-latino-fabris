package it.polimi.ingsw.client;

import it.polimi.ingsw.serializable.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * This class allows to handle the connection with the server.
 * Receives and sends to it game messages to let the game continue.
 */
public class NetworkHandler implements Runnable {

    private volatile boolean connected;
    private ObjectOutputStream outputStm;
    private final Client client;
    private final InputReader inputReader;


    public NetworkHandler(Socket server, Client client) {
        connected = true;
        this.client = client;

        try {
            outputStm = new ObjectOutputStream(server.getOutputStream());
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException e) {
            System.out.println("protocol violation");
        }

        inputReader = new InputReader(server, client);
        new Thread(inputReader).start();

    }


    public boolean isConnected() {
        return connected;
    }


    @Override
    public void run() {

        while (inputReader.isConnected()) {

            try {

                Object returnedValue = handleServerRequest();
                handleClientResponse(returnedValue);

            } catch (IOException e) {
                System.out.println("network handler exception");
                e.printStackTrace();
            }
        }
        disconnect();
    }


    /**
     * Allows to manage the requests received from the server,
     * thanks to the inputReader instance.
     *
     * @throws IOException generated by inputStream
     */
    private Object handleServerRequest() throws IOException {

        Message receivedMessage = null;

        //System.out.println("Before TAKE, NH");
        try {
            receivedMessage = (Message) inputReader.getObjectsQueue().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //  System.out.println("AFTER TAKE, NH, taken: " + receivedMessage.getMethod());


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

        if (clientResponse == null)
            return;

        outputStm.writeObject(clientResponse);

    }


    /**
     * Points out that the connection with the server has been interrupted.
     */
    public void disconnect() {
        System.out.println("Network handler received disconnected");
        this.connected = false;
    }


    public InputReader getInputReader() {
        return inputReader;
    }


}
