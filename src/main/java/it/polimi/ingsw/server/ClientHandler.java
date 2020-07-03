package it.polimi.ingsw.server;

import it.polimi.ingsw.serializable.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.SynchronousQueue;


/**
 * Handles all interactions between the server and a client.
 */
public class ClientHandler implements Runnable {

    private final VirtualView client;
    private final SynchronousQueue<Object> receivedObjects;
    private boolean connected;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean killed;


    public ClientHandler(VirtualView client) {

        this.client = client;
        receivedObjects = new SynchronousQueue<>();
        connected = true;
        killed = false;
        Socket clientSocket = client.getSocket();

        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Sends message through network to client associated to this ClientHandler instance.
     * @param message message to send
     */
    protected synchronized void sendMessage(Message message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            System.out.println("server has died while sending");
        }
    }


    protected void kill() {
        this.killed = true;
    }


    protected SynchronousQueue<Object> getObjectsQueue() {
        return receivedObjects;
    }

}
