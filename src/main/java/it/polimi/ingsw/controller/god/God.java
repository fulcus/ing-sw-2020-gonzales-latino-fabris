package it.polimi.ingsw.controller.god;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.*;

/**
 * This interface allows to see the Gods' main methods
 */
public abstract class God {


    protected GodController godController;


    public God(GodController godController) {
        this.godController = godController;
    }


    /**
     * Default evolution of the turn: move, checks if win condition is met, builds.
     *
     * @param worker Selected worker that will act in the current turn.
     */
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        move(worker);
        win(worker);
        build(worker);
    }


    /**
     * Default rules to move the worker.
     *
     * @param worker Selected worker that will move.
     */
    public void move(Worker worker) throws UnableToMoveException {

        WorkerMoveMap moveMap = updateMoveMap(worker);

        while (true) {
            int[] movePosition = getGodController().getInputMove();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();

            if (moveMap.isAllowedToMoveBoard(xMove, yMove)) {
                worker.setPosition(xMove, yMove);
                godController.displayBoard();
                return;
            } else {
                getGodController().errorMoveScreen();
            }
        }


    }


    /**
     * The standard build action.
     *
     * @param worker This is the current worker.
     */
    public void build(Worker worker) throws UnableToBuildException {

        WorkerBuildMap buildMap = updateBuildMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();

        while (true) {
            //returns build position + type: block/dome
            int[] buildInput = godController.getBuildingInput();

            int xBuild = worker.getPosition().getX() + buildInput[0];
            int yBuild = worker.getPosition().getY() + buildInput[1];
            //int buildType = buildInput[2]; //0 is block, 1 is dome

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {


                Cell buildPosition = board.findCell(xBuild, yBuild);

                //build Dome  and fix the condition that if the worker wants to build underneath
                //and the building will be a dome won't be allowed

                if (buildPosition.getLevel() == 3 && !buildPosition.equals(worker.getPosition())) {
                    worker.buildDome(xBuild, yBuild);
                    godController.displayBoard();
                    return;
                }

                //build Block
                else if (buildPosition.getLevel() < 3) {
                    worker.buildBlock(xBuild, yBuild);
                    godController.displayBoard();
                    return;
                }

            } else
                godController.errorBuildScreen();   //input different than 0 or 1
        }
    }



    /**
     * Checks if win conditions are met.
     *
     * @param worker The selected worker. Used to get his player.
     */
    public void win(Worker worker) {
        boolean won;
        boolean normalCondition = worker.getLevel() == 3 && worker.getLevelVariation() == 1;
        if (worker.getPlayer().getCanWinInPerimeter())
            won = normalCondition;
        else
            won = normalCondition && !worker.getPosition().isInPerimeter();


        if (won)
            godController.winGame(worker.getPlayer().getNickname());
    }


    /**
     * Sets the permissions to move of the selected worker.
     *
     * @param worker Selected worker.
     */
    //will be called at the beginning of each move, which will then comply with the matrix.
    public WorkerMoveMap updateMoveMap(Worker worker)
            throws UnableToMoveException {

        WorkerMoveMap moveMap = worker.getMoveMap();
        moveMap.resetMap();

        moveMap.updateCellsOutOfMap();
        moveMap.updateMoveUpRestrictions();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInOccupiedCell();

        moveMap.printMap();    //debugging


        if (!moveMap.anyAvailableMovePosition())
            throw new UnableToMoveException();

        return moveMap;
    }


    public WorkerBuildMap updateBuildMap(Worker worker)
            throws UnableToBuildException {

        WorkerBuildMap buildMap = worker.getBuildMap();
        buildMap.resetMap();

        buildMap.updateCellsOutOfMap();
        buildMap.cannotBuildUnderneath();
        buildMap.cannotBuildInOccupiedCell();

        buildMap.printMap();    //debugging

        if (!buildMap.anyAvailableBuildPosition())
            throw new UnableToBuildException();


        return buildMap;
    }


    public GodController getGodController() {
        return godController;
    }


    public abstract String getDescription();
}