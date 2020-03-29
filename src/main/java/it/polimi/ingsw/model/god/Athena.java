package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

public class Athena implements God{

    public void evolveTurn(Worker w) {
        lose(w);
        move(w);
        win(w);
        build(w);
        verifyRestriction(w);
    }

    public void move(Worker w) {
    }

    public void build(Worker w) {
    }

    public boolean win(Worker w){
    }

    public boolean lose(Worker w) {
    }

    public void verifyRestriction(Worker w){

    }
}
