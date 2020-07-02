package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;


/**
 * Represents the card of the God Zeus.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Zeus extends God {

    public final String description = "Your Worker may build a block under itself.";
    private Cell oldCell;

    public Zeus(GodController godController) {
        super(godController);
    }


    /**
     * Lets the worker build a block or a dome.
     *
     * @param worker worker playing the current turn.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     */
    @Override
    public void build(Worker worker) throws UnableToBuildException {

        WorkerBuildMap buildMap = updateBuildMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();

        while (true) {
            //returns build position
            int[] buildInput = godController.getBuildingInput();

            int xBuild = worker.getPosition().getX() + buildInput[0];
            int yBuild = worker.getPosition().getY() + buildInput[1];

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {


                Cell buildPosition = board.findCell(xBuild, yBuild);

                //build Dome and check that in case the worker wants to build underneath
                //building a dome won't be allowed. for zeus.

                if (buildPosition.getLevel() == 3) {
                    if(!buildPosition.equals(worker.getPosition())) {
                        worker.buildDome(xBuild, yBuild);
                        godController.displayBoard();
                        break;
                    } else
                        godController.cannotBuildDomeUnderneath();
                }
                //build block
                else if (buildPosition.getLevel() < 3) {
                    worker.buildBlock(xBuild, yBuild);
                    godController.displayBoard();
                    break;
                }

            } else
                godController.errorBuildScreen();   //input different than 0 or 1
        }

        Cell newCell = worker.getPosition();
        //if worker built underneath update his level && levelVariation
        if(newCell == oldCell)
            worker.setPosition(newCell);
    }


    /**
     * The difference with the default method is that the build matrix of the worker is updated giving the possibility to build underneath the chosen worker.
     *
     * @param worker worker playing the turn.
     * @return The WorkerBuildMap of the chosen worker.
     * @throws UnableToBuildException signals that the worker cannot build anywhere.
     */
    @Override
    public WorkerBuildMap updateBuildMap(Worker worker) throws UnableToBuildException {
        WorkerBuildMap buildMap = worker.getBuildMap();
        buildMap.reset();

        buildMap.updateCellsOutOfMap();
        buildMap.cannotBuildInOccupiedCell();
        //cannotBuildInOccupiedCell forbids worker to build in his own position
        //but canBuildUnderneath overwrites the previous rule to allow worker to build underneath himself
        buildMap.canBuildUnderneath();

        //buildMap.printMap();  //debug

        if (!buildMap.anyAvailableBuildPosition())
            throw new UnableToBuildException();

        oldCell = worker.getPosition();

        return buildMap;
    }


    public GodController getGodController() {
        return godController;
    }


    public String getDescription() {
        return description;
    }

}
