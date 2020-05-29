package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.BoardClient;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GuiManager implements View {

    protected static AtomicReference<String> nickname1;
    protected static AtomicReference<String> nickname2;
    protected static AtomicReference<String> nickname3;
    protected static AtomicReference<String> color1;
    protected static AtomicReference<String> color2;
    protected static AtomicReference<String> color3;
    protected static AtomicReference<String> god1;
    protected static AtomicReference<String> god2;
    protected static AtomicReference<String> god3;

    protected static final AtomicReference<BoardClient> boardClient = new AtomicReference<>(new BoardClient());


    private final AtomicInteger xWorker = new AtomicInteger();
    private final AtomicInteger yWorker = new AtomicInteger();

    protected static final AtomicInteger numberOfPlayers = new AtomicInteger(0); //overwritten by joinGame or asknumberofplayers
    protected static final AtomicInteger playersConnected = new AtomicInteger(0);
    protected static final AtomicBoolean isInLobby = new AtomicBoolean(false);

    protected static final SynchronousQueue<Object> queue = new SynchronousQueue<>();
    protected static String playerNickname;
    private String playerColor;

    //roots of scenes
    protected static Parent numberOfPlayersRoot;
    protected static Parent nicknameRoot;
    protected static Parent colorRoot;
    protected static Parent chooseGodRoot;
    protected static Parent startPlayerRoot;
    protected static Parent boardRoot;
    protected static Parent lobbyRoot;
    protected static Parent connectRoot;
    protected static Parent disconnectionRoot;

    //controllers of fxmls
    private ConnectController connectController;
    private NumberOfPlayersController numberOfPlayersController;
    private NicknameController nicknameController;
    private ColorController colorController;
    private LobbyController lobbyController;
    protected static ChooseGodController chooseGodController;
    private StartPlayerController startPlayerController;
    private BoardController boardController;
    private DisconnectionController disconnectionController;


    public GuiManager() {

        nickname1 = null;
        nickname2 = null;
        nickname3 = null;
        color1 = null;
        color2 = null;
        color3 = null;
        god1 = null;
        god2 = null;
        god3 = null;

        //wait for graphics to initialize
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        createControllers();
    }


    private void createControllers() {

        FXMLLoader connectLoader = new FXMLLoader(getClass().getResource("/scenes/connect.fxml"));
        FXMLLoader numberOfPlayersLoader = new FXMLLoader(getClass().getResource("/scenes/choose-num-of-players.fxml"));
        FXMLLoader nicknameLoader = new FXMLLoader(getClass().getResource("/scenes/choose-nickname.fxml"));
        FXMLLoader colorLoader = new FXMLLoader(getClass().getResource("/scenes/choose-color.fxml"));
        FXMLLoader lobbyLoader = new FXMLLoader(getClass().getResource("/scenes/lobby.fxml"));
        FXMLLoader chooseGodLoader = new FXMLLoader(getClass().getResource("/scenes/choose-god.fxml"));
        FXMLLoader startPlayerLoader = new FXMLLoader(getClass().getResource("/scenes/start-player.fxml"));
        FXMLLoader boardLoader = new FXMLLoader(getClass().getResource("/scenes/board.fxml"));
        FXMLLoader disconnectionLoader = new FXMLLoader(getClass().getResource("/scenes/disconnection.fxml"));


        try {
            connectRoot = connectLoader.load();
            numberOfPlayersRoot = numberOfPlayersLoader.load();
            nicknameRoot = nicknameLoader.load();
            colorRoot = colorLoader.load();
            chooseGodRoot = chooseGodLoader.load();
            startPlayerRoot = startPlayerLoader.load();
            boardRoot = boardLoader.load();
            lobbyRoot = lobbyLoader.load();
            disconnectionRoot = disconnectionLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

/*
        ImageCursor cursor = new ImageCursor(new Image("/labels/cursor.png"));
        connectRoot.setCursor(cursor);
        numberOfPlayersRoot.setCursor(cursor);
        nicknameRoot.setCursor(cursor);
        colorRoot.setCursor(cursor);
        chooseGodRoot.setCursor(cursor);
        startPlayerRoot.setCursor(cursor);
        boardRoot.setCursor(cursor);
        lobbyRoot.setCursor(cursor);
*/

        connectController = connectLoader.getController();
        numberOfPlayersController = numberOfPlayersLoader.getController();
        nicknameController = nicknameLoader.getController();
        colorController = colorLoader.getController();
        lobbyController = lobbyLoader.getController();
        chooseGodController = chooseGodLoader.getController();
        startPlayerController = startPlayerLoader.getController();
        boardController = boardLoader.getController();
        disconnectionController = disconnectionLoader.getController();

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

        //System.out.println("guimanager received: " + IP);   //debug
        return IP;
    }

    public void connectionOutcome(boolean connected) {

        if (!connected) {
            System.out.println("connection error");
            connectController.displayError();
        }
    }

    /**
     * Displays that the player has been disconnected and reason.
     */
    public void notifyOtherPlayerDisconnection() {

        Platform.runLater(() -> {

            Scene currentScene = Gui.getStage().getScene();

            int height = (int) currentScene.getHeight();
            int width = (int) currentScene.getWidth();

            WritableImage wi = new WritableImage(width, height);
            Image image = currentScene.snapshot(wi);


            // disconnectionRoot.getChildren().add(new ImageView(image));
            Scene disconnectionScene = new Scene(disconnectionRoot, width, height);


            Gui.getStage().setScene(disconnectionScene);

            disconnectionController.setBackground(image);


        });


    }


    public void waitToBeAssigned() {
        //do nothing here, the method is specifically useful for the cli.
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
        GuiManager.numberOfPlayers.set(numberOfPlayers);

        //change scene
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(nicknameRoot)));

    }

    public void createGame() {

        //change scene
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(numberOfPlayersRoot)));

    }

    /**
     * @return The number of players.
     */
    public int askNumberOfPlayers() {

        int numInt = 0;
        try {
            String numString = (String) queue.take();
            numInt = Integer.parseInt(numString);

            //change scene
            Platform.runLater(() -> Gui.getStage().setScene(new Scene(nicknameRoot)));

        } catch (InterruptedException | NumberFormatException ex) {
            numInt = askNumberOfPlayers();
        }

        numberOfPlayers.set(numInt);

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
        Platform.runLater(() -> {
            boardController.setCellRequested(true);
            boardController.printToMainText("Place your " + workerSex.toLowerCase() + " worker!");
        });

        try {
            initialWorkerPosition[0] = (int) queue.take();  //row
            initialWorkerPosition[1] = (int) queue.take();  //col

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return initialWorkerPosition;
    }

    public void printChoosingColor(String choosingPlayer) {
        //can do nothing
    }

    public void printChoosingNickname() {
        //can do nothing
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


    private void setMyInfo(String nickname, String color) {
        nickname1 = new AtomicReference<>(nickname);
        color1 = new AtomicReference<>(color);

        playersConnected.addAndGet(1);

    }

    private void setPlayerInfo(String nickname, String color) {

        if (nickname2 == null) {
            nickname2 = new AtomicReference<>(nickname);
            color2 = new AtomicReference<>(color);
        } else if (nickname3 == null) {
            nickname3 = new AtomicReference<>(nickname);
            color3 = new AtomicReference<>(color);
        }


        playersConnected.addAndGet(1);

        //if client is in lobby RENDER
        if (isInLobby.get())
            Platform.runLater(() -> lobbyController.showPlayer(nickname, color));
        //otherwise it has already been saved and will be rendered in initialize
    }

    private void setPlayerGod(String nickname, String god) {

        if (nickname.equals(nickname2.get())) {
            god2 = new AtomicReference<>(god);
        } else if (nickname.equals(nickname3.get())) {
            god3 = new AtomicReference<>(god);
        }
    }

    public void invalidInitialWorkerPosition() {
        //TODO POPUP
    }

    public String askPlayerNickname() {
        String nickname = null;

        //nicknameController.removeErrorNickFromScreen();

        nicknameController.removeWaitingOtherFromScreen();

        nicknameController.enableNicknameText();

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
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(colorRoot)));

    }


    public String askPlayerColor() {

        String color = null;

        colorController.removeWaitingOtherFromScreen();

        //colorController.removeErrorColorFromScreen();

        colorController.enableButtons();


        try {

            color = (String) queue.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playerColor = color;

        System.out.println("guimanager received: " + color);
        return color;
    }


    public void notifyValidColor() {
        //adding this players info to "database"
        setMyInfo(playerNickname, playerColor);

        //change scene
        Platform.runLater(() -> {
            lobbyController.init();
            Gui.getStage().setScene(new Scene(lobbyRoot));
        });

    }

    /**
     * @return The name of the chosen God.
     */
    public String askPlayerGod() {

        String chosenGod = null;

        Platform.runLater(() -> chooseGodController.askPlayerGod());


        try {
            chosenGod = (String) queue.take();
            god1 = new AtomicReference<>(chosenGod);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Selected " + chosenGod);

        return chosenGod;
    }

    public void playerChoseInvalidGod() {

        Platform.runLater(() -> chooseGodController.playerChoseInvalidGod());

    }

    public String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods) {

        String chosenGod = null;

        Platform.runLater(() -> chooseGodController.getGodFromChallenger(numOfPlayers, alreadyChosenGods));

        try {
            chosenGod = (String) queue.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Selected " + chosenGod);

        return chosenGod;

    }

    public String challengerChooseStartPlayer() {

        String startPlayer = null;

        Platform.runLater(() -> {
            Gui.getStage().setScene(new Scene(startPlayerRoot));
            startPlayerController.init();
        });

        try {
            startPlayer = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //assumes start player is always correct
        Platform.runLater(() -> {
            boardController.init();
            Gui.getStage().setScene(new Scene(boardRoot));
        });


        return startPlayer;
    }

    public void invalidStartPlayer() {

    }


    public void notAvailableColor() {

        colorController.displayErrorColor();
    }


    public void notAvailableNickname() {

        nicknameController.displayErrorNick();
    }

    public String askChosenWorker() {

        int[] chosenCell = new int[2];

        while (true) {
            Platform.runLater(() -> {
                boardController.setCellRequested(true);
                boardController.printToMainText("Select one of your workers!");
            });

            try {
                chosenCell[0] = (Integer) queue.take(); //row
                chosenCell[1] = (Integer) queue.take(); //column

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //check if cell contains worker
            WorkerClient workerClient = boardClient.get().findCell(chosenCell[0], chosenCell[1]).getWorkerClient();

            if (workerClient != null && workerClient.getWorkerColor().toLowerCase().equals(color1.get().toLowerCase())) {
                //selectedWorker.set(workerClient);

                //todo always enters if ?
                xWorker.set(chosenCell[0]);
                yWorker.set(chosenCell[1]);

                return workerClient.getWorkerSex();
            }

            System.out.println("cell doesn't contain worker");

            Platform.runLater(() ->
                    boardController.printToMainText("You have to select one of your workers!\nTry Again"));

        }


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

        try {
            AnchorPane win = FXMLLoader.load(getClass().getResource("/scenes/win.fxml"));
            Platform.runLater(()->((GridPane)boardRoot).getChildren().add(win));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
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
        Platform.runLater(() -> boardController.printMap());
    }

    public void update(CellClient toUpdateCell) {
        boardController.update(toUpdateCell);
    }

    public void printAllGods(ArrayList<String> godsNameAndDescription) {


    }

    public void challengerError() {

    }

    public void printChosenGods(ArrayList<String> chosenGods) {

        Platform.runLater(() -> chooseGodController.printChosenGods(chosenGods));

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
        return build();
    }

    private String build() {
        int[] chosenCell = new int[2];


        Platform.runLater(() -> {
            boardController.setCellRequested(true);
            boardController.printToMainText("Build!");
        });


        try {
            chosenCell[0] = (Integer) queue.take(); //row
            chosenCell[1] = (Integer) queue.take(); //column

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("building cell selected: " + chosenCell[0] + "," + chosenCell[1]);

        String result = boardClient.get()
                .workerCellRelativePositionCompass(xWorker.get(), yWorker.get(), chosenCell[0], chosenCell[1]);


        System.out.println("converted in: " + result);

        return result;
    }

    /**
     * This method asks to Atlas' owner to insert the position where he wants to build and what type of building.
     *
     * @return The compass direction of the place where to build.
     */
    public String[] askBuildingDirectionAtlas() {

        String[] selectedBuildingDirection = new String[2];

        selectedBuildingDirection[0] = build(); //choose building direction (N,S,W,E..)
        selectedBuildingDirection[1] = askToUseGodPower(); //building type (B or D)

        return selectedBuildingDirection;

    }


    /**
     * This method asks the user to insert the direction of his next movement.
     *
     * @return The compass direction of the movement.
     */
    public String askMovementDirection() {
        return move();
    }

    private String move() {

        int[] chosenCell = new int[2];

        Platform.runLater(() -> {
            boardController.setCellRequested(true);
            boardController.printToMainText("Move your worker!");
        });

        try {
            chosenCell[0] = (Integer) queue.take();//column
            chosenCell[1] = (Integer) queue.take();//row

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String result = boardClient.get()
                .workerCellRelativePositionCompass(xWorker.get(), yWorker.get(), chosenCell[0], chosenCell[1]);

        System.out.println("converted in: " + result);

        xWorker.set(chosenCell[0]);
        yWorker.set(chosenCell[1]);

        return result;
    }

    /**
     * Allows to get the input of the player to move again.
     *
     * @return The will of the player on keeping going moving his worker on the board.
     */
    public String askMoveAgain() {
        return askToUseGodPower();
    }

    private String askToUseGodPower() {
        Platform.runLater(() -> {
            boardController.setGodPowerRequested(true);
            boardController.printToMainText("Choose if you want to use your god power");
        });

        String answer = null;

        try {
            answer = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return answer;
    }

    /**
     * Allows to get the input of the player to jump to an higher level.
     *
     * @return The will of the player to reach an higher level.
     */
    public String askWantToMoveUp() {
        return null;                //todo delete everywhere
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
        return askToUseGodPower();
    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainDemeter() {
        return askToUseGodPower();
    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHestia() {
        return askToUseGodPower();
    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build before moving.
     */
    public String askBuildPrometheus() {
        return askToUseGodPower();
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

        Platform.runLater(() -> chooseGodController.waitChallengerChooseGods(challenger));

    }

    /**
     * Lets player know that another player is choosing his god
     *
     * @param otherPlayer the player that is choosing his god
     */
    public void waitOtherPlayerChooseGod(String otherPlayer) {

        Platform.runLater(() -> chooseGodController.waitOtherPlayerChooseGod(otherPlayer));

    }

    /**
     * Lets player know the god chosen by another player
     *
     * @param otherPlayer player who chose the god
     * @param chosenGod   god chosen by the otherPlayer
     */
    public void otherPlayerChoseGod(String otherPlayer, String chosenGod) {

        System.out.println("otherPlayer: " + otherPlayer);

        Platform.runLater(() -> chooseGodController.otherPlayerChoseGod(otherPlayer, chosenGod));

        setPlayerGod(otherPlayer, chosenGod);
    }

    /**
     * Lets player know that the challenger is choosing the start player
     */
    public void waitChallengerStartPlayer() {
        Platform.runLater(() -> {
            boardController.init();
            boardController.printToMainText("Waiting for challenger to choose the start player...");
            Gui.getStage().setScene(new Scene(boardRoot));
        });
    }

    /**
     * Lets player know who is the start player
     *
     * @param startPlayer start player nickname
     */
    public void printStartPlayer(String startPlayer) {
        Platform.runLater(() -> {
            //first if is useless: will be immediately overwritten by initial worker position
            if (startPlayer.equals(playerNickname))
                boardController.printToMainText("You are the start player!");
            else
                boardController.printToMainText(startPlayer + " is the start player!");
        });
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
     * Lets player know that he has lost, and who is the winner
     *
     * @param winner nickname of the winner
     */
    public boolean losingView(String winner) {

        try {
            AnchorPane lose = FXMLLoader.load(getClass().getResource("/scenes/lose.fxml"));
            Platform.runLater(()->((GridPane)boardRoot).getChildren().add(lose));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
