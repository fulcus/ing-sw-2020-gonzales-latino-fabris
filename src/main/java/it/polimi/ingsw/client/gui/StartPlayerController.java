package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static it.polimi.ingsw.client.gui.GuiManager.*;

public class StartPlayerController {

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

    public StartPlayerController() {
    }

    public void init() {

        //display player names
        playerName1.setText(nickname1.get());
        playerName2.setText(nickname2.get());

        //adapted for 2 players game
        if (numberOfPlayers.get() == 3)
            playerName3.setText(nickname3.get());
        else {
            playerName3.getParent().setVisible(false);
        }

        //load god images
        //"charon" and other god names will be replaced with player.getGod
        String path1 = "/gods/full_" + god1.get().toLowerCase() + ".png";
        Image player1God = new Image(path1);
        godImage1.setImage(player1God);

        String path2 = "/gods/full_" + god2.get().toLowerCase() + ".png";
        Image player2God = new Image(path2);
        godImage2.setImage(player2God);

        //adapted for 2 players game
        String path3 = numberOfPlayers.get() == 3 ? "/gods/full_" + god3.get().toLowerCase() + ".png" : "/frames/bg_panelMid.png";

        Image player3God = new Image(path3);
        godImage3.setImage(player3God);

    }

    @FXML
    private void choosePlayer(MouseEvent event) {

        String playerId = ((Button) event.getSource()).getId();
        System.out.println("chose " + playerId);    //debug

        String nickname = null;

        switch (playerId) {
            case "player1":
                nickname = nickname1.get();
                break;
            case "player2":
                nickname = nickname2.get();
                break;
            case "player3":
                nickname = nickname3.get();
                break;
            default:
                System.out.println("error choosePlayer"); //debug
                break;
        }

        try {
            GuiManager.queue.put(nickname);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gui.getStage().setScene(new Scene(boardRoot));

    }

}
