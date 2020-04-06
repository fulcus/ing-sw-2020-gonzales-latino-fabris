package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;

public class Artemis implements God {

    private Cell initialPosition;

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

        //todo View + Controller ask if wants to move again, if yes return position, if false return null
        int[] secondMovePosition = getInputMoveAgain();
        if (secondMovePosition == null)
            return;

        WorkerMoveMap workersMoveMap = updateMoveMap(worker);

        int xMove = secondMovePosition[0];
        int yMove = secondMovePosition[1];

        Cell secondMoveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

        if (secondMoveCell != initialPosition && workersMoveMap.isAllowedToMoveBoard(xMove, yMove)) {
            worker.setPosition(xMove, yMove);
        } else {
            //todo View error + loop
        }
    }

}
