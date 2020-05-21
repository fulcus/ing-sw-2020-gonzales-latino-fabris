package it.polimi.ingsw.client.gui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    public WelcomeController() {
    }

    private void fadeIn() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scenes/connect.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        FadeTransition ft = new FadeTransition(Duration.millis(6000), root);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

    }

    @FXML
    private void next() {
        //Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();

        Parent connect = null;
        try {
            connect = FXMLLoader.load(getClass().getResource("/scenes/connect.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Gui.getStage().setScene(new Scene(connect));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fadeIn();
    }
}
