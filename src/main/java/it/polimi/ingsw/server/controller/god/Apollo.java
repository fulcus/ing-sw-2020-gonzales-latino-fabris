package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;


/**
 * Represents the card of the God Apollo.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Apollo extends God {

    public final String description = "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";


    public Apollo(GodController godController) {
        super(godController);
    }


    /**
     * The difference from the super class method is that here the worker can also move into another enemy worker's position,
     * swapping the position of the two workers.
     *
     * @param worker Selected worker that will move.
     * @throws UnableToMoveException If the worker cannot move anywhere.
     */
    @Override
    public void move(Worker worker) throws UnableToMoveException {

        WorkerMoveMap moveMap = updateMoveMap(worker);

        while (true) {
            int[] movePosition = godController.getInputMove();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();

            Cell moveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

            //if moveCell doesn't exist returns false
            if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {

                //swaps enemy and worker
                //moveMap rules assure that worker in moveCell is enemy
                if (moveCell.hasWorker())
                    worker.swapPosition(moveCell);
                else
                    worker.setPosition(xMove, yMove);

                godController.displayBoard();
                return;
            } else {
                godController.errorMoveScreen();
            }
        }
    }


    /**
     * Updates the worker's MoveMap, taking into account that Apollo can also move into an enemy neighbor position.
     * @param worker worker playing the turn.
     * @return The MoveMap of the selected worker.
     * @throws UnableToMoveException If the worker cannot win anywhere.
     */
    public WorkerMoveMap updateMoveMap(Worker worker) throws UnableToMoveException {
        WorkerMoveMap moveMap = worker.getMoveMap();
        moveMap.resetMap();

        moveMap.updateCellsOutOfMap();
        moveMap.updateMoveUpRestrictions();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInDomeCell();
        moveMap.cannotMoveInFriendlyWorkerCell();


        if (!moveMap.anyAvailableMovePosition())
            throw new UnableToMoveException();

        return moveMap;
    }


    public GodController getGodController() {
        return godController;
    }


    public String getDescription() {
        return description;
    }
}
