package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;


public class AddPlayerController implements Initializable {

    public AddPlayerController() {
    }

    @FXML
    private TextField NickText;

    @FXML
    private void blue() {
        System.out.println("blue");
    }

    @FXML
    private void white() {
        System.out.println("white");
    }

    @FXML
    private void beige() {
        System.out.println("beige");
    }

    @FXML
    private void login() {
        System.out.println("nick received: ");
        System.out.println(NickText.getCharacters());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //IPText.setFont(Font.loadFont("/fonts/negotiate-free.tff",12));
    }

    @FXML
    private void two() {
        System.out.println("two");
    }

    @FXML
    private void three() {
        System.out.println("three");
    }

    @FXML
    private void next() {
        System.out.println("next scene");
    }
}
