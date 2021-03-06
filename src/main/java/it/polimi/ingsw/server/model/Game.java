package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.server.controller.god.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * Represents the game with its main components.
 * Allows to create the board where the game will be held
 * and to add the players that will participate to it.
 */
public class Game {

    private final Board board;
    private Integer numberOfPlayers;
    private final ArrayList<Player> players;
    private final ArrayList<God> chosenGods;
    private Player challenger;


    /**
     * Creates a new game.
     *
     * @param numberOfPlayers Number of players of the game.
     */
    public Game(int numberOfPlayers) {
        board = new Board();
        this.numberOfPlayers = numberOfPlayers; //autoboxing
        players = new ArrayList<>(numberOfPlayers);
        chosenGods = new ArrayList<>(numberOfPlayers);
    }


    /**
     * Adds a new player to the game.
     * and chooses challenger if target number of players has been reached.
     *
     * @param nickname Nickname chosen by the player.
     * @param  virtualView instance of the client virtual view.
     * @return player that was just created.
     */
    public Player addPlayer(String nickname, VirtualView virtualView) {
        Player newPlayer = new Player(this, nickname, virtualView);
        players.add(newPlayer);

        if (players.size() == numberOfPlayers)
            randomChallenger();

        return newPlayer;
    }


    /**
     * Picks a challenger randomly from the players of this game.
     */
    private void randomChallenger() {
        Random rand = new Random();
        int challengerIndex = rand.nextInt(numberOfPlayers);
        challenger = players.get(challengerIndex);

        //set challenger as last of arraylist players
        int lastIndex = numberOfPlayers - 1;
        Player temp = players.get(lastIndex);
        players.set(lastIndex, challenger);
        players.set(challengerIndex, temp);
    }


    /**
     * Lets the Challenger choose the Gods that will be used in the game.
     *
     * @param godChosenByChallenger God chosen by the Challenger.
     */
    public void addGodChosenByChallenger(God godChosenByChallenger) {
        chosenGods.add(godChosenByChallenger);
    }


    public Board getBoard() {
        return board;
    }


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }


    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }


    public Player getChallenger() {
        return challenger;
    }


    public ArrayList<God> getChosenGods() {
        return chosenGods;
    }


    /**
     * Removes a player from the game.
     *
     * @param loser The player that will be removed.
     */
    public void removePlayer(Player loser) {
        chosenGods.remove(loser.getGod());
        players.remove(loser);
        numberOfPlayers--;
    }

}
