package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.*;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final Map map;
    private final int numberOfPlayers;
    private ArrayList<Player> players;
    private God[] deckGods;
    private ArrayList<God> chosenGods;
    private Player challenger;

    /** Creates a new game.
     * @param numberOfPlayers Number of players of the game.
     */
    public Game(int numberOfPlayers) {
        map = new Map();
        this.numberOfPlayers = numberOfPlayers;
        players = new ArrayList<>(numberOfPlayers);
        deckGods = createDeckGods();
        chosenGods = new ArrayList<God>(getNumberOfPlayers());
    }

    /** Adds a new player to the game and chooses challenger if target number of players has been reached.
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

    /**
     * Creates the deck where we can find the God cards of the game
     * @return The full deck of the Gods
     */
    private God[] createDeckGods(){
        God[] god = new God[14];
        deckGods[0] = new Apollo();
        deckGods[1] = new Artemis();
        deckGods[2] = new Athena();
        deckGods[3] = new Atlas();
        deckGods[4] = new Charon();
        deckGods[5] = new Demeter();
        deckGods[6] = new Hephaestus();
        deckGods[7] = new Hera();
        deckGods[8] = new Hestia();
        deckGods[9] = new Minotaur();
        deckGods[10] = new Pan();
        deckGods[11] = new Prometheus();
        deckGods[12] = new Triton();
        deckGods[13] = new Zeus();
        return god;
    }

    public void addChosenGods() {
        chosenGods = getChallenger().chooseInitialGods(deckGods);
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
