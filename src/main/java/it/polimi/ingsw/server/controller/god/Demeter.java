package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;


public class Demeter extends God {

    public final String description = "Your Worker may build one additional time, but not on the same space.";

    Cell firstBuildCell;

    public Demeter (GodController godController) {
        super(godController);
        firstBuildCell = null;
    }

    @Override
    public void evolveTurn(Worker w) throws UnableToBuildException, UnableToMoveException, WinException {
        move(w);
        win(w);
        firstBuildCell = firstBuild(w);
        buildAgain(w);
    }


    public Cell firstBuild(Worker worker) throws UnableToBuildException {

        WorkerBuildMap buildMap = updateBuildMap(worker);

        Board board = worker.getPlayer().getGame().getBoard();

        while (true) {
            //returns build position + type: block/dome
            int[] buildInput = godController.getBuildingInput();

            int xBuild = worker.getPosition().getX() + buildInput[0];
            int yBuild = worker.getPosition().getY() + buildInput[1];

            Cell buildPosition = board.findCell(xBuild, yBuild);

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {


                //build Dome  and fix the condition that if the worker wants to build underneath
                //and the building will be a dome won't be allowed

                if (buildPosition.getLevel() == 3 && !buildPosition.equals(worker.getPosition())) {
                    worker.buildDome(xBuild, yBuild);
                    godController.displayBoard();
                    return buildPosition;
                }

                //build Block
                else if (buildPosition.getLevel() < 3) {
                    worker.buildBlock(xBuild, yBuild);
                    godController.displayBoard();
                    return buildPosition;
                }

            } else
                godController.errorBuildScreen();
        }
    }


    private void buildAgain(Worker worker) {

        if (!godController.wantToBuildAgain(this))
            return;

        WorkerBuildMap buildMap;

        while (true) {

            try {
                buildMap = updateBuildMap(worker);
            } catch (UnableToBuildException ex) {
                godController.errorBuildScreen();
                return;
            }

            Board board = worker.getPlayer().getGame().getBoard();


            int[] buildInput = godController.getBuildingInput();  //returns build position + type: block/dome
            int xBuild = worker.getPosition().getX() + buildInput[0];
            int yBuild = worker.getPosition().getY() + buildInput[1];

            Cell buildPosition = board.findCell(xBuild, yBuild);

            if (buildPosition != firstBuildCell) {

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
                    godController.errorBuildScreen();   //input is not correct

            } else
                godController.errorBuildInSamePosition();

            // Asks again to the player if he still wants to build again:
            // if not the method ends, otherwise the player decides to try to build another time.
            if (!godController.errorBuildDecisionScreen())
                return;

        }
    }


    public GodController getGodController(){
        return godController;
    }


    public String getDescription() {
        return description;
    }

}