package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.WorkerClient;

import java.util.ArrayList;
import java.util.InputMismatchException;

public interface View {

    /**
     * Assigns the nickname of the player to the View
     *
     * @param nickname nickname of the player associated with this instance of Cli
     */
    //called by clientView
    void setPlayer(String nickname);

    String getServerAddress();

    void joinGame(int numberOfPlayers);

    void createGame();

    void connectionOutcome(boolean connected);

    void notifyValidNick();

    void notifyValidColor();

    void waitToBeAssigned();

    /**
     * Displays that the player has been disconnected and reason.
     */
    void notifyOtherPlayerDisconnection();


    /**
     * This method displays to the user Initial Game Interface
     */
    void beginningView();


    /**
     * @return The number of players.
     */
    int askNumberOfPlayers();


    /**
     * This method asks the player to set his worker initial position.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    int[] askInitialWorkerPosition(String workerSex) throws InputMismatchException;


    void printChoosingColor(String choosingPlayer);


    void printChoosingNickname();

    void setOtherPlayersInfo(String nickname, String color);

    void invalidInitialWorkerPosition();


    String askPlayerNickname();


    String askPlayerColor();


    /**
     * @return The name of the chosen God.
     */
    String askPlayerGod();


    void playerChoseInvalidGod();


    String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods);


    String challengerChooseStartPlayer();


    void invalidStartPlayer();


    void notAvailableColor();


    void notAvailableNickname();


    String askChosenWorker();


    /**
     * Allows to print the ERROR to the screen
     */
    void printErrorScreen();


    /**
     * Prints to screen that one of the player has won the game
     */
    boolean winningView();


    void unableToMoveLose();


    void unableToBuildLose();


    void notifyPlayersOfLoss(String loserNickname);


    /**
     * This method prints an updated version of the Board, depending on the Class' parameter "mymap".
     */
    void printMap();


    void update(CellClient toUpdateCell);


    void printAllGods(ArrayList<String> godsNameAndDescription);


    void challengerError();


    void printChosenGods(ArrayList<String> chosenGods);


    void selectedWorkerCannotMove(String sex);


    void selectedWorkerCannotBuild(String sex);


    String askTypeofView();


    /**
     * This method asks the user to insert the position where he wants to build.
     *
     * @return The compass direction of the place where to build.
     */
    String askBuildingDirection();


    /**
     * This method asks to Atlas' owner to insert the position where he wants to build and what type of building.
     *
     * @return The compass direction of the place where to build.
     */
    String[] askBuildingDirectionAtlas();


    /**
     * This method asks the user to insert the direction of his next movement.
     *
     * @return The compass direction of the movement.
     */
    String askMovementDirection();


    /**
     * Allows to get the input of the player to move again.
     *
     * @return The will of the player on keeping going moving his worker on the board.
     */
    String askMoveAgain();


    /**
     * Allows to get the input of the player to move an enemy's worker.
     *
     * @return The will of the player to move an enemy's worker
     */
    String askWantToMoveEnemy();


    /**
     * Allows to move one worker's enemy.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The Worker to move selected by the player.
     */
    String askWorkerToMove(ArrayList<WorkerClient> enemyWorkers, WorkerClient myWorker);


    /**
     * Says that the worker can build under himself/herself.
     * This is allowed only when playing with Zeus.
     */
    void printBuildUnderneath();


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    String askBuildAgainHephaestus();


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    String askBuildAgainDemeter();


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    String askBuildAgainHestia();


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build before moving.
     */
    String askBuildPrometheus();


    /**
     * Points out the player cannot move in a certain position.
     */
    void printMoveErrorScreen();


    /**
     * Asks the player if he still wants to move during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    String printMoveDecisionError();


    /**
     * Asks the player if he still wants to build during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    String printBuildDecisionError();


    /**
     * Points out a player is not allowed to build.
     */
    void printBuildGeneralErrorScreen();


    /**
     * Points out a player is not allowed to build a block in a certain position.
     */
    void printBuildBlockErrorScreen();


    /**
     * Points out that a player is not allowed to build again in a certain position.
     */
    void printBuildInSamePositionScreen();


    /**
     * Lets player know that the challenger is choosing the gods for the game.
     *
     * @param challenger nickname of the challenger
     */
    void waitChallengerChooseGods(String challenger);


    /**
     * Lets player know that another player is choosing his god
     *
     * @param otherPlayer the player that is choosing his god
     */
    void waitOtherPlayerChooseGod(String otherPlayer);


    /**
     * Lets player know the god chosen by another player
     *
     * @param otherPlayer player who chose the god
     * @param chosenGod   god chosen by the otherPlayer
     */
    void otherPlayerChoseGod(String otherPlayer, String chosenGod);


    /**
     * Lets player know that the challenger is choosing the start player
     */
    void waitChallengerStartPlayer();


    /**
     * Lets player know who is the start player
     *
     * @param startPlayer start player nickname
     */
    void printStartPlayer(String startPlayer);


    /**
     * Lets  player know that another player is choosing the initial position for his workers
     *
     * @param player player who is performing the action
     */
    void otherPlayerSettingInitialWorkerPosition(String player);


    /**
     * Lets player know that it's another player's turn
     *
     * @param currentPlayer nickname of the player that is playing his turn
     */
    void otherPlayerTurn(String currentPlayer);


    /**
     * Lets player know that he has lost, and who is the winner.
     *
     * @param winner nickname of the winner
     */
    boolean losingView(String winner);

}
