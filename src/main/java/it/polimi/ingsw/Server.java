package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.view.VirtualClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public final static int SOCKET_PORT = 7777;
    private static GameController gameController;
    private int maxNumberOfClients;

    public Server() {
        gameController = new GameController();

    }


    public static void main(String[] args) {

        int connectedClients = 0;
        ServerSocket socket;

        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {

            System.out.println("cannot open server socket");
            System.exit(1);
            return;

        }


        while (true) {

            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                joinGame(client);

                VirtualClient clientHandler = new VirtualClient(client);


                //separate clientHandler class vs virtualClient
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }

        }
    }


    //called by server right after accept
    private static void joinGame(Socket joiningClientSocket) {

        //new thread?
        VirtualClient newClient = new VirtualClient(joiningClientSocket);

        if (gameController.getGame() == null)
            gameController.setUpGame(newClient);
        else
            gameController.addPlayer(newClient);


    }


}
