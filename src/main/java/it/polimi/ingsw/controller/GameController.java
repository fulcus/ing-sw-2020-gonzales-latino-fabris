package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

/**
 * Handles the input given by the view and sends them to the model to change the Game status
 */
public class GameController {

    private Game game;
    private Turn currentTurn;

    public GameController(){
        game = null;
        currentTurn = null;
    }

    public void playerSetting(String pl1, String pl2, String pl3){
        game = new Game(3);
        currentTurn = new Turn(game);
        game.addPlayer(pl1);
        game.addPlayer(pl2);
        game.addPlayer(pl3);
    }

    public void playerSetting(String pl1, String pl2){
        game = new Game(2);
        currentTurn = new Turn(game);
        game.addPlayer(pl1);
        game.addPlayer(pl2);
    }

    public void initializeGame(){
        game.createDeckGods();
        game.addChosenGods();
    }

    public void chooseGod(int player, int god){
        game.getPlayers().get(player).chooseGod(game.getChosenGods());
    }

    /**
     * Increments the turn number and allows to the next player to play
     */
    public void endTurn(){
        currentTurn.nextTurn();
    }


    //PROBABILMENTE è UN CHECK DA FARE NELLA VIEW NEL MOMENTO IN CUI SI INSERISCONO GLI USERNAMES DEI PLAYERS
    public boolean checkUsernames(String pl1, String pl2, String pl3){
        if (pl1.equals(pl2) || pl1.equals(pl3) || pl2.equals(pl3)){
            return false;
        }
        return true;
    }


}
