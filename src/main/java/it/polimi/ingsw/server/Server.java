package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements Runnable {
    public final static int SOCKET_PORT = 7777;
    private static GameController gameController;
    private static int numberOfClients; //senza multipartita
    private ExecutorService executorService;

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server());
        serverThread.start();
    }


    @Override
    public void run() {

        ServerSocket socket;
        gameController = new GameController();
        numberOfClients = 0;
        executorService = Executors.newCachedThreadPool();

        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {

            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }


        while (true) {

            try {

                //player created asynchronously (might be not created yet at this point)
                if (gameController.getGame() != null && numberOfClients == gameController.getGame().getNumberOfPlayers())
                    break;

                Socket client = socket.accept();
                numberOfClients++;
                System.out.println("client " + numberOfClients + " connected");
                createClientThread(client);

            } catch (IOException e) {
                System.out.println("connection dropped");
            }

        }

        Game game = gameController.getGame();



        //asynchronous
        //if both chose nick && color
        gameController.getTurnHandler().setUpTurns();
        gameController.getTurnHandler().startTurnFlow();


    }


    //called by server right after accept
    private void createClientThread(Socket joiningClientSocket) {
        //new thread?
        ClientView newClient = new ClientView(joiningClientSocket, gameController);
        newClient.connected();


        if (gameController.getGame() == null)
            gameController.setUpGame(newClient);


        //Thread thread = new Thread(newClient, "server_" + joiningClientSocket.getInetAddress());
        //thread.start();
        executorService.execute(newClient);

    }


}
