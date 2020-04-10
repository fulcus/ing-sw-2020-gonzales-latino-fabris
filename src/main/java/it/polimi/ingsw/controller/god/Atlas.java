package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;


public class Atlas extends God{

    public String description = "Your Worker may build a dome at any level.";


    public Atlas(GodController godController) {
        super(godController);
    }

    /**
     * Allows to build in the correct way for the player who owns Atlas
     * @param worker This the current worker.
     */
    @Override
    public void build(Worker worker) throws UnableToBuildException {
       buildAllowAnyLevelDome(worker);
    }


    private void buildAllowAnyLevelDome(Worker worker) throws UnableToBuildException {

        while (true) {
            //same as default build without "lvl == 3 condition" for buildDome
            WorkerBuildMap buildMap = updateBuildMap(worker);
            Board board = worker.getPlayer().getGame().getBoard();


            int[] buildInput = godController.getBuildingInput();  //returns build position + type: block/dome
            int xBuild = buildInput[0] + worker.getPosition().getX();
            int yBuild = buildInput[1] + worker.getPosition().getY();
            int buildType = buildInput[2]; //0 is block, 1 is dome


            if (buildType == 1) {   //build Dome AT ANY LEVEL

                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {
                    worker.buildDome(xBuild, yBuild);
                    return;
                } else
                    godController.errorBuildDomeScreen();


            } else if (buildType == 0){   //build Block

                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && board.findCell(xBuild, yBuild).getLevel() < 3) {
                    worker.buildBlock(xBuild, yBuild);
                    return;
                } else
                    godController.errorBuildBlockScreen();
            }
            else
                godController.errorBuildScreen();
        }
    }


    public GodController getGodController() {
        return godController;
    }
}
