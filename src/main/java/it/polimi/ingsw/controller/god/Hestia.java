package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Worker;

public class Hestia implements God{

    private static final String name = "HESTIA";

    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);

    }


    public String getName(){
        return name;
    }

}
