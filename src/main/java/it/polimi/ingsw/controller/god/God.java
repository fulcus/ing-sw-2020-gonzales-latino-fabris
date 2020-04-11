package it.polimi.ingsw.controller.god;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.*;

/**
 * This interface allows to see the Gods' main methods
 */
public abstract class God {


    public String description;
    protected GodController godController;


    public God(GodController godController) {
        this.godController = godController;
    }


    /**
     * Default evolution of the turn: move, checks if win condition is met, builds.
     * @param worker Selected worker that will act in the current turn.
     */
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        move(worker);
        win(worker);
        build(worker);
    }


    /**
     * Default rules to move the worker.
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
            int buildType = buildInput[2]; //0 is block, 1 is dome

            Cell buildPosition = board.findCell(xBuild, yBuild);

            //build Dome
            if (buildType == 1) {

                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() == 3) {
                    worker.buildDome(xBuild, yBuild);
                    return;
                } else
                    godController.errorBuildDomeScreen();

            } else if (buildType == 0) {    //build Block
                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() < 3) {
                    worker.buildBlock(xBuild, yBuild);
                    return;
                } else
                    godController.errorBuildBlockScreen();

            } else
                godController.errorBuildScreen();

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
            godController.winGame();
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

        moveMap.cannotStayStill();
        moveMap.cannotMoveInOccupiedCell();
        moveMap.updateMoveUpRestrictions();
        moveMap.updateCellsOutOfMap();

        if(!moveMap.anyAvailableMovePosition())
            throw new UnableToMoveException();

        return moveMap;
    }


    public WorkerBuildMap updateBuildMap(Worker worker)
            throws UnableToBuildException {

        WorkerBuildMap buildMap = worker.getBuildMap();

        buildMap.cannotBuildUnderneath();
        buildMap.cannotBuildInWorkerCell();
        buildMap.cannotBuildInDomeCell();
        buildMap.updateCellsOutOfMap();

        if(!buildMap.anyAvailableBuildPosition())
            throw new UnableToBuildException();


        return buildMap;
    }


    public GodController getGodController() {
        return godController;
    }
    

}