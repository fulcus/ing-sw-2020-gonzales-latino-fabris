package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

public class Pan implements God{

    public void evolveTurn(Worker w) {
        lose(w);
        move(w);
        win(w);
        build(w);
    }

    public void move(Worker w) {
    }

    public void build(Worker w) {
    }

    public boolean win(Worker w){
    }

    public boolean lose(Worker w) {
    }

}
