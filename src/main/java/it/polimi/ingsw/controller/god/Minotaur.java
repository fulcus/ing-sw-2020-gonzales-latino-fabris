package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.*;

import java.util.Scanner;

/**
 * This class is the one that describes the Minotaur behaviour
 */
public class Minotaur implements God {


    @Override
    public void move(Worker worker) {
        updateMoveMap(worker);
        movePushBack(worker);
    }

    private void movePushBack(Worker worker){

        //todo Controller method that calls View method that returns int xMovePosition and yMovePosition in array
        int[] movePosition = getInputMove();
        int xMove = movePosition[0];
        int yMove = movePosition[1];

        int xWorker = worker.getPosition().getX();
        int yWorker = worker.getPosition().getY();

        Map map = worker.getPlayer().getGame().getMap();
        Cell moveCell = map.findCell(xMove, yMove);
        Worker enemyWorker;

        if (worker.getMoveMap().isAllowedToMoveBoard(xMove, yMove)) {   //if moveCell doesn't exist returns false

            //forces enemy back and puts worker in its former place
            if(moveCell.hasWorker()) {    //moveMap rules assure that worker in moveCell is enemy
                enemyWorker = moveCell.getWorker();
                Cell newEnemyPosition = map.findCell(2*xMove - xWorker,2*yMove - yWorker);

                //checks if enemy can move in cell
                if(newEnemyPosition != null && !newEnemyPosition.isOccupied()) {
                    enemyWorker.setPosition(newEnemyPosition);
                    worker.setPosition(moveCell);
                }
                else    //cannot move in that cell
                //todo controller & view method that returns error
                //also put this in a loop (recursively call moveSwap?)
            }
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
