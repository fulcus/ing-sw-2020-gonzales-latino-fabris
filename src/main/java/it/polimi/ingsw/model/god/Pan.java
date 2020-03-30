package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

public class Pan implements God{

    private int previousLevel;

    public void evolveTurn(Worker w) {
        loose(w);
        previousLevel = w.getPosition().getLevel();
        move(w);
        win(w);
        build(w);
    }

    public void move(Worker w) {
    }

    public void build(Worker w) {
    }

    public boolean win(Worker w){
        if ((w.getPosition().getLevel()==3 && previousLevel==2) || (previousLevel - w.getPosition().getLevel() > 1) )
            return true;
        return false;
    }

    public boolean loose(Worker w) {

    }

}
