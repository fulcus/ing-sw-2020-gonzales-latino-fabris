package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;

import java.util.Scanner;

/**
 * This class is the one that describes the Minotaur behaviour
 */
public class Minotaur implements God {

    private GameController gameController;

    public Minotaur(GameController gameController){
        this.gameController = gameController;
    }


    @Override
    public void move(Worker worker) {
        updateMoveMap(worker);
        movePushBack(worker);
    }

    private void movePushBack(Worker worker){

        while (true) {
            int[] movePosition = getInputMove();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();

            int xWorker = worker.getPosition().getX();
            int yWorker = worker.getPosition().getY();

            Map map = worker.getPlayer().getGame().getMap();
            Cell moveCell = map.findCell(xMove, yMove);
            Worker enemyWorker;

            if (worker.getMoveMap().isAllowedToMoveBoard(xMove, yMove)) {   //if moveCell doesn't exist returns false

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
                        gameController.errorScreen();
                    //also put this in a loop (recursively call moveSwap?)
                }
            } else {
                //worker.setPosition(moveCell);
                //todo controller & view method that returns error

                //also put this in a loop (recursively call moveSwap?)
            }
            gameController.errorScreen();
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


    /**
     * Allows to get the will of the player to move to the next position
     * @return  Array with the direction the player wants to move his worker
     */
    public int[] getInputMove(){
        int[] input = new int[2];
        String playerInput = gameController.getView().askMovementDirection();
        switch (playerInput) {
            case "N" : {
                input[0] = -1;
                input[1] = 0;
                break;
            }
            case "NE" : {
                input[0] = -1;
                input[1] = -1;
                break;
            }
            case "NW" : {
                input[0] = -1;
                input[1] = 1;
                break;
            }
            case "S" : {
                input[0] = 1;
                input[1] = 0;
                break;
            }
            case "SE" : {
                input[0] = 1;
                input[1] = 1;
                break;
            }
            case "SW" : {
                input[0] = 1;
                input[1] = -1;
                break;
            }
            case "W" : {
                input[0] = 0;
                input[1] = -1;
                break;
            }
            case "E" : {
                input[0] = 0;
                input[1] = 1;
                break;
            }
            default : {
                input[0] = 0;
                input[1] = 0;
                break;
            }

        }
        return input;
    }
}
