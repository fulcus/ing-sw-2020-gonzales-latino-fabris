package it.polimi.ingsw.server.controller;


import it.polimi.ingsw.server.controller.god.God;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.ViewClient;

import java.util.ArrayList;

/**
 * Manages IO of Gods.
 */
public class GodController {

    private final GameController gameController;
    private ViewClient currentClient; //assigned at the beginning of each turn


    public GodController(GameController gameController) {

        this.gameController = gameController;
        this.currentClient = null;
    }


    public void updateCurrentClient(ViewClient client) {
        this.currentClient = client;
    }


    public ViewClient getCurrentClient() {
        return currentClient;
    }


    /**
     * This method translates compass directions (N,S,E,...) into coordinates.
     *
     * @param compassInput Compass direction to be translated.
     * @return Variation in coordinates
     */
    @SuppressWarnings("ConstantConditions")
    public int[] getInputInCoordinates(String compassInput) {

        int[] result = new int[2];

        switch (compassInput) {
            case "N": {
                result[0] = -1;
                result[1] = 0;
                break;
            }
            case "NE": {
                result[0] = -1;
                result[1] = 1;
                break;
            }
            case "NW": {
                result[0] = -1;
                result[1] = -1;
                break;
            }
            case "S": {
                result[0] = 1;
                result[1] = 0;
                break;
            }
            case "SE": {
                result[0] = 1;
                result[1] = 1;
                break;
            }
            case "SW": {
                result[0] = 1;
                result[1] = -1;
                break;
            }
            case "W": {
                result[0] = 0;
                result[1] = -1;
                break;
            }
            case "E": {
                result[0] = 0;
                result[1] = 1;
                break;
            }
            case "U": {
                result[0] = 0;
                result[1] = 0;
                break;
            }
            default: {
                //to signal wrong input
                result[0] = -100;
                result[1] = -100;
                break;
            }

        }

        return result;

    }

    /**
     * This method returns the coordinates' variation of the selected movement.
     *
     * @return Coordinates' variation.
     */
    public int[] getInputMove() {

        return getInputInCoordinates(currentClient.askMovementDirection());
    }


    /**
     * Allows to manage the will to move again.
     *
     * @return True if the player wants to move again, False otherwise.
     */
    public boolean wantToMoveAgain() {
        return currentClient.askMoveAgain().equals("Y");
    }

    /**
     * Allows to get the right input for the God if the player wants to move up or not.
     *
     * @return True if the player with his worker doesn't want to jump to an higher level, False otherwise.
     */
    public boolean wantToMoveUp() {

        String answer = currentClient.askWantToMoveUp();
        return answer.equals("Y");

    }


    /**
     * Allows to manage the will of moving an enemy on the board.
     *
     * @return True if the player wants to do it.
     */
    public boolean wantToMoveEnemy() {
        String answer = currentClient.askWantToMoveEnemy();
        return answer.equals("Y");
    }


    /**
     * Allows to manage the constriction of moving an enemy worker.
     *
     * @param enemyWorkers It's the list of the detected near enemyWorkers.
     * @param worker       It's the worker has selected for this turn.
     * @return The worker the player has chosen to move.
     */
    public Worker forceMoveEnemy(ArrayList<Worker> enemyWorkers, Worker worker) {

        String workerToMoveCompassPosition = currentClient.askWorkerToMove(enemyWorkers, worker);

        if (workerToMoveCompassPosition == null)
            return null;

        int[] relativeBoardPosition = getInputInCoordinates(workerToMoveCompassPosition);
        int enemyX = worker.getPosition().getX() + relativeBoardPosition[0];
        int enemyY = worker.getPosition().getY() + relativeBoardPosition[1];

        return worker.getPlayer().getGame().getBoard().findCell(enemyX, enemyY).getWorker();
    }


    /**
     * Prompts the view to print the board.
     */
    public void displayBoard() {

        ArrayList<Player> players = gameController.getGame().getPlayers();

        for(Player player : players)
            player.getClient().printMap();
    }


    /**
     * This method returns the coordinates where a player wants to build and the specific building.
     *
     * @return Coordinates' variation and type of building.
     */
    @SuppressWarnings("ConstantConditions")
    public int[] getBuildingInputAtlas() {

        int[] buildingInput = new int[3];
        String[] playerInput = currentClient.askBuildingDirectionAtlas();

        int[] playerInputCoord = getInputInCoordinates(playerInput[0]);
        buildingInput[0] = playerInputCoord[0];
        buildingInput[1] = playerInputCoord[1];

        if (playerInput[1].equals("B"))
            buildingInput[2] = 0;
        else
            buildingInput[2] = 1;

        return buildingInput;
    }


    /**
     * This method returns the coordinates where a player wants to build.
     *
     * @return Coordinates' variation.
     */
    public int[] getBuildingInput() {

        int[] buildingInput = new int[2];
        String playerInput = currentClient.askBuildingDirection();

        int[] playerInputCoord = getInputInCoordinates(playerInput);
        buildingInput[0] = playerInputCoord[0];
        buildingInput[1] = playerInputCoord[1];

        return buildingInput;
    }


    /**
     * Allows to translate the players' answer to the will of build another time, all related to a specific God.
     *
     * @param god It's the specific god of the player.
     * @return True for the will of build, False otherwise.
     */
    public boolean wantToBuildAgain(God god) {
        String answer = null;
        if (god.toString().equals("Hephaestus"))
            answer = currentClient.askBuildAgainHephaestus();

        if (god.toString().equals("Demeter"))
            answer = currentClient.askBuildAgainDemeter();

        if (god.toString().equals("Hestia"))
            answer = currentClient.askBuildAgainHestia();

        if (god.toString().equals("Prometheus"))
            answer = currentClient.askBuildPrometheus();

        assert answer != null;
        return answer.equals("Y");
    }

    /**
     * Lets the player build underneath himself.
     */
    public void allowBuildUnderneath() {
        currentClient.printBuildUnderneath();
    }


    /**
     * Allows to call the view to print the error screen
     */
    public void errorMoveScreen() {
        currentClient.printMoveErrorScreen();
    }


    /**
     * Allows to manage the decision of the player to retry the move of his worker.
     *
     * @return True if the player wants to retry.
     */
    public boolean errorMoveDecisionScreen() {

        return currentClient.printMoveDecisionError().equals("Y");
    }


    /**
     * Allows to manage the decision of the player to retry the build of his worker.
     *
     * @return True if the player wants to retry.
     */
    public boolean errorBuildDecisionScreen() {
        return currentClient.printBuildDecisionError().equals("Y");
    }


    /**
     * Allows to manage the error screen saw when there's not the possibility to build in the same position.
     */
    public void errorBuildInSamePosition() {
        currentClient.printBuildInSamePositionScreen();
    }


    /**
     * Allows to manage the error screen saw by the player when his building phase fails.
     */
    public void errorBuildScreen() {
        currentClient.printBuildGeneralErrorScreen();
    }


    /**
     * Allows to manage the error screen saw by the player when his block cannot be built
     */
    public void errorBuildBlockScreen() {
        currentClient.printBuildBlockErrorScreen();
    }

}
