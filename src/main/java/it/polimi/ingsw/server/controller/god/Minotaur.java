package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;

import java.util.ArrayList;


/**
 * Represents the card of the God Minotaur.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Minotaur extends God {

    public final String description = "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";


    public Minotaur(GodController godController) {
        super(godController);
    }


    /**
     * The minotaur move allows also to push back an enemy worker, if the cell behind the enemy (following the same direction) is free.
     * Default move is still valid.
     *
     * @param worker Selected worker that will move.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     */
    @Override
    public void move(Worker worker) throws UnableToMoveException {

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
            //if moveCell doesn't exist returns false
            if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {

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

    private void updateUnpushableEnemies(Worker worker) {

        WorkerMoveMap moveMap = worker.getMoveMap();

        Board map = worker.getPlayer().getGame().getBoard();
        ArrayList<Worker> enemies = moveMap.neighboringEnemyWorkers();

        int xWorker = worker.getPosition().getX();
        int yWorker = worker.getPosition().getY();
        int xEnemy, yEnemy;

        for (Worker enemy : enemies) {

            xEnemy = enemy.getPosition().getX();
            yEnemy = enemy.getPosition().getY();

            Cell newEnemyPosition = map.findCell(2 * xEnemy - xWorker, 2 * yEnemy - yWorker);

            int relativeX = xEnemy - xWorker + 1;
            int relativeY = yEnemy - yWorker + 1;

            //checks if enemy can move in cell
            if (newEnemyPosition == null || newEnemyPosition.isOccupied()) {
                moveMap.setCell(relativeX, relativeY, false);
            }
            //else leaves previous value in cell (might be false for other reasons)
        }

    }


    /**
     * The difference from the default method is that the worker can move into an enemy occupied cell.
     *
     * @param worker worker playing the turn.
     * @return The WorkerMoveMap of the chosen worker of the current turn.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     */
    @Override
    public WorkerMoveMap updateMoveMap(Worker worker) throws UnableToMoveException {
        WorkerMoveMap moveMap = worker.getMoveMap();
        moveMap.reset();

        moveMap.updateCellsOutOfMap();
        moveMap.updateMoveUpRestrictions();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInDomeCell();
        moveMap.cannotMoveInFriendlyWorkerCell();

        updateUnpushableEnemies(worker);

        //moveMap.printMap();    //debugging

        if (!moveMap.anyAvailableMovePosition())
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
