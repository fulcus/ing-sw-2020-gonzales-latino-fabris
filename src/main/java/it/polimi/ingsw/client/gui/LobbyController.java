package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {

    @FXML
    private Label playerName1;
    @FXML
    private Label playerName2;
    @FXML
    private Label playerName3;
    @FXML
    private ImageView godImage1;
    @FXML
    private ImageView godImage2;
    @FXML
    private ImageView godImage3;
    @FXML
    private ImageView loader1;
    @FXML
    private ImageView loader2;
    @FXML
    private ImageView loader3;
    @FXML
    private ImageView banner3;
    @FXML
    private Button next;
    @FXML
    private Text loadingText1;
    @FXML
    private Text loadingText2;
    @FXML
    private Text loadingText3;

    private int playersConnected;
    private int numberOfPlayers;

    public LobbyController() {
        playersConnected = 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfPlayers = 3;    //temporary, into will be stored in global variable of client

        //disable next button until all players are connected
        next.setDisable(true);

        //hide label
        if (numberOfPlayers == 2) {
            playerName3.setVisible(false);
            banner3.setVisible(false);
            loader3.setVisible(false);
            loadingText3.setVisible(false);
        }

        //add client player as first
        showPlayer("fra", "apollo");
        showPlayer("albe", "athena");
        //showPlayer("paperino", "atlas");

    }

    public void showPlayer(String nickname, String god) {

        if (playerName1.getText().equals("")) {

            playerName1.setText(nickname);

            String path = "/gods/full_" + god.toLowerCase() + ".png";
            Image godImage = new Image(path);
            godImage1.setImage(godImage);
            loader1.setVisible(false);
            loadingText1.setVisible(false);

        } else if (playerName2.getText().equals("")) {

            playerName2.setText(nickname);

            String path = "/gods/full_" + god.toLowerCase() + ".png";
            Image godImage = new Image(path);
            godImage2.setImage(godImage);
            loader2.setVisible(false);
            loadingText2.setVisible(false);

        } else if (playerName3.getText().equals("")) {

            playerName3.setText(nickname);

            String path = "/gods/full_" + god.toLowerCase() + ".png";
            Image godImage = new Image(path);
            godImage3.setImage(godImage);
            loader3.setVisible(false);
            loadingText3.setVisible(false);

        } else
            System.out.println("Error: cannot add more than three players");    //debugging

        playersConnected++;

        if (playersConnected == numberOfPlayers)
            next.setDisable(false);
    }

    @FXML
    private void next() {
        System.out.println("next");
    }
}
