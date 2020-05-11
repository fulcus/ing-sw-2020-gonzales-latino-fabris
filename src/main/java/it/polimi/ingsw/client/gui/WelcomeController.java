package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WelcomeController {

    @FXML
    private TextField IPText;

    public WelcomeController() {
    }

    @FXML
    private void play() {
        System.out.println("play");
    }

    @FXML
    private void connect() {
        System.out.println("connect");
        System.out.println(IPText.getCharacters());
    }

}