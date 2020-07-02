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
            GuiManager.queue.put("2");
        } catch (InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }


    @FXML
    private void three() {
        try {
            GuiManager.queue.put("3");
        } catch (InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
}