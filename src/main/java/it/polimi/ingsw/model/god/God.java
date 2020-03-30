package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

/**
 * This interface allows to see the Gods' main methods
 */

public interface God {
    /**
     * Default evolution of the turn: move, checks if win condition is met, builds.
     * @param worker Selected worker that will act in the current turn.
     */
    default void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);
    }

    /**
     * Default rules to move the worker.
     * @param worker Selected worker that will move.
     */
    default void move(Worker worker) {
        Scanner input = new Scanner(System.in);
        System.out.println("Where do you want to move? (input coordinates)");
        int x = input.nextInt();
        int y = input.nextInt();

        worker.setPosition(x,y);

    }

    void build(Worker worker);

    /**
     * Checks if win conditions are met.
     * @param worker The selected worker. Used to get his player.
     * @return True if the worker's player has won. False otherwise.
     */
    //add end game for player if win is true
    default boolean win(Worker worker) {
        for(Worker w : worker.getPlayer().getWorkers()) {
            if (w.getLevel() == 3 && w.getLevelVariation() == 1)
                return true;
        }

        return false;
    }

}