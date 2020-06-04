package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;

import java.util.ArrayList;


/**
 * Represents the card of the God Charon.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Charon extends God {

    public final String description = "Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if that space is unoccupied.";


    public Charon(GodController godController) {
        super(godController);
    }


    /**
     * The evolution of the turn for the player that holds the Charon God card is different from the abstract implementation.
     * Here we can force to move an enemy to another position at the beginning of the turn.
     *
     * @param worker Selected worker that will act in the current turn.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException, WinException {
        forceMoveEnemy(worker);
        move(worker);
        win(worker);
        build(worker);
    }


    /**
     * If in the opposite direction there's a cell that isn't occupied,
     * the player can choose to move a neighbor enemy worker to that opposite cell.
     *
     * @param worker The selected worker for the current turn.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     */
    public void forceMoveEnemy(Worker worker) throws UnableToMoveException {

        //if worker cannot move, throw exception without waiting for move()
        updateMoveMap(worker);

        Board board = worker.getPlayer().getGame().getBoard();

        ArrayList<Worker> neighboringEnemies = worker.getMoveMap().neighboringEnemyWorkers();
        ArrayList<Worker> movableNeighboringEnemies = new ArrayList<>(4);
        int newEnemyX;
        int newEnemyY;

        if (!neighboringEnemies.isEmpty()) {

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
            if (!movableNeighboringEnemies.isEmpty()) {

                if (!godController.wantToMoveEnemy())
                    return;

                Worker enemyToMove = godController.forceMoveEnemy(movableNeighboringEnemies, worker);

                if (enemyToMove == null)
                    return;

                int newEnemyToMoveX = 2 * worker.getPosition().getX() - enemyToMove.getPosition().getX();
                int newEnemyToMoveY = 2 * worker.getPosition().getY() - enemyToMove.getPosition().getY();

                enemyToMove.setPosition(newEnemyToMoveX, newEnemyToMoveY);
                godController.displayBoard();
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
