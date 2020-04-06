package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;


public class Hestia implements God {


    @Override
    public void evolveTurn(Worker w) {
        move(w);
        win(w);
        build(w);
        buildAgain(w);
    }


    private void buildAgain(Worker worker) {

        WorkerBuildMap buildMap = updateBuildMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();


        int[] buildInput = getInputSecondBuildPosition();  //returns build position + type: block/dome
        int xBuild = buildInput[0];
        int yBuild = buildInput[1];
        int buildType = buildInput[2]; //0 is block, 1 is dome

        Cell buildPosition = board.findCell(xBuild, yBuild);


        //build Dome
        if (buildType == 1) {

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() == 3
                    && !buildPosition.isInPerimeter()) {

                worker.buildDome(xBuild, yBuild);

            } else {
                //todo View + Controller error
            }

        } else if (buildType == 2) {    //build Block
            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() < 3
                    && !buildPosition.isInPerimeter()) {

                worker.buildBlock(xBuild, yBuild);

            } else {
                //todo View + Controller error: not allowed to build here
            }
        } else {
            //todo View + Controller error: wrong input for build type (can be 1 or 2)
        }
    }

}