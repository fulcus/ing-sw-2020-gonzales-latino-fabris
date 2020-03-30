package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

/**
 * This interface allows to see the Gods' main methods
 */

public interface God {

    default void evolveTurn(Worker worker) {
        loseCannotMove(worker);
        move(worker);
        win(worker);
        loseCannotBuild(worker);
        build(worker);
    }

    default void move(Worker worker) {
        Scanner input = new Scanner(System.in);
        System.out.println("Where do you want to move? (input coordinates)");
        int x = input.nextInt();
        int y = input.nextInt();

        worker.setPosition(x,y);

    }

    void build(Worker w);

    boolean win(Worker w);

    boolean loseCannotMove(Worker w);

    boolean loseCannotBuild(Worker w);

}
