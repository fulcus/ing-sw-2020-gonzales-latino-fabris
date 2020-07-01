package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.BoardClient;
import it.polimi.ingsw.client.PlayerClient;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.serializable.CellClient;
import it.polimi.ingsw.serializable.WorkerClient;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Manages the display of requests and answers to the server.
 * Guarantees a proper evolution of the scenes of the game.
 */
public class GuiManager implements View {

    protected static final ArrayList<PlayerClient> players = new ArrayList<>();
    private PlayerClient myPlayer;

    protected static final AtomicReference<BoardClient> boardClient = new AtomicReference<>(new BoardClient());

    protected static final AtomicInteger numberOfPlayers = new AtomicInteger(0); //overwritten by joinGame or asknumberofplayers
    protected static final AtomicInteger playersConnected = new AtomicInteger(0);
    protected static final AtomicBoolean isInLobby = new AtomicBoolean(false);

    protected static final SynchronousQueue<Object> queue = new SynchronousQueue<>();
    protected static String myNickname;
    private String myColor;
    private WorkerClient selectedWorker;

    //roots of scenes
    protected static Parent numberOfPlayersRoot;
    protected static Parent nicknameRoot;
    protected static Parent colorRoot;
    protected static Parent chooseGodRoot;
    protected static Parent startPlayerRoot;
    protected static Parent boardRoot;
    protected static Parent lobbyRoot;
    protected static Parent connectRoot;

    //controllers of fxmls
    private ConnectController connectController;
    private NumberOfPlayersController numberOfPlayersController;
    private NicknameController nicknameController;
    private ColorController colorController;
    private LobbyController lobbyController;
    protected static ChooseGodController chooseGodController;
    private StartPlayerController startPlayerController;
    private BoardController boardController;


    public GuiManager() {

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

        try {
            connectRoot = connectLoader.load();
            numberOfPlayersRoot = numberOfPlayersLoader.load();
            nicknameRoot = nicknameLoader.load();
            colorRoot = colorLoader.load();
            chooseGodRoot = chooseGodLoader.load();
            startPlayerRoot = startPlayerLoader.load();
            boardRoot = boardLoader.load();
            lobbyRoot = lobbyLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectController = connectLoader.getController();
        numberOfPlayersController = numberOfPlayersLoader.getController();
        nicknameController = nicknameLoader.getController();
        colorController = colorLoader.getController();
        lobbyController = lobbyLoader.getController();
        chooseGodController = chooseGodLoader.getController();
        startPlayerController = startPlayerLoader.getController();
        boardController = boardLoader.getController();

    }


    /**
     * Asks the IP of the server where the client wants to connect to.
     *
     * @return The IP of the server to connect to.
     */
    public String getServerAddress() {

        String IP = null;
        try {
            IP = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return IP;
    }


    /**
     * Shows if the connection to the server wasn't successful.
     *
     * @param connected True if the connection was established, false otherwise.
     */
    public void connectionOutcome(boolean connected) {

        if (!connected) {
            System.out.println("connection error");
            connectController.displayError();
        }
    }


    /**
     * Displays that a player has been disconnected and reason.
     *
     * @param disconnectedPlayer The name of the disconnected player.
     */
    public void notifyOtherPlayerDisconnection(String disconnectedPlayer) {

        Parent currentRoot = Gui.getStage().getScene().getRoot();
        FXMLLoader disconnectionLoader = new FXMLLoader(getClass().getResource("/scenes/disconnection.fxml"));

        Parent disconnectionRoot = null;
        try {
            disconnectionRoot = disconnectionLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EndGameController endGameController = disconnectionLoader.getController();

        Parent finalDisconnectionRoot = disconnectionRoot;
        Platform.runLater(() -> {
            currentRoot.setEffect(new GaussianBlur());

            endGameController.setDisconnectionPlayer(disconnectedPlayer);
            StackPane root = new StackPane(currentRoot);
            root.getChildren().add(finalDisconnectionRoot);
            Gui.getStage().setScene(new Scene(root));

        });


    }


    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {
        //do Nothing here
    }


    /**
     * Lets the player know the number of players for the game he's been assigned.
     * The player in this specific case did not choose the number of players for the game, but someone else did (the so called "creator").
     *
     * @param numberOfPlayers The number of players of the game the player has been assigned to.
     */
    public void joinGame(int numberOfPlayers) {
        //sets number of players attribute
        GuiManager.numberOfPlayers.set(numberOfPlayers);
        //change scene
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(nicknameRoot)));
    }


    /**
     * Lets the player know he is the creator of a new game.
     * Loads the choosing number of players scene.
     */
    public void createGame() {
        //change scene
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(numberOfPlayersRoot)));
    }


    /**
     * Asks to the creator of a game how many players will the game hold.
     *
     * @return The number of players decided by the creator player.
     */
    public int askNumberOfPlayers() {

        int numInt;
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
     * It will be invoked both for the male worker and the female worker of every player.
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


    /**
     * Allows to store in the client memory the general info about other players.
     *
     * @param nickname Nickname of the player to register.
     * @param color    Color chosen by that specific player for the current game.
     */
    public void setOtherPlayersInfo(String nickname, String color) {

        players.add(new PlayerClient(nickname, color));
        playersConnected.addAndGet(1);

        //if client is in lobby RENDER
        if (isInLobby.get())
            Platform.runLater(() -> lobbyController.showPlayer(nickname, color));
        //otherwise it has already been saved and will be rendered in initialize
    }


    private void setPlayerGod(String nickname, String god) {
        for (PlayerClient player : players) {
            if (player.getNickname().equals(nickname))
                player.setGod(god);
        }
    }


    /**
     * Lets the player know the position he wrote for the initial worker position was wrong.
     */
    public void invalidInitialWorkerPosition() {
        Platform.runLater(() -> {
            boardController.printToMainText("You cannot place your worker here!");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        acceptTextBarInfo();
    }


    /**
     * Asks to the player the nickname for the game.
     *
     * @return The nickname chosen by the player.
     */
    public String askPlayerNickname() {
        String nickname = null;

        //nicknameController.removeErrorNickFromScreen();
        nicknameController.removeWaitingOtherFromScreen();
        nicknameController.enableNicknameText();

        try {

            nickname = (String) queue.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myNickname = nickname;
        return nickname;
    }


    /**
     * Notifies the player that his nickname was accepted by the server, going to the next scene.
     */
    public void notifyValidNick() {
        //change scene
        Platform.runLater(() -> Gui.getStage().setScene(new Scene(colorRoot)));
    }


    /**
     * Asks to the player the color for the game.
     * Only three colors are available: blue, white and beige.
     *
     * @return The color chosen by the player.
     */
    public String askPlayerColor(ArrayList<String> availableColors) {

        String color = null;

        colorController.removeWaitingOtherFromScreen();
        //colorController.removeErrorColorFromScreen();
        colorController.enableButtons();

        try {

            color = (String) queue.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myColor = color;
        return color;
    }


    /**
     * Notifies the player that his color choice was accepted by the server, going to the next scene.
     */
    public void notifyValidColor() {
        //adding this players info to "database"
        myPlayer = new PlayerClient(myNickname, myColor);
        players.add(0, myPlayer);
        playersConnected.addAndGet(1);

        //change scene
        Platform.runLater(() -> {
            lobbyController.init();
            Gui.getStage().setScene(new Scene(lobbyRoot));
        });

    }


    /**
     * Asks to the player which God among the available ones wants to play with during the current game.
     *
     * @return The name of the chosen God.
     */
    public String askPlayerGod() {
        Platform.runLater(() -> chooseGodController.askPlayerGod());

        String chosenGod = null;
        try {
            chosenGod = (String) queue.take();
            myPlayer.setGod(chosenGod);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Selected " + chosenGod);
        return chosenGod;
    }


    /**
     * Lets the player know that his God's choice was rejected by the server.
     */
    public void playerChoseInvalidGod() {
        Platform.runLater(() -> chooseGodController.playerChoseInvalidGod());
    }


    /**
     * Lets the challenger know how many gods he still has to choose.
     * Then the challenger, if other gods need to be selected, chooses another god for the game.
     *
     * @param numOfPlayers      The number of players of the current game.
     * @param alreadyChosenGods The number of gods that the challenger has already chosen for the game.
     * @return Another name of the God the challenger chooses for the current game.
     */
    public String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods) {

        String chosenGod = null;
        ArrayList<String> chosenGods = new ArrayList<>();

        Platform.runLater(() -> chooseGodController.getGodFromChallenger(numOfPlayers, alreadyChosenGods));

        try {
            chosenGod = (String) queue.take();
            chosenGods.add(chosenGod);
            chooseGodController.printChosenGods(chosenGods);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return chosenGod;
    }


    /**
     * Asks to the challenger which player will be the starting one.
     *
     * @return The nickname of the starting player.
     */
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
            isInLobby.set(false); //for "play again" feature
        });

        return startPlayer;
    }


    /**
     * Lets the player know that the chosen color was not available,
     * because another player had already chosen it before.
     */
    public void notAvailableColor() {
        colorController.displayErrorColor();
    }


    /**
     * Tells the player that the inserted nickname was not available.
     * This error can occur when the length of the nickname is too long or when the same nick was already chosen by another player.
     */
    public void notAvailableNickname() {
        nicknameController.displayErrorNick();
    }

    /**
     * Lets the client know that the nickname entered is too long or empty.
     */
    public void nicknameFormatError() {
        nicknameController.displayErrorNick();
    }


    /**
     * Asks to the player which one of his worker wants to play with during the current turn.
     *
     * @return The sex of the worker the player wants to play with.
     */
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
            WorkerClient clickedWorker = boardClient.get().findCell(chosenCell[0], chosenCell[1]).getWorkerClient();

            if (clickedWorker != null
                    && clickedWorker.getWorkerColor().toLowerCase().equals(myColor.toLowerCase())) {

                System.out.println("clickedWorker: " + clickedWorker);

                selectedWorker = clickedWorker;

                //todo always enters if ?
                //xWorker.set(chosenCell[0]);
                //yWorker.set(chosenCell[1]);

                return clickedWorker.getWorkerSex();
            }

            System.out.println("cell doesn't contain worker");

            Platform.runLater(() ->
                    boardController.printToMainText("You have to select one of your workers!\nTry Again"));
        }

    }

    /**
     * Warns user of invalid build action: he cannot build a dome underneath himself.
     * This error can only occur if player uses Zeus' power.
     */
    public void printCannotBuildDomeUnderneath() {

        Platform.runLater(() -> {
            boardController.printToMainText("You cannot build a dome underneath yourself.");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        acceptTextBarInfo();
    }


    /**
     * The player has won the game, so the winning scene is going to be loaded.
     *
     * @return Generic return to ensure server waits for confirmation from client before disconnecting it.
     */
    public boolean winningView() {

        try {
            AnchorPane win = FXMLLoader.load(getClass().getResource("/scenes/win.fxml"));

            Platform.runLater(() -> {

                for (Node child : boardRoot.getChildrenUnmodifiable())
                    child.setEffect(new GaussianBlur());

                ((GridPane) boardRoot).getChildren().add(win);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * Lets the player know he has lost the game because both of his workers cannot move.
     *
     * @return Generic return to ensure server waits for confirmation from client before disconnecting it.
     */
    public boolean unableToMoveLose() {
        Platform.runLater(() -> {
            boardController.printToMainText("You cannot move anywhere!");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        losingView("");
        return true;
    }


    /**
     * Lets the player know he has lost the game because both of his workers cannot build.
     *
     * @return Generic return to ensure server waits for confirmation from client before disconnecting it.
     */
    public boolean unableToBuildLose() {

        Platform.runLater(() -> {
            boardController.printToMainText("You cannot build anywhere!");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        losingView("");
        return true;
    }


    /**
     * In a 3 players game, this method notifies the other players that a player has lost the game.
     *
     * @param loserNickname The nickname of the player that has lost the game.
     */
    public void notifyPlayersOfLoss(String loserNickname) {
        Platform.runLater(() -> {
            //TODO UPDATE NUMOFPLAYERS
            boardController.printToMainText(loserNickname + " has lost this game!");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
            boardController.removeGodFrame(loserNickname);
        });

        acceptTextBarInfo();
    }

    /**
     * This method lets the system show an updated version of the Board.
     */
    public void printMap() {
        Platform.runLater(() -> boardController.printMap());
    }


    /**
     * Updates the cell of the board that has changed its contents.
     *
     * @param toUpdateCell The cell that needs to be updated.
     */
    public void update(CellClient toUpdateCell) {
        boardController.update(toUpdateCell);
    }


    /**
     * Shows all the Gods chosen by the challenger for the current game, going to the correct scene.
     *
     * @param chosenGods The list of the chosen gods.
     */
    public void printChosenGods(ArrayList<String> chosenGods) {
        Platform.runLater(() -> chooseGodController.printChosenGods(chosenGods));
    }


    /**
     * Lets the player know the selected worker cannot move.
     *
     * @param sex The sex of the selected worker
     */
    public void selectedWorkerCannotMove(String sex) {

        String selectedWorkerSex = sex.toLowerCase();
        String otherSex;

        if (selectedWorkerSex.equals("male"))
            otherSex = "female";
        else
            otherSex = "male";

        Platform.runLater(() -> {
            boardController.printToMainText("Your " + selectedWorkerSex +
                    " worker cannot move, you must move your " + otherSex + " worker");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        selectedWorker = boardClient.get().findWorker(myColor, otherSex);
        System.out.println("unableToMove selectedWorker: " + selectedWorker.getWorkerColor() + selectedWorker.getWorkerSex());
        System.out.println(selectedWorker);
        acceptTextBarInfo();
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
                .workerCellRelativePositionCompass(selectedWorker, chosenCell[0], chosenCell[1]);


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
        selectedBuildingDirection[1] = askToUseAtlasPower(); //building type (B or D)

        return selectedBuildingDirection;

    }


    private String askToUseAtlasPower() {
        Platform.runLater(() -> {
            boardController.setGodPowerRequested(true);
            boardController.printToMainText("Choose if you want to build a BLOCK or a DOME");
            boardController.enableGodPower();
        });

        String answer = null;

        try {
            answer = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> boardController.disableGodPower());

        return answer;
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
            chosenCell[0] = (Integer) queue.take();//row
            chosenCell[1] = (Integer) queue.take();//column

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("move selectedWorker: " + selectedWorker.getWorkerColor() + selectedWorker.getWorkerSex());
        System.out.println(selectedWorker);
        String result = boardClient.get()
                .workerCellRelativePositionCompass(selectedWorker, chosenCell[0], chosenCell[1]);

        System.out.println("converted in: " + result);

        //xWorker.set(chosenCell[0]);
        //yWorker.set(chosenCell[1]);

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
            boardController.enableGodPower();
        });

        String answer = null;

        try {
            answer = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> boardController.disableGodPower());

        return answer;
    }


    /**
     * Allows to get the input of the player to move an enemy's worker.
     *
     * @return The will of the player to move an enemy's worker
     */
    public String askWantToMoveEnemy() {
        return askToUseGodPower();
    }


    /**
     * Allows to move one worker's enemy.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The Worker to move selected by the player, null if there aren't enemies around.
     */
    public String askWorkerToMove(ArrayList<WorkerClient> enemyWorkers, WorkerClient myWorker) {
        return selectEnemyWorker();
    }


    private String selectEnemyWorker() {

        int[] chosenCell = new int[2];

        while (true) {
            Platform.runLater(() -> {
                boardController.setCellRequested(true);
                boardController.printToMainText("Select a neighboring enemy worker!");
            });

            try {
                chosenCell[0] = (Integer) queue.take(); //row
                chosenCell[1] = (Integer) queue.take(); //column

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //check if cell contains worker
            WorkerClient clickedWorker = boardClient.get().findCell(chosenCell[0], chosenCell[1]).getWorkerClient();

            if (clickedWorker != null
                    && !clickedWorker.getWorkerColor().toLowerCase().equals(myColor.toLowerCase())) {

                System.out.println("clickedWorker: " + clickedWorker);    //debug

                String result = boardClient.get()
                        .workerCellRelativePositionCompass(selectedWorker, chosenCell[0], chosenCell[1]);

                System.out.println("converted in: " + result);

                if (!result.equals("FALSE") && !result.equals("U"))
                    return result;
            }

            System.out.println("cell doesn't contain worker");

            Platform.runLater(() ->
                    boardController.printToMainText("You have to select an enemy worker!\nTry Again"));

        }

    }


    /**
     * Says that the worker can build under himself/herself.
     * This is allowed only when playing with Zeus.
     */
    public void printBuildUnderneath() {
        /*
        Platform.runLater(() -> {
            boardController.printToMainText("Remember that you can also build underneath!");
            boardController.setConfirmButtonVisible();
        });

        acceptTextBarInfo();*/
    }


    /**
     * Asks to the player that holds Hephaestus as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHephaestus() {
        return askToUseGodPower();
    }


    /**
     * Asks to the player that holds Demeter as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainDemeter() {
        return askToUseGodPower();
    }


    /**
     * Asks to the player that holds Hestia as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHestia() {
        return askToUseGodPower();
    }


    /**
     * Asks to the player that holds Prometheus as a God if he wants to build before moving.
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
        Platform.runLater(() -> {
            boardController.printToMainText("You cannot move here!");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        acceptTextBarInfo();
    }


    /**
     * Asks the player if he still wants to move during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printMoveDecisionError() {
        return askToUseGodPower();
    }


    /**
     * Asks the player if he still wants to build during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printBuildDecisionError() {
        return askToUseGodPower();
    }


    /**
     * Points out a player is not allowed to build.
     */
    public void printBuildGeneralErrorScreen() {
        Platform.runLater(() -> {
            boardController.printToMainText("You cannot build here!");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        acceptTextBarInfo();
    }


    /**
     * Points out a player is not allowed to build a block in a certain position.
     */
    public void printBuildBlockErrorScreen() {
        Platform.runLater(() -> {
            boardController.printToMainText("You cannot build a block here!");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        acceptTextBarInfo();
    }


    /**
     * Points out that a player is not allowed to build again in a certain position.
     */
    public void printBuildInSamePositionScreen() {
        Platform.runLater(() -> {
            boardController.printToMainText("You cannot build again here!");
            boardController.printToGodTextArea("");
            boardController.setConfirmButtonVisible();
        });

        acceptTextBarInfo();
    }


    /**
     * Lets player know that the challenger is choosing the gods for the game.
     *
     * @param challenger nickname of the challenger.
     */
    public void waitChallengerChooseGods(String challenger) {
        Platform.runLater(() -> chooseGodController.waitChallengerChooseGods(challenger));
    }


    /**
     * Lets player know that another player is choosing his god.
     *
     * @param otherPlayer the player that is choosing his god.
     */
    public void waitOtherPlayerChooseGod(String otherPlayer) {
        Platform.runLater(() -> chooseGodController.waitOtherPlayerChooseGod(otherPlayer));
    }


    /**
     * Lets player know the god chosen by another player.
     *
     * @param otherPlayer player who chose the god.
     * @param chosenGod   god chosen by the otherPlayer.
     */
    public void otherPlayerChoseGod(String otherPlayer, String chosenGod) {
        Platform.runLater(() -> chooseGodController.otherPlayerChoseGod(otherPlayer, chosenGod));
        setPlayerGod(otherPlayer, chosenGod);
    }


    /**
     * Lets player know that the challenger is choosing the start player.
     */
    public void waitChallengerStartPlayer() {
        Platform.runLater(() -> {
            boardController.init();
            boardController.printToMainText("Waiting for challenger to choose the start player...");
            Gui.getStage().setScene(new Scene(boardRoot));
        });
    }


    /**
     * Lets player know who is the start player.
     *
     * @param startPlayer start player nickname.
     */
    public void printStartPlayer(String startPlayer) {
        Platform.runLater(() -> {
            //first if is useless: will be immediately overwritten by initial worker position
            if (startPlayer.equals(myNickname))
                boardController.printToMainText("You are the start player!");
            else
                boardController.printToMainText(startPlayer + " is the start player!");
        });
    }


    /**
     * Lets  player know that another player is choosing the initial position for his workers.
     *
     * @param player player who is performing the action.
     */
    public void otherPlayerSettingInitialWorkerPosition(String player) {
        Platform.runLater(() -> boardController.printToMainText("Other player's setting initial worker's position. Wait..."));
    }


    /**
     * Lets player know that it's another player's turn.
     *
     * @param currentPlayer nickname of the player that is playing his turn.
     */
    public void otherPlayerTurn(String currentPlayer) {
        Platform.runLater(() -> boardController.printToMainText("Other player's turn. Wait..."));
    }


    /**
     * Lets player know that he has lost, and who is the winner.
     *
     * @param winner nickname of the winner.
     * @return Always returns true.
     */
    public boolean losingView(String winner) {

        try {
            FXMLLoader loseLoader = new FXMLLoader(getClass().getResource("/scenes/lose.fxml"));
            AnchorPane lose = loseLoader.load();
            EndGameController loseController = loseLoader.getController();

            Platform.runLater(() -> {
                loseController.setWinner(winner);

                for (Node child : boardRoot.getChildrenUnmodifiable())
                    child.setEffect(new GaussianBlur());

                ((GridPane) boardRoot).getChildren().add(lose);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    /**
     * Allows to get the confirm that the player has read the message on the main textbar of the board.
     */
    private void acceptTextBarInfo() {

        boolean accept = false;
        try {

            do {
                accept = (boolean) queue.take();
            } while (!accept);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("confirmed reading message");
    }
}
