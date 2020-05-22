package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StartPlayerController implements Initializable {

    @FXML
    private Label playerName1;
    @FXML
    private Label playerName2;
    @FXML
    private Label playerName3;
    @FXML
    private Button player1;
    @FXML
    private Button player2;
    @FXML
    private Button player3;
    @FXML
    private ImageView godImage1;
    @FXML
    private ImageView godImage2;
    @FXML
    private ImageView godImage3;

    private volatile int numberOfPlayers;

    public StartPlayerController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //display player names
        playerName1.setText("nick1");
        playerName2.setText("nick2");

        //adapted for 2 players game
        if (numberOfPlayers == 3)
            playerName3.setText("nick3");
        else {
            playerName3.getParent().setVisible(false);
        }

        //load god images
        //"charon" and other god names will be replaced with player.getGod
        String path1 = "/gods/full_" + "charon" + ".png";
        Image player1God = new Image(path1);
        godImage1.setImage(player1God);

        String path2 = "/gods/full_" + "demeter" + ".png";
        Image player2God = new Image(path2);
        godImage2.setImage(player2God);

        //adapted for 2 players game
        String path3 = numberOfPlayers == 3 ? "/gods/full_" + "hephaestus" + ".png" : "/frames/bg_panelMid.png";

        Image player3God = new Image(path3);
        godImage3.setImage(player3God);

    }

    @FXML
    private void choosePlayer(MouseEvent event) {
        //get which button was clicked ie which player was chosen
        String playerId = ((Button) event.getSource()).getId();
        System.out.println("chose " + playerId);
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

}
