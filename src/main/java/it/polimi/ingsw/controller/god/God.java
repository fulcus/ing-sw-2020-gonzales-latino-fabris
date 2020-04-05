package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;

import java.util.Scanner;

/**
 * This interface allows to see the Gods' main methods
 */

public interface God {
    /**
     * Default evolution of the turn: move, checks if win condition is met, builds.
     *
     * @param worker Selected worker that will act in the current turn.
     */
    default void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);
    }


    /**
     * Default rules to move the worker.
     *
     * @param worker Selected worker that will move.
     */
    default void move(Worker worker) {
        WorkerMoveMap workersMatrix = updateMoveMap(worker);

        //todo Controller method that calls View method that returns int xMovePosition and yMovePosition in array
        int[] movePosition = getInputPosition();
        int xMove = movePosition[0];
        int yMove = movePosition[1];


        if (workersMatrix.isAllowedToMoveBoard(xMove, yMove)) {
            worker.setPosition(xMove, yMove);
        }


    }


    /**
     * The standard build action.
     *
     * @param worker This is the current worker.
     * @return It returns the cell wherein the worker has just built.
     */
    default Cell build(Worker worker) {

        //TODO evitare che una volta scelta la cella, non può più cambiare. fare(while nel while)
        //todo distinzione tra dome e block. Can only build dome on lvl 3


        Scanner input = new Scanner(System.in);
        String buildingName;
        Cell buildingCell;
        int buildingX;
        int buildingY;

        System.out.println("Insert the x y position where you want to build in");

        do {
            buildingX = input.nextInt();
            buildingY = input.nextInt();

            //devi controllare anche che stia nella mappa.
            //questo controllo va fatto con un metodo static in Map
            if (!(worker.getPlayer().getGame().getMap().findCell(buildingX, buildingY).isOccupied())) {

                buildingCell = worker.getPlayer().getGame().getMap().findCell(buildingX, buildingY);
                break;
            }

            System.out.println("You cannot build here. Insert a new position");

        } while (true);


        do {

            if (buildingCell.getLevel() == 3) {
                System.out.println("You can build a Dome here, type Dome to build");
                buildingName = input.nextLine();

                if (buildingName.equals("Dome")) {
                    worker.buildDome(buildingX, buildingY);
                    break;
                }

            } else {

                System.out.println("You can build a Block here, type Block to build");
                buildingName = input.nextLine();

                if (buildingName.equals("Block")) {
                    worker.buildBlock(buildingX, buildingY);
                    break;
                }

            }

        } while (true);

        System.out.println(buildingName + "successfully built");

        return buildingCell;

    }


    /**
     * Checks if win conditions are met.
     *
     * @param worker The selected worker. Used to get his player.
     * @return True if the worker's player has won. False otherwise.
     */
    //add end game for player if win is true
    default boolean win(Worker worker) {
        boolean won;
        boolean normalCondition = worker.getLevel() == 3 && worker.getLevelVariation() == 1;
        if (worker.getPlayer().getCanWinInPerimeter())
            won = normalCondition;
        else
            won = normalCondition && !worker.getPosition().isInPerimeter();


        if (won)
        //todo View + Controller call some method to win
    }


    /**
     * Sets the permissions to move of the selected worker.
     *
     * @param worker Selected worker.
     */
    //will be called at the beginning of each move, which will then comply with the matrix.
    default WorkerMoveMap updateMoveMap(Worker worker) {
        WorkerMoveMap workersMoveMap = worker.getMoveMap();

        workersMoveMap.cannotStayStill();
        workersMoveMap.cannotMoveInOccupiedCell();
        workersMoveMap.updateMoveUpRestrictions();
        workersMoveMap.updateCellsOutOfMap(); //todo also with buildMap

        return workersMoveMap;
    }
}