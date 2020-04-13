package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.controller.UnableToMoveException;
import it.polimi.ingsw.model.Worker;


public class Prometheus extends God {

    public String description =
            "If your Worker does not move up, it may build both before and after moving.";


    public Prometheus(GodController godController) {
        super(godController);
    }

    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        worker.getPlayer().setPermissionToMoveUp(true);

        //should not lose with optional build
        if (worker.getBuildMap().anyAvailableBuildPosition()) {

            if (getGodController().wantToMoveUp()) {
                build(worker);
                worker.getPlayer().setPermissionToMoveUp(false);
            } else
                godController.errorBuildScreen();
        }
        move(worker);
        win(worker);
        build(worker);
    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
