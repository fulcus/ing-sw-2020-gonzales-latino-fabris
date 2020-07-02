package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.god.God;
import it.polimi.ingsw.server.VirtualView;
import java.util.ArrayList;


/**
 * A player of the game.
 * Every player corresponds to a specific client that is connected to play the game.
 * It's characterized by a unique nickname, a color and a God card.
 * Two workers are assigned to play the game on the board of the game.
 */
public class Player {

    private final Game game;
    private final String nickname;
    private Color color;
    private God god;
    private final ArrayList<Worker> workers;
    private boolean canWinInPerimeter;  //true if can win on perimeter
    private boolean canMoveUp;
    private final VirtualView client;


    /**
     * Creates a Player.
     *
     * @param game     Represents the belonging game of the player.
     * @param nickname The name chosen by the user for the belonging game.
     * @param virtualView Instance of the client associated to this player.
     */
    public Player(Game game, String nickname, VirtualView virtualView) {

        this.game = game;
        this.nickname = nickname;
        this.client = virtualView;
        god = null;
        canWinInPerimeter = true;
        canMoveUp = true;
        workers = new ArrayList<>(2);
        workers.add(new Worker(this, Sex.MALE));
        workers.add(new Worker(this, Sex.FEMALE));
    }


    public VirtualView getClient() {
        return client;
    }


    public String getNickname() {
        return nickname;
    }


    public God getGod() {
        return god;
    }


    /**
     * Assigns to the player a God from the available Gods of the current game
     * @param god god to be assigned.
     */
    public void setGod(God god) {
        this.god = god;
    }


    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Decides if the player has some restriction to win a perimeter cell of the board.
     *
     * @param value true allows the player to win in perimeter, false otherwise
     */
    public void setPermissionToWinInPerimeter(boolean value) {
        canWinInPerimeter = value;
    }


    /**
     * Decides if the player has some restriction to move a worker to a cell which has an higher level
     * than the one the selected worker is currently on.
     *
     * @param value true allows the player to move up, false otherwise
     */
    public void setPermissionToMoveUp(boolean value) {
        canMoveUp = value;
    }


    /**
     * @return True if the Player can move up, false if this player can not move up for the current turn
     */
    public boolean getCanMoveUp() {
        return canMoveUp;
    }


    /**
     * @return True if the Player can win with a worker on the perimeter, false otherwise
     */
    public boolean getCanWinInPerimeter() {
        return canWinInPerimeter;
    }


    public ArrayList<Worker> getWorkers() {
        return workers;
    }


    public Game getGame() {
        return game;
    }


    /**
     * States that the player has lost and removes him and his workers from the game.
     */
    public void lose() {

        //remove workers from board
        for (Worker worker : workers) {
            Cell workerCell = worker.getPosition();
            workerCell.moveOut();
        }

        //remove from players arraylist
        game.removePlayer(this);

    }


}
