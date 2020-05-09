package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WelcomeController {

    @FXML
    private TextField IPTextInput;

    public WelcomeController() {
    }

    @FXML
    private void clickPlay() {
        System.out.println("play");
    }

    @FXML
    private void clickConnect() {
        System.out.println("connect");
        System.out.println(IPTextInput.getCharacters());
    }

}