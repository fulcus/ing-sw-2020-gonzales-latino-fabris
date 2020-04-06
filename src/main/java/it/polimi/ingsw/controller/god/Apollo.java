package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;

public class Apollo implements God{

    @Override
    public void move(Worker worker) {
        updateMoveMap(worker);
        moveSwap(worker);
    }

    private void moveSwap(Worker worker){

        //todo Controller method that calls View method that returns int xMovePosition and yMovePosition in array
        int[] movePosition = getInputMoveSwap();
        int xMove = movePosition[0];
        int yMove = movePosition[1];
        Cell moveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);
        Worker enemyWorker;


        if (worker.getMoveMap().isAllowedToMoveBoard(xMove, yMove)) {   //if moveCell doesn't exist returns false

            //swaps enemy and worker
            if(moveCell.hasWorker()) {    //moveMap rules assure that worker in moveCell is enemy
                enemyWorker = moveCell.getWorker();
                enemyWorker.setPosition(worker.getPosition());
            }
            worker.setPosition(xMove,yMove);
        } else {
            //todo controller & view method that returns error
            //also put this in a loop (recursively call moveSwap?)
        }

    }


    public WorkerMoveMap updateMoveMap(Worker worker) {
        WorkerMoveMap workersMoveMap = worker.getMoveMap();

        workersMoveMap.cannotStayStill();
        workersMoveMap.cannotMoveInDomeCell();
        workersMoveMap.cannotMoveInFriendlyWorkerCell();
        workersMoveMap.updateMoveUpRestrictions();

        return workersMoveMap;
    }


}