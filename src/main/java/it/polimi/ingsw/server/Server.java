package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Server implements Runnable {
    public final static int SOCKET_PORT = 7777;
    private static GameController gameController;
    private ExecutorService executorService;

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server());
        serverThread.start();
    }


    @Override
    public void run() {

        ServerSocket socket;
        gameController = new GameController();
        //senza multipartita
        int numberOfClients = 0;
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

                createClient(client);

            } catch (IOException e) {
                System.out.println("connection dropped");
            }

        }


        //waits for all players to finish adding their player ie setting nickname and color

        executorService.shutdown();

        boolean terminated;
        try {

            do {
                terminated = executorService.awaitTermination(20, TimeUnit.SECONDS);
                //after x seconds print: waiting for other players to join
            } while (!terminated);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gameController.getTurnHandler().setUpTurns();
        gameController.getTurnHandler().startTurnFlow();

    }


    //called by server right after accept
    private void createClient(Socket joiningClientSocket) {
        //new thread?
        ViewClient newClient = new ViewClient(joiningClientSocket, gameController);
        newClient.connected();

        //cannot accept other clients before writing "start"
        newClient.beginningView();


        if (gameController.getGame() == null)
            gameController.setUpGame(newClient);

        executorService.execute(() -> gameController.addPlayer(newClient));

    }


}
