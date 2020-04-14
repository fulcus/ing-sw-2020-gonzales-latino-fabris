package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.controller.UnableToMoveException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;


public class Hestia extends God {

    public String description = "Your Worker may build one additional time, but this cannot be on a perimeter space.";


    public Hestia(GodController godController) {
        super(godController);
    }


    @Override
    public void evolveTurn(Worker w) throws UnableToMoveException, UnableToBuildException {
        move(w);
        win(w);
        build(w);
        buildAgain(w);
    }


    private void buildAgain(Worker worker) {
        WorkerBuildMap buildMap;

        //If I choose to not build again I can pass my turn
        if (!godController.wantToBuildAgain(this))
            return;

        while(true) {
            try {
                buildMap = updateBuildMap(worker);
            } catch (UnableToBuildException ex) {
                godController.errorBuildScreen();
                return;
            }

            Board board = worker.getPlayer().getGame().getBoard();

            int[] buildInput = godController.getBuildingInput();  //returns build position + type: block/dome
            int xBuild = buildInput[0] + worker.getPosition().getX();
            int yBuild = buildInput[1] + worker.getPosition().getY();
            int buildType = buildInput[2]; //0 is block, 1 is dome

            Cell buildPosition = board.findCell(xBuild, yBuild);

            if(buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {

            //build Dome
            if (buildType == 1) {

                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() == 3
                        && !buildPosition.isInPerimeter()) {

                    worker.buildDome(xBuild, yBuild);
                    godController.displayBoard();
                    return;
                } else
                    godController.errorBuildDomeScreen();


            } else if (buildType == 0) {    //build Block
                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() < 3
                        && !buildPosition.isInPerimeter()) {

                    worker.buildBlock(xBuild, yBuild);
                    godController.displayBoard();
                    return;
                } else
                    godController.errorBuildBlockScreen();
            } else
                godController.errorBuildScreen();

            } else
                godController.errorBuildScreen();

            // If I don't want to build anymore I quit the method
            if (!godController.errorBuildDecisionScreen())
                return;
        }
    }



    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
