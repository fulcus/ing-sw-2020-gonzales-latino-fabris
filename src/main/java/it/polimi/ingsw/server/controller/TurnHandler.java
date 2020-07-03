package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.server.controller.god.God;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;

import java.util.ArrayList;


/**
 * Allows to handle the turns of the players, from the beginning, when the game parameters need to be set,
 * until when one of the players wins the game.
 * Players are will be playing the game in a cyclical way.
 */
public class TurnHandler implements Runnable {

    private final Game game;
    private VirtualView currentClient;
    private final GameController gameController;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private Integer numberOfPlayers;
    private int unableToMove;
    private boolean gameAlive;
    private boolean numberOfPLayersHasChanged;
    private int turnCounter;


    /**
     * Instances the TurnHandler for a specific game.
     *
     * @param game           The game the TurnHandler refers to.
     * @param gameController The GameController from which the game has been created.
     */
    public TurnHandler(Game game, GameController gameController) {
        gameAlive = true;
        this.game = game;
        currentPlayer = null;
        this.currentClient = null;
        this.gameController = gameController;
        this.players = game.getPlayers();
        numberOfPlayers = game.getNumberOfPlayers();
        numberOfPLayersHasChanged = false;
        turnCounter = 0;
    }


    /**
     * Describes and divides the flow of the turns in two phases: the setup of the game and the its strategy gaming phase.
     * Runs until one of the player wins the game or a disconnection occurs.
     */
    @Override
    public void run() {
        setUpTurns();
        startTurnFlow();
    }


    /**
     * Lets the Challenger choose as many Gods as players in the game
     * and sends the chosen gods to the other players.
     */
    private void challengerChooseGods() {

        Player challenger = game.getChallenger();
        VirtualView challengerClient = challenger.getClient();
        ArrayList<God> godsDeck = gameController.getGodsDeck();


        //send arraylist with all gods to all players
        for (Player player : players) {
            if (player != challenger)
                player.getClient().waitChallengerChooseGods(challenger.getNickname());
        }

        //lets challenger select the gods
        int alreadyChosenGods = 0;
        while (alreadyChosenGods < numberOfPlayers) {

            String chosenGod = challengerClient.getGodFromChallenger(numberOfPlayers, alreadyChosenGods);
            boolean foundGod = false;

            for (God god : godsDeck) {

                String godName = god.toString().toLowerCase();

                if (chosenGod.toLowerCase().equals(godName)
                        && !game.getChosenGods().contains(god)) {

                    game.addGodChosenByChallenger(god);
                    foundGod = true;
                    break;
                }
            }

            if (foundGod)
                alreadyChosenGods++;
            //else
                //challengerClient.challengerError(); //print: the god you typed doesn't exist
            //removed: only occurs with hacked client
        }

        //turn arraylist of gods in arraylist of strings
        ArrayList<String> chosenGods = new ArrayList<>();

        for (God god : game.getChosenGods())
            chosenGods.add(god.toString());

        //send gods chosen by challenger to other players
        for (Player otherPlayer : players) {
            if (otherPlayer != challenger)
                otherPlayer.getClient().printChosenGods(chosenGods);
        }

    }


    /**
     * Lets players choose their own god among the ones chosen by the challenger.
     */
    private void playersChooseGods() {
        //remember: Challenger must be last
        //challenger is the last of arraylist [see Game.randomChallenger()]
        ArrayList<God> alreadyTakenGods = new ArrayList<>(numberOfPlayers);


        for (Player player : players) {

            for (Player otherPlayer : players) {
                if (otherPlayer != player)
                    otherPlayer.getClient().waitOtherPlayerChooseGod(player.getNickname());
            }

            VirtualView playerClient = player.getClient();
            //playerClient.printChosenGods(chosenGods);


            boolean foundGod = false;

            while (!foundGod) {
                String inputGod = playerClient.askPlayerGod();

                for (God god : game.getChosenGods()) {
                    String godName = god.toString().toLowerCase();

                    if (inputGod.toLowerCase().equals(godName) && !alreadyTakenGods.contains(god)) {
                        player.setGod(god);
                        alreadyTakenGods.add(god);
                        foundGod = true;

                        for (Player otherPlayer : players) {
                            if (otherPlayer != player)
                                otherPlayer.getClient().otherPlayerChoseGod(player.getNickname(), god.toString());
                        }
                    }

                }
                if (!foundGod)
                    playerClient.playerChoseInvalidGod();
            }

        }
    }


    /**
     * Lets the challenger choose the start player
     * and puts him in the first position of the arraylist players of game.
     */
    private void challengerChooseStartPlayer() {

        Player challenger = game.getChallenger();
        VirtualView challengerClient = challenger.getClient();
        String startPlayerNick = null;
        Player startPlayer = null;


        for (Player otherPlayer : players) {
            if (otherPlayer != challenger)
                otherPlayer.getClient().waitChallengerStartPlayer();
        }


        while (startPlayer == null) {

            startPlayerNick = challengerClient.challengerChooseStartPlayer();   //returns nickname of startPlayer
            for (Player player : players) {

                if (startPlayerNick.equals(player.getNickname())) {
                    startPlayer = player;
                    break;
                }
            }

            //if (startPlayer == null)
                //challengerClient.invalidStartPlayer();
            //only occurs with hacked client
        }

        for (Player otherPlayer : players) {
            if (otherPlayer != challenger)
                otherPlayer.getClient().printStartPlayer(startPlayerNick);
        }

        //set startPlayer as first of arraylist players.
        //challenger already was the last.

        int startPlayerIndex = players.indexOf(startPlayer);
        Player temp = players.get(0);
        players.set(0, startPlayer);
        players.set(startPlayerIndex, temp);

    }


    /**
     * All players set the position for all workers.
     */
    private void setInitialWorkersPosition() {
        boolean positionSet;

        for (Player player : players) {

            for (Player otherPlayer : players) {
                if (otherPlayer != player)
                    otherPlayer.getClient().otherPlayerSettingInitialWorkerPosition(player.getNickname());
            }

            VirtualView playerClient = player.getClient();

            if (players.indexOf(player) == 0)
                playerClient.printMap();

            for (Worker worker : player.getWorkers()) {
                positionSet = false;

                while (!positionSet) {
                    int[] initialPosition = playerClient.askInitialWorkerPosition(worker.getSex().name());
                    int x = initialPosition[0];
                    int y = initialPosition[1];

                    if (game.getBoard().findCell(x, y) != null
                            && !game.getBoard().findCell(x, y).isOccupied()) {

                        worker.setPosition(x, y);
                        positionSet = true;

                        playerClient.printMap();

                    } else
                        playerClient.invalidInitialWorkerPosition();
                }
            }

            //Once a player set his workers, others will receive the updated map
            for (Player player_ : players) {
                if (player_ != player)
                    player_.getClient().printMap();
            }

        }

    }


    /**
     * Executes the preparation of the game.
     */
    public void setUpTurns() {
        challengerChooseGods();
        playersChooseGods();
        challengerChooseStartPlayer();
        setInitialWorkersPosition();
    }


    /**
     * Executes the succession of turns by the players.
     */
    private void startTurnFlow() {

        int cyclicalCounter = 0;


        while (gameAlive) {


            currentPlayer = players.get(cyclicalCounter);
            currentClient = currentPlayer.getClient();

            gameController.getGodController().updateCurrentClient(currentClient);

            unableToMove = 0;


        /*
            //if none of currentPlayer's workers can move, lose
            if (!worker1.getMoveMap().anyAvailableMovePosition()
                    && !worker2.getMoveMap().anyAvailableMovePosition())
                losePlayer();
         */

            for (Player otherPlayer : players) {
                if (otherPlayer != currentPlayer)
                    otherPlayer.getClient().otherPlayerTurn(currentPlayer.getNickname());
            }

            Worker chosenWorker = chooseWorker();
            turn(chosenWorker);


            cyclicalCounter++;
            turnCounter++;

            if (numberOfPLayersHasChanged) {
                cyclicalCounter = handleCyclicalCounter(cyclicalCounter);
                setNumberOfPLayersHasChanged(false);
            } else if (cyclicalCounter == numberOfPlayers)
                cyclicalCounter = 0;


        }
    }


    /**
     * Handles cyclical counter when number of Players changes.
     *
     * @param cyclicalCounter value of counter when numOfPlayers decreases.
     * @return The new value of cyclicalCounter.
     */
    public int handleCyclicalCounter(int cyclicalCounter) {

        if (cyclicalCounter == 1)
            cyclicalCounter = 0;
        else if (cyclicalCounter == 2)
            cyclicalCounter = 1;
        else
            cyclicalCounter = 0;

        return cyclicalCounter;

    }


    /**
     * Allows the player to choose a worker to play the turn.
     *
     * @return the chosen worker.
     */
    private Worker chooseWorker() {

        String inputSex = currentClient.askChosenWorker();

        if (currentPlayer.getWorkers().get(0).getSex().toString().equals(inputSex))
            return currentPlayer.getWorkers().get(0);
        else
            return currentPlayer.getWorkers().get(1);

    }


    /**
     * Allows to let the turn of the player, who has chosen a specific worker, to evolve.
     *
     * @param turnWorker The worker picked for the turn.
     */
    private void turn(Worker turnWorker) {

        Worker otherWorker = null;
        String loserNickname;

        for (Worker worker : currentPlayer.getWorkers()) {
            if (worker != turnWorker)
                otherWorker = worker;
        }

        try {

            currentPlayer.getGod().evolveTurn(turnWorker);

        } catch (UnableToMoveException ex) {
            unableToMove++;

            if("lose".equals(ex.getErrorCode()))
                unableToMove++;

            if (unableToMove == 1) {
                currentClient.selectedWorkerCannotMove(turnWorker.getSex().name());
                turn(otherWorker);

            } else {
                loserNickname = currentPlayer.getNickname();
                currentClient.unableToMoveLose();
                currentPlayer.lose();
                currentClient.killClient();

                if (players.size() == 2)
                    handleGameChange(loserNickname);
            }

        } catch (UnableToBuildException ex) {

            loserNickname = currentPlayer.getNickname();
            currentClient.unableToBuildLose();
            currentPlayer.lose();
            currentClient.killClient();

            if (players.size() == 2)
                handleGameChange(loserNickname);

        } catch (WinException ex) {

            gameController.winGame(currentPlayer);

        } finally {

            //if everyone else has lost, only player left wins
            //if WinException is thrown, condition is false

            if (players.size() == 1) {
                //last player left has index 0
                Player winner = players.get(0);
                gameController.winGame(winner);
            }

            unableToMove = 0;    //reset it
        }

    }


    /**
     * Handles the turn flow and resizes the game players dimension when someone loses.
     * Lets other players know that one of thw players has lost the game and so will be removed from the current game.
     *
     * @param loserNickname The nickname of the loser.
     */
    public void handleGameChange(String loserNickname) {

        setNumberOfPLayersHasChanged(true);
        setNumberOfPlayers(game.getNumberOfPlayers());
        gameController.getGodController().displayBoard();
        gameController.notifyPlayersOfLoss(loserNickname);

    }


    /**
     * Sets the attribute gameAlive to the false value.
     * The game is no more playable.
     */
    public void stopTurnFlow() {
        gameAlive = false;
    }


    public boolean getGameAlive() {
        return gameAlive;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public void setNumberOfPLayersHasChanged(boolean numberOfPLayersHasChanged) {
        this.numberOfPLayersHasChanged = numberOfPLayersHasChanged;
    }


    public boolean numberOfPLayersHasChanged() {
        return numberOfPLayersHasChanged;
    }


    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }


    public int getTurnCounter() {
        return turnCounter;
    }


}
