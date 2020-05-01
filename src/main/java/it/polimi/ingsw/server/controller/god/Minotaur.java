package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;


/**
 * This class is the one that describes the Minotaur behaviour
 */
public final class Minotaur extends God {

    public String description = "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";


    public Minotaur(GodController godController){
        super(godController);
    }


    @Override
    public void move(Worker worker) throws UnableToMoveException {
        movePushBack(worker);
    }

    private void movePushBack(Worker worker) throws UnableToMoveException {

        WorkerMoveMap moveMap = updateMoveMap(worker);
        Board map = worker.getPlayer().getGame().getBoard();

        while (true) {
            int[] movePosition = godController.getInputMove();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();

            int xWorker = worker.getPosition().getX();
            int yWorker = worker.getPosition().getY();


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
                        godController.displayBoard();
                        return;
                    } else { //cannot move in that cell
                        godController.errorMoveScreen();
                    }

                } else {
                    worker.setPosition(xMove, yMove);
                    godController.displayBoard();
                    return;
                }

            } else
                godController.errorMoveScreen();



        }
    }


    public WorkerMoveMap updateMoveMap(Worker worker) throws UnableToMoveException {
        WorkerMoveMap moveMap = worker.getMoveMap();
        moveMap.resetMap();

        moveMap.updateCellsOutOfMap();
        moveMap.updateMoveUpRestrictions();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInDomeCell();
        moveMap.cannotMoveInFriendlyWorkerCell();

        //moveMap.printMap();    //debugging


        if(!moveMap.anyAvailableMovePosition())
            throw new UnableToMoveException();

        return moveMap;
    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
