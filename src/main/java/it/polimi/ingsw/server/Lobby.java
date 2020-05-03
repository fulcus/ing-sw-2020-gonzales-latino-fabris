package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.TurnHandler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Lobby {
    private final ArrayList<GameController> games;
    private static GameController availableGame;
    private int connectedToAvailableGame;

    public Lobby() {
        games = new ArrayList<>();
        availableGame = null;
    }


    public void allocatePlayer(Socket clientSocket) {


        //no existing games available (full or first client to connect to server)
        if (availableGame == null) {

            connectedToAvailableGame = 0;

            availableGame = new GameController();
            games.add(availableGame);


            ViewClient newClient = new ViewClient(clientSocket, availableGame);

            createGame(newClient);
        }


        //there is an available game to join (not full)
        else {

            availableGame = games.get(games.size() - 1);

            ViewClient newClient = new ViewClient(clientSocket, availableGame);

            ExecutorService gameExecutor = availableGame.getExecutorPlayerAdder();

            gameExecutor.execute(() -> availableGame.addPlayer(newClient));
            connectedToAvailableGame++;

            //waits for all players to finish adding their player ie setting nickname and color
            //sets availableGame null
            if(connectedToAvailableGame == availableGame.getGame().getNumberOfPlayers()) {

                gameExecutor.shutdown();

                boolean terminated;

                try {

                    do {
                        terminated = gameExecutor.awaitTermination(20, TimeUnit.SECONDS);
                        //after x seconds print: waiting for other players to join
                    } while (!terminated);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                TurnHandler gameTurnHandler = availableGame.getTurnHandler();
                Thread turnThread = new Thread(gameTurnHandler);
                turnThread.start();

                availableGame = null;

            }

        }

    }


    //called by server right after accept
    private void createGame(ViewClient newClient) {

        availableGame.setUpGame(newClient);

        ExecutorService executor = availableGame.getExecutorPlayerAdder();

        executor.execute(() -> availableGame.addPlayer(newClient));
        connectedToAvailableGame++;

    }

}
