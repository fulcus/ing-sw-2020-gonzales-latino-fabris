package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Worker;

public class Hestia implements God{


    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);

    }


}
