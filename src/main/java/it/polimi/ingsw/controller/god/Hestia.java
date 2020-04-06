package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Worker;

public class Hestia implements God{

    private GameController gameController;

    public Hestia(GameController gameController) {
        this.gameController = gameController;
    }


    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);

    }

    public GameController getGameController() {
        return gameController;
    }

}
