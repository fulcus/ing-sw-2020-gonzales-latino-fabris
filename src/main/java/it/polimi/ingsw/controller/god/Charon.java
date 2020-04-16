package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.controller.UnableToMoveException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;

import java.util.ArrayList;

public class Charon extends God {

    public String description = "Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if that space is unoccupied.";


    public Charon(GodController godController) {
        super(godController);
    }

    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        forceMoveEnemy(worker);
        move(worker);
        win(worker);
        build(worker);
    }


    //ERRORI: 1) dove è il while?
    //2) updateMoveMap sbagliata: non va modificata:
    //la move è standard, forceMoveEnemy è un metodo a parte
    public void forceMoveEnemy(Worker worker) {

        if (!godController.wantToMoveEnemy())
            return;

        WorkerMoveMap moveMap;

        try {
            moveMap = updateMoveMap(worker);
        } catch (UnableToMoveException ex) {
            godController.errorMoveScreen();
            return;
        }


        Board board = worker.getPlayer().getGame().getBoard();

        ArrayList<Worker> neighboringEnemies = moveMap.neighboringEnemyWorkers();
        int newEnemyX;
        int newEnemyY;

        if(!neighboringEnemies.isEmpty()) {

            //for each neighboring enemy calculates opposite position
            //and removes them from arraylist if opposite position is occupied
            for(Worker enemy : neighboringEnemies) {
                newEnemyX = 2 * worker.getPosition().getX() - enemy.getPosition().getX();
                newEnemyY = 2 * worker.getPosition().getY() - enemy.getPosition().getY();
                Cell newEnemyPosition = board.findCell(newEnemyX,newEnemyY);

                if(newEnemyPosition.isOccupied() || newEnemyX>4 || newEnemyX<0 || newEnemyY<0 || newEnemyY>4)
                    neighboringEnemies.remove(enemy);
            }

            //now in neighboringEnemies there are only enemy workers that can be displaced

            Worker enemyToMove = godController.ForceMoveEnemy(neighboringEnemies, worker);

            if(enemyToMove == null)
                return;

            int newEnemyToMoveX = 2 * worker.getPosition().getX() - enemyToMove.getPosition().getX();
            int newEnemyToMoveY = 2 * worker.getPosition().getY() - enemyToMove.getPosition().getY();

            //TODO SE ATHENA IMPEDISCE A TUTTI DI MUOVERSI IN ALTO,
            //SPOSTANDO IL NEMICO IN DIREZIONE OPPOSTA; DEVO ASSICURARMI
            //CHE NON SALGA. E poi una volta mosso il nemico non devo rifare update map?
            enemyToMove.setPosition(newEnemyToMoveX,newEnemyToMoveY);
            godController.displayBoard();

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


        moveMap.printMap();

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
