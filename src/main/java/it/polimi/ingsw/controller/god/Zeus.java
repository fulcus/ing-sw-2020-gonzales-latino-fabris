package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;


public class Zeus extends God{

    public String description = "Your Worker may build a block under itself.";

    public Zeus(GodController godController) {
        super(godController);
    }


    @Override
    public WorkerBuildMap updateBuildMap(Worker worker) throws UnableToBuildException {
        WorkerBuildMap buildMap = worker.getBuildMap();

        buildMap.cannotBuildInWorkerCell();
        //WARNING: previous rule (cannotBuildInWorkerCell forbids worker to build in his own position
        //but canBuildUnderneath overwrites the previous rule to allow worker to build underneath him
        buildMap.canBuildUnderneath();
        buildMap.cannotBuildInDomeCell();

        if(!buildMap.anyTrueCell())
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
