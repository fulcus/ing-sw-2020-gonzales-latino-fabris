package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class Hera implements God{

    private GodController godController;
    public String description = "An opponent cannot win by moving into a perimeter space.";


    public Hera(GodController godController){
        this.godController = godController;
    }


    @Override
    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);
        cannotWinInPerimeterRestriction(worker);
    }

    private void cannotWinInPerimeterRestriction(Worker worker) {
        for(Player p : worker.getPlayer().getGame().getPlayers()) {

            if (p != worker.getPlayer()) {
                p.setPermissionToWinInPerimeter(false);
            }
        }
    }


    public GodController getGodController() {
        return godController;
    }
}

