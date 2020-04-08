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
    }

    /**
     * This method calls the sequence of actions that can be done by the player who owns Hephaestus.
     * @param worker This is the current worker.
     */
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        move(worker);
        win(worker);
        firstBuildCell = build(worker);
        secondBuild(worker);
    }

    /**
     * This method allows the player to build in the same place twice.
     * @param worker This is the player's current worker.
     */
    public void secondBuild(Worker worker) {

        if(firstBuildCell.getLevel() >= 3)
            return;


        boolean buildAgainInSamePosition = askBuildAgainInSamePosition();  //true if player wants to build again

        if(!buildAgainInSamePosition)
            return;

        //check is useless because worker is certainly allowed to build in first build cell
        worker.buildBlock(firstBuildCell.getX(), firstBuildCell.getY());

    }


    public GodController getGodController() {
        return godController;
    }
}
