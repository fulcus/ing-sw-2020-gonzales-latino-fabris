package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

/**
 * This interface allows to see the Gods' main methods
 */

public interface God {

    String getName();

    void evolveTurn(Worker w);

    void move(Worker w);

    void build(Worker w);

    boolean win(Worker w);

    boolean lose(Worker w);

}
