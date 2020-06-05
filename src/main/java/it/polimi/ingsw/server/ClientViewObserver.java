package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Cell;


/**
 * Useful interface to let a class know the evolution of the states inside the game.
 */
public interface ClientViewObserver {

    /**
     * Calls the procedure that sends new data to update the client view.
     * @param toUpdate Reference to the updated Cell of Model.
     */
    void update(Cell toUpdate);
}
