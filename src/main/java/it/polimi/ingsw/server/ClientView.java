package it.polimi.ingsw.server;

import it.polimi.ingsw.serializableObjects.ClientCell;
import it.polimi.ingsw.serializableObjects.Message;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.TurnHandler;
import it.polimi.ingsw.server.model.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Represents the interface of each client with the server.
 */
public class ClientView implements ClientViewObserver, Runnable {

    private final Socket socket;   //a virtual view instance for each client
    private Player player;
    private final GameController gameController;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private TurnHandler turnHandler;
    private boolean inGame;


    public ClientView(Socket socket, GameController gameController) {
        this.socket = socket;
        this.gameController = gameController;
        inGame = true;

        try{
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){}
    }


    public Player getPlayer() {
        return player;
    }


    /**
     * Associates the ClientView to a player.
     *
     * @param player Is the instance of the player associated to the client.
     */
    public void setPlayer(Player player) {
        this.player = player;   //used to assign player to class
        //send playerNickname to be assigned as attribute in CLIView
        String playerNickname = player.getNickname();
        sendMessage(new Message("setPlayer", playerNickname));
    }


    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {
        sendMessage(new Message("beginningView"));
    }


    /**
     * Asks the first player that connects the number of players of the game.
     *
     * @return The number of players.
     */
    public int askNumberOfPlayers() {
        return (int) sendMessageWithReturn(new Message("askNumberOfPlayers"));
    }


    /**
     * This method asks the player to set his worker initial position.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    public int[] askInitialWorkerPosition(String workerSex) {
        //todo probabilmente sbagliato
        return (int[]) sendMessageWithReturn(new Message("askInitialWorkerPosition", workerSex));
    }


    /**
     * The message sent by the method describes itself.
     */
    public void invalidInitialWorkerPosition() {
        sendMessage(new Message("invalidInitialWorkerPosition"));
    }


    /**
     * The message sent by the method describes itself.
     * @return The nickname of the player.
     */
    public String askPlayerNickname() {
        return (String) sendMessageWithReturn(new Message("askPlayerNickname"));
    }


    /**
     * The message sent by the method describes itself.
     * @return The color to choose.
     */
    public String askPlayerColor() {
        return (String) sendMessageWithReturn(new Message("askPlayerColor"));
    }


    /**
     * The method asks the God the player wants to play with.
     * @return The name of the chosen God.
     */
    public String askPlayerGod() {
        return (String) sendMessageWithReturn(new Message("askPlayerGod"));
    }


    /**
     * The name of the method describes itself
     */
    public void playerChoseInvalidGod() {
        sendMessage(new Message("playerChoseInvalidGod"));
    }


    /**
     * The name of the method describes itself.
     * @param numOfPlayers How many players play are connected to play the game.
     * @param alreadyChosenGods How many Gods are already chosen.
     * @return
     */
    public String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods) {
        return (String) sendMessageWithReturn(new Message("getGodFromChallenger", numOfPlayers, alreadyChosenGods));
    }

    /**
     * The name of the method describes itself.
     * @return The player that will start the game.
     */
    public String challengerChooseStartPlayer() {
        return (String) sendMessageWithReturn(new Message("challengerChooseStartPlayer"));
    }

    /**
     * The name of the method describes itself.
     */
    public void invalidStartPlayer() {
        sendMessage(new Message("invalidStartPlayer"));
    }

    /**
     * The name of the method describes itself.
     */
    public void notAvailableColor() {
        sendMessage(new Message("notAvailableColor"));
    }


    /**
     * The name of the method describes itself.
     */
    public void notAvailableNickname() {
        sendMessage(new Message("notAvailableNickname"));
    }


    /**
     * The name of the method describes itself.
     * @return The chosen worker.
     */
    public String askChosenWorker() {
        return (String) sendMessageWithReturn(new Message("askChosenWorker"));
    }


    /**
     * Allows to print the ERROR to the screen
     */
    public void printErrorScreen() {
        sendMessage(new Message("printErrorScreen"));
    }


    /**
     * Prints to screen that one of the player has won the game
     */
    public void winningView(String winnerNickname) {
        sendMessage(new Message("winningView", winnerNickname));
    }


    /**
     * The name of the method describes itself.
     */
    public void unableToMoveLose() {
        sendMessage(new Message("unableToMoveLose"));
    }


    /**
     * The name of the method describes itself.
     */
    public void unableToBuildLose() {
        sendMessage(new Message("unableToBuildLose"));
    }


    /**
     * This method prints an updated version of the Board, depending on the Class' parameter "mymap".
     */
    public void printMap() {
        sendMessage(new Message("printMap"));
    }


    /**
     * Allows to set the new version of the cell that has been changed during the last action.
     * @param toUpdateCell The cell that has to be updated.
     */
    @Override
    public void update(Cell toUpdateCell) {
        sendMessage(new Message("update", new ClientCell(toUpdateCell)));
    }


    /**
     * The name of the method describes itself.
     * @param godsNameAndDescription The name pf the method describes itself.
     */
    public void printAllGods(ArrayList<String> godsNameAndDescription) {
        sendMessage(new Message("printAllGods", godsNameAndDescription));
    }


    /**
     * The name of the method describes itself.
     */
    public void challengerError() {
        sendMessage(new Message("challengerError"));
    }


    /**
     * The name of the method describes itself.
     * @param chosenGods The gods that have been chosen for the game.
     */
    public void printChosenGods(ArrayList<String> chosenGods) {
        sendMessage(new Message("printChosenGods", chosenGods));
    }


    /**
     * The name of the method describes itself.
     * @param sex Allows to distinguish which one of the worker has been selected.
     */
    public void selectedWorkerCannotMove(String sex) {
        sendMessage(new Message("selectedWorkerCannotMove", sex));
    }


    /**
     * The name of the method describes itself.
     * @param sex Allows to distinguish which one of the worker has been selected.
     */
    public void selectedWorkerCannotBuild(String sex) {
        sendMessage(new Message("selectedWorkerCannotBuild", sex));
    }


    /**
     * This method asks the user to insert the position where he wants to build.
     *
     * @return The compass direction of the place where to build.
     */
    public String askBuildingDirection() {
        return (String) sendMessageWithReturn(new Message("askBuildingDirection"));
    }


    /**
     * This method asks to Atlas' owner to insert the position where he wants to build and what type of building.
     *
     * @return The compass direction of the place where to build.
     */
    public String[] askBuildingDirectionAtlas() {
        //todo probabilmente sbagliato
        return (String[]) sendMessageWithReturn(new Message("askBuildingDirectionAtlas"));
    }


    /**
     * This method asks the user to insert the direction of his next movement.
     *
     * @return The compass direction of the movement.
     */
    public String askMovementDirection() {
        return (String) sendMessageWithReturn(new Message("askMovementDirection"));
    }


    /**
     * Allows to get the input of the player to move again.
     *
     * @return The will of the player on keeping going moving his worker on the board.
     */
    public String askMoveAgain() {
        return (String) sendMessageWithReturn(new Message("askMoveAgain"));
    }


    /**
     * Allows to get the input of the player to jump to an higher level.
     *
     * @return The will of the player to reach an higher level.
     */
    public String askWantToMoveUp() {
        return (String) sendMessageWithReturn(new Message("askWantToMoveUp"));
    }


    /**
     * Allows to get the input of the player to move an enemy's worker.
     *
     * @return The will of the player to move an enemy's worker
     */
    public String askWantToMoveEnemy() {
        return (String) sendMessageWithReturn(new Message("askWantToMoveEnemy"));
    }


    /**
     * Allows to move one worker's enemy.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The Worker to move selected by the player.
     */
    public String askWorkerToMove(ArrayList<Worker> enemyWorkers, Worker myWorker) {

        ArrayList<WorkerClient> enemyWorkersClient = new ArrayList<WorkerClient>(enemyWorkers.size());

        for (Worker worker : enemyWorkers)
            enemyWorkersClient.add(new WorkerClient(worker));


        return (String) sendMessageWithReturn(new Message("askWorkerToMove", enemyWorkersClient, new WorkerClient(myWorker)));

    }


    /**
     * Says that the worker can build under himself/herself.
     * This is allowed only when playing with Zeus.
     */
    public void printBuildUnderneath() {
        sendMessage(new Message("printBuildUnderneath"));
    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHephaestus() {
        return (String) sendMessageWithReturn(new Message("askBuildAgainHephaestus"));
    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainDemeter() {
        return (String) sendMessageWithReturn(new Message("askBuildAgainDemeter"));
    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHestia() {
        return (String) sendMessageWithReturn(new Message("askBuildAgainHestia"));
    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build before moving.
     */
    public String askBuildPrometheus() {
        return (String) sendMessageWithReturn(new Message("askBuildPrometheus"));
    }


    /**
     * Points out the player cannot move in a certain position.
     */
    public void printMoveErrorScreen() {
        sendMessage(new Message("printMoveErrorScreen"));
    }


    /**
     * Asks the player if he still wants to move during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printMoveDecisionError() {
        return (String) sendMessageWithReturn(new Message("printMoveDecisionError"));
    }


    /**
     * Asks the player if he still wants to build during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printBuildDecisionError() {
        return (String) sendMessageWithReturn(new Message("printBuildDecisionError"));
    }


    /**
     * Points out a player is not allowed to build.
     */
    public void printBuildGeneralErrorScreen() {
        sendMessage(new Message("printBuildGeneralErrorScreen"));
    }


    /**
     * Points out a player is not allowed to build a block in a certain position.
     */
    public void printBuildBlockErrorScreen() {
        sendMessage(new Message("printBuildBlockErrorScreen"));
    }


    /**
     * Points out that a player is not allowed to build again in a certain position.
     */
    public void printBuildInSamePositionScreen() {
        sendMessage(new Message("printBuildInSamePositionScreen"));
    }


    //always cast return of this method
    private Object sendMessageWithReturn(Message message) {
        try {
            //flush?
            output.writeObject(message);
            return input.readObject();
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("protocol violation");
        }

        //todo handle exception better
        return null;
    }


    /**
     * Writes a message to the server.
     * @param message The message the player sends to the server during the game.
     */
    private void sendMessage(Message message) {

        try {
            //flush?
            output.writeObject(message);
        } catch (IOException e) {
            System.out.println("server has died");
        }
    }


    /**
     * Allows to print the fact the connection has been established.
     */
    public void connected() {
        System.out.println("Connected to " + socket.getInetAddress());
    }


    /**
     * Disconnects the client from the server.
     */
    public void killClient() {

        sendMessage(new Message("shutdownClient"));
        inGame = false;

    }


    @Override
    public void run() {
        try {
            startClient();
        } catch (IOException e) {
            System.out.println("client " + socket.getInetAddress() + " connection dropped");
        }
    }


    /**
     * Lets the client play his turn.
     *
     * @throws IOException thrown by Socket.close()
     */
    private void startClient() throws IOException {

        turnHandler = gameController.getTurnHandler();
        gameController.addPlayer(this);

        while (inGame) {
            //waits until woken up by turnFlow
            do {
                try {
                    wait();
                } catch (InterruptedException e) {
                }

            } while (turnHandler.getCurrentPlayer() != player);

            Worker chosenWorker = turnHandler.chooseWorker();
            turnHandler.turn(chosenWorker);

        }

        socket.close();
    }


}


