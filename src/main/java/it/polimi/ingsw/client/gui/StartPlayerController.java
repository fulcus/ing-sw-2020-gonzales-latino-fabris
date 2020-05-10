package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StartPlayerController implements Initializable {

    @FXML
    private Label playerName1;
    @FXML
    private Label playerName2;
    @FXML
    private Label playerName3;

    public StartPlayerController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //display player names
        playerName1.setText("nick1");
        playerName2.setText("nick2");
        playerName3.setText("nick3");

        //load god images

    }
}
