package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static it.polimi.ingsw.client.gui.GuiManager.numberOfPlayers;
import static it.polimi.ingsw.client.gui.GuiManager.players;


/**
 * Manages the scene of the GUI where the challenger chooses the starting player for the game.
 */
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

    /**
     * Sets the initial parameters for the scene.
     */
    protected void init() {
        String nickname1 = players.get(0).getNickname();
        String god1 = players.get(0).getGod();
        String nickname2 = players.get(1).getNickname();
        String god2 = players.get(1).getGod();


        //display player names
        playerName1.setText(nickname1);
        playerName2.setText(nickname2);

        //adapted for 2 players game
        if (numberOfPlayers.get() == 3) {
            String nickname3 = players.get(2).getNickname();
            playerName3.setText(nickname3);
        } else {
            playerName3.getParent().setVisible(false);
        }

        //load god images
        //"charon" and other god names will be replaced with player.getGod
        String path1 = "/gods/full_" + god1.toLowerCase() + ".png";
        Image player1God = new Image(path1);
        godImage1.setImage(player1God);

        String path2 = "/gods/full_" + god2.toLowerCase() + ".png";
        Image player2God = new Image(path2);
        godImage2.setImage(player2God);

        //adapted for 2 players game
        String path3;

        if (numberOfPlayers.get() == 2) {
            path3 = "/frames/bg_panelMid.png";

        } else {
            String god3 = players.get(2).getGod();
            path3 = "/gods/full_" + god3.toLowerCase() + ".png";
        }

        Image player3God = new Image(path3);
        godImage3.setImage(player3God);

    }

    @FXML
    private void choosePlayer(MouseEvent event) {

        String playerId = ((Button) event.getSource()).getId();

        String nickname = null;

        switch (playerId) {
            case "player1":
                nickname = players.get(0).getNickname();
                break;
            case "player2":
                nickname = players.get(1).getNickname();
                break;
            case "player3":
                nickname = players.get(2).getNickname();
                break;
            default:
                System.out.println("error in choosePlayer"); //debug
                break;
        }

        try {
            GuiManager.queue.put(nickname);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
