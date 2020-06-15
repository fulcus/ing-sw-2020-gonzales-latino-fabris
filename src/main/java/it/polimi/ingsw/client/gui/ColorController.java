package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Manages the scene of the choice of the colors for the game.
 */
public class ColorController {

    @FXML
    private Button white;
    @FXML
    private Button blue;
    @FXML
    private Button beige;

    @FXML
    private Text colorError;

    @FXML
    private Text waitingOther;


    public ColorController() {
    }


    @FXML
    private void blue() {


        String color = "BLUE";

        System.out.println("controller: clicked blue");

        try {
            //give color to manager thread
            GuiManager.queue.put(color);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void white() {

        String color = "WHITE";

        try {
            //give color to manager thread
            GuiManager.queue.put(color);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    private void beige() {

        String color = "BEIGE";

        try {
            //give color to manager thread
            GuiManager.queue.put(color);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }


    /**
     * Displays to the player that the color was already taken by another player.
     */
    public void displayErrorColor() {
        colorError.setVisible(true);
    }

    public void removeErrorColorFromScreen() {
        colorError.setVisible(false);
    }

    public void displayWaitingOther() {
        waitingOther.setVisible(true);
    }

    /**
     * Sets invisible the waiting other player choosing color message.
     */
    public void removeWaitingOtherFromScreen() {
        waitingOther.setVisible(false);
    }


    /**
     * Enables color buttons to let the player choose one of the game colors.
     */
    public void enableButtons() {
        white.setDisable(false);
        blue.setDisable(false);
        beige.setDisable(false);
    }

}
