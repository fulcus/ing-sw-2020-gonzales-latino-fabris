package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class NumberOfPlayersController {


    public NumberOfPlayersController() {
    }

    @FXML
    private void two(MouseEvent e) {
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

        Parent nick = null;
        try {

            //TODO: set attribute for lobby built for 2 players

            nick = FXMLLoader.load(getClass().getResource("/scenes/choose-nickname.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        window.setScene(new Scene(nick));
    }


    @FXML
    private void three(MouseEvent e) {
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

        Parent nick = null;
        try {
            //TODO: set attribute for lobby built for 3 players

            nick = FXMLLoader.load(getClass().getResource("/scenes/choose-nickname.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        window.setScene(new Scene(nick));
    }
}