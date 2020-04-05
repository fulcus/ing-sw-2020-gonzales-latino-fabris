package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.god.*;
import it.polimi.ingsw.view.*;


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
     */
    public void initializeGame(){
        game.createDeckGods();
        game.getChallenger().chooseInitialGods(chooseInitialGod(view.askGameGods()));
    }

    /**
     * Allows to assign the God to every player of the game
     * @param choices Is the ordered list of gods to assign to the players
     */
    public void chooseGod(ArrayList<Integer> choices){
        int i=0;
        while(i<game.getNumberOfPlayers()){
            turnHandler.getCurrentPlayer().setGod(choices.get(i));
            turnHandler.nextPlayer();
        }
    }

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



}
