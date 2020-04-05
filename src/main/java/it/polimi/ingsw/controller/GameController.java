package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.god.*;
import it.polimi.ingsw.view.*;
import java.lang.Class.*;


import java.util.ArrayList;

/**
 * Handles the input given by the view and sends them to the model to change the Game status
 */
public class GameController {

    private Game game;
    private TurnHandler turnHandler;
    private CLIMainView view;

    public GameController(){
        game = null;
        turnHandler = null;
    }

    public void playerSetting(ArrayList<String> playerUsernames){
        game = new Game(playerUsernames.size());
        turnHandler = new TurnHandler(game);
        game.addPlayer(playerUsernames.get(0));
        game.addPlayer(playerUsernames.get(1));
        if (playerUsernames.size() == 3)
            game.addPlayer(playerUsernames.get(2));
    }

    /**
     * Allows to create the deck of God cards of the game
     * Allows to set the chosen Gods of the game thanks to the challenger
     * Allows to assign the God to every player of the game
     */
    public void initializeGame(){
        game.createDeckGods();
        game.getChallenger().chooseInitialGods(chooseInitialGod(view.askGameGods()));
        chooseGod(view.askPlayingGod());
    }

    /**
     * Allows to assign the God to every player of the game
     * @param choices Is the ordered list of gods to assign to the players
     */
    public void chooseGod(ArrayList<Integer> choices){
        int i=0;
        while(i<game.getNumberOfPlayers()){
            turnHandler.getCurrentPlayer().setGod(choices.get(i));
        }
    }

    /**
     * Allows to add to the list of the chosenGods of the game the ones that the challenger decided to choose
     * @param chosen Contains the challenger inputs to the Gods he wants to play with
     * @return The list of the Gods
     */
    public ArrayList<God> chooseInitialGod(ArrayList<Integer> chosen){
        int i=0;
        ArrayList<God> gods = new ArrayList<God>(game.getNumberOfPlayers());
        while(i<game.getNumberOfPlayers()){
            switch (chosen.get(i)) {
                case 1 :
                    gods.add(i, new Apollo());
                    break;
                case 2 :
                    gods.add(i, new Artemis());
                    break;
                case 3:
                    gods.add(i, new Athena());
                    break;
                case 4 :
                    gods.add(i, new Atlas());
                    break;
                case 5 :
                    gods.add(i, new Charon());
                    break;
                case 6 :
                    gods.add(i, new Demeter());
                    break;
                case 7 :
                    gods.add(i, new Hephaestus());
                    break;
                case 8 :
                    gods.add(i, new Hera());
                    break;
                case 9 :
                    gods.add(i, new Hestia());
                    break;
                case 10 :
                    gods.add(i, new Minotaur());
                    break;
                case 11 :
                    gods.add(i, new Pan());
                    break;
                case 12 :
                    gods.add(i, new Prometheus());
                    break;
                case 13 :
                    gods.add(i, new Triton());
                    break;
                case 14 :
                    gods.add(i, new Zeus());
                    break;
                default:break;
            }
            i++;
        }
        return gods;
    }

    /**
     * Increments the turn number and allows to the next player to play
     */
    public void endTurn(){
        turnHandler.nextTurn();
    }


    /**
     * Allows to print all the deck of Gods' cards
     */
    public void printAllGods(){
        System.out.println("Here's the Gods' card list:\n");
        for(int i=1; i<=game.getDeckGods().length; i++) {
            System.out.println(i + "]  " + game.getDeckGods()[i].getClass().getSimpleName());
        }
    }

    /**
     * Allows to print the Gods that the challenger has chosen for the game
     */
    public void printGameGods(){
        for(int i=1; i<=game.getChosenGods().size(); i++){
            System.out.println(i + "]  " + game.getChosenGods().get(i).getClass().getSimpleName());
        }
    }

}
