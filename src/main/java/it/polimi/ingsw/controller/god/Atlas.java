package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;

import java.util.Scanner;

public class Atlas implements God{

    private GameController gameController;

    public Atlas(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     *
     * @param worker This the current worker.
     * @return It returns the cell wherein the worker has just built.
     */
    @Override
    public Cell build(Worker worker) {
       return buildAllowAnyLevelDome(worker);
    }


    private Cell buildAllowAnyLevelDome(Worker worker) {

        //same as default build without "lvl == 3 condition" for buildDome
        WorkerBuildMap buildMap = updateBuildMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();


        int[] buildInput = getInputBuildAllowDomePosition();  //returns build position + type: block/dome
        int xBuild = buildInput[0];
        int yBuild = buildInput[1];
        int buildType = buildInput[2]; //0 is block, 1 is dome

        Cell buildPosition = board.findCell(xBuild, yBuild);

        //build Dome AT ANY LEVEL
        if (buildType == 1) {

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {
                worker.buildDome(xBuild, yBuild);
            } else {
                buildPosition = null;
                //todo View + Controller error: cant build dome there
            }

        } else if (buildType == 2) {    //build Block
            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && board.findCell(xBuild, yBuild).getLevel() < 3) {
                worker.buildBlock(xBuild, yBuild);

            } else {
                buildPosition = null;
                //todo View + Controller error: cant build block there
            }
        } else {
            buildPosition = null;
            //todo View + Controller error: wrong input for build type (can be 1 or 2)
        }

        return buildPosition;

    }


    public GameController getGameController() {
        return gameController;
    }
}
