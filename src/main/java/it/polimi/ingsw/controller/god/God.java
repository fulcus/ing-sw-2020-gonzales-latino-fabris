package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.*;

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
        WorkerMoveMap moveMap = updateMoveMap(worker);

        //todo Controller method that calls View method that returns int xMovePosition and yMovePosition in array
        int[] movePosition = getInputMovePosition();
        int xMove = movePosition[0];
        int yMove = movePosition[1];


        if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {
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
        WorkerBuildMap buildMap = updateBuildMap(worker);
        Map map = worker.getPlayer().getGame().getMap();
        //todo distinzione tra dome e block. Can only build dome on lvl 3


        int[] buildInput = getInputBuildPosition();  //returns build position + type: block/dome
        int xBuild = buildInput[0];
        int yBuild = buildInput[1];
        int buildType = buildInput[2]; //0 is block, 1 is dome

        Cell buildPosition = null;

        //build Dome
        if (buildType == 1) {

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && map.findCell(xBuild, yBuild).getLevel() == 3) {
                worker.buildDome(xBuild, yBuild);
                buildPosition = map.findCell(xBuild, yBuild);
            } else {
                //todo View + Controller error
            }

        } else if (buildType == 2) {    //build Block
            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && map.findCell(xBuild, yBuild).getLevel() < 3) {
                worker.buildBlock(xBuild, yBuild);
                buildPosition = map.findCell(xBuild, yBuild);

            } else {
                //todo View + Controller error
            }
        }

        return buildPosition;

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