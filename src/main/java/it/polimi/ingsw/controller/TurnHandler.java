package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.god.God;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.view.CLIMainView;

import java.util.ArrayList;

public class TurnHandler {
    private final Game game;
    private final CLIMainView view;
    private final GameController gameController;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private Integer numberOfPlayers;    //is it updated when game.numberOfPlayers is decreased?

    public TurnHandler(Game game, CLIMainView view, GameController gameController) {
        this.game = game;
        currentPlayer = null;
        this.view = view;
        this.gameController = gameController;
        this.players = game.getPlayers();
        numberOfPlayers = game.getNumberOfPlayers();
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * Lets the Challenger choose Gods equal to the number of players.
     */
    private void challengerChooseGods() {
        Player challenger = game.getChallenger();
        view.printAllGods(gameController.getGodsDeck());

        ArrayList<God> godsDeck = gameController.getGodsDeck();

        //lets challenger select the gods
        int i = 0;
        while (i < numberOfPlayers) {

            String chosenGod = view.getGodFromChallenger(challenger.getNickname(),i);
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
        ArrayList<God> alreadyTakenGods = new ArrayList<God>(numberOfPlayers);
        boolean foundGod = false;

        for (Player player : players) {
            foundGod = false;
            while (!foundGod) {
                String inputGod = view.askPlayerGod(player.getNickname());

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


    /**
     * Lets the challenger choose the start player
     * and puts him in the first position of the arraylist players of game.
     */
    private void challengerChooseStartPlayer() {
        String startPlayerNick;
        boolean foundPlayer = false;
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
        for (Player player : players) {
            for (Worker worker : player.getWorkers()) {
                positionSet = false;

                while (!positionSet) {
                    int[] initialPosition = view.askInitialWorkerPosition(worker.getSex());
                    int x = initialPosition[0];
                    int y = initialPosition[1];

                    if (game.getBoard().findCell(x, y) != null && !game.getBoard().findCell(x, y).isOccupied()) {
                        worker.setPosition(x, y);
                        positionSet = true;
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


    protected void setUpTurns() {
        challengerChooseGods();
        playersChooseGods();
        challengerChooseStartPlayer();
        setInitialWorkersPosition();
    }


    protected void startTurnFlow() {
        int cyclicalCounter = 0;
        //int cannotMoveCounter = 0;

        while (true) {

            currentPlayer = players.get(cyclicalCounter);



            /*
            //if none of currentPlayer's workers can move, lose
            if (!worker1.getMoveMap().anyAvailableMovePosition()
                    && !worker2.getMoveMap().anyAvailableMovePosition())
                losePlayer();
            */

            Worker chosenWorker = chooseWorker();

            try {
                currentPlayer.getGod().evolveTurn(chosenWorker);
            } catch (UnableToMoveException ex) {
                //cannotMoveCounter++;
                /*if(cannotMoveCounter == 1)
                //choose other worker
                else*/
                currentPlayer.lose();
                //todo display something from GodController (?)
            } catch (UnableToBuildException ex) {
                currentPlayer.lose();
                //todo display something from GodController (?)
            } finally {
                if (players.size() == 1)
                    players.get(0).getGod().getGodController().winGame();
            }


            cyclicalCounter++;
            if (cyclicalCounter == numberOfPlayers)
                cyclicalCounter = 0;
        }
    }


}

