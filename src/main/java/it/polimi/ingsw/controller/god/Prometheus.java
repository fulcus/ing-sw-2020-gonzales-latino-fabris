package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Worker;


public class Prometheus implements God {

    private GodController godController;

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
