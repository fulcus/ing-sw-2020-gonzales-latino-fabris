package it.polimi.ingsw.client;

import it.polimi.ingsw.serializable.CellClient;
import it.polimi.ingsw.serializable.WorkerClient;

import java.util.ArrayList;
import java.util.InputMismatchException;


/**
 * Interface that defines the methods of the interfaces (CLI and GUI)
 * that need to be implemented to guarantee a correct game flow.
 */
public interface View {


    /**
     * Asks the IP of the server where the client wants to connect to.
     *
     * @return The IP of the server to connect to.
     */
    String getServerAddress();


    /**
     * Lets the player know the number of players for the game he's been assigned.
     * The player in this specific case did not choose the number of players for the game, but someone else did (the so called "creator").
     *
     * @param numberOfPlayers The number of players of the game the player has been assigned to.
     */
    void joinGame(int numberOfPlayers);


    /**
     * Lets the player know he is the creator of a new game.
     */
    void createGame();


    /**
     * Shows if the connection to the server was successful or not
     *
     * @param connected True if the connection was established, false otherwise.
     */
    void connectionOutcome(boolean connected);


    /**
     * Lets the client know that the nickname entered is too long or empty.
     */
    void nicknameFormatError();


    /**
     * Notifies the player that his nickname was accepted by the server.
     */
    void notifyValidNick();


    /**
     * Notifies the player that his color choice was accepted by the server.
     * Sets also the local info about the player (his nickname and color).
     */
    void notifyValidColor();


    /**
     * Displays that a player has been disconnected and reason.
     *
     * @param disconnectedPlayer The name of the disconnected player.
     */
    void notifyOtherPlayerDisconnection(String disconnectedPlayer);


    /**
     * This method displays to the user Initial Game Interface
     */
    void beginningView();


    /**
     * Asks to the creator of a game how many players will the game hold.
     *
     * @return The number of players decided by the creator player.
     */
    int askNumberOfPlayers();


    /**
     * This method asks the player to set his worker initial position.
     * It will be invoked both for the male worker and the female worker of every player.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    int[] askInitialWorkerPosition(String workerSex) throws InputMismatchException;



    /**
     * Allows to set and to store in the local cli memory the general settings info of other players.
     * Allows also to store into specific colored class attributes the info of the players,
     * so that a better representation when printing the game's board can be done.
     *
     * @param nickname Nickname of the player to register.
     * @param color    Color chosen by that specific player for the current game.
     */
    void setOtherPlayersInfo(String nickname, String color);


    /**
     * Lets the player know the position he wrote for the initial worker position was wrong.
     */
    void invalidInitialWorkerPosition();


    /**
     * Asks to the player the nickname for the game.
     *
     * @return The nickname chosen by the player.
     */
    String askPlayerNickname();


    /**
     * Asks to the player the color for the game.
     * Only three colors are available: blue, white and beige.
     *
     * @return The color chosen by the player.
     */
    String askPlayerColor(ArrayList<String> availableColors);


    /**
     * Asks to the player which God among the available ones wants to play with during the current game.
     *
     * @return The name of the chosen God.
     */
    String askPlayerGod();


    /**
     * Lets the player know that his God's choice was rejected by the server.
     */
    void playerChoseInvalidGod();


    /**
     * Lets the challenger know how many gods he still has to choose.
     * Then the challenger, if other gods need to be selected, chooses another god for the game.
     *
     * @param numOfPlayers      The number of players of the current game.
     * @param alreadyChosenGods The number of gods that the challenger has already chosen for the game.
     * @return Another name of the God the challenger chooses for the current game.
     */
    String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods);


    /**
     * Asks to the challenger which player will be the starting one.
     *
     * @return The nickname of the starting player.
     */
    String challengerChooseStartPlayer();


    /**
     * Lets the challenger know that was an error occurred choosing the starting player.
     * The challenger must choose among the nicknames of the players registered in the current game.
     */
    void invalidStartPlayer();


    /**
     * Lets the player know that the chosen color was not available,
     * maybe because another player had already chosen it before or
     * maybe because that was not a color defined by the game.
     */
    void notAvailableColor();


    /**
     * Tells the player that the inserted nickname was not available.
     * This error can occur when the length of the nickname is too long or when the same nick was already chosen by another player.
     */
    void notAvailableNickname();


    /**
     * Asks to the player which one of his worker wants to play with during the current turn.
     *
     * @return The sex of the worker the player wants to play with.
     */
    String askChosenWorker();


    /**
     * Allows to print the ERROR to the screen
     */
    void printErrorScreen();


    /**
     * Prints to screen that the player has won the game.
     */
    boolean winningView();


    /**
     * Lets the player know he has lost the game because both of his workers cannot move.
     */
    boolean unableToMoveLose();


    /**
     * Lets the player know he has lost the game because both of his workers cannot build.
     */
    void unableToBuildLose();


    /**
     * In a 3 players game, this method notifies the other players that a player has lost the game.
     *
     * @param loserNickname The nickname of the player that has lost the game.
     */
    void notifyPlayersOfLoss(String loserNickname);


    /**
     * This method prints an updated version of the Board, depending on the Class' parameter "mymap".
     */
    void printMap();


    /**
     * Updates the cell of the board that has changed its contents.
     *
     * @param toUpdateCell The cell that needs to be updated.
     */
    void update(CellClient toUpdateCell);


    /**
     * Prints all the available gods of the game and their description.
     *
     * @param godsNameAndDescription The gods available for the game, the challenger will chose among this ones.
     */
    void printAllGods(ArrayList<String> godsNameAndDescription);


    /**
     * Lets the player know the selected god does not exist in this game.
     */
    void challengerError();


    /**
     * Prints all the Gods chosen by the challenger for the current game.
     *
     * @param chosenGods The list of the chosen gods.
     */
    void printChosenGods(ArrayList<String> chosenGods);


    /**
     * Lets the player know the selected worker cannot move.
     *
     * @param sex The sex of the selected worker
     */
    void selectedWorkerCannotMove(String sex);


    /**
     * Asks to the player if he prefers the CLI or the GUI.
     *
     * @return The type of interface chosen by the player.
     */
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
     * Asks to the player that holds Hephaestus as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    String askBuildAgainHephaestus();


    /**
     * Asks to the player that holds Demeter as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    String askBuildAgainDemeter();


    /**
     * Asks to the player that holds Hestia as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    String askBuildAgainHestia();


    /**
     * Asks to the player that holds Prometheus as a God if he wants to build before moving.
     *
     * @return The will of the player to build before moving.
     */
    String askBuildPrometheus();


    /**
     * Points out the player cannot move in a certain position.
     */
    void printMoveErrorScreen();


    /**
     * Warns user of invalid build action: he cannot build a dome underneath himself.
     * This error can only occur if player uses Zeus' power.
     */
    void printCannotBuildDomeUnderneath();


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
