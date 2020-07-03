package it.polimi.ingsw.server.controller;


import it.polimi.ingsw.server.VirtualView;
import it.polimi.ingsw.server.controller.god.God;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;

import java.util.ArrayList;


/**
 * Manages communication between the Gods and the clients.
 */
public class GodController {

    private final GameController gameController;
    private VirtualView currentClient; //assigned at the beginning of each turn


    public GodController(GameController gameController) {
        this.gameController = gameController;
        this.currentClient = null;
    }


    public void updateCurrentClient(VirtualView client) {
        this.currentClient = client;
    }


    public VirtualView getCurrentClient() {
        return currentClient;
    }


    /**
     * This method translates compass directions (N,S,E,...) into coordinates.
     *
     * @param compassInput Compass direction to be translated.
     * @return Variation in coordinates
     */
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
     * Asks to the player where he wants to move his worker and then translates the input into coordinates.
     *
     * @return The coordinates' variation of the chosen movement.
     */
    public int[] getMoveInput() {
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
     * Allows to manage the will of moving an enemy on the board.
     *
     * @return True if the player wants to do it, false otherwise.
     */
    public boolean wantToMoveEnemy() {
        String answer = currentClient.askWantToMoveEnemy();
        return answer.equals("Y");
    }


    /**
     * Allows to manage the constriction of moving an enemy worker.
     *
     * @param enemyWorkers It's the list of the detected enemyWorkers nearby.
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
     * Prompts the view to display the board.
     */
    public void displayBoard() {

        ArrayList<Player> players = gameController.getGame().getPlayers();

        for (Player player : players)
            player.getClient().printMap();
    }


    /**
     * Asks to the player where he wants to make his worker build and what kind of building,
     * then translates the input into coordinates and type of building.
     * This method is specifically used when playing with Atlas.
     *
     * @return Coordinates' variation and type of building.
     */
    @SuppressWarnings("ConstantConditions")
    public int[] getBuildInputAtlas() {

        int[] buildInput = new int[3];
        String[] playerInput = currentClient.askBuildingDirectionAtlas();

        int[] playerInputCoordinates = getInputInCoordinates(playerInput[0]);
        buildInput[0] = playerInputCoordinates[0];
        buildInput[1] = playerInputCoordinates[1];

        if (playerInput[1].equals("B"))
            buildInput[2] = 0;
        else
            buildInput[2] = 1;

        return buildInput;
    }


    /**
     * Asks to the player where he wants to make his worker build and then translates the input into coordinates.
     *
     * @return Coordinates' variation.
     */
    public int[] getBuildInput() {

        int[] buildingInput = new int[2];
        String playerInput = currentClient.askBuildingDirection();

        int[] playerInputCoordinate = getInputInCoordinates(playerInput);
        buildingInput[0] = playerInputCoordinate[0];
        buildingInput[1] = playerInputCoordinate[1];

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


        return answer.equals("Y");
    }


    /**
     * Allows to call the viewClient to print the error when a wrong move input was given to the server.
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
     * Allows to manage the error when there's not the possibility to build in the same position.
     */
    public void errorBuildInSamePosition() {
        currentClient.printBuildInSamePositionScreen();
    }


    /**
     * Allows to manage the error saw by the player when his building phase fails.
     */
    public void errorBuildScreen() {
        currentClient.printBuildGeneralErrorScreen();
    }


    /**
     * Allows to manage the error saw by the player when his block cannot be built
     */
    public void errorBuildBlockScreen() {
        currentClient.printBuildBlockErrorScreen();
    }

    /**
     * Warns user of invalid build action: he cannot build a dome underneath himself.
     * This error can only occur if player uses Zeus' power.
     */
    public void cannotBuildDomeUnderneath() {
        currentClient.printCannotBuildDomeUnderneath();
    }

}
