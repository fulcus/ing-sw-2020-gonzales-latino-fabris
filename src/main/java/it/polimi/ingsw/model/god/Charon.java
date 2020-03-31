package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

public class Charon implements God{

    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);
    }


}
