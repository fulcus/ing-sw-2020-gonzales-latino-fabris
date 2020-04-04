package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import java.util.Random;

public class TurnHandler {
    private int counter;
    private Game game;
    private Player currentPlayer;

    public TurnHandler(Game game){
        this.game = game;
        currentPlayer = null;
        counter = 0;
    }

    /**
     * Allows to select the correct player for this turn
     */
    public void nextPlayer() {
        int j=0;
        //in the initial turn (turn 0) the first player is set to be the first one after the challenger
        if (counter==0){
            if(game.getChallenger().equals(game.getPlayers().get(game.getNumberOfPlayers()-1)))
                currentPlayer = game.getPlayers().get(0);
            else
                while(j<game.getNumberOfPlayers()-1){
                    if(game.getPlayers().get(j).equals(game.getChallenger()))
                        currentPlayer = game.getPlayers().get(j+1);
                    j++;
            }
        }
        //in the turns of the game (counter > 0) the next currentPlayer will be the following one
        //in the ArrayList of the players of the current Game
        else {
            //to assign the next currentPlayer it needs to be distinguished
            // if the current player is the las one of the game.players or not
            if (currentPlayer.equals(game.getPlayers().get(game.getNumberOfPlayers()-1)))
                currentPlayer = game.getPlayers().get(0);
            else{
                j=0;
                while(j<game.getNumberOfPlayers()-1){
                    if(game.getPlayers().get(j).equals(currentPlayer))
                        currentPlayer = game.getPlayers().get(j+1);
                    j++;
                }
            }
        }
    }

    /**
     * Allows to set the initial position of the workers of the current player
     * @param gender Selected worker thanks to its gender attribute from the view
     * @param x Coordinate in the map
     * @param y Coordinate in the map
     */
    public void setWorkerInit(String gender, int x, int y) {
        if (gender.equals(Sex.MALE))
            currentPlayer.getWorkers().get(0).setPosition(x, y);
        if (gender.equals(Sex.FEMALE))
            currentPlayer.getWorkers().get(1).setPosition(x, y);
    }



    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getCounter(){
        return counter;
    }

    public void nextTurn(){
        counter++;
        nextPlayer();
    }
}
