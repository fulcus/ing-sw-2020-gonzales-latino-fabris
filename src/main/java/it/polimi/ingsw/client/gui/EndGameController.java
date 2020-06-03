package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EndGameController {

    @FXML
    private Text notifyText;

    public EndGameController() {
    }

    @FXML
    private void playAgain() {
        //todo
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    protected void setDisconnectionPlayer(String disconectedPlayer) {
        if (disconectedPlayer.equals("me")) {
            notifyText.setText("You \n disconnected");
        } else {
            notifyText.setText(disconectedPlayer + "\n disconnected");
        }
    }

}
