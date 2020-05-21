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

public class ConnectController implements Initializable {

    @FXML
    private TextField IPText;

    private String IPAddress;

    public ConnectController() {
        IPAddress = null;
    }

    @FXML
    private void connect() {

        IPAddress = IPText.getCharacters().toString();
        boolean connected = false;

        try {
            //give ip address to thread
            GuiManager.queue.put(IPAddress);

            //wait for response from server
            connected = GuiManager.booleans.take();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        if (connected) {

            //check if player is creator
            Parent root = null;

            try {
                System.out.println("before take");
                boolean isCreator = GuiManager.booleans.take();
                System.out.println("after take");

                String path = isCreator ? "/scenes/choose-num-of-players.fxml" : "/scenes/choose-nickname.fxml";
                root = FXMLLoader.load(getClass().getResource(path));

            } catch (IOException | InterruptedException ioException) {
                ioException.printStackTrace();
            }
            Gui.getStage().setScene(new Scene(root));

        } else {
            //todo print error
            System.out.println("connection error");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
