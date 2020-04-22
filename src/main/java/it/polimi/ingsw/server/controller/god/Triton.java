package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;


public class Triton extends God{

    public String description = "Each time your Worker moves into a perimeter space, it may immediately move again.";

    private Cell initialPosition;

    public Triton(GodController godController) {
        super(godController);
    }

    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        initialPosition = worker.getPosition();
        move(worker);
        win(worker);
        while(worker.getPosition().isInPerimeter()) {
            initialPosition = worker.getPosition();
            if (!godController.wantToMoveAgain())
                break;
            moveAgain(worker);
            win(worker);
        }
        //win(worker);
        build(worker);
    }


    private void moveAgain(Worker worker) {

        /*if (!godController.wantToMoveAgain())
            return;
         */

        while (true) {

            WorkerMoveMap moveMap;
            try {
                moveMap = updateMoveMap(worker);
            } catch (UnableToMoveException ex) {
                godController.errorMoveScreen();
                return;
            }

            int[] secondMovePosition = godController.getInputMove();
            int xMove = secondMovePosition[0] + worker.getPosition().getX();
            int yMove = secondMovePosition[1] + worker.getPosition().getY();

            Cell secondMoveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

            if (secondMoveCell != initialPosition && moveMap.isAllowedToMoveBoard(xMove,yMove)) {
                worker.setPosition(xMove, yMove);
                godController.displayBoard();
                return;
            } else
                godController.errorMoveScreen();

            //Asking if the player is sure to keep going on moving on the board
            if (!godController.errorMoveDecisionScreen())
                return;
        }
    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}

