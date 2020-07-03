package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;

import java.util.ArrayList;


/**
 * Represents the card of the God Charon.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Charon extends God {

    public final String description = "Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if that space is unoccupied.";
    private boolean hasForcedMoveEnemy;

    public Charon(GodController godController) {
        super(godController);
    }


    /**
     * The evolution of the turn for the player that holds the Charon God card is different from the abstract implementation.
     * Here we can force to move an enemy to another position at the beginning of the turn.
     *
     * @param worker Selected worker that will act in the current turn.
     * @throws UnableToMoveException  The worker isn't allowed to move anywhere.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     * @throws WinException           The worker has reached the third level of a building and so wins the game.
     */
    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException, WinException {
        hasForcedMoveEnemy = false;

        checkUnableToMove(worker);
        forceMoveEnemy(worker);
        move(worker);
        win(worker);
        build(worker);
    }

    private void checkUnableToMove(Worker worker) throws UnableToMoveException {

        //if worker cannot move, throw exception without waiting for move()
        try {
            updateMoveMap(worker);
        } catch (UnableToMoveException ex) {
            //there aren't movable enemies around, hence worker is unable to move
            if (getMovableEnemies(worker) == null) {
                throw new UnableToMoveException();
            }
            //else there is at least one movable enemy, hence forcing him to move
            //will make my worker able to move
        }
    }

    private ArrayList<Worker> getMovableEnemies(Worker worker) {

        Board board = worker.getPlayer().getGame().getBoard();

        ArrayList<Worker> neighboringEnemies = worker.getMoveMap().neighboringEnemyWorkers();
        ArrayList<Worker> movableNeighboringEnemies = new ArrayList<>(4);
        int newEnemyX;
        int newEnemyY;

        if (neighboringEnemies.isEmpty())
            return null;

        //for each neighboring enemy calculates opposite position
        //and removes them from arraylist if opposite position is occupied
        for (Worker enemy : neighboringEnemies) {

            newEnemyX = 2 * worker.getPosition().getX() - enemy.getPosition().getX();
            newEnemyY = 2 * worker.getPosition().getY() - enemy.getPosition().getY();
            Cell newEnemyPosition = board.findCell(newEnemyX, newEnemyY);

            if (newEnemyPosition != null && !newEnemyPosition.isOccupied())
                movableNeighboringEnemies.add(enemy);
        }

        //movableNeighboringEnemies are only enemy workers that can be displaced
        if (movableNeighboringEnemies.isEmpty()) {
            return null;
        } else
            return movableNeighboringEnemies;

    }


    /**
     * If in the opposite direction there's a cell that isn't occupied,
     * the player can choose to move a neighbor enemy worker to that opposite cell.
     *
     * @param worker The selected worker for the current turn.
     */
    private void forceMoveEnemy(Worker worker) {

        ArrayList<Worker> movableEnemies = getMovableEnemies(worker);

        //movableNeighboringEnemies are only enemy workers that can be displaced
        if (movableEnemies == null || !godController.wantToMoveEnemy())
            return;

        Worker enemyToMove = godController.forceMoveEnemy(movableEnemies, worker);

        if (enemyToMove == null)
            return;

        int newEnemyToMoveX = 2 * worker.getPosition().getX() - enemyToMove.getPosition().getX();
        int newEnemyToMoveY = 2 * worker.getPosition().getY() - enemyToMove.getPosition().getY();

        enemyToMove.setPosition(newEnemyToMoveX, newEnemyToMoveY);
        godController.displayBoard();

        hasForcedMoveEnemy = true;
    }


    /**
     * Default rules to move the worker during a turn of the game.
     *
     * @param worker Selected worker that will move.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     */
    @Override
    public void move(Worker worker) throws UnableToMoveException {

        WorkerMoveMap moveMap;
        try {
            moveMap = updateMoveMap(worker);
        } catch (UnableToMoveException ex) {
            if (hasForcedMoveEnemy)
                throw new UnableToMoveException("lose");
            else
                throw new UnableToMoveException();
        }

        while (true) {
            int[] movePosition = getGodController().getMoveInput();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();
            if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {
                worker.setPosition(xMove, yMove);
                godController.displayBoard();
                return;
            } else {
                getGodController().errorMoveScreen();
            }
        }

    }


    public GodController getGodController() {
        return godController;
    }


    public String getDescription() {
        return description;
    }

}
