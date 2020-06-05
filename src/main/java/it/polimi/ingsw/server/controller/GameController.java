package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.ViewClient;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.controller.god.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Controls the flow of the setup of the game.
 */
public class GameController {

    private Game game;
    private TurnHandler turnHandler;
    private GodController godController;
    private final ArrayList<God> godsDeck;
    private final ExecutorService executorPlayerAdder;
    private final ArrayList<ViewClient> gameClients;
    private final Object nicknameLock;
    private final Object colorLock;
    private volatile boolean accessible;
    private volatile boolean full;
    private int clientsConnected;


    public GameController() {
        game = null;
        turnHandler = null;
        godsDeck = new ArrayList<>(14);
        executorPlayerAdder = Executors.newCachedThreadPool();
        gameClients = new ArrayList<>();
        nicknameLock = new Object();
        colorLock = new Object();
        accessible = false;
        clientsConnected = 1; //counting creator
        full = false;
    }


    /**
     * The first client that finds that no game is available then creates a new one.
     * So the client sets up the new game and is been added to it.
     *
     * @param newClient The client that creates the new game and sets it up.
     */
    public void create(ViewClient newClient) {

        //send message to creator
        newClient.createGame();

        setUpGame(newClient);

        executorPlayerAdder.execute(() -> addPlayer(newClient));

    }


    /**
     * The joiner client is added to a game that is already been created.
     * @param newClient The client that has just joined the game and needs to be added to it.
     */
    public synchronized void join(ViewClient newClient) {

        //send message to client
        newClient.joinGame(game.getNumberOfPlayers());

        //send client nickname and color of all players that are already in
        sendOtherPlayersInfo(newClient);


        executorPlayerAdder.execute(() -> addPlayer(newClient));

        //send client nickname and color of all players that are already in

        clientsConnected++;

        //waits for all players to finish adding their player ie setting nickname and color
        //sets availableGame null
        if (clientsConnected == game.getNumberOfPlayers()) {

            full = true;

            executorPlayerAdder.shutdown();

            boolean terminated;

            try {
                do {
                    terminated = executorPlayerAdder.awaitTermination(20, TimeUnit.SECONDS);
                    //after x seconds print: waiting for other players to join
                } while (!terminated);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(turnHandler).start();

        }
    }


    /**
     * Sends client nickname and color of all players that are already registered.
     * @param newClient The client that has just joined and needs to get the other player's information.
     */
    private void sendOtherPlayersInfo(ViewClient newClient) {

        for (ViewClient otherClient : gameClients) {

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


    /**
     * Sets up game and starts the logic flow.
     */
    public synchronized void setUpGame(ViewClient firstClient) {

        godController = new GodController(this);
        createDeckGods();

        int numOfPlayers = firstClient.askNumberOfPlayers();

        game = new Game(numOfPlayers);
        accessible = true;

        turnHandler = new TurnHandler(game, this);

    }


    public boolean getAccessible() {
        return accessible;
    }


    /**
     * Adds a player to the game, setting his attributes of nickname and color.
     * @param client client to add.
     */
    public void addPlayer(ViewClient client) {

        //prints client connected in server
        client.connected();
        //cannot accept other clients before writing "start"
        client.beginningView();

        gameClients.add(client);

        setUpObserverView(client);

        setPlayerNickname(client);
        setPlayerColor(client);

        //send player nickname and color to all other clients
        String clientNickname = client.getPlayer().getNickname();
        String clientColor = client.getPlayer().getColor().name();

        for (ViewClient otherClient : gameClients) {
            if (!otherClient.equals(client))
                otherClient.setOtherPlayersInfo(clientNickname, clientColor);
        }

    }


    /**
     * The client is registered to observe the behaviour of the cells of the board.
     * @param client Client that is registered to observe.
     */
    private void setUpObserverView(ViewClient client) {

        for (int i = 0; i < Board.SIDE; i++) {
            for (int j = 0; j < Board.SIDE; j++) {

                game.getBoard().findCell(i, j).register(client);
            }
        }

    }


    /**
     * Removes client from observing the cells of the map.
     * @param client The client that is removed from the list of the observers.
     */
    public void removeClientObserver(ViewClient client) {

        for (int i = 0; i < Board.SIDE; i++) {
            for (int j = 0; j < Board.SIDE; j++) {

                game.getBoard().findCell(i, j).remove(client);
            }
        }

    }


    /**
     * Lets the player choose his nickname.
     *
     * @param client view of the player.
     */
    private void setPlayerNickname(ViewClient client) {

        for (ViewClient otherClient : gameClients) {
            if (!otherClient.equals(client))
                otherClient.printChoosingNickname();
        }

        while (true) {

            String chosenNickname = client.askPlayerNickname();

            synchronized (nicknameLock) {
                if (checkNicknameValidity(chosenNickname, client, game)) {
                    return;
                }
            }

            client.notAvailableNickname();
        }
    }


    /**
     * Checks if the nickname chosen by the player is valid.
     * A nickname is valid if no one has already chosen it and if it is a String longer than 0 but shorter than 9 characters.
     * @param chosenNickname The nickname chosen by the player.
     * @param client The client associated to the chosen nickname
     * @param game The game where the player needs to be added.
     * @return True if the nickname was valid, false otherwise.
     */
    private boolean checkNicknameValidity(String chosenNickname, ViewClient client, Game game) {

        if (nicknameIsAvailable(chosenNickname) && chosenNickname.length() > 0 && chosenNickname.length() < 9) {
            Player newPlayer = game.addPlayer(chosenNickname, client);
            client.setPlayer(newPlayer);
            client.notifyValidNick();
            return true;
        }

        return false;
    }


    /**
     * Lets the player choose his color.
     *
     * @param client view of the player.
     */
    private void setPlayerColor(ViewClient client) {

        for (ViewClient otherClient : gameClients) {
            if (!otherClient.equals(client))
                otherClient.printChoosingColor(client.getPlayer().getNickname());
        }

        while (true) {

            String chosenColor = client.askPlayerColor();

            //edited
            synchronized (colorLock) {
                if (checkColorValidity(chosenColor, client))
                    return;
            }

            client.notAvailableColor();
        }

    }


    /**
     * Checks if the nickname chosen by the player is valid.
     * A color is valid if no one has already chosen it and if it is one of the available colors of the game: blue, white, beige.
     * @param chosenColor The color chosen by the player.
     * @param client The client associated to the player.
     * @return True if the color was valid, false otherwise.
     */
    private boolean checkColorValidity(String chosenColor, ViewClient client) {

        if (colorIsAvailable(chosenColor) && colorIsValid(chosenColor)) {
            client.notifyValidColor();
            client.getPlayer().setColor(Color.stringToColor(chosenColor));
            return true;
        }

        return false;
    }


    /**
     * Helper method to check color validity.
     * A color is valid if no one has already chosen it and if it is one of the available colors of the game: blue, white, beige.
     * @param chosenColor The color chosen by the player.
     * @return True if the color is valid, false otherwise.
     */
    protected boolean colorIsValid(String chosenColor) {
        return chosenColor.equals(Color.BLUE.name())
                || chosenColor.equals(Color.BEIGE.name())
                || chosenColor.equals(Color.WHITE.name());
    }


    /**
     * Helper method to check nickname validity.
     * @param chosenNickname The nickname chosen by the player.
     * @return True if the nickname is valid, false otherwise.
     */
    protected boolean nicknameIsAvailable(String chosenNickname) {

        for (Player player : game.getPlayers()) {

            if (chosenNickname.equals(player.getNickname()))
                return false;
        }

        return true;
    }


    /**
     * Helper method to check color validity.
     * A color is valid if no one has already chosen it and if it is one of the available colors of the game: blue, white, beige.
     * @param chosenColor The color chosen by the player.
     * @return True if the color is available, false otherwise.
     */
    protected boolean colorIsAvailable(String chosenColor) {

        for (Player player : game.getPlayers()) {

            if (player.getColor() != null
                    && chosenColor.equals(player.getColor().toString()))
                return false;
        }

        return true;
    }


    /**
     * Creates the deck where we can find all the God cards.
     */
    private void createDeckGods() {
        godsDeck.add(new Apollo(godController));
        godsDeck.add(new Artemis(godController));
        godsDeck.add(new Athena(godController));
        godsDeck.add(new Atlas(godController));
        godsDeck.add(new Charon(godController));
        godsDeck.add(new Demeter(godController));
        godsDeck.add(new Hephaestus(godController));
        godsDeck.add(new Hera(godController));
        godsDeck.add(new Hestia(godController));
        godsDeck.add(new Minotaur(godController));
        godsDeck.add(new Pan(godController));
        godsDeck.add(new Prometheus(godController));
        godsDeck.add(new Triton(godController));
        godsDeck.add(new Zeus(godController));
    }


    /**
     * The winner of the game is notified of his success, while the others are notified of their lose.
     * The turn handler stops his flow.
     * @param winner The player that has won the game.
     */
    public void winGame(Player winner) {
        //winningView and losingView are blocking since they must return boolean (although unused)
        ViewClient winnerClient = winner.getClient();

        //print "you have won" in winner view
        winnerClient.winningView();
        winnerClient.killClient();
        //TODO AVOID TO DISCONNECT OTHERS
        //print "you have lost" in loser views
        for (Player player : game.getPlayers()) {

            if (player != winner) {
                player.getClient().losingView(winner.getNickname());
                player.getClient().killClient();
            }
        }

        turnHandler.stopTurnFlow();
    }


    public ArrayList<God> getGodsDeck() {
        return godsDeck;
    }


    public Game getGame() {
        return game;
    }


    public GodController getGodController() {
        return godController;
    }


    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    /*
    public synchronized int getPlayersConnected() {
        return playersConnected;
    }*/


    public ExecutorService getExecutorPlayerAdder() {
        return executorPlayerAdder;
    }


    /**
     * Allows to notify the disconnection to the game.
     * @param disconnectedPlayer The player that has disconnected from the game.
     */
    public void handleGameDisconnection(String disconnectedPlayer) {

        //if disconnection is due to a player disconnection
        for (ViewClient connectedClient : gameClients) {

            if (connectedClient.isInGame()) {
                connectedClient.notifyOtherPlayerDisconnection(disconnectedPlayer);
                connectedClient.killClient();//Sends shut down and sets inGame=false;
            }

        }
    }


    /**
     * Notifies players someone lost the game.
     * @param loserNickname The nickname of the player who lost the game.
     */
    public void notifyPlayersOfLoss(String loserNickname) {

        for (Player player : game.getPlayers()) {
            player.getClient().notifyPlayersOfLoss(loserNickname);
        }
    }


    public ArrayList<ViewClient> getGameClients() {
        return gameClients;
    }


    public boolean isFull() {
        return full;
    }


    public void setFull(boolean full) {
        this.full = full;
    }


}


