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
        Scanner input = new Scanner(System.in);
        System.out.println("Do you wanna move again?");
        String answer = input.nextLine();

        while(worker.getPosition().isInPerimeter()) {
            if (answer.equals("Y")) {
                int x = input.nextInt();
                int y = input.nextInt();
                worker.setPosition(x,y);

            } else if (answer.equals("N")) {
                System.out.println("Stopped moving.");
                break;
            }
            else
                System.out.println("Type Y or N to answer.");
        }
    }

    private void moveAgain(Worker worker) {

        //todo View + Controller ask if wants to move again, if yes return position, if false return null
        int[] secondMovePosition = getInputMoveAgain();
        if (secondMovePosition == null)
            return;

        WorkerMoveMap workersMoveMap = updateMoveMap(worker);

        int xMove = secondMovePosition[0];
        int yMove = secondMovePosition[1];

        Cell secondMoveCell = worker.getPlayer().getGame().getMap().findCell(xMove, yMove);

        if (secondMoveCell != initialPosition) {
            worker.setPosition(xMove, yMove);
        } else {
            //todo View error + loop
        }

    }


}

}
