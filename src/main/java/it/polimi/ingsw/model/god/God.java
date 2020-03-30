package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

/**
 * This interface allows to see the Gods' main methods
 */

public interface God {
    /**
     * Default evolution of the turn: move, checks if win condition is met, builds.
     * @param worker Selected worker that will act in the current turn.
     */
    default void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);
    }

    /**
     * Default rules to move the worker.
     * @param worker Selected worker that will move.
     */
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



    /**
     * Checks if win conditions are met.
     * @param worker The selected worker. Used to get his player.
     * @return True if the worker's player has won. False otherwise.
     */
    //add end game for player if win is true
    default boolean win(Worker worker) {
        return worker.getLevel() == 3 && worker.getLevelVariation() == 1;
    }

}