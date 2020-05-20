package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectController implements Initializable {

    @FXML
    private TextField IPText;

    private volatile String IPAddress;


    public ConnectController() {
        IPAddress = null;
    }

    @FXML
    private void connect() {

        IPAddress = IPText.getCharacters().toString();
        /*
        synchronized (this) {
            notifyAll();
        }*/
        try {
            GuiManager.queue.put(IPAddress);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("clicked connect " + IPAddress);


    }

    protected String getServerAddress() {
        return IPAddress;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //IPText.setFont(Font.loadFont("/fonts/negotiate-free.tff",12));
    }

}
