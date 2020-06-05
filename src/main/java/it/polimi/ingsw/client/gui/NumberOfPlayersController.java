package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;


/**
 * Manages the choose number of players scene of the GUI.
 */
public class NumberOfPlayersController {


    public NumberOfPlayersController() {
    }


    @FXML
    private void two() {

        try {
            //TODO: set attribute for lobby built for 2 players
            GuiManager.queue.put("2");
        } catch (InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }


    @FXML
    private void three() {

        try {
            //TODO: set attribute for lobby built for 3 players
            GuiManager.queue.put("3");
        } catch (InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
}