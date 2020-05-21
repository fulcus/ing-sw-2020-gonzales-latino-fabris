package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class NicknameController {

    @FXML
    private TextField nicknameText;


    public NicknameController() {
    }

    @FXML
    private void login() {
        String nickname = nicknameText.getCharacters().toString();

        try {
            //give nick to manager thread
            GuiManager.queue.put(nickname);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

}
