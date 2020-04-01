package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;

import java.util.Scanner;

public class Apollo implements God{

    @Override
    public void move(Worker worker) {
        updateMoveMatrix(worker);
        moveSwap(worker);
    }

    private void moveSwap(Worker worker){
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();
        int y = input.nextInt();
        Cell chosenCell = worker.getPlayer().getGame().getMap().findCell(x, y);

        Worker enemyWorker = chosenCell.getWorker(); //si può fare? è safe? e se getWorker() == null ?

        //chosenCell contains enemy worker
        if(chosenCell.hasWorker() && enemyWorker.getPlayer() != worker.getPlayer())
            enemyWorker.setPosition(worker.getPosition());  //swap enemyWorker

        worker.setPosition(x,y);

    }

    public WorkerMoveMap updateMoveMatrix(Worker worker) {
        WorkerMoveMap workersMoveMap = worker.getMoveMap();

        workersMoveMap.cannotStayStill();
        workersMoveMap.cannotMoveInDomeCell();
        workersMoveMap.cannotMoveInFriendlyWorkerCell();

        return workersMoveMap;
    }

}