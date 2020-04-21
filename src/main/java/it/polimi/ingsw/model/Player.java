package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.view.ClientView;

import java.util.ArrayList;

/**
 * A player of the game.
 */
public class Player {

    private final Game game;
    //private ArrayList<PlayerObserver> playerObservers;
    private final String nickname;
    private Color color;
    private God god;
    private final ArrayList<Worker> workers;
    private boolean canWinInPerimeter;  //true if can win on perimeter
    private boolean canMoveUp;
    private final ClientView client;


    /**
     * Creates a Player.
     *
     * @param game     Represents the belonging game of the player.
     * @param nickname The name chosen by the user for the belonging game.
     */
    public Player(Game game, String nickname, ClientView clientView) {

        this.game = game;
        this.nickname = nickname;
        this.client = clientView;
        god = null;
        canWinInPerimeter = true;
        canMoveUp = true;
        //playerObservers = new ArrayList<PlayerObserver>();
        workers = new ArrayList<>(2);
        workers.add(new Worker(this, Sex.MALE));
        workers.add(new Worker(this, Sex.FEMALE));
    }

    public ClientView getClient() {
        return client;
    }



    public String getNickname() {
        return nickname;
    }


    public God getGod() {
        return god;
    }


    /**
     * Allows the player to choose a God from the available gods of the current game
     */
    public void setGod(God god) {
        this.god = god;
    }


    public Color getColor() {
        return color;
    }


    public void setColor(Color color) { this.color = color; }

    /**
     *
     * @param value true allows the player to win in perimeter, false otherwise
     */
    public void setPermissionToWinInPerimeter(boolean value) { canWinInPerimeter = value;}

    /**
     *
     * @param value true allows the player to move up, false otherwise
     */
    public void setPermissionToMoveUp(boolean value) {canMoveUp = value;}

    /**
     * @return True if the Player can move up, false if this player can not move up for the current turn
     */
    public boolean getCanMoveUp() { return canMoveUp;}


    /**
     * @return True if the Player can win with a worker on the perimeter, false otherwise
     */
    public boolean getCanWinInPerimeter() { return canWinInPerimeter; }


    public ArrayList<Worker> getWorkers() {
        return workers;
    }


    public Game getGame() {
        return game;
    }

    public void lose() {

        //remove workers from board
        for(Worker worker : workers) {
            Cell workerCell = worker.getPosition();
            workerCell.moveOut();
        }

        //remove from players arraylist
        game.removePlayer(this);
    }


}
