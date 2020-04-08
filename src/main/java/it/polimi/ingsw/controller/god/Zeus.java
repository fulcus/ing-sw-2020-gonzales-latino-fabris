package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;


public class Zeus extends God{
    
    public String description = "Your Worker may build a block under itself.";

    public Zeus(GodController godController) {
        super(godController);
    }


    @Override
    public WorkerBuildMap updateBuildMap(Worker worker) {
        WorkerBuildMap buildMap = worker.getBuildMap();

        buildMap.cannotBuildInWorkerCell();
        //WARNING: previous rule (cannotBuildInWorkerCell forbids worker to build in his own position
        //but canBuildUnderneath overwrites the previous rule to allow worker to build underneath him
        buildMap.canBuildUnderneath();
        buildMap.cannotBuildInDomeCell();
        buildMap.updateCellsOutOfMap();

        if(!buildMap.anyTrueCell())
        //todo Controller lose

        return buildMap;
    }


    public GodController getGodController() {
        return godController;
    }

}
