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