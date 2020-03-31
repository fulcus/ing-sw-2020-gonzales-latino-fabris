package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

import java.util.Scanner;

public class Prometheus implements God{

    @Override
    public void evolveTurn(Worker worker) {
        if(!wantToMoveUp()) {
            build(worker);
            worker.getPlayer().cannotMoveUp();
        }
        move(worker);
        win(worker);
        build(worker);
    }

    private boolean wantToMoveUp() {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to move up?");
        String answer = input.nextLine();

        while(true) {

            if (answer.equals("Y")) {
                return false;
            } else if(answer.equals("N")) {
                return true;
            } else
                System.out.println("Type Y or N to answer");
        }
    }

}
