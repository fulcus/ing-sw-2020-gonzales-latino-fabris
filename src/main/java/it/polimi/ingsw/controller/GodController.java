package it.polimi.ingsw.controller;


import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.view.GodView;

/**
 * Manages IO of Gods.
 */
public class GodController {
    private final GodView godView;


    public GodController(GodView godView) {
        this.godView = godView;
    }


    /**
     * This method translates compass directions (N,S,E,...) into coordinates.
     * @param compassInput Compass direction to be translated.
     * @return Variation in coordinates
     */
    @SuppressWarnings("ConstantConditions")
    public int[] getInputInCoordinates(String compassInput){

        int[] result = new int[2];

        switch (compassInput) {
            case "N" : {
                result[0] = -1;
                result[1] = 0;
                break;
            }
            case "NE" : {
                result[0] = -1;
                result[1] = -1;
                break;
            }
            case "NW" : {
                result[0] = -1;
                result[1] = 1;
                break;
            }
            case "S" : {
                result[0] = 1;
                result[1] = 0;
                break;
            }
            case "SE" : {
                result[0] = 1;
                result[1] = 1;
                break;
            }
            case "SW" : {
                result[0] = 1;
                result[1] = -1;
                break;
            }
            case "W" : {
                result[0] = 0;
                result[1] = -1;
                break;
            }
            case "E" : {
                result[0] = 0;
                result[1] = 1;
                break;
            }
            default : {
                result[0] = 0;
                result[1] = 0;
                break;
            }

        }

        return result;

    }

    /**
     * This method returns the coordinates' variation of the selected movement.
     * @return Coordinates' variation.
     */
    public int[] getInputMove(){

        return getInputInCoordinates(godView.askMovementDirection());
    }


    /**
     * Allows to translate if the player wants or not to move another time.
     * @return The understandable input for the God's specific method.
     */
    public int[] getInputMoveAgain() {
        String answer = godView.askMoveAgain();
        int[] input;
        if (answer.equals("Y")) {
            input = getInputMove();
        }
        else
            input = null;

        return input;
    }

    /**
     * Allows to get the right input for the God if the player wants to move up or not.
     * @return True if the player with his worker doesn't want to jump to an higher level, False otherwise.
     */
    public boolean wantToMoveUp(){

        String answer = godView.askWantToMoveUp();
        return !answer.equals("Y");

    }

    /**
     * This method returns the coordinates where a player wants to build and the specific building.
     * @return Coordinates' variation and type of building.
     */
    public int[] getBuildingInput(){

        int[] buildingInput = new int[3];
        String[] playerInput = godView.askBuildingDirection();

        int[] playerInputCoord = getInputInCoordinates(playerInput[0]);
        buildingInput[0] = playerInputCoord[0];
        buildingInput[1] = playerInputCoord[1];

        if (playerInput[2].equals("B"))
            buildingInput[2] = 0;
        else
            buildingInput[2] = 1;

        return buildingInput;
    }


    /**
     * Allows to translate the players' answer to the will of build another time, all related to a specific God.
     * @param god It's the specific god of the player.
     * @return True for the will of build, False otherwise.
     */
    public boolean getBuildAgain(God god){
        String answer = null;
        if (god.toString().equals("Hephaestus"))
            answer = godView.askBuildAgainHephaestus();

        if (god.toString().equals("Demeter"))
            answer = godView.askBuildAgainDemeter();


        return answer.equals("Y");
    }


    /**
     * Allows to call the GameController to notify that a player has  won the game.
     */
    //TODO forse questo è possibile rivederlo - da mettere che non si interfacci con la view direttamente, ma col gameController??
    public void winGame() {
        view.winningView();
        System.exit(0);
    }


    /**
     * Allows to call the view to print the error screen
     */
    //TODO : Vitto ora lo modifica come avevamo detto
    public void errorScreen() {
        view.printErrorScreen();
    }



}
