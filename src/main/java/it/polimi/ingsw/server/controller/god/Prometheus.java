package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;


public class Prometheus extends God {

    public final String description = "If your Worker does not move up, it may build both before and after moving.";
    private boolean canMoveUpBefore;

    public Prometheus(GodController godController) {
        super(godController);
    }


    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException, WinException {
        buildBefore(worker);
        move(worker);
        win(worker);
        build(worker);

    }


    private void buildBefore(Worker worker) throws UnableToMoveException {

        Player player = worker.getPlayer();
        //save canMoveUp in case athena set it to false
        canMoveUpBefore = player.getCanMoveUp();

        //if worker cannot move, throw exception without waiting for move()
        updateMoveMap(worker);


        //if worker cannot build before move go straight to move
        try {
            updateBuildMap(worker);
        } catch (UnableToBuildException ex) {
            return;
        }


        if (godController.wantToBuildAgain(this)) {

            //shouldn't lose for optional move
            try {
                build(worker);

            } catch (UnableToBuildException ex) {
                godController.errorBuildScreen();
                return;
            }

            //can't move up, bc he chose to build before moving
            worker.getPlayer().setPermissionToMoveUp(false);

        }
    }


    @Override
    public void move(Worker worker) throws UnableToMoveException {

        super.move(worker);

        //reset canMoveUp permission to its original state (to avoid interference with Athena)
        worker.getPlayer().setPermissionToMoveUp(canMoveUpBefore);
    }

    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
