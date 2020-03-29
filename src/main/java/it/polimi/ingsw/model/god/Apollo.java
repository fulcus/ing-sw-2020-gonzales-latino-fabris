package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

public class Apollo implements God{

    public void evolveTurn(Worker w) {
        loose(w);
        move(w);
        moveSwap(w);
        win(w);
        build(w);
    }

    public void move(Worker w) {
    }

    public void build(Worker w) {
    }

    public boolean win(Worker w){
    }

    public boolean loose(Worker w) {
    }

    public void moveSwap(Worker w){

    }


}
