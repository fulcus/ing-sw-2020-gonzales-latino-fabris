package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Map;
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

    default void build(Worker w){

        //TODO evitare che una volta scelta la cella, non può più cambiare fare(while nel while)
        Scanner input = new Scanner(System.in);
        String buildingName;
        int buildingX;
        int buildingY;

        System.out.println("Insert the x y position where you want to build in");

        do {
            buildingX = input.nextInt();
            buildingY = input.nextInt();

            //questo controllo va fatto con un metodo static in Map
            if (buildingX < Map.SIDE && buildingY < Map.SIDE && !(w.getPlayer().getGame().getMap().findCell(buildingX,buildingY).isOccupied()))
                break;

            System.out.println("Invalid position or occupied cell! It must be in a 5X5 map.TRY AGAIN");

        } while (true);



        do {

            if(w.getPlayer().getGame().getMap().findCell(buildingX,buildingY).getLevel() == 3)
            {
                System.out.println("You can build a Dome here, type Dome to build");
                buildingName = input.nextLine();

                if(buildingName.equals("Dome")) {
                    w.buildDome(buildingX, buildingY);
                    break;
                }

            }

            else {

                System.out.println("You can build a Block here, type Block to build");
                buildingName = input.nextLine();

                if(buildingName.equals("Block")) {
                    w.buildBlock(buildingX, buildingY);
                    break;
                }

            }

        } while (true);

        System.out.println(buildingName + "successfully built");

    }

    boolean win(Worker w);

    boolean loseCannotMove(Worker w);

    boolean loseCannotBuild(Worker w);

}
