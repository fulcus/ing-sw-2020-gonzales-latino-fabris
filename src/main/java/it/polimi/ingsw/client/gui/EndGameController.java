package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EndGameController {

    @FXML
    private Text disconnectedPlayer;

    public EndGameController() {
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    protected void setDisconnectionPlayer(String disconectedPlayer) {

        if (disconectedPlayer.equals("me"))
            disconnectedPlayer.setText("YOU");
        else
            disconnectedPlayer.setText(disconectedPlayer);
    }

}
