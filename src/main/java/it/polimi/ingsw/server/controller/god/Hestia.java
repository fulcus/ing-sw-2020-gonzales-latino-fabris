package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerBuildMap;


/**
 * Represents the card of the God Hestia.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Hestia extends God {

    public final String description = "Your Worker may build one additional time, but this cannot be on a perimeter space.";


    public Hestia(GodController godController) {
        super(godController);
    }


    /**
     * The evolution of the turn for the player that holds the Hestia God card is different from the abstract implementation.
     * Takes also into account that the selected worker can build again.
     *
     * @param w Selected worker that will act in the current turn.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
    @Override
    public void evolveTurn(Worker w) throws UnableToMoveException, UnableToBuildException, WinException {
        move(w);
        win(w);
        build(w);
        buildAgain(w);
    }

    /**
     * Same as normal build except that it calls special updateBuildMap and catches exception
     *
     * @param worker Worker playing the turn
     */
    private void buildAgain(Worker worker) {
        WorkerBuildMap buildMap;

        //If I choose to not build again I can pass my turn
        if (!godController.wantToBuildAgain(this))
            return;

        while (true) {
            try {
                buildMap = updateBuildMapHestia(worker);
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

                //build Dome and fix the condition that if the worker wants to build underneath
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


    /**
     * Sets the permissions to build of the selected worker.
     * Differently from the default method it also sets the perimeter building cells as false.
     *
     * @param worker worker playing the turn.
     * @return The WorkerBuildMap of the chosen worker of the turn.
     * @throws UnableToBuildException signals that the worker cannot build anywhere
     */
    private WorkerBuildMap updateBuildMapHestia(Worker worker) throws UnableToBuildException {
        WorkerBuildMap buildMap = worker.getBuildMap();
        buildMap.resetMap();

        buildMap.updateCellsOutOfMap();
        buildMap.cannotBuildUnderneath();
        buildMap.cannotBuildInOccupiedCell();

        //only difference
        buildMap.cannotBuildInPerimeter();

        //buildMap.printMap();    //debugging

        if (!buildMap.anyAvailableBuildPosition())
            throw new UnableToBuildException();


        return buildMap;

    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
