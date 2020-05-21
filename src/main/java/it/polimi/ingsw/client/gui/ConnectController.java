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

    private volatile String IPAddress;
    private volatile boolean creator;

    public ConnectController() {
        IPAddress = null;
    }

    @FXML
    private void connect() {

        IPAddress = IPText.getCharacters().toString();
        boolean connected = false;

        try {
            GuiManager.queue.put(IPAddress);
            connected = GuiManager.booleans.take();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        if(connected) {

            //todo check if player is creator
            //Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
            Stage window = Gui.getStage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/scenes/choose-num-of-players.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            window.setScene(new Scene(root));

        } else {
            //todo print error
            System.out.println("connection error");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
