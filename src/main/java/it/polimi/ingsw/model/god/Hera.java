package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

public class Hera implements God{

    public void evolveTurn(Worker w) {
        loose(w);
        move(w);
        win(w);
        build(w);
        limitWin(w);
    }

    public void move(Worker w) {
    }

    public void build(Worker w) {
    }

    public boolean win(Worker w){
    }

    public boolean loose(Worker w) {
    }

    public void limitWin(Worker w){

    }
}
