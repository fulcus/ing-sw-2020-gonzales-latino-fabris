package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;


public class Artemis implements God {

    private GameController gameController;
    private Cell initialPosition;


    public Artemis(GameController gameController) {
        this.gameController = gameController;
    }


    @Override
    public void evolveTurn(Worker worker) {
        initialPosition = worker.getPosition();
        move(worker);
        win(worker);
        moveAgain(worker);
        win(worker);
        build(worker);
    }


    private void moveAgain(Worker worker) {

        while (true) {

            int[] secondMovePosition = getInputMoveAgain();
            if (secondMovePosition == null)
                return;
            else {
                WorkerMoveMap workersMoveMap = updateMoveMap(worker);

                int xMove = secondMovePosition[0] + worker.getPosition().getX();
                int yMove = secondMovePosition[1] + worker.getPosition().getY();

                Cell secondMoveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

                if (secondMoveCell != initialPosition && workersMoveMap.isAllowedToMoveBoard(xMove, yMove)) {
                    worker.setPosition(xMove, yMove);
                    return;
                }
                gameController.errorScreen();

            }
        }

    }


    public int[] getInputMoveAgain(){
        String answer = gameController.getView().askMoveAgain();
        int[] input;
        if (answer.equals("Y")) {
            input = getInputMove();
        }
        else
            input = null;

        return input;
    }


    public GameController getGameController() {
        return gameController;
    }
}
