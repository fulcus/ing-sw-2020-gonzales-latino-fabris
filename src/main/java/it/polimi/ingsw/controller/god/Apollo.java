package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;


public class Apollo implements God{

    private GameController gameController;

    public Apollo (GameController gameController){
        this.gameController=gameController;
    }

    @Override
    public void move(Worker worker) {
        moveSwap(worker);
    }

    private void moveSwap(Worker worker){

        while(true) {
            int[] movePosition = getInputMove();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();

            WorkerMoveMap moveMap = updateMoveMap(worker);
            Cell moveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);
            Worker enemyWorker;


            if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {   //if moveCell doesn't exist returns false

                //swaps enemy and worker
                if (moveCell.hasWorker()) {    //moveMap rules assure that worker in moveCell is enemy
                    enemyWorker = moveCell.getWorker();
                    enemyWorker.setPosition(worker.getPosition());
                }
                worker.setPosition(xMove, yMove);
                return;
            } else {
                gameController.errorScreen();
            }
        }

    }


    public WorkerMoveMap updateMoveMap(Worker worker) {
        WorkerMoveMap moveMap = worker.getMoveMap();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInDomeCell();
        moveMap.cannotMoveInFriendlyWorkerCell();
        moveMap.updateMoveUpRestrictions();

        if(!moveMap.anyAvailableMovePosition())
            //todo Controller lose

        return moveMap;
    }







    public GameController getGameController() {
        return gameController;
    }
}