package it.polimi.ingsw.server;

import it.polimi.ingsw.serializable.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.SynchronousQueue;


/**
 * Allows to receive all the inputs coming from the clients.
 */
public class ClientInputReader implements Runnable {

    private final VirtualView client;
    private volatile SynchronousQueue<Object> receivedObjects;
    private boolean connected;
    private ObjectInputStream input;
    private boolean killed;


    public ClientInputReader(VirtualView client) {

        this.client = client;
        receivedObjects = new SynchronousQueue<>();
        connected = true;
        killed = false;


        try {
            input = new ObjectInputStream(client.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public SynchronousQueue<Object> getObjectsQueue() {
        return receivedObjects;
    }


    /**
     * While the connection is on, it allows to receive and read the Objects coming from the client-side.
     * Can understand if the message was a ping from the client or a different one.
     */
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
                        System.out.println("Ping from " + clientSocket.getInetAddress());
                    } else
                        System.out.println("Received message different from ping");

                } else
                    receivedObjects.add(readObject);

            } catch (IOException e) {

                if (!killed) {
                    client.setInGame(false);

                    if (client.getPlayer() != null) {
                        System.out.println(client.getPlayer().getNickname() + " disconnected");
                        client.getGameController().handleGameDisconnection(client.getPlayer().getNickname());
                    } else {
                        System.out.println("Someone disconnected");
                        client.getGameController().handleGameDisconnection("Someone");
                    }
                }
                connected = false;

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }


}
