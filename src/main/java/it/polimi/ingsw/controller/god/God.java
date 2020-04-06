package it.polimi.ingsw.controller.god;


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

        //todo Controller method that calls View method that returns int xMovePosition and yMovePosition in array
        int[] movePosition = getInputMovePosition();
        int xMove = movePosition[0];
        int yMove = movePosition[1];


        if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {
            worker.setPosition(xMove, yMove);
        }else {
            //todo View error + loop
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
        WorkerMoveMap moveMap = worker.getMoveMap();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInOccupiedCell();
        moveMap.updateMoveUpRestrictions();
        moveMap.updateCellsOutOfMap();

        return moveMap;
    }


    default WorkerBuildMap updateBuildMap(Worker worker) {
        WorkerBuildMap buildMap = worker.getBuildMap();

        buildMap.cannotBuildUnderneath();
        buildMap.cannotBuildInWorkerCell();
        buildMap.cannotBuildInDomeCell();
        buildMap.updateCellsOutOfMap();

        return buildMap;
    }


}