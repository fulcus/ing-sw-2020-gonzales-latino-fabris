package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;


/**
 * This class is the one that describes the Minotaur behaviour
 */
public class Minotaur implements God {

    private GodController godController;

    public Minotaur(GodController godController){
        this.godController = godController;
    }


    @Override
    public void move(Worker worker) {
        movePushBack(worker);
    }

    private void movePushBack(Worker worker){

        while (true) {
            int[] movePosition = godController.getMovementInput();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();

            int xWorker = worker.getPosition().getX();
            int yWorker = worker.getPosition().getY();

            WorkerMoveMap moveMap = updateMoveMap(worker);
            Board map = worker.getPlayer().getGame().getBoard();
            Cell moveCell = map.findCell(xMove, yMove);

            Worker enemyWorker;

            if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {   //if moveCell doesn't exist returns false

                //forces enemy back and puts worker in its former place
                if (moveCell.hasWorker()) {    //moveMap rules assure that worker in moveCell is enemy
                    enemyWorker = moveCell.getWorker();
                    Cell newEnemyPosition = map.findCell(2 * xMove - xWorker, 2 * yMove - yWorker);

                    //checks if enemy can move in cell
                    if (newEnemyPosition != null && !newEnemyPosition.isOccupied()) {
                        enemyWorker.setPosition(newEnemyPosition);
                        worker.setPosition(moveCell);
                        return;
                    } else    //cannot move in that cell
                        //todo controller & view method that returns error
                        godController.errorScreen();
                    //also put this in a loop (recursively call moveSwap?)
                }
            } else {
                //worker.setPosition(moveCell);
                //todo controller & view method that returns error

                //also put this in a loop (recursively call moveSwap?)
            }
            godController.errorScreen();
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


    public GodController getGodController() {
        return godController;
    }
}
