package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.TurnHandler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents the lobby, where clients can join existing games or create new ones.
 */
public class Lobby {

    private final ArrayList<GameController> games;
    private GameController availableGame;
    private int connectedToAvailableGame;

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

        if (availableGame == null) {
            //CREATE
            //no existing games available (all full or first client to connect to server)

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
            joinGame(newClient);
        }

    }


    private void createGame(ViewClient newClient) {

        newClient.createGame();

        availableGame.setUpGame(newClient);

        ExecutorService executor = availableGame.getExecutorPlayerAdder();
        executor.execute(() -> availableGame.addPlayer(newClient));

        connectedToAvailableGame++;

    }


    private void joinGame(ViewClient newClient) {

        newClient.joinGame(availableGame.getGame().getNumberOfPlayers());   //send message to client

        //send client nickname and color of all players that are already in
        sendOtherPlayersInfo(newClient);

        ExecutorService gameExecutor = availableGame.getExecutorPlayerAdder();
        gameExecutor.execute(() -> availableGame.addPlayer(newClient));

        connectedToAvailableGame++;

        //waits for all players to finish adding their player ie setting nickname and color
        //sets availableGame null
        if (connectedToAvailableGame == availableGame.getGame().getNumberOfPlayers()) {

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
            new Thread(gameTurnHandler).start();

            availableGame = null;
        }
    }

    private void sendOtherPlayersInfo(ViewClient newClient) {

        for(ViewClient otherClient : availableGame.getGameClients()) {
            if(otherClient != newClient) {  //always true bc newClient isn't in gameClients yet

                String otherClientNickname = otherClient.getPlayer().getNickname();
                String otherClientColor = otherClient.getPlayer().getColor().name();

                newClient.setOtherPlayersInfo(otherClientNickname,otherClientColor);
            }
        }
    }
}
