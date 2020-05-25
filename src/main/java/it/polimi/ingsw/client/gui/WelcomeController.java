package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;

import static it.polimi.ingsw.client.gui.GuiManager.connectRoot;

public class WelcomeController {

    public WelcomeController() {
    }

    @FXML
    private void next() {
        Gui.getStage().setScene(new Scene(connectRoot));
    }

}
