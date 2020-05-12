package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //IPText.setFont(Font.loadFont("/fonts/negotiate-free.tff",12));
    }
}