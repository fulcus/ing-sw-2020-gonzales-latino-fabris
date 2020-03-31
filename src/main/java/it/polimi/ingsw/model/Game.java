package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final Map map;
    private final int numberOfPlayers;
    private ArrayList<Player> players;
    private ArrayList<God> chosenGods;
    private Player challenger;

    /** Creates a new game.
     * @param numberOfPlayers Number of players of the game.
     */
    public Game(int numberOfPlayers) {
        map = new Map();
        this.numberOfPlayers = numberOfPlayers;
        players = new ArrayList<>(numberOfPlayers);
        chosenGods = new ArrayList<>(numberOfPlayers);
    }

    /** Adds a new player to the game and chooses challenger if target number of players has beeb reached.
     * @param nickname Nickname chosen by the player.
     */
    //viene fatto check su nickname e colore prima di creare nuovo player
    public void addPlayer(String nickname) {
        players.add(new Player(this, nickname));

        if(players.size() == numberOfPlayers)
            randomChallenger();
    }

    /** Picks a challenger randomly.
     */
    private void randomChallenger() {
        Random rand = new Random();
        int i = rand.nextInt(numberOfPlayers);
        challenger = players.get(i);
    }

    public void addChosenGod(God god) {
        chosenGods.add(god);
    }

    public Map getMap() {
        return map;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
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

}
