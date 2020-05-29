package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;

import static it.polimi.ingsw.client.gui.GuiManager.connectRoot;

public class WelcomeController {

    @FXML
    private Button start;

    public WelcomeController() {
    }

    @FXML
    private void next() {
        Gui.getStage().setScene(new Scene(connectRoot));
    }

    @FXML
    private void glow() {
        Glow glow = new Glow(0.5);
        start.setEffect(glow);
    }

    @FXML
    private void normal() {
        Glow glow = new Glow(0);
        start.setEffect(glow);
    }

}
