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
        System.out.println("clicked 2");
        //Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();


        try {

            //TODO: set attribute for lobby built for 2 players

            GuiManager.queue.put("2");


        } catch (InterruptedException ioException) {
            ioException.printStackTrace();
        }

    }


    @FXML
    private void three(MouseEvent e) {

        try {
            //TODO: set attribute for lobby built for 3 players

            GuiManager.queue.put("3");


        } catch (InterruptedException ioException) {
            ioException.printStackTrace();
        }

    }
}