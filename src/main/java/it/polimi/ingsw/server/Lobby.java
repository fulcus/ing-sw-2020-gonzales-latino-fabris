package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;

import java.net.Socket;
import java.util.ArrayList;


/**
 * Lets any newly connected clients join an existing game or create a new one.
 */
public class Lobby {

    private final ArrayList<GameController> games;

    public Lobby() {
        games = new ArrayList<>();
    }

    /**
     * Allocates client to a game, or creates a new one if all games are full.
     *
     * @param clientSocket client to allocate to a game.
     */
    public void allocateClient(Socket clientSocket) {

        System.out.println("Connected to " + clientSocket.getInetAddress());

        //this attribute is set true if client found existing game to join
        boolean availableEmptySpot = false;

        GameController availableGame = null;

        synchronized (this) {
            //search first empty spot in games
            for (GameController game : games) {
                if (!game.isEnded() && !game.isFull()) {
                    availableGame = game;
                    availableEmptySpot = true;
                    game.incrementClients();
                    break;
                }
            }
        }

        //JOIN
        if (availableEmptySpot) {

            VirtualView newClient = new VirtualView(clientSocket, availableGame);
            availableGame.join(newClient);

        } else {
            //CREATE
            GameController newGame = new GameController();
            VirtualView newClient = new VirtualView(clientSocket, newGame);
            newGame.create(newClient);
            games.add(newGame);

        }
    }

}
