package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.BoardClient;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiManager implements View {

    protected static volatile AtomicInteger numberOfPlayers;
    protected static volatile String nickname1;
    protected static volatile String nickname2;
    protected static volatile String nickname3;
    protected static volatile String color1;
    protected static volatile String color2;
    protected static volatile String color3;
    protected static volatile AtomicInteger playersConnected;

    protected static final SynchronousQueue<Object> queue = new SynchronousQueue<>();
    private String playerNickname; //to be assigned when setPlayer of ViewClient is deserialized
    private String challenger;
    private final BoardClient board;
    private int numberOfPlayers;

    protected static final FXMLLoader connectLoader = new FXMLLoader(GuiManager.class.getResource("/scenes/connect.fxml"));;
    private final FXMLLoader numberOfPlayersLoader;
    private final FXMLLoader nicknameLoader;
    private final FXMLLoader colorLoader;
    protected static final FXMLLoader lobbyLoader = new FXMLLoader(GuiManager.class.getResource("/scenes/lobby.fxml"));;
    private final FXMLLoader startPlayerLoader;
    private final FXMLLoader boardLoader;

    private final ConnectController connectController;
    private final NumberOfPlayersController numberOfPlayersController;
    private final NicknameController nicknameController;
    private final ColorController colorController;
    private final LobbyController lobbyController;
    private final StartPlayerController startPlayerController;
    private final BoardController boardController;



    public GuiManager() {
        numberOfPlayersLoader = new FXMLLoader(getClass().getResource("/scenes/choose-num-of-players.fxml"));
        nicknameLoader = new FXMLLoader(getClass().getResource("/scenes/choose-nickname.fxml"));
        colorLoader = new FXMLLoader(getClass().getResource("/scenes/choose-color.fxml"));
        startPlayerLoader = new FXMLLoader(getClass().getResource("/scenes/start-player.fxml"));
        boardLoader = new FXMLLoader(getClass().getResource("/scenes/board.fxml"));


        connectController = connectLoader.getController();
        numberOfPlayersController = numberOfPlayersLoader.getController();
        nicknameController = nicknameLoader.getController();
        colorController = colorLoader.getController();
        lobbyController = lobbyLoader.getController();
        startPlayerController = startPlayerLoader.getController();
        boardController = boardLoader.getController();

        board = new BoardClient();
        playersConnected = new AtomicInteger(0);

        nickname1 = null;
        nickname2 = null;
        nickname3 = null;
        color1 = null;
        color2 = null;
        color3 = null;
    }

    /**
     * Assigns the nickname of the player to the View
     *
     * @param nickname nickname of the player associated with this instance of Cli
     */
    public void setPlayer(String nickname) {
        playerNickname = nickname;
    }

    public String getServerAddress() {

        String IP = null;
        try {
            IP = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("guimanager received: " + IP);   //debug
        return IP;
    }

    public void connectionOutcome(boolean connected) {
        System.out.println("connectionOutcome");

        if (!connected) {
            System.out.println("connection error");
            connectController.displayError();
        }
    }

    /**
     * Displays that the player has been disconnected and reason.
     */
    public void notifyOtherPlayerDisconnection() {
        //TODO POPUP
    }

    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {
        //do Nothing here
    }

    //only called if joining game and NOT creating
    public void joinGame(int numberOfPlayers) {
        System.out.println("joinGame guiManager"); //debug

        //sets number of players attribute
        GuiManager.numberOfPlayers = new AtomicInteger(numberOfPlayers);

        //change scene
        try {

            Parent root = nicknameLoader.load();
            Platform.runLater(() -> Gui.getStage().setScene(new Scene(root)));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void createGame() {
        System.out.println("createGame guiManager"); //debug

        //change scene
        try {

            Parent root = numberOfPlayersLoader.load();
            Platform.runLater(() -> Gui.getStage().setScene(new Scene(root)));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * @return The number of players.
     */
    public int askNumberOfPlayers() {

        String numString;
        int numInt = 0;
        try {
            numString = (String) queue.take();
            numInt = Integer.parseInt(numString);

            //change scene
            Parent root = nicknameLoader.load();
            Platform.runLater(() -> Gui.getStage().setScene(new Scene(root)));
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        this.numberOfPlayers = numInt;

        return numInt;
    }

    /**
     * This method asks the player to set his worker initial position.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    public int[] askInitialWorkerPosition(String workerSex) throws InputMismatchException {

        int[] initialWorkerPosition = new int[2];

        boardController.setCellRequested(true);
        boardController.askInitialWorkerPosition(workerSex);//TODO RUN LATER

        try {
            initialWorkerPosition[0] = (Integer) queue.take();//row
            initialWorkerPosition[1] = (Integer) queue.take();//col

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return initialWorkerPosition;

    }

    public void printChoosingColor(String choosingPlayer) {

    }

    public void printChoosingNickname() {

    }

    /**
     * Saves information about player that just joined the game.
     *
     * @param nickname nickname of other player
     * @param color    color of other player
     */
    public void setOtherPlayersInfo(String nickname, String color) {
        setPlayerInfo(nickname, color);
    }


    private void setPlayerInfo(String nickname, String color) {

        if (nickname1 == null) {
            nickname1 = nickname;
            color1 = color;

        } else if (nickname2 == null) {
            nickname2 = nickname;
            color2 = color;
        } else if (nickname3 == null) {
            nickname3 = nickname;
            color3 = color;
        }

        //if client is in lobby there's at least 1 player connected (him)
        //RENDER
        if (playersConnected.get() > 0)
            Platform.runLater(()->lobbyController.showPlayer(nickname, color));
        //otherwise it has already been saved and will be rendered in initialize
    }


    public void invalidInitialWorkerPosition() {
        //TODO POPUP
    }

    public String askPlayerNickname() {
        String nickname = null;

        try {

            nickname = (String) queue.take();
            System.out.println("nick taken");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("guimanager received: " + nickname);
        setPlayer(nickname);
        return nickname;
    }


    public void notifyValidNick() {
        //change scene
        Parent root = null;

        try {
            root = colorLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent finalRoot = root;
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(finalRoot)));
    }


    public String askPlayerColor() {

        String color = null;

        try {

            color = (String) queue.take();
            System.out.println("color taken");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("guimanager received: " + color);
        return color;
    }


    public void notifyValidColor() {
        //change scene
        Parent root = null;

        System.out.println("the color is valid");

        try {
            root = lobbyLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent finalRoot = root;
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(finalRoot)));
    }

    /**
     * @return The name of the chosen God.
     */
    public String askPlayerGod() {
        return null;
    }

    public void playerChoseInvalidGod() {
    }

    public String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods) {
        return null;
    }

    public String challengerChooseStartPlayer() {

        String challenger = null;
        try {
            challenger = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("guimanager received: " + challenger);   //debug

        //change scene
        try {

            Parent root = boardLoader.load();
            Platform.runLater(() -> Gui.getStage().setScene(new Scene(root)));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return challenger;
    }

    public void invalidStartPlayer() {

    }

    public void notAvailableColor() {

    }

    public void notAvailableNickname() {

    }

    public String askChosenWorker() {
        return null;
    }

    /**
     * Allows to print the ERROR to the screen
     */
    public void printErrorScreen() {

    }

    /**
     * Prints to screen that one of the player has won the game
     */
    public boolean winningView() {
        return false;
    }

    public void unableToMoveLose() {

    }

    public void unableToBuildLose() {

    }

    public void notifyPlayersOfLoss(String loserNickname) {

    }

    /**
     * This method prints an updated version of the Board, depending on the Class' parameter "mymap".
     */
    public void printMap() {
        boardController.printMap();
    }

    public void update(CellClient toUpdateCell) {
        board.update(toUpdateCell);
    }

    public void printAllGods(ArrayList<String> godsNameAndDescription) {

    }

    public void challengerError() {

    }

    public void printChosenGods(ArrayList<String> chosenGods) {

    }

    public void selectedWorkerCannotMove(String sex) {

    }

    public void selectedWorkerCannotBuild(String sex) {

    }

    public String askTypeofView() {
        return null;
    }

    /**
     * This method asks the user to insert the position where he wants to build.
     *
     * @return The compass direction of the place where to build.
     */
    public String askBuildingDirection() {

        int[] chosenCell = new int[2];
        String selectedBuildingDirection = null;

        boardController.setCellRequested(true);
        boardController.askBuildingDirection();//TODO RUN LATER


        try {
            chosenCell[0] = (Integer) queue.take();//column
            chosenCell[1] = (Integer) queue.take();//row

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //CONVERT INTO COMPASS, HERE OR IN BOARD CONTROLLER

        return selectedBuildingDirection;
    }

    /**
     * This method asks to Atlas' owner to insert the position where he wants to build and what type of building.
     *
     * @return The compass direction of the place where to build.
     */
    public String[] askBuildingDirectionAtlas() {
        return new String[0];
    }

    /**
     * This method asks the user to insert the direction of his next movement.
     *
     * @return The compass direction of the movement.
     */
    public String askMovementDirection() {
        return null;
    }

    /**
     * Allows to get the input of the player to move again.
     *
     * @return The will of the player on keeping going moving his worker on the board.
     */
    public String askMoveAgain() {
        return null;
    }

    /**
     * Allows to get the input of the player to jump to an higher level.
     *
     * @return The will of the player to reach an higher level.
     */
    public String askWantToMoveUp() {
        return null;
    }

    /**
     * Allows to get the input of the player to move an enemy's worker.
     *
     * @return The will of the player to move an enemy's worker
     */
    public String askWantToMoveEnemy() {
        return null;
    }

    /**
     * Allows to move one worker's enemy.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The Worker to move selected by the player.
     */
    public String askWorkerToMove(ArrayList<WorkerClient> enemyWorkers, WorkerClient myWorker) {
        return null;
    }

    /**
     * Says that the worker can build under himself/herself.
     * This is allowed only when playing with Zeus.
     */
    public void printBuildUnderneath() {

    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHephaestus() {
        return null;
    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainDemeter() {
        return null;
    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHestia() {
        return null;
    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build before moving.
     */
    public String askBuildPrometheus() {
        return null;
    }

    /**
     * Points out the player cannot move in a certain position.
     */
    public void printMoveErrorScreen() {

    }

    /**
     * Asks the player if he still wants to move during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printMoveDecisionError() {
        return null;
    }

    /**
     * Asks the player if he still wants to build during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printBuildDecisionError() {
        return null;
    }

    /**
     * Points out a player is not allowed to build.
     */
    public void printBuildGeneralErrorScreen() {

    }

    /**
     * Points out a player is not allowed to build a block in a certain position.
     */
    public void printBuildBlockErrorScreen() {

    }

    /**
     * Points out that a player is not allowed to build again in a certain position.
     */
    public void printBuildInSamePositionScreen() {

    }

    /**
     * Lets player know that the challenger is choosing the gods for the game.
     *
     * @param challenger nickname of the challenger
     */
    public void waitChallengerChooseGods(String challenger) {

    }

    /**
     * Lets player know that another player is choosing his god
     *
     * @param otherPlayer the player that is choosing his god
     */
    public void waitOtherPlayerChooseGod(String otherPlayer) {

    }

    /**
     * Lets player know the god chosen by another player
     *
     * @param otherPlayer player who chose the god
     * @param chosenGod   god chosen by the otherPlayer
     */
    public void otherPlayerChoseGod(String otherPlayer, String chosenGod) {

    }

    /**
     * Lets player know that the challenger is choosing the start player
     */
    public void waitChallengerStartPlayer() {

    }

    /**
     * Lets player know who is the start player
     *
     * @param startPlayer start player nickname
     */
    public void printStartPlayer(String startPlayer) {

    }

    /**
     * Lets  player know that another player is choosing the initial position for his workers
     *
     * @param player player who is performing the action
     */
    public void otherPlayerSettingInitialWorkerPosition(String player) {

    }

    /**
     * Lets player know that it's another player's turn
     *
     * @param currentPlayer nickname of the player that is playing his turn
     */
    public void otherPlayerTurn(String currentPlayer) {

    }

    /**
     * Lets player know that he has lost, and who is the winner.
     *
     * @param winner nickname of the winner
     */
    public boolean losingView(String winner) {
        return false;
    }


}
