package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;


/**
 * Represents the card of the God Prometheus.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Prometheus extends God {

    public final String description = "If your Worker does not move up, it may build both before and after moving.";
    private boolean canMoveUpBefore;

    public Prometheus(GodController godController) {
        super(godController);
    }


    /**
     * The evolution of the turn for the player that holds the Prometheus God card is different from the abstract implementation.
     * Takes also into account that the selected worker can build before moving.
     *
     * @param worker Selected worker that will act in the current turn.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     * @throws UnableToMoveException  The worker isn't allowed to move anywhere.
     * @throws WinException           The worker has reached the third level of a building and so wins the game.
     */
    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException, WinException {
        buildBefore(worker);
        move(worker);
        win(worker);
        build(worker);
    }


    /**
     * If the player decides to not move up and move staying o the same level (or lower)
     * the player is allowed to build twice, even before the move.
     *
     * @param worker The selected worker for this turn.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     */
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
            godController.errorBuildScreen();
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


    /**
     * As the default god's move, but if the player decided to not move up,
     * the worker cannot actually do it.
     *
     * @param worker Selected worker that will move.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     */
    @Override
    public void move(Worker worker) throws UnableToMoveException {

        super.move(worker);

        //reset canMoveUp permission to its original state (to avoid interference with Athena)
        worker.getPlayer().setPermissionToMoveUp(canMoveUpBefore);
    }

    /**
     * Sets the permissions to move of the selected worker.
     * It is called at the beginning of each move, which will then comply with the matrix.
     *
     * @param worker worker playing the turn.
     * @return The WorkerMoveMap of the worker chosen for this turn.
     * @throws UnableToMoveException signals that the worker cannot move anywhere
     */
    @Override
    public WorkerMoveMap updateMoveMap(Worker worker) throws UnableToMoveException {

        WorkerMoveMap moveMap = worker.getMoveMap();
        moveMap.reset();

        moveMap.updateCellsOutOfMap();
        moveMap.updateMoveUpRestrictions();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInOccupiedCell();

        //moveMap.printMap();    //debugging

        if (!moveMap.anyAvailableMovePosition())
            throw new UnableToMoveException("lose");

        return moveMap;
    }


    public GodController getGodController() {
        return godController;
    }


    public String getDescription() {
        return description;
    }

}
