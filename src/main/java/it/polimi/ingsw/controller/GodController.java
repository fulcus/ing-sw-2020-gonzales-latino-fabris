package it.polimi.ingsw.controller;


import it.polimi.ingsw.view.CLIMainView;

/**
 * Manages IO of Gods.
 */
public class GodController {
    private final CLIMainView view;
    private final GameController gameController;

    public GodController(CLIMainView view, GameController gameController) {
        this.view = view;
        this.gameController = gameController;
    }


    /**
     * This method translates compass directions (N,S,E,...) into coordinates.
     * @param compassInput Compass direction to be translated.
     * @return Variation in coordinates
     */
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
    public int[] getMovementInput(){

        return getInputInCoordinates(view.askMovementDirection());
    }


    public int[] getInputMoveAgain() {
        String answer = view.askMoveAgain();
        int[] input;
        if (answer.equals("Y")) {
            input = getMovementInput();
        }
        else
            input = null;

        return input;
    }

    /**
     * Allows to get the right input for the God if the player wants to move up or not.
     * @return True if the player with his worker wants to jump to an higher level, False otherwise.
     */
    public boolean wantToMoveUp(){
        String answer = view.askWantToMoveUp();

        while(true) {

            if (answer.equals("Y")) {
                return false;
            } else if(answer.equals("N")) {
                return true;
            } else
                errorScreen();
        }
    }

    /**
     * This method returns the coordinates' variation of the selected building.
     * @return Coordinates' variation.
     */
    public int[] getBuildingInput(){

        return getInputInCoordinates(view.askBuildingDirection());
    }



    public void winGame() {
        view.winningView();
        gameController.endGame();
    }


    public void errorScreen() {
        view.printErrorScreen();
    }



}
