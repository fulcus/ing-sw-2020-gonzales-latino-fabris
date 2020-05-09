package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;


public class Artemis extends God {

    private Cell initialPosition;
    public final String description = "Your Worker may move one additional time, but not back to its initial space.";


    public Artemis(GodController godController) {
        super(godController);
    }


    @Override
    public void evolveTurn(Worker worker) throws UnableToBuildException, UnableToMoveException, WinException {
        initialPosition = worker.getPosition();
        move(worker);
        win(worker);
        moveAgain(worker);
        win(worker);
        build(worker);
    }


    private void moveAgain(Worker worker) {

        if (!godController.wantToMoveAgain())
            return;

        WorkerMoveMap moveMap;
        try {
            moveMap = updateMoveMap(worker);
        } catch (UnableToMoveException ex) {
            godController.errorMoveScreen();
            return;
        }

        while (true) {

            int[] secondMovePosition = godController.getInputMove();


            int xMove = secondMovePosition[0] + worker.getPosition().getX();
            int yMove = secondMovePosition[1] + worker.getPosition().getY();

            Cell secondMoveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

            if (secondMoveCell != initialPosition && moveMap.isAllowedToMoveBoard(xMove, yMove)) {
                worker.setPosition(xMove, yMove);
                godController.displayBoard();
                return;
            }

            // Asks again to the player if he still wants to move again:
            // if not the method ends and the worker remains on the same cell
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
