package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class NicknameController {

    @FXML
    private TextField nicknameText;

    @FXML
    private Text error;



    public NicknameController() {
    }

    protected Text getNickError() {
        return error;
    }


    @FXML
    private void login(KeyEvent ke) {

        if (ke.getCode().equals(KeyCode.ENTER)) {

            System.out.println("entered method login");
            //error.setVisible(false);

            String nickname = nicknameText.getCharacters().toString();
            System.out.println("taken nick  " + nickname);

            try {
                //give nick to manager thread
                GuiManager.queue.put(nickname);

                System.out.println("put nickname");

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }


}
