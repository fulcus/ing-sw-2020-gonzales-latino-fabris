package it.polimi.ingsw.server;

import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.Message;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.TurnHandler;
import it.polimi.ingsw.server.model.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Represents a mock instance of the client server side.
 * Allows to send to the client through the network the messages and receive the client's answer when needed.
 */
public class ViewClient implements ClientViewObserver {


    private final Socket socket;   //a virtual view instance for each client
    private Player player;
    private final GameController gameController;
    private ObjectOutputStream output;
    private TurnHandler turnHandler;
    private boolean inGame;
    private final ClientInputReader input;


    public ViewClient(Socket socket, GameController gameController) {
        this.socket = socket;
        this.gameController = gameController;
        inGame = true;
        input = new ClientInputReader(this);

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HeartbeatServer heartbeatServer = new HeartbeatServer(this);

        new Thread(heartbeatServer).start();

        new Thread(input).start();

    }


    public Player getPlayer() {
        return player;
    }

    public Socket getSocket() {
        return socket;
    }

    public GameController getGameController() {
        return gameController;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }


    /**
     * Sends the message to assign the nickname of the player to the CLI.
     * @param player nickname of the player associated with this instance of CLI.
     */
    public void setPlayer(Player player) {
        this.player = player;   //used to assign player to class
    }


    /**
     * Sends the message to the player, to let him know the number of players for the game he's been assigned.
     * The player in this specific case did not choose the number of players for the game, but someone else did (the so called "creator").
     *
     * @param numberOfPlayers The number of players of the game the player has been assigned to.
     */
    public void joinGame(int numberOfPlayers) {
        sendMessage(new Message("joinGame", numberOfPlayers));
    }


    /**
     * Sends the message to let the player know he is the creator of a new game.
     */
    public void createGame() {
        sendMessage(new Message("createGame"));
    }


    /**
     * Sends the message to let the the user Initial Game Interface be shown to the player.
     */
    public void beginningView() {
        sendMessage(new Message("beginningView"));
    }


    /**
     * Sends the message to ask the first player that connects the number of players of the game.
     *
     * @return The number of players.
     */
    public int askNumberOfPlayers() {

        return (int) sendMessageWithReturn(new Message("askNumberOfPlayers"));
    }

    /**
     * Sends the message to ask the player to set his worker initial position.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    public int[] askInitialWorkerPosition(String workerSex) {

        return (int[]) sendMessageWithReturn(new Message("askInitialWorkerPosition", workerSex));
    }


    /**
     * Sends the message to let the player know the position he wrote for the initial worker position was wrong.
     */
    public void invalidInitialWorkerPosition() {
        sendMessage(new Message("invalidInitialWorkerPosition"));
    }


    /**
     * Sends the message to ask to the player the nickname for the game.
     *
     * @return The nickname chosen by the player.
     */
    public String askPlayerNickname() {
        return (String) sendMessageWithReturn(new Message("askPlayerNickname"));
    }

    /**
     * Lets the client know that the nickname entered is too long or empty.
     */
    public void nicknameFormatError() {
        sendMessage(new Message("nicknameFormatError"));
    }

    /**
     * Sends the message to notify the client that his nickname has been accepted.
     */
    public void notifyValidNick() {
        sendMessage(new Message("notifyValidNick"));
    }


    /**
     * Sends the message to notify the client that his color has been accepted.
     */
    public void notifyValidColor() {
        sendMessage(new Message("notifyValidColor"));
    }


    /**
     * Sends to the client the message to ask to the player the color for the game.
     * Only three colors are available: blue, white and beige.
     *
     * @return The color chosen by the player.
     */
    public String askPlayerColor() {
        return (String) sendMessageWithReturn(new Message("askPlayerColor"));
    }



    /**
     * Sends the message to the client to set and to store in the local interface memory the general info of other players.
     *
     * @param nickname Nickname of the player to register.
     * @param color    Color chosen by that specific player for the current game.
     */
    public void setOtherPlayersInfo(String nickname, String color) {
        sendMessage(new Message("setOtherPlayersInfo", nickname, color));
    }


    /**
     * Sends the message to ask to the player which God among the available ones wants to play with during the current game.
     *
     * @return The name of the chosen God.
     */
    public String askPlayerGod() {
        return (String) sendMessageWithReturn(new Message("askPlayerGod"));
    }


    /**
     * Sends to the player the message that the God he chose was not allowed and rejected by the server.
     */
    public void playerChoseInvalidGod() {
        sendMessage(new Message("playerChoseInvalidGod"));
    }


    /**
     * Sends a message to the challenger to let him know how many gods he still has to choose.
     *
     * @param numOfPlayers      The number of players of the current game.
     * @param alreadyChosenGods The number of gods that the challenger has already chosen for the game.
     * @return Another name of the God the challenger chooses for the current game.
     */
    public String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods) {
        return (String) sendMessageWithReturn(new Message("getGodFromChallenger", numOfPlayers, alreadyChosenGods));
    }

    /**
     * Sends a message to the challenger and asks to choose which player will be the starting one.
     *
     * @return The nickname of the starting player.
     */
    public String challengerChooseStartPlayer() {
        return (String) sendMessageWithReturn(new Message("challengerChooseStartPlayer"));
    }


    /**
     * Sends a message to let the challenger know that was an error occurred choosing the starting player.
     * The challenger must choose among the nicknames of the players registered in the current game.
     */
    public void invalidStartPlayer() {
        sendMessage(new Message("invalidStartPlayer"));
    }


    /**
     * Sends a message to let the player know that the chosen color was not available.
     */
    public void notAvailableColor() {
        sendMessage(new Message("notAvailableColor"));
    }


    /**
     * Sends a message to tell the player that the inserted nickname was not available.
     */
    public void notAvailableNickname() {
        sendMessage(new Message("notAvailableNickname"));
    }


    /**
     * Sends a message to ask to the player which one of his worker wants to play with during the current turn.
     *
     * @return The sex of the worker the player wants to play with.
     */
    public String askChosenWorker() {
        return (String) sendMessageWithReturn(new Message("askChosenWorker"));
    }


    /**
     * Sends a message to print the ERROR to the screen of the client.
     */
    public void printErrorScreen() {
        sendMessage(new Message("printErrorScreen"));
    }


    /**
     * Sends a message to show to screen that the player has won the game.
     */
    public boolean winningView() {
        return (boolean) sendMessageWithReturn(new Message("winningView"));
    }


    /**
     * Sends a message to let the player know he has lost the game because both of his workers cannot move.
     */
    public void unableToMoveLose() {
        sendMessage(new Message("unableToMoveLose"));
    }


    /**
     * Sends a message to let the player know he has lost the game because both of his workers cannot build.
     */
    public void unableToBuildLose() {
        sendMessage(new Message("unableToBuildLose"));
    }


    /**
     * In a 3 players game, this method notifies the other players that a player has lost the game.
     *
     * @param loserNickname The nickname of the player that has lost the game.
     */
    public void notifyPlayersOfLoss(String loserNickname) {
        sendMessage(new Message("notifyPlayersOfLoss", loserNickname));
    }


    /**
     * Sends a message to make a new print of an updated version of the Board.
     */
    public void printMap() {
        sendMessage(new Message("printMap"));
    }


    /**
     * Sends a message to update the cell of the board that has changed its contents.
     *
     * @param toUpdateCell The cell that needs to be updated.
     */
    @Override
    public void update(Cell toUpdateCell) {
        sendMessage(new Message("update", new CellClient(toUpdateCell)));
    }


    /**
     * Sends a message to show all the available gods of the game and their description.
     *
     * @param godsNameAndDescription The gods available for the game, the challenger will chose among this ones.
     */
    public void printAllGods(ArrayList<String> godsNameAndDescription) {
        sendMessage(new Message("printAllGods", godsNameAndDescription));
    }


    /**
     * Sends a message to let the player know the selected god does not exist in this game.
     */
    public void challengerError() {
        sendMessage(new Message("challengerError"));
    }


    /**
     * Sends a message to show all the Gods chosen by the challenger for the current game.
     *
     * @param chosenGods The list of the chosen gods.
     */
    public void printChosenGods(ArrayList<String> chosenGods) {
        sendMessage(new Message("printChosenGods", chosenGods));
    }


    /**
     * Sends a message to let the player know the selected worker cannot move.
     *
     * @param sex The sex of the selected worker
     */
    public void selectedWorkerCannotMove(String sex) {
        sendMessage(new Message("selectedWorkerCannotMove", sex));
    }


    /**
     * Sends a message to let the player know the selected worker cannot build.
     *
     * @param sex The sex of the selected worker.
     */
    public void selectedWorkerCannotBuild(String sex) {
        sendMessage(new Message("selectedWorkerCannotBuild", sex));
    }


    /**
     * Sends a message to ask the user to insert the position where he wants to build.
     *
     * @return The compass direction of the place where to build.
     */
    public String askBuildingDirection() {
        return (String) sendMessageWithReturn(new Message("askBuildingDirection"));
    }


    /**
     * Sends a message to ask to Atlas' owner to insert the position where he wants to build and what type of building.
     *
     * @return The compass direction of the place where to build.
     */
    public String[] askBuildingDirectionAtlas() {
        //todo probabilmente sbagliato
        return (String[]) sendMessageWithReturn(new Message("askBuildingDirectionAtlas"));
    }


    /**
     * Sends a message to ask to the user to insert the direction of his next movement.
     *
     * @return The compass direction of the movement.
     */
    public String askMovementDirection() {
        return (String) sendMessageWithReturn(new Message("askMovementDirection"));
    }


    /**
     * Sends a messsage to the user to get the input of the player to move again.
     *
     * @return The will of the player on keeping going moving his worker on the board.
     */
    public String askMoveAgain() {
        return (String) sendMessageWithReturn(new Message("askMoveAgain"));
    }


    /**
     * Sends a message to get the input of the player to move an enemy's worker.
     *
     * @return The will of the player to move an enemy's worker
     */
    public String askWantToMoveEnemy() {
        return (String) sendMessageWithReturn(new Message("askWantToMoveEnemy"));
    }


    /**
     * Sends a message to ask to move one worker's enemy.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The Worker to move selected by the player, null if there aren't enemies around.
     */
    public String askWorkerToMove(ArrayList<Worker> enemyWorkers, Worker myWorker) {

        ArrayList<WorkerClient> enemyWorkersClient = new ArrayList<>(enemyWorkers.size());

        for (Worker worker : enemyWorkers)
            enemyWorkersClient.add(new WorkerClient(worker));


        return (String) sendMessageWithReturn(new Message("askWorkerToMove", enemyWorkersClient, new WorkerClient(myWorker)));
    }


    /**
     * Sends a message to make the player aware that the worker can build under himself/herself.
     * This is allowed only when playing with Zeus.
     */
    public void printBuildUnderneath() {
        sendMessage(new Message("printBuildUnderneath"));
    }


    /**
     * Sends a message to ask to the player that holds Hephaestus as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHephaestus() {
        return (String) sendMessageWithReturn(new Message("askBuildAgainHephaestus"));
    }


    /**
     * Sends a message to ask to the player that holds Demeter as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainDemeter() {
        return (String) sendMessageWithReturn(new Message("askBuildAgainDemeter"));
    }


    /**
     * Sends a message to ask to the player that holds Hestia as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHestia() {
        return (String) sendMessageWithReturn(new Message("askBuildAgainHestia"));
    }


    /**
     * Sends a message to ask to the player that holds Prometheus as a God if he wants to build before moving.
     *
     * @return The will of the player to build before moving.
     */
    public String askBuildPrometheus() {
        return (String) sendMessageWithReturn(new Message("askBuildPrometheus"));
    }


    /**
     * Sends a message to point out the player cannot move in a certain position.
     */
    public void printMoveErrorScreen() {
        sendMessage(new Message("printMoveErrorScreen"));
    }


    /**
     * Sends a message to ask to the player if he still wants to move during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printMoveDecisionError() {
        return (String) sendMessageWithReturn(new Message("printMoveDecisionError"));
    }


    /**
     * Sends a message to ask to the player if he still wants to build during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printBuildDecisionError() {
        return (String) sendMessageWithReturn(new Message("printBuildDecisionError"));
    }


    /**
     * Sends a message to point out that a player is not allowed to build.
     */
    public void printBuildGeneralErrorScreen() {
        sendMessage(new Message("printBuildGeneralErrorScreen"));
    }


    /**
     * Sends a message to point out that a player is not allowed to build a block in a certain position.
     */
    public void printBuildBlockErrorScreen() {
        sendMessage(new Message("printBuildBlockErrorScreen"));
    }


    /**
     * Sends a message to point out that a player is not allowed to build again in a certain position.
     */
    public void printBuildInSamePositionScreen() {
        sendMessage(new Message("printBuildInSamePositionScreen"));
    }


    /**
     * Sends a message to let the player know that the challenger is choosing the gods for the game.
     *
     * @param challenger nickname of the challenger.
     */
    public void waitChallengerChooseGods(String challenger) {
        sendMessage(new Message("waitChallengerChooseGods", challenger));
    }


    /**
     * Sends a message to let the player know that another player is choosing his god.
     *
     * @param otherPlayer the player that is choosing his god.
     */
    public void waitOtherPlayerChooseGod(String otherPlayer) {
        sendMessage(new Message("waitOtherPlayerChooseGod", otherPlayer));
    }


    /**
     * Sends a message to let the player know the god chosen by another player.
     *
     * @param otherPlayer player who chose the god.
     * @param chosenGod   god chosen by the otherPlayer.
     */
    public void otherPlayerChoseGod(String otherPlayer, String chosenGod) {
        sendMessage(new Message("otherPlayerChoseGod", otherPlayer, chosenGod));
    }


    /**
     * Sends a message to let the player know that the challenger is choosing the start player.
     */
    public void waitChallengerStartPlayer() {
        sendMessage(new Message("waitChallengerStartPlayer"));
    }


    /**
     * Sends a message to let the the player know who is the start player.
     *
     * @param startPlayer The start player's nickname.
     */
    public void printStartPlayer(String startPlayer) {
        sendMessage(new Message("printStartPlayer", startPlayer));
    }


    /**
     * Sends a message to let the player know that another player is choosing the initial position for his workers.
     *
     * @param player The player who is performing the action.
     */
    public void otherPlayerSettingInitialWorkerPosition(String player) {
        sendMessage(new Message("otherPlayerSettingInitialWorkerPosition", player));
    }


    /**
     * Sends a message to let the player know that it's another player's turn.
     *
     * @param currentPlayer The nickname of the player that is playing his turn.
     */
    public void otherPlayerTurn(String currentPlayer) {
        sendMessage(new Message("otherPlayerTurn", currentPlayer));
    }


    /**
     * Sends a message to let the player know that he has lost, and who is the winner.
     *
     * @param winner nickname of the winner
     * @return Always returns true.
     */
    public boolean losingView(String winner) {
        return (boolean) sendMessageWithReturn(new Message("losingView", winner));
    }


    /**
     * Sends messages through the network and is supposed to get an answer sent by the client.
     *
     * @param message The message the server has to send to the client.
     * @return The received object from the client.
     */
    private Object sendMessageWithReturn(Message message) {

        Object receivedObject;

        try {
            //flush?
            output.writeObject(message);

            receivedObject = input.getObjectsQueue().take();

            return receivedObject;

        } catch (IOException e) {
            System.out.println("server has died while sending with return");
        } catch (ClassCastException e) {
            System.out.println("protocol violation");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //todo handle exception better
        return null;
    }


    /**
     * Writes a message to the server.
     *
     * @param message The message the player sends to the client during the game.
     */
    protected synchronized void sendMessage(Message message) {

        try {
            output.writeObject(message);
        } catch (IOException e) {
            System.out.println("server has died while sending");
        }
    }


    /**
     * Shows that the connection has been established.
     */
    public void connected() {
        System.out.println("Connected to " + socket.getInetAddress());
    }


    /**
     * Disconnects the client from the server.
     */
    public void killClient() {
        gameController.removeClientObserver(this);
        input.setKilled(true);
        sendMessage(new Message("shutdownClient"));
        inGame = false;
    }


    /**
     * Sends a message to let other players know that someone has disconnected from the game.
     *
     * @param disconnectedPlayer The nickname of the disconnected player.
     */
    public void notifyOtherPlayerDisconnection(String disconnectedPlayer) {
        sendMessage(new Message("notifyOtherPlayerDisconnection", disconnectedPlayer));
    }

}


