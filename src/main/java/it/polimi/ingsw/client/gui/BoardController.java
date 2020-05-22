package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML
    private Text playerNickname;

    @FXML
    private Text player1Nickname;

    @FXML
    private Text player2Nickname;

    @FXML
    private ImageView playerGod;

    @FXML
    private ImageView player1God;

    @FXML
    private ImageView player2God;

    @FXML
    private ImageView player2Bar;

    @FXML
    private ImageView player2Frame;

    @FXML
    private Text mainText;

    private int numberOfPlayers;//TODO make a unique number of players inside gui, that will updates gui if it changes.


    @FXML
    private GridPane board;

    public BoardController() {
        numberOfPlayers = 3;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String myGod = "Apollo";
        String God1 = "Hera";
        String God2 = "Triton";

        if (numberOfPlayers == 2) {
            player2Frame.setVisible(false);
            player2Bar.setVisible(false);
            player2Nickname.setVisible(false);
            player2God.setVisible(false);
            setGodsImages(myGod, God1);
            setPlayersNicknames("Alberto", "Fra");
        } else {
            setGodsImages(myGod, God1, God2);
            setPlayersNicknames("Alberto", "Vitto", "Fra");
        }

        mainText.setText("WELCOME");


    }

    @FXML
    private void chooseCell(MouseEvent event) {
        Node source = (Node) event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse clicked cell in [%d, %d]%n", rowIndex, colIndex);
    }

    @FXML
    private void showGodDescription() {
        System.out.println("God description");
    }

    private void setGodsImages(String myGod, String player1God, String player2God) {

        Image myGodImage = new Image("/gods/full_" + myGod.toLowerCase() + ".png");
        Image player1GodImage = new Image("/gods/full_" + player1God.toLowerCase() + ".png");
        Image player2GodImage = new Image("/gods/full_" + player2God.toLowerCase() + ".png");

        playerGod.setImage(myGodImage);
        this.player1God.setImage(player1GodImage);
        this.player2God.setImage(player2GodImage);

    }

    private void setGodsImages(String myGod, String player1God) {

        Image myGodImage = new Image("/gods/full_" + myGod.toLowerCase() + ".png");
        Image player1GodImage = new Image("/gods/full_" + player1God.toLowerCase() + ".png");

        playerGod.setImage(myGodImage);
        this.player1God.setImage(player1GodImage);

    }

    private void setPlayersNicknames(String myNickname, String player1Nickname) {
        playerNickname.setText(myNickname);
        this.player1Nickname.setText(player1Nickname);
    }

    private void setPlayersNicknames(String myNickname, String player1Nickname, String player2Nickname) {
        playerNickname.setText(myNickname);
        this.player1Nickname.setText(player1Nickname);
        this.player2Nickname.setText(player2Nickname);
    }

    @FXML
    private void menuClicked() {
        System.out.println("MENU");
    }


}