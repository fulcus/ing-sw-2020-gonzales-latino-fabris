package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.controller.UnableToMoveException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;


public class Hephaestus extends God {

    public String description = "Your Worker may build one additional block (not dome) on top of your first block.";

    Cell firstBuildCell;

    public Hephaestus(GodController godController){
        super(godController);
        firstBuildCell = null;
        this.godController = godController;
    }

    /**
     * This method calls the sequence of actions that can be done by the player who owns Hephaestus.
     * @param worker This is the current worker.
     */
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        move(worker);
        win(worker);
        firstBuildCell = buildCell(worker);
        secondBuild(worker);
    }


    /**
     * Allows to build into a near cell of the board.
     * @param worker It's the selected worker.
     * @return The cell where has been built the first building.
     * @throws UnableToBuildException Says that the building cannot be built anywhere.
     */
    public Cell buildCell(Worker worker) throws UnableToBuildException {
        WorkerBuildMap buildMap = updateBuildMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();

        while (true) {
            //returns build position + type: block/dome
            int[] buildInput = godController.getBuildingInput();

            int xBuild = buildInput[0];
            int yBuild = buildInput[1];
            int buildType = buildInput[2]; //0 is block, 1 is dome

            Cell buildPosition = board.findCell(xBuild, yBuild);

            //build Dome
            if (buildType == 1) {

                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() == 3) {
                    worker.buildDome(xBuild, yBuild);
                    godController.displayBoard();
                    return buildPosition;
                } else {
                    godController.errorBuildDomeScreen();
                }

            } else if (buildType == 0) {    //build Block
                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() < 3) {
                    worker.buildBlock(xBuild, yBuild);
                    godController.displayBoard();
                    return buildPosition;
                } else {
                    godController.errorBuildBlockScreen();
                }
            } else
                godController.errorBuildScreen();

        }
    }


    /**
     * This method allows the player to build in the same place twice.
     * @param worker This is the player's current worker.
     */
    public void secondBuild(Worker worker) {

        if(firstBuildCell.getLevel() >= 3)
            return;

        boolean buildAgainInSamePosition = godController.getBuildAgain(this);  //true if player wants to build again

        if(!buildAgainInSamePosition)
            return;

        //check is useless because worker is certainly allowed to build in first build cell
        worker.buildBlock(firstBuildCell.getX(), firstBuildCell.getY());
        godController.displayBoard();

    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
