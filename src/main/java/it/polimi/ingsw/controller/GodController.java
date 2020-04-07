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
