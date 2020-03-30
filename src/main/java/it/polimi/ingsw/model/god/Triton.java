package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class Triton implements God{

    public void evolveTurn(Worker worker) {
        move(worker);
        while(worker.getPosition().isInPerimeter())
            moveAgain(worker);
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
}
