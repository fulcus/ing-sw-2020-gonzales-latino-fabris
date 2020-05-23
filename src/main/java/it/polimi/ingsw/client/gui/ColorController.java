package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ColorController {

    @FXML
    private Button white;
    @FXML
    private Button blue;
    @FXML
    private Button beige;


    public ColorController() {
    }


    @FXML
    private void blue(MouseEvent e) {


        String color = "blue";

        System.out.println("controller: clicked blue");

        try {
            //give color to manager thread
            GuiManager.queue.put(color);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void white(MouseEvent e) {

        String color = "white";

        try {
            //give color to manager thread
            GuiManager.queue.put(color);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

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

        String color = "beige";

        try {
            //give color to manager thread
            GuiManager.queue.put(color);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
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
