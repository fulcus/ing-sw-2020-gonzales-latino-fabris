package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PlayerGeneralSettingsController implements Initializable {


    //dove viene inizializzato? forse nel thread principale della gui
    //E qui si può fare una cosa come gui.getGuiManager;
    private GuiManager guiManager;


    public PlayerGeneralSettingsController() {
    }


    @FXML
    private TextField nickname;


    @FXML
    private void blue(MouseEvent e) {

        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();

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
    }


    @FXML
    private void white(MouseEvent e) {
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
    }


    @FXML
    private void beige(MouseEvent e) {
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
    }


    @FXML
    private void login(MouseEvent e) {

        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();

        Parent lobby = null;

        //ci sarà un modo per verificare dal server
        //se il nick è già stato scelto da un altro player
        //e che andrà a decidere se il risultato boolean del seguente if

        //forse l'askPlayerNickname() va posto nella scena precedente?
        if (nickAvailable(nickname.getCharacters())) {
            lobby = FXMLLoader.load(getClass().getResource("/scenes/choose-color.fxml"));

            window.setScene(new Scene(lobby));

        }
        else {
            //dovrebbe essere una sorta di pop-up
            //che esce sopra la scena.
            //è un metodo dell'interfaccia implementata da GuiManager
            //forse quindi serve un attributo ti tipo GuiManager in ogni classe controller della gui?
            guiManager.notAvailableNickname();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //IPText.setFont(Font.loadFont("/fonts/negotiate-free.tff",12));
    }


    @FXML
    private void two(MouseEvent e) {
        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();

        Parent nick = null;
        try {

            //TODO: set attribute for lobby built for 2 players

            nick = FXMLLoader.load(getClass().getResource("/scenes/choose-nickname.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        window.setScene(new Scene(nick));
    }


    @FXML
    private void three(MouseEvent e) {
        Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();

        Parent nick = null;
        try {
            //TODO: set attribute for lobby built for 3 players

            nick = FXMLLoader.load(getClass().getResource("/scenes/choose-nickname.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        window.setScene(new Scene(nick));
    }


    //never used in this controller's scenes by now.
    @FXML
    private void next() {
        System.out.println("next scene");
    }
}
