package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ChooseGodController {

    public ChooseGodController(){

    }

    @FXML
    private void clickedApollo(MouseEvent event) {

        System.out.println("choose Apollo");
    }

    @FXML
    private void clickedNext(MouseEvent event) {

        System.out.println("Next");
    }


}
