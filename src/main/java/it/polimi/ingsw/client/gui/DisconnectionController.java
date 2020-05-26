package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DisconnectionController {

    @FXML
    private ImageView background;


    public DisconnectionController(){

    }

    protected void playAgain(){

    }

    protected void exit(){

    }

    protected void setBackground(Image background){

        this.background.fitWidthProperty().bind(Gui.getStage().widthProperty());
        this.background.fitHeightProperty().bind(Gui.getStage().heightProperty());
        this.background.setImage(background);
    }

}
