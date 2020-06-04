package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;


/**
 * Represents the card of the God Atlas.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Atlas extends God {

    public final String description = "Your Worker may build a dome at any level.";


    public Atlas(GodController godController) {
        super(godController);
    }


    /**
     * The player who holds the Atlas God card that is allowed to build a dome in any position whenever he wants,
     * but can also build in the default way.
     *
     * @param worker This the current worker chosen for the turn.
     */
    @Override
    public void build(Worker worker) throws UnableToBuildException {
        buildAllowAnyLevelDome(worker);
    }

    //same as default build without "lvl == 3 condition" for buildDome
    private void buildAllowAnyLevelDome(Worker worker) throws UnableToBuildException {

        WorkerBuildMap buildMap = updateBuildMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();

        while (true) {

            int[] buildInput = godController.getBuildingInputAtlas();  //returns build position + type: block/dome

            int xBuild = buildInput[0] + worker.getPosition().getX();
            int yBuild = buildInput[1] + worker.getPosition().getY();
            int buildType = buildInput[2]; //0 is block, 1 is dome

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {

                if (buildType == 1) {   //build Dome AT ANY LEVEL

                    worker.buildDome(xBuild, yBuild);
                    godController.displayBoard();
                    return;

                } else if (buildType == 0) {   //build Block

                    if (board.findCell(xBuild, yBuild).getLevel() < 3) {
                        worker.buildBlock(xBuild, yBuild);
                        godController.displayBoard();
                        return;

                    } else
                        godController.errorBuildBlockScreen();
                } else
                    godController.errorBuildScreen();   //wrong buildType input
            } else
                godController.errorBuildScreen();   //not allowed to move there
        }
    }


    public GodController getGodController() {
        return godController;
    }


    public String getDescription() {
        return description;
    }

}
