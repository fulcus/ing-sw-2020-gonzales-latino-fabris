package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;


/**
 * Represents the card of the God Demeter.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Hephaestus extends God {

    public final String description = "Your Worker may build one additional block (not dome) on top of your first block.";
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
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException, WinException {
        move(worker);
        win(worker);
        firstBuildCell = firstBuild(worker);
        secondBuild(worker);
    }


    /**
     * Allows to build into a near cell of the board.
     *
     * @param worker It's the selected worker for this turn.
     * @return The cell where has been built the first building.
     * @throws UnableToBuildException Says that the building cannot be built anywhere.
     */
    public Cell firstBuild(Worker worker) throws UnableToBuildException {
        WorkerBuildMap buildMap = updateBuildMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();

        while (true) {
            //returns build position + type: block/dome
            int[] buildInput = godController.getBuildingInput();

            int xBuild = worker.getPosition().getX() + buildInput[0];
            int yBuild =worker.getPosition().getY() + buildInput[1];

            Cell buildPosition = board.findCell(xBuild, yBuild);

            if (buildMap.isAllowedToBuildBoard(xBuild, yBuild)) {


                //build Dome  and fix the condition that if the worker wants to build underneath
                //and the building will be a dome won't be allowed

                if (buildPosition.getLevel() == 3) {
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


    /**
     * This method allows the player to build in the same place twice.
     * @param worker This is the player's current worker.
     */
    public void secondBuild(Worker worker) {

        //print: you cannot build again in the same place since it's already at max height
        if(firstBuildCell.getLevel() == 3)
            return;

        //true if player wants to build again
        if(!godController.wantToBuildAgain(this))
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
