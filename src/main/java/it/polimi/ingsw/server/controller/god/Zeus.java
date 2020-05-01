package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;


public class Zeus extends God{

    public final String description = "Your Worker may build a block under itself.";

    public Zeus(GodController godController) {
        super(godController);
    }


    @Override
    public WorkerBuildMap updateBuildMap(Worker worker) throws UnableToBuildException {
        WorkerBuildMap buildMap = worker.getBuildMap();
        buildMap.resetMap();

        buildMap.updateCellsOutOfMap();
        buildMap.cannotBuildInOccupiedCell();
        //WARNING: previous rule (cannotBuildInOccupiedCell) forbids worker to build in his own position
        //but canBuildUnderneath overwrites the previous rule to allow worker to build underneath himself
        godController.allowBuildUnderneath();
        buildMap.canBuildUnderneath();

        //buildMap.printMap();

        if(!buildMap.anyAvailableBuildPosition())
            throw new UnableToBuildException();

        return buildMap;
    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
