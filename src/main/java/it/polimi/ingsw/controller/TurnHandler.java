package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.CLIMainView;

import java.util.ArrayList;

public class TurnHandler {
    private final Game game;
    private final CLIMainView view;
    private final GameController gameController;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private int turnNumber;
    private final int numberOfPlayers;

    public TurnHandler(Game game, CLIMainView view, GameController gameController) {
        this.game = game;
        currentPlayer = null;
        turnNumber = 0;
        this.view = view;
        this.gameController = gameController;
        this.players = game.getPlayers();
        numberOfPlayers = game.getNumberOfPlayers();
    }


    /**
     * Allows to select the correct player for this turn
     */
    public void nextPlayer() {
        int j = 0;
        //in the initial turn (turn 0) the first player is set to be the first one after the challenger
        if (turnNumber == 0) {
            if (game.getChallenger().equals(players.get(numberOfPlayers - 1)))
                currentPlayer = players.get(0);
            else
                while (j < numberOfPlayers - 1) {
                    if (players.get(j).equals(game.getChallenger()))
                        currentPlayer = players.get(j + 1);
                    j++;
                }
        }
        //in the turns of the game (counter > 0) the next currentPlayer will be the following one
        //in the ArrayList of the players of the current Game
        else {
            //to assign the next currentPlayer it needs to be distinguished
            // if the current player is the las one of the game.players or not
            if (currentPlayer.equals(players.get(numberOfPlayers - 1)))
                currentPlayer = players.get(0);
            else {
                j = 0;
                while (j < numberOfPlayers - 1) {
                    if (players.get(j).equals(currentPlayer))
                        currentPlayer = players.get(j + 1);
                    j++;
                }
            }
        }
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public void nextTurn() {
        turnNumber++;
        nextPlayer();
    }

    /**
     * Lets the Challenger choose Gods equal to the number of players.
     */
    private void challengerChooseGods() {

        /*
        //todo in view : print gods and their description
        System.out.println("You are the challenger. You shall pick " + game.getNumberOfPlayers()
        + " Gods from the deck: ");
        for(God god : godsDeck) {
            System.out.println(god.getClass().getSimpleName() + "'s Power:");
            System.out.println(god.getDescription());
            System.out.println("");
        }
        */

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


    public void start() {
        challengerChooseGods();
        playersChooseGods();
        challengerChooseStartPlayer();
        setInitialWorkersPosition();

        //start actual game (new method)
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

}
