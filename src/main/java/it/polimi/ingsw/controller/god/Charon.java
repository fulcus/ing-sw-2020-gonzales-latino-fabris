package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;

import java.util.ArrayList;

public class Charon implements God {

    private GodController godController;

    public Charon(GodController godController) {
        this.godController = godController;
    }

    @Override
    public void evolveTurn(Worker worker) {
        forceMoveEnemy(worker);
        move(worker);
        win(worker);
        build(worker);
    }

    public void forceMoveEnemy(Worker worker) {
        WorkerMoveMap moveMap = updateMoveMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();


        ArrayList<Worker> neighboringEnemies = moveMap.neighboringEnemyWorkers();
        int newEnemyX;
        int newEnemyY;

        if(!neighboringEnemies.isEmpty()) {

            //for each neighboring enemy calculates opposite position
            //and removes them from arraylist if opposite position is occupied
            for(Worker Enemy : neighboringEnemies) {
                newEnemyX = 2 * worker.getPosition().getX() - Enemy.getPosition().getX();
                newEnemyY = 2 * worker.getPosition().getY() - Enemy.getPosition().getY();
                Cell newEnemyPosition = board.findCell(newEnemyX,newEnemyY);

                if(newEnemyPosition.isOccupied())
                    neighboringEnemies.remove(Enemy);
            }

            //now in neighboringEnemies there are only enemy workers that can be displaced

            //todo View + Controller askForceMoveEnemy prints  "would you like to displace an enemy worker?"
            // if N returns null, if Y prints list of possible workers to displace (passed as parameter)
            // and lets user select one of them and returns it as Worker


            Worker enemyToMove = askForceMoveEnemy(neighboringEnemies);

            if(enemyToMove == null)
                return;

            int newEnemyToMoveX = 2 * worker.getPosition().getX() - enemyToMove.getPosition().getX();
            int newEnemyToMoveY = 2 * worker.getPosition().getY() - enemyToMove.getPosition().getY();

            enemyToMove.setPosition(newEnemyToMoveX,newEnemyToMoveY);

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
