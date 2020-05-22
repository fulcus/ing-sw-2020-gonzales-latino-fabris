package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class ConnectController {

    @FXML
    private TextField IPText;
    @FXML
    private Text error;     //todo

    public ConnectController() {
    }

    @FXML
    private void connect() {

        String IPAddress = IPText.getCharacters().toString();

        try {
            //give ip address to manager thread
            GuiManager.queue.put(IPAddress);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

}
