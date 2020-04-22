package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;


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

            Cell buildPosition = board.findCell(xBuild, yBuild);

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {


                //build Dome  and fix the condition that if the worker wants to build underneath
                //and the building will be a dome won't be allowed

                if (buildPosition.getLevel() == 3) {
                    worker.buildDome(xBuild, yBuild);
                    godController.displayBoard();
                    return;
                }

                //build Block
                else if (buildPosition.getLevel() < 3) {
                    worker.buildBlock(xBuild, yBuild);
                    godController.displayBoard();
                    return;
                }

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
