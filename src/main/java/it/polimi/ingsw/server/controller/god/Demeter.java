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
public class Demeter extends God {

    public final String description = "Your Worker may build one additional time, but not on the same space.";
    Cell firstBuildCell;


    public Demeter (GodController godController) {
        super(godController);
        firstBuildCell = null;
    }


    /**
     * The evolution of the turn for the player that holds the Demeter God card is different from the abstract implementation.
     * Here we can build twice, but the second time needs to be into a different cell.
     *
     * @param w The selected worker for this turn.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
    @Override
    public void evolveTurn(Worker w) throws UnableToBuildException, UnableToMoveException, WinException {
        move(w);
        win(w);
        firstBuildCell = firstBuild(w);
        buildAgain(w);
    }


    /**
     * Allows to build a block or a dome.
     *
     * @param worker The chosen worker for this turn.
     * @return The cell where the first build has been made.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     */
    private Cell firstBuild(Worker worker) throws UnableToBuildException {

        WorkerBuildMap buildMap = updateBuildMap(worker);

        Board board = worker.getPlayer().getGame().getBoard();

        while (true) {
            //returns build position
            int[] buildInput = godController.getBuildInput();

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


    /**
     * The player can choose if he wants to build again.
     * If so, the next building block or dome needs to be built into a different position with respect to the first building position.
     * @param worker The chosen worker for the turn.
     */
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


            int[] buildInput = godController.getBuildInput();  //returns build position + type: block/dome
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