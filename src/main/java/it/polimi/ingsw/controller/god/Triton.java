package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;


public class Triton implements God{

    private GodController godController;
    public String description = "Each time your Worker moves into a perimeter space, it may immediately move again.";

    private Cell initialPosition;

    public Triton(GodController godController) {
        this.godController = godController;
    }

    public void evolveTurn(Worker worker) {
        initialPosition = worker.getPosition();
        move(worker);
        win(worker);
        while(worker.getPosition().isInPerimeter()) {
            initialPosition = worker.getPosition();
            moveAgain(worker);
            win(worker);
        }
        win(worker);
        build(worker);
    }


    private void moveAgain(Worker worker) {

        while (true) {
            int[] secondMovePosition = godController.getInputMoveAgain();
            if (secondMovePosition == null)
                return;

            WorkerMoveMap moveMap = updateMoveMap(worker);

            int xMove = secondMovePosition[0] + worker.getPosition().getX();
            int yMove = secondMovePosition[1] + worker.getPosition().getY();

            Cell secondMoveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

            if (secondMoveCell != initialPosition && moveMap.isAllowedToMoveBoard(xMove,yMove)) {
                worker.setPosition(xMove, yMove);
            } else {
                godController.errorScreen();
            }
        }
    }


    public GodController getGodController() {
        return godController;
    }
}

