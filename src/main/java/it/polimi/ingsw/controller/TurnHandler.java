package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.CLIMainView;

import java.util.ArrayList;
import java.util.Scanner;

public class TurnHandler {
    private final Game game;
    private final CLIMainView view;
    private final GameController gameController;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private int turnCount;
    private final int numberOfPlayers;

    public TurnHandler(Game game, CLIMainView view, GameController gameController) {
        this.game = game;
        currentPlayer = null;
        turnCount = 0;
        this.view = view;
        this.gameController = gameController;
        this.players = game.getPlayers();
        numberOfPlayers = game.getNumberOfPlayers();
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * Allows to print the God of a player
     */
    public void printPlayerGod() {
        System.out.println(currentPlayer.getNickname() + " has chosen " + currentPlayer.getGod().getClass().getSimpleName());
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void nextTurn() {
        turnCount++;
        nextPlayer();
    }

    /**
     * Lets the Challenger choose Gods equal to the number of players.
     */
    private void challengerChooseGods() {

        view.printAllGods(gameController.getGodsDeck());

        ArrayList<God> godsDeck = gameController.getGodsDeck();

        //lets challenger select the gods
        int i = 0;
        while (i < numberOfPlayers) {
            String chosenGod = view.getGodFromChallenger();
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

            if (!foundGod)
                i++;
            else
                view.challengerError(); //print: the god you typed doesnt exist
        }

        view.printChosenGods();
        //print: these are the gods chosen by the challenger + list chosenGods

    }

    /**
     * Lets players choose their own god among the ones chosen by the challenger.
     */
    private void playersChooseGods() {
        //remember: Challenger must be last
        //challenger is the last of arraylist [see Game.randomChallenger()]
        ArrayList<God> alreadyTakenGods = new ArrayList<God>(numberOfPlayers));
        boolean foundGod = false;

        for (Player player : players) {

            while (!foundGod) {
                String inputGod = view.choosePlayerGod();

                for (God god : game.getChosenGods()) {
                    String godName = god.getClass().getSimpleName().toLowerCase();
                    if (inputGod.toLowerCase().equals(godName) && !alreadyTakenGods.contains(god)) {
                        player.setGod(god);
                        alreadyTakenGods.add(god);
                        foundGod = true;
                    }

                }
                if (!foundGod)
                    view.playerChoseInvalidGod();
            }
        }
    }


    public void setUpTurns() {
        challengerChooseGods();
        playersChooseGods();
        challengerChooseStartPlayer();
        setInitialWorkersPosition();

        startTurnFlow();    //starts regular flow of the turns
    }

    /**
     * Lets the challenger choose the start player
     * and puts him in the first position of the arraylist players of game.
     */
    private void challengerChooseStartPlayer() {
        String startPlayerNick;
        boolean foundPlayer = false;
        Player startPlayer = null;

        while(startPlayer == null) {

            startPlayerNick = view.challengerChooseStartPlayer();   //returns nickname of startPlayer
            for(Player player : players) {
                if(startPlayerNick.equals(player.getNickname())) {
                    startPlayer = player;
                    break;
                }
            }
            if(startPlayer == null)
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
        for(Player player : players) {
            for(Worker worker : player.getWorkers()) {
                positionSet = false;

                while(!positionSet) {
                    int[] initialPosition = view.getInitialWorkerPosition();
                    int x = initialPosition[0];
                    int y = initialPosition[1];

                    if (game.getBoard().findCell(x, y) != null) {
                        worker.setPosition(x, y);
                        positionSet = true;
                    } else
                        view.invalidInitialWorkerPosition();
                }
            }
        }
    }



    private void startTurnFlow() {
        int turnCount = 0;
        int i = 0;
        while (true) {
            currentPlayer = players.get(i);

            Worker chosenWorker = chooseWorker();

            currentPlayer.getGod().evolveTurn(chosenWorker);

            if(gameController.getEndGame()) {
                view.endGame();
                //?? exit program
                return;
            }
            i++;
            if (i == numberOfPlayers)
                i = 0;
        }
    }



    private Worker chooseWorker() {


        while(true) {
            String inputSex = view.chooseWorker(); //returns MALE or FEMALE, check this in view
            for(Worker worker : currentPlayer.getWorkers()) {
                String workerSex = worker.getSex().getClass().getSimpleName().toUpperCase();
                if(workerSex.equals(inputSex))
                    return worker;
                else
                    view.invalidSexWorker();   //additional check here (maybe useless)
            }
        }


    }



}

