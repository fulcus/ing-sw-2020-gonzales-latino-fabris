package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;

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

        while (true) {
            int[] movePosition = getInputMove();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();


            if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {
                worker.setPosition(xMove, yMove);
            } else {
                getGameController().errorScreen();
            }
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
        Board board = worker.getPlayer().getGame().getBoard();


        int[] buildInput = getInputBuildPosition();  //returns build position + type: block/dome
        int xBuild = buildInput[0];
        int yBuild = buildInput[1];
        int buildType = buildInput[2]; //0 is block, 1 is dome

        Cell buildPosition = board.findCell(xBuild,yBuild);

        //build Dome
        if (buildType == 1) {

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() == 3) {
                worker.buildDome(xBuild, yBuild);

            } else {
                buildPosition = null;
                //todo View + Controller error: cant build dome there
            }

        } else if (buildType == 2) {    //build Block
            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() < 3) {
                worker.buildBlock(xBuild, yBuild);

            } else {
                buildPosition = null;
                //todo View + Controller error: cant build block there
            }
        } else {
            //todo View + Controller error: wrong input for build type (can be 1 or 2)
        }

        return buildPosition;

    }


    /**
     * Checks if win conditions are met.
     *
     * @param worker The selected worker. Used to get his player.
     * @return True if the worker's player has won. False otherwise.
     */
        default void win(Worker worker) {
        boolean won;
        boolean normalCondition = worker.getLevel() == 3 && worker.getLevelVariation() == 1;
        if (worker.getPlayer().getCanWinInPerimeter())
            won = normalCondition;
        else
            won = normalCondition && !worker.getPosition().isInPerimeter();


        if (won)
            worker.getPlayer().getGod().getGameController().winGame();
    }


    /**
     * Sets the permissions to move of the selected worker.
     *
     * @param worker Selected worker.
     */
    //will be called at the beginning of each move, which will then comply with the matrix.
    default WorkerMoveMap updateMoveMap(Worker worker) {
        WorkerMoveMap moveMap = worker.getMoveMap();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInOccupiedCell();
        moveMap.updateMoveUpRestrictions();
        moveMap.updateCellsOutOfMap();

        if(!moveMap.anyAvailableMovePosition())
            //todo Controller lose

        return moveMap;
    }


    default WorkerBuildMap updateBuildMap(Worker worker) {
        WorkerBuildMap buildMap = worker.getBuildMap();

        buildMap.cannotBuildUnderneath();
        buildMap.cannotBuildInWorkerCell();
        buildMap.cannotBuildInDomeCell();
        buildMap.updateCellsOutOfMap();

        if(!buildMap.anyAvailableBuildPosition())
            //todo Controller lose


        return buildMap;
    }


    /**
     * Allows to get the will of the player to move to the next position
     * @return  Array with the direction the player wants to move his worker
     */
    default int[] getInputMove(){
        int[] input = new int[2];
        String playerInput = getGameController().getView().askMovementDirection();
        switch (playerInput) {
            case "N" : {
                input[0] = -1;
                input[1] = 0;
                break;
            }
            case "NE" : {
                input[0] = -1;
                input[1] = -1;
                break;
            }
            case "NW" : {
                input[0] = -1;
                input[1] = 1;
                break;
            }
            case "S" : {
                input[0] = 1;
                input[1] = 0;
                break;
            }
            case "SE" : {
                input[0] = 1;
                input[1] = 1;
                break;
            }
            case "SW" : {
                input[0] = 1;
                input[1] = -1;
                break;
            }
            case "W" : {
                input[0] = 0;
                input[1] = -1;
                break;
            }
            case "E" : {
                input[0] = 0;
                input[1] = 1;
                break;
            }
            default : {
                input[0] = 0;
                input[1] = 0;
                break;
            }

        }
        return input;
    }


    GameController getGameController();

}