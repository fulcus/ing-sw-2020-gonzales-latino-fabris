package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;


public class Triton implements God{

    private GameController gameController;
    private Cell initialPosition;

    public Triton(GameController gameController) {
        this.gameController = gameController;
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
            int[] secondMovePosition = getInputMoveAgain();
            if (secondMovePosition == null)
                return;

            WorkerMoveMap moveMap = updateMoveMap(worker);

            int xMove = secondMovePosition[0] + worker.getPosition().getX();
            int yMove = secondMovePosition[1] + worker.getPosition().getY();

            Cell secondMoveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

            if (secondMoveCell != initialPosition && moveMap.isAllowedToMoveBoard(xMove,yMove)) {
                worker.setPosition(xMove, yMove);
            } else {
                gameController.errorScreen();
            }
        }
    }




    public int[] getInputMoveAgain() {
        String answer = gameController.getView().askMoveAgain();
        int[] input;
        if (answer.equals("Y")) {
            input = gameController.getMovementInput();
        }
        else
            input = null;

        return input;
    }


    public GameController getGameController() {
        return gameController;
    }
}

