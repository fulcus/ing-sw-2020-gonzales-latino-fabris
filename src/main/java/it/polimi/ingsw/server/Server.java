package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public final static int SOCKET_PORT = 7777;
    private static GameController gameController;
    private static int numberOfClients; //senza multipartita


    public static void main(String[] args) {

        ServerSocket socket;
        gameController = new GameController();
        numberOfClients = 0;

        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {

            System.out.println("cannot open server socket");
            System.exit(1);
            return;

        }


        while (true) {

            try {

                if (gameController.getGame() != null && numberOfClients == gameController.getGame().getNumberOfPlayers())
                    break;

                Socket client = socket.accept();
                numberOfClients++;
                createClientThread(client);

            } catch (IOException e) {
                System.out.println("connection dropped");
            }

        }

        Game game = gameController.getGame();

        if (game.getPlayers().size() == game.getNumberOfPlayers()) {
            gameController.getTurnHandler().setUpTurns();
            gameController.getTurnHandler().startTurnFlow();
        }

    }


    //called by server right after accept
    private static void createClientThread(Socket joiningClientSocket) {
        //new thread?
        ClientView newClient = new ClientView(joiningClientSocket, gameController);
        newClient.connected();


        if (gameController.getGame() == null)
            gameController.setUpGame(newClient);


        Thread thread = new Thread(newClient, "server_" + joiningClientSocket.getInetAddress());
        thread.start();

    }


}
