package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Worker;


public class Prometheus implements God {

    private GameController gameController;

    public Prometheus(GameController gameController){
        this.gameController = gameController;
    }

    @Override
    public void evolveTurn(Worker worker) {
        if (!wantToMoveUp()) {
            build(worker);
            worker.getPlayer().setPermissionToMoveUp(false);
        }
        updateMoveMap(worker);
        move(worker);
        win(worker);
        build(worker);
    }

    public boolean wantToMoveUp(){
        String answer = gameController.getView().askWantToMoveUp();

        while(true) {

            if (answer.equals("Y")) {
                return false;
            } else if(answer.equals("N")) {
                return true;
            } else
                gameController.errorScreen();
        }
    }



}
