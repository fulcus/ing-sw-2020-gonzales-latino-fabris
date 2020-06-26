package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.WinException;
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
    private Cell newCell;

    public Zeus(GodController godController) {
        super(godController);
    }

    /**
     * Checks if default win conditions are met.
     *
     * @param worker Worker playing the turn.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
    @Override
    public void win(Worker worker) throws WinException {
        if (worker.getLevel() == 3 && worker.getLevelVariation() == 1) {
            throw new WinException();
        }
    }

    /**
     * Lets the worker build a block or a dome.
     *
     * @param worker worker playing the current turn.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     */
    @Override
    public void build(Worker worker) throws UnableToBuildException {
        super.build(worker);
        newCell = worker.getPosition();
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
        buildMap.resetMap();

        buildMap.updateCellsOutOfMap();
        buildMap.cannotBuildInOccupiedCell();
        //cannotBuildInOccupiedCell forbids worker to build in his own position
        //but canBuildUnderneath overwrites the previous rule to allow worker to build underneath himself
        buildMap.canBuildUnderneath();

        //buildMap.printMap();  //debug

        if (!buildMap.anyAvailableBuildPosition())
            throw new UnableToBuildException();

        godController.allowBuildUnderneath();
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
