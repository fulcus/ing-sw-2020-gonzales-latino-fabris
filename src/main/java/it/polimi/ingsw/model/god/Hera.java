package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class Hera implements God{

    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);
        cannotWinInPerimeterRestriction(worker);
    }

    private void cannotWinInPerimeterRestriction(Worker worker) {
        worker.getPlayer().cannotWinInPerimeter();

    }
}

