package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.*;

import java.util.Scanner;

public class Prometheus implements God {

    @Override
    public void evolveTurn(Worker worker) {
        if (!wantToMoveUp()) {  //todo View & Controller
            build(worker);
            worker.getPlayer().setPermissionToMoveUp(false);
        }
        updateMoveMap(worker);
        move(worker);
        win(worker);
        build(worker);
    }

}
