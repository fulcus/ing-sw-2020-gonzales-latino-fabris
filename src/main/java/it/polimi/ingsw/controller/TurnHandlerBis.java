package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.view.CLIMainView;
import it.polimi.ingsw.view.VirtualClient;

import java.util.ArrayList;

public class TurnHandlerBis {
    private final Game game;
    private VirtualClient view;
    private final GameController gameController;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private Integer numberOfPlayers;    //is it updated when game.numberOfPlayers is decreased?
    int unableToMove;
    int unableToBuild;

    public TurnHandlerBis(Game game, GameController gameController) {
        this.game = game;
        currentPlayer = null;
        this.view = null;
        this.gameController = gameController;
        this.players = game.getPlayers();
        numberOfPlayers = game.getNumberOfPlayers();
    }


    /**
     * Lets the Challenger choose Gods equal to the number of players.
     */
    private void challengerChooseGods() {
        Player challenger = game.getChallenger();
        gameController.getGodController().updateCurrentClient();

        ///////////////

        currentPlayer.getVirtualClient().printAllGods(gameController.getGodsDeck());

        ArrayList<God> godsDeck = gameController.getGodsDeck();

        //lets challenger select the gods
        int i = 0;
        while (i < numberOfPlayers) {

            String chosenGod = view.getGodFromChallenger(challenger.getNickname(), i);
            boolean foundGod = false;

            for (God god : godsDeck) {

                String godName = god.getClass().getSimpleName().toLowerCase();

                if (chosenGod.toLowerCase().equals(godName)
                        && !game.getChosenGods().contains(god)) {

                    game.addGodChosenByChallenger(god);
                    foundGod = true;
                    break;
                }
            }

            if (foundGod)
                i++;
            else
                currentPlayer.getView().challengerError(); //print: the god you typed doesnt exist
        }

        //forall players views
        getView().printChosenGods();
        //print: these are the gods chosen by the challenger + list chosenGods

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
            foundGod = false;
            while (!foundGod) {
                String inputGod = currentPlayer.getView().askPlayerGod(player.getNickname());

                for (God god : game.getChosenGods()) {
                    String godName = god.getClass().getSimpleName().toLowerCase();
                    if (inputGod.toLowerCase().equals(godName) && !alreadyTakenGods.contains(god)) {
                        player.setGod(god);
                        alreadyTakenGods.add(god);
                        foundGod = true;
                    }

                }
                if (!foundGod)
                    currentPlayer.getView().playerChoseInvalidGod();
            }
        }
    }


    /**
     * Lets the challenger choose the start player
     * and puts him in the first position of the arraylist players of game.
     */
    private void challengerChooseStartPlayer() {
        String startPlayerNick;
        Player startPlayer = null;

        while (startPlayer == null) {

            startPlayerNick = view.challengerChooseStartPlayer(game.getChallenger().getNickname());   //returns nickname of startPlayer
            for (Player player : players) {
                if (startPlayerNick.equals(player.getNickname())) {
                    startPlayer = player;
                    break;
                }
            }
            if (startPlayer == null)
                view.invalidStartPlayer();
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

        view.printMap();

        for (Player player : players) {

            for (Worker worker : player.getWorkers()) {
                positionSet = false;

                while (!positionSet) {
                    int[] initialPosition = view.askInitialWorkerPosition(worker.getSex(), player.getNickname());
                    int x = initialPosition[0];
                    int y = initialPosition[1];

                    if (game.getBoard().findCell(x, y) != null && !game.getBoard().findCell(x, y).isOccupied()) {
                        worker.setPosition(x, y);
                        positionSet = true;
                        view.printMap();
                    } else
                        view.invalidInitialWorkerPosition();
                }
            }
        }
    }


    private Worker chooseWorker() {

        String inputSex = view.askChosenWorker(currentPlayer.getNickname());

        if (currentPlayer.getWorkers().get(0).getSex().toString().equals(inputSex))
            return currentPlayer.getWorkers().get(0);
        else
            return currentPlayer.getWorkers().get(1);

    }

    private void displayBoard() {
        view.printMap();
    }

    protected void setUpTurns() {
        challengerChooseGods();
        playersChooseGods();
        challengerChooseStartPlayer();
        setInitialWorkersPosition();
    }


    protected void startTurnFlow() {
        int cyclicalCounter = 0;


        //noinspection InfiniteLoopStatement
        while (true) {
            currentPlayer = players.get(cyclicalCounter);
            unableToMove = 0;
            unableToBuild = 0;
        /*
            //if none of currentPlayer's workers can move, lose
            if (!worker1.getMoveMap().anyAvailableMovePosition()
                    && !worker2.getMoveMap().anyAvailableMovePosition())
                losePlayer();
         */

            Worker chosenWorker = chooseWorker();

            turn(chosenWorker);

            cyclicalCounter++;
            if (cyclicalCounter == numberOfPlayers)
                cyclicalCounter = 0;
        }
    }


    private void turn(Worker turnWorker) {

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

                view.selectedWorkerCannotMove(turnWorker.getSex().name());
                turn(otherWorker);

            } else {

                view.unableToMoveLose(currentPlayer.getNickname());
                currentPlayer.lose();
                displayBoard();
            }

        } catch (UnableToBuildException ex) {
            unableToBuild++;

            if (unableToBuild == 1) {

                view.selectedWorkerCannotBuild(turnWorker.getSex().name());
                turn(otherWorker);

            } else {

                view.unableToBuildLose(currentPlayer.getNickname());
                currentPlayer.lose();   //specify why: unable to build
                displayBoard();
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
