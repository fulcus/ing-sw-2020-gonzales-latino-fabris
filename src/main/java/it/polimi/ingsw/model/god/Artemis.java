package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class Artemis implements God{

    private Cell initialPosition;

    @Override
    public void evolveTurn(Worker w) {
        initialPosition = w.getPosition();
        move(w);
        moveAgain(w);
        win(w);
        build(w);
    }

    private void moveAgain(Worker worker) {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you wanna move again?");
        String answer = input.nextLine();
        while(true) {

            if (answer.equals("Y")) {
                int x = input.nextInt();
                int y = input.nextInt();
                Cell chosenCell = worker.getPlayer().getGame().getMap().findCell(x, y);

                if (chosenCell != initialPosition) {
                    worker.setPosition(x, y);
                    break;
                }
                System.out.println("You can't move back to the initial space");

            } else if (!answer.equals("N"))
                System.out.println("Write Y or N to answer");
        }
    }

}
