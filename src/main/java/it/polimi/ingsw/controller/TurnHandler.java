package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.CLIMainView;

import java.util.ArrayList;

public class TurnHandler {
    private final Game game;
    private final CLIMainView view;
    private final GameController gameController;

    private Player currentPlayer;
    private int turnNumber;


    public TurnHandler(Game game, CLIMainView view, GameController gameController) {
        this.game = game;
        currentPlayer = null;
        turnNumber = 0;
        this.view = view;
        this.gameController = gameController;
    }


    /**
     * Allows to select the correct player for this turn
     */
    public void nextPlayer() {
        int j = 0;
        //in the initial turn (turn 0) the first player is set to be the first one after the challenger
        if (turnNumber == 0) {
            if (game.getChallenger().equals(game.getPlayers().get(game.getNumberOfPlayers() - 1)))
                currentPlayer = game.getPlayers().get(0);
            else
                while (j < game.getNumberOfPlayers() - 1) {
                    if (game.getPlayers().get(j).equals(game.getChallenger()))
                        currentPlayer = game.getPlayers().get(j + 1);
                    j++;
                }
        }
        //in the turns of the game (counter > 0) the next currentPlayer will be the following one
        //in the ArrayList of the players of the current Game
        else {
            //to assign the next currentPlayer it needs to be distinguished
            // if the current player is the las one of the game.players or not
            if (currentPlayer.equals(game.getPlayers().get(game.getNumberOfPlayers() - 1)))
                currentPlayer = game.getPlayers().get(0);
            else {
                j = 0;
                while (j < game.getNumberOfPlayers() - 1) {
                    if (game.getPlayers().get(j).equals(currentPlayer))
                        currentPlayer = game.getPlayers().get(j + 1);
                    j++;
                }
            }
        }
    }


    /**
     * Allows to set the initial position of the workers of the current player
     *
     * @param gender Selected worker thanks to its gender attribute from the view
     * @param x      Coordinate in the map
     * @param y      Coordinate in the map
     */
    public void setWorkerInitialPosition(Sex gender, int x, int y) {
        if (gender.equals(Sex.MALE))
            currentPlayer.getWorkers().get(0).setPosition(x, y);
        if (gender.equals(Sex.FEMALE))
            currentPlayer.getWorkers().get(1).setPosition(x, y);
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

    public int getTurnNumber() {
        return turnNumber;
    }

    public void nextTurn() {
        turnNumber++;
        nextPlayer();
    }

    /**
     * Lets the Challenger choose Gods equal to the number of players.
     */
    public void challengerChooseGods() {

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
        while (i < game.getNumberOfPlayers()) {
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
    public void playersChooseGods() {
        //remember: Challenger must be last
        //challenger is the last of arraylist [see Game.randomChallenger()]
        ArrayList<God> alreadyTakenGods = new ArrayList<God>(game.getNumberOfPlayers());
        boolean foundGod = false;

        for (Player player : game.getPlayers()) {

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
        //challengerChooseStartPlayer();
        //idea: reorder players arraylist to put startPlayer first and iterate on them
        //setInitialWorkersPosition();

        //start actual game (new method)
    }

}
