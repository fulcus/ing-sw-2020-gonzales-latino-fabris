package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private void blue(MouseEvent e) {


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
    private void white(MouseEvent e) {

        String color = "WHITE";

        try {
            //give color to manager thread
            GuiManager.queue.put(color);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    private void beige(MouseEvent e) {

        String color = "BEIGE";

        try {
            //give color to manager thread
            GuiManager.queue.put(color);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }


    public void displayErrorColor() {
        colorError.setVisible(true);
    }

    public void removeErrorColorFromScreen() {
        colorError.setVisible(false);
    }

    public void displayWaitingOther() {
        waitingOther.setVisible(true);
    }

    public void removeWaitingOtherFromScreen() {
        waitingOther.setVisible(false);
    }

    public void enableButtons() {
        white.setDisable(false);
        blue.setDisable(false);
        beige.setDisable(false);
    }

}
