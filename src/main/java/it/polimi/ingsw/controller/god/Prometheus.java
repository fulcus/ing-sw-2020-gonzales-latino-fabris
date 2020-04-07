package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Worker;


public class Prometheus implements God {

    private GodController godController;
    public String description = "If your Worker does not move up, it may build both before and after moving.";


    public Prometheus(GodController godController){
        this.godController = godController;
    }

    @Override
    public void evolveTurn(Worker worker) {
        if (!getGodController().wantToMoveUp()) {
            build(worker);
            worker.getPlayer().setPermissionToMoveUp(false);
        }
        move(worker);
        win(worker);
        build(worker);
    }


    public GodController getGodController() {
        return godController;
    }

}
