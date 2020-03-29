package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

/**
 * This interface allows to see the Gods' main methods
 */

public interface God {

    public void evolveTurn(Worker w);

    public void move(Worker w);

    public void build(Worker w);

    public boolean win(Worker w);

    public boolean loose(Worker w);

}
