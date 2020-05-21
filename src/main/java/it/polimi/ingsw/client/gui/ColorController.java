package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ColorController {

    public ColorController() {
    }


    @FXML
    private void blue(MouseEvent e) {

        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
        /*

        Parent lobby = null;
        try {
            //ci sarà un modo per verificare dal server
            //se il colore è già stato scelto da un altro player
            //e che andrà a decidere se il risultato boolean del seguente if
            if (colorAvailable) {
                lobby = FXMLLoader.load(getClass().getResource("/scenes/lobby.fxml"));

                window.setScene(new Scene(lobby));

            }
            else {
                //dovrebbe essere una sorta di pop-up
                //che esce sopra la scena.
                //è un metodo dell'interfaccia implementata da GuiManager
                //forse quindi serve un attributo ti tipo GuiManager in ogni classe controller della gui?
                guiManager.notAvailableColor();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //window.setScene(new Scene(lobby));

         */
    }


    @FXML
    private void white(MouseEvent e) {
        /*
        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();

        Parent lobby = null;
        //ci sarà un modo per verificare dal server
        //se il colore è già stato scelto da un altro player
        //e che andrà a decidere se il risultato boolean del seguente if
        if (colorAvailable) {
            lobby = FXMLLoader.load(getClass().getResource("/scenes/lobby.fxml"));

            window.setScene(new Scene(lobby));

        }
        else {
            //dovrebbe essere una sorta di pop-up
            //che esce sopra la scena.
            //è un metodo dell'interfaccia implementata da GuiManager
            //forse quindi serve un attributo ti tipo GuiManager in ogni classe controller della gui?
            guiManager.notAvailableColor();
        }

        //window.setScene(new Scene(lobby));
         */
    }


    @FXML
    private void beige(MouseEvent e) {
        /*
        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();

        Parent lobby = null;
        //ci sarà un modo per verificare dal server
        //se il colore è già stato scelto da un altro player
        //e che andrà a decidere se il risultato boolean del seguente if
        if (colorAvailable) {
            lobby = FXMLLoader.load(getClass().getResource("/scenes/lobby.fxml"));

            window.setScene(new Scene(lobby));

        }
        else {
            //dovrebbe essere una sorta di pop-up
            //che esce sopra la scena.
            //è un metodo dell'interfaccia implementata da GuiManager
            //forse quindi serve un attributo ti tipo GuiManager in ogni classe controller della gui?
            guiManager.notAvailableColor();
        }

        //window.setScene(new Scene(lobby));
        */

    }




}
