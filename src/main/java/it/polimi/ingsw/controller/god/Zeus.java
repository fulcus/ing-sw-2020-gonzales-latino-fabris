package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;


public class Zeus implements God{


    private GameController gameController;

    public Zeus(GameController gameController){
        this.gameController = gameController;
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





    public GameController getGameController() {
        return gameController;
    }

}
