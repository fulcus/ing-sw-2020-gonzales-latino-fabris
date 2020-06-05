package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.TurnHandler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents the lobby, where clients can join existing games or create new ones.
 */
public class Lobby {

    private final ArrayList<GameController> games;
    private GameController availableGame;
    private int connectedToAvailableGame;
    Timer timer;

    public Lobby() {
        games = new ArrayList<>();
        availableGame = null;
    }

    /**
     * Allocates to a game, or creates a new one if all games are full.
     *
     * @param clientSocket client to allocate to a game.
     */
    public void allocateClient(Socket clientSocket) {

        //CREATE
        //no existing games available (all full or first client to connect to server)
        if (availableGame == null) {

            connectedToAvailableGame = 0;
            availableGame = new GameController();
            games.add(availableGame);

            ViewClient newClient = new ViewClient(clientSocket, availableGame);
            createGame(newClient);

        } else {
            //JOIN
            //there is an available game to join (not full)

            availableGame = games.get(games.size() - 1);

            ViewClient newClient = new ViewClient(clientSocket, availableGame);

            boolean printOnce = false;

            do {

                if (!printOnce) {
                    newClient.waitCreatorChooseNumOfPlayers();
                    printOnce = true;
                }

            } while (!availableGame.getAccessible());

            joinGame(newClient);
        }

    }



    private void createGame(ViewClient newClient) {

        connectedToAvailableGame++;

        GameController newGame = availableGame;

        newClient.createGame();

        newGame.setUpGame(newClient);

        ExecutorService executor = newGame.getExecutorPlayerAdder();
        executor.execute(() -> newGame.addPlayer(newClient));

    }


    private void joinGame(ViewClient newClient) {

        //send message to client
        newClient.joinGame(availableGame.getGame().getNumberOfPlayers());

        //send client nickname and color of all players that are already in
        sendOtherPlayersInfo(newClient);

        GameController currentGame = availableGame;

        ExecutorService gameExecutor = currentGame.getExecutorPlayerAdder();
        gameExecutor.execute(() -> currentGame.addPlayer(newClient));

        //send client nickname and color of all players that are already in

        connectedToAvailableGame++;
        availableGame = null;

        //waits for all players to finish adding their player ie setting nickname and color
        //sets availableGame null
        if (connectedToAvailableGame == currentGame.getGame().getNumberOfPlayers()) {

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

            TurnHandler gameTurnHandler = currentGame.getTurnHandler();
            new Thread(gameTurnHandler).start();


        }
    }

    private void sendOtherPlayersInfo(ViewClient newClient) {

        for (ViewClient otherClient : availableGame.getGameClients()) {

            //first condition always true bc newClient isn't in gameClients yet
            if (otherClient != newClient
                    && otherClient.getPlayer() != null
                    && otherClient.getPlayer().getColor() != null) {

                String otherClientNickname = otherClient.getPlayer().getNickname();
                String otherClientColor = otherClient.getPlayer().getColor().name();

                newClient.setOtherPlayersInfo(otherClientNickname, otherClientColor);

            }
        }
    }
}
