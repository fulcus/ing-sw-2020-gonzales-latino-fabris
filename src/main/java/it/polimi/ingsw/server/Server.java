package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Server of the game.
 */
public class Server implements Runnable {

    public final static int SOCKET_PORT = 7777;

    private final Lobby lobby;
    private int clientsConnected;

    public Server() {
        lobby = new Lobby();
        clientsConnected = 0;
    }

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server());
        serverThread.start();
    }


    @Override
    public void run() {

        ServerSocket socket;

        try {
            socket = new ServerSocket(SOCKET_PORT);

        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        System.out.println("*** Server started ***");

        while (true) {

            try {

                //accept everyone, and put them in the first available game
                //if all games are full, let him create a new one
                Socket client = socket.accept();

                clientsConnected++;
                System.out.println("client " + clientsConnected + " connected");


                new Thread(()->lobby.allocateClient(client)).start();

            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}
