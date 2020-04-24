package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.god.God;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.ClientView;

import java.util.ArrayList;

public class TurnHandler {
    private final Game game;
    private ClientView currentClient;
    private final GameController gameController;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private Integer numberOfPlayers;    //is it updated when game.numberOfPlayers is decreased?
    int unableToMove;
    int unableToBuild;

    public TurnHandler(Game game, GameController gameController) {
        this.game = game;
        currentPlayer = null;
        this.currentClient = null;
        this.gameController = gameController;
        this.players = game.getPlayers();
        numberOfPlayers = game.getNumberOfPlayers();
    }


    /**
     * Lets the Challenger choose Gods equal to the number of players.
     */
    private void challengerChooseGods() {

        ArrayList<God> godsDeck = gameController.getGodsDeck();
        ArrayList<String> godsNameAndDescription = new ArrayList<>(14);

        for (God god : godsDeck)
            godsNameAndDescription.add(god.toString() + ": " + god.getDescription());


        for (Player player : players)
            player.getClient().printAllGods(godsNameAndDescription);

        ClientView challengerClient = game.getChallenger().getClient();

        //lets challenger select the gods
        int alreadyChosenGods = 0;
        while (alreadyChosenGods < numberOfPlayers) {

            String chosenGod = challengerClient.getGodFromChallenger(numberOfPlayers,alreadyChosenGods);
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
            else
                challengerClient.challengerError(); //print: the god you typed doesnt exist
        }

    }

    /**
     * Lets players choose their own god among the ones chosen by the challenger.
     */
    private void playersChooseGods() {
        //remember: Challenger must be last
        //challenger is the last of arraylist [see Game.randomChallenger()]
        ArrayList<God> alreadyTakenGods = new ArrayList<>(numberOfPlayers);
        boolean foundGod;

        for (Player player : players) {
            ClientView playerClient = player.getClient();
            ArrayList<String> chosenGods = new ArrayList<>();
            for(God god : game.getChosenGods()){
                chosenGods.add(god.toString().toLowerCase());
            }
            playerClient.printChosenGods(chosenGods);
            foundGod = false;

            while (!foundGod) {
                String inputGod = playerClient.askPlayerGod();

                for (God god : game.getChosenGods()) {
                    String godName = god.toString().toLowerCase();
                    if (inputGod.toLowerCase().equals(godName) && !alreadyTakenGods.contains(god)) {
                        player.setGod(god);
                        alreadyTakenGods.add(god);
                        foundGod = true;
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

        ClientView challengerClient = game.getChallenger().getClient();
        String startPlayerNick;
        Player startPlayer = null;

        while (startPlayer == null) {

            startPlayerNick = challengerClient.challengerChooseStartPlayer();   //returns nickname of startPlayer
            for (Player player : players) {
                if (startPlayerNick.equals(player.getNickname())) {
                    startPlayer = player;
                    break;
                }
            }
            if (startPlayer == null)
                challengerClient.invalidStartPlayer();
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
            ClientView playerClient = player.getClient();
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
        }
    }



    public void setUpTurns() {
        challengerChooseGods();
        playersChooseGods();
        challengerChooseStartPlayer();
        setInitialWorkersPosition();
    }





    public void startTurnFlow() {
        int cyclicalCounter = 0;


        //noinspection InfiniteLoopStatement
        while (true) {
            currentPlayer = players.get(cyclicalCounter);
            currentClient = currentPlayer.getClient();
            gameController.getGodController().updateCurrentClient(currentClient);

            unableToMove = 0;
            unableToBuild = 0;


        /*
            //if none of currentPlayer's workers can move, lose
            if (!worker1.getMoveMap().anyAvailableMovePosition()
                    && !worker2.getMoveMap().anyAvailableMovePosition())
                losePlayer();
         */

            currentClient.notify();

            cyclicalCounter++;
            if (cyclicalCounter == numberOfPlayers)
                cyclicalCounter = 0;
        }
    }

    /**
     * Allows the player to choose a worker to play the turn.
     * @return the chosen worker.
     */
    public Worker chooseWorker() {

        String inputSex = currentClient.askChosenWorker();

        if (currentPlayer.getWorkers().get(0).getSex().toString().equals(inputSex))
            return currentPlayer.getWorkers().get(0);
        else
            return currentPlayer.getWorkers().get(1);

    }


    /**
     * The turn evolution of the worker.
     * @param turnWorker The worker picked for the turn.
     */
    public void turn(Worker turnWorker) {

        Worker otherWorker = null;

        for (Worker worker : currentPlayer.getWorkers()) {
            if (worker != turnWorker)
                otherWorker = worker;
        }


        try {
            currentPlayer.getGod().evolveTurn(turnWorker);
        } catch (UnableToMoveException ex) {
            unableToMove++;

            if (unableToMove == 1) {

                currentClient.selectedWorkerCannotMove(turnWorker.getSex().name());
                turn(otherWorker);

            } else {

                currentClient.unableToMoveLose();
                currentPlayer.lose();
                currentClient.printMap();
            }

        } catch (UnableToBuildException ex) {
            unableToBuild++;

            if (unableToBuild == 1) {

                currentClient.selectedWorkerCannotBuild(turnWorker.getSex().name());
                turn(otherWorker);

            } else {

                currentClient.unableToBuildLose();
                currentPlayer.lose();   //specify why: unable to build
                currentClient.printMap();
            }

        } finally {
            //if everyone else has lost, only player left wins
            if (players.size() == 1) {
                Player winner = players.get(0); //last player left has index 0
                winner.getGod().getGodController().winGame(winner.getNickname());
            }
            unableToMove = 0;    //reset it
            unableToBuild = 0;
        }

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

}
