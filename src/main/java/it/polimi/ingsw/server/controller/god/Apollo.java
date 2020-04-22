package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;


public class Apollo extends God {

    public String description = "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";

    public Apollo(GodController godController) {
        super(godController);
    }


    @Override
    public void move(Worker worker) throws UnableToMoveException {
        moveSwap(worker);
    }

    private void moveSwap(Worker worker) throws UnableToMoveException {

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


    public WorkerMoveMap updateMoveMap(Worker worker) throws UnableToMoveException {
        WorkerMoveMap moveMap = worker.getMoveMap();
        moveMap.resetMap();

        moveMap.updateCellsOutOfMap();
        moveMap.updateMoveUpRestrictions();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInDomeCell();
        moveMap.cannotMoveInFriendlyWorkerCell();

        moveMap.printMap();    //debugging

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