package it.polimi.ingsw.controller.god;

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
        for(Player p : worker.getPlayer().getGame().getPlayers()) {

            if(p != worker.getPlayer()) {
                p.setPermissionToWinInPerimeter(false);
        }

    }
}

