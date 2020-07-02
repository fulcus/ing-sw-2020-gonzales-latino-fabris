package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EndGameController {

    @FXML
    private Text disconnectedPlayer;

    @FXML
    private Text winner;

    public EndGameController() {
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    protected void setDisconnectionPlayer(String disconnectedPlayer) {
        this.disconnectedPlayer.setText(disconnectedPlayer);
    }

    protected void setEndgameText(String text) {
        this.winner.setText(text);
    }

}
