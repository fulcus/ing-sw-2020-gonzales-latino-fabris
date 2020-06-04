package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.controller.*;


/**
 * This abstract class allows to see the Gods' main methods and their default implementation.
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
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException, WinException {
        move(worker);
        win(worker);
        build(worker);
    }


    /**
     * Default rules to move the worker during a turn of the game.
     *
     * @param worker Selected worker that will move.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
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
     * Lets the worker build a block or a dome.
     *
     * @param worker worker playing the current turn.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
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

                //build Dome and check that in case the worker wants to build underneath
                //building a dome won't be allowed. for zeus.

                //todo if(buildPosition.equals(worker.getPosition()))
                // print: cannot build a dome underneath
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
     * Checks if default win conditions are met.
     *
     * @param worker Worker playing the turn.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
    public void win(Worker worker) throws WinException {

        boolean won;
        boolean normalCondition = worker.getLevel() == 3 && worker.getLevelVariation() == 1;
        if (worker.getPlayer().getCanWinInPerimeter())
            won = normalCondition;
        else
            won = normalCondition && !worker.getPosition().isInPerimeter();


        if (won)
            throw new WinException();

    }


    /**
     * Sets the permissions to move of the selected worker.
     *
     * @param worker worker playing the turn.
     * @return The WorkerMoveMap of the worker chosen for this turn.
     * @throws UnableToMoveException signals that the worker cannot move anywhere
     */
    //will be called at the beginning of each move, which will then comply with the matrix.
    public WorkerMoveMap updateMoveMap(Worker worker) throws UnableToMoveException {

        WorkerMoveMap moveMap = worker.getMoveMap();
        moveMap.resetMap();

        moveMap.updateCellsOutOfMap();
        moveMap.updateMoveUpRestrictions();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInOccupiedCell();

        //moveMap.printMap();    //debugging

        if (!moveMap.anyAvailableMovePosition())
            throw new UnableToMoveException();

        return moveMap;
    }


    /**
     * Sets the permissions to build of the selected worker.
     *
     * @param worker worker playing the turn.
     * @return The WorkerBuildMap of the chosen worker of this turn.
     * @throws UnableToBuildException signals that the worker cannot build anywhere.
     */
    public WorkerBuildMap updateBuildMap(Worker worker) throws UnableToBuildException {

        WorkerBuildMap buildMap = worker.getBuildMap();
        buildMap.resetMap();

        buildMap.updateCellsOutOfMap();
        buildMap.cannotBuildUnderneath();
        buildMap.cannotBuildInOccupiedCell();

        //buildMap.printMap();    //debugging

        if (!buildMap.anyAvailableBuildPosition())
            throw new UnableToBuildException();

        return buildMap;
    }


    public GodController getGodController() {
        return godController;
    }


    public abstract String getDescription();


    public String toString() {
        return getClass().getSimpleName();
    }

}