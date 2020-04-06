package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;

import java.util.Scanner;

public class Triton implements God{

    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        while(worker.getPosition().isInPerimeter()) {
            moveAgain(worker);
            win(worker);
        }
        win(worker);
        build(worker);
    }


    private void moveAgain(Worker worker) {

        //todo View + Controller ask if wants to move again, if yes returns position, if not returns null
        int[] secondMovePosition = getInputMoveAgain();
        if (secondMovePosition == null)
            return;


        WorkerMoveMap moveMap = updateMoveMap(worker);

        int xMove = secondMovePosition[0];
        int yMove = secondMovePosition[1];


        if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {
            worker.setPosition(xMove, yMove);
        } else {
            //todo View error + loop
        }

    }

}

