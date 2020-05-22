package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.serializableObjects.WorkerClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @FXML
    private Text playerNickname;

    @FXML
    private Text player1Nickname;

    @FXML
    private Text player2Nickname;

    @FXML
    private ImageView playerGod;

    @FXML
    private ImageView player1God;

    @FXML
    private ImageView player2God;

    @FXML
    private ImageView player2Bar;

    @FXML
    private ImageView player2Frame;

    @FXML
    private Text mainText;

    private int numberOfPlayers;//TODO make a unique number of players inside gui, that will updates gui if it changes.


    private boolean cellRequested;

    private WorkerClient selectedWorker;//Useful to make conversion from coordinates to compass points


    @FXML
    private GridPane board;

    public BoardController() {
        selectedWorker = null;
        numberOfPlayers = 3;
        cellRequested = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String myGod = "Apollo";
        String God1 = "Hera";
        String God2 = "Triton";

        if(numberOfPlayers==2){
            player2Frame.setVisible(false);
            player2Bar.setVisible(false);
            player2Nickname.setVisible(false);
            player2God.setVisible(false);
            setGodsImages(myGod,God1);
            setPlayersNicknames("Alberto","Fra");
        }

        else {
            setGodsImages(myGod, God1, God2);
            setPlayersNicknames("Alberto","Vitto","Fra");
        }

        mainText.setText("WELCOME");


    }

    public void askInitialWorkerPosition(String workerSex) {
        mainText.setText("Place your" + workerSex + "worker!");
    }

    public void askBuildingDirection(){
        mainText.setText("Move your worker!");
    }
    public void setCellRequested(boolean cellRequested) {
        this.cellRequested = cellRequested;
    }

    @FXML
    private void chooseCell(MouseEvent event) {
        Node source = (Node)event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse clicked cell in [%d, %d]%n", rowIndex, colIndex);

        //if user clicks on a cell, and a method has requested a cell,
        //coordinates are sent to gi manager
        if (cellRequested) {
            try {
                GuiManager.queue.put(rowIndex);
                GuiManager.queue.put(colIndex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        setCellRequested(false);
    }

    @FXML
    private void showGodDescription(){
        System.out.println("God description");
    }

    private void setGodsImages(String myGod, String player1God, String player2God){

        Image myGodImage = new Image("/gods/full_" + myGod.toLowerCase() + ".png");
        Image player1GodImage = new Image("/gods/full_" + player1God.toLowerCase() + ".png");
        Image player2GodImage = new Image("/gods/full_" + player2God.toLowerCase() + ".png");

        playerGod.setImage(myGodImage);
        this.player1God.setImage(player1GodImage);
        this.player2God.setImage(player2GodImage);

    }

    private void setGodsImages(String myGod, String player1God){

        Image myGodImage = new Image("/gods/full_" + myGod.toLowerCase() + ".png");
        Image player1GodImage = new Image("/gods/full_" + player1God.toLowerCase() + ".png");

        playerGod.setImage(myGodImage);
        this.player1God.setImage(player1GodImage);

    }

    private void setPlayersNicknames(String myNickname, String player1Nickname){
        playerNickname.setText(myNickname);
        this.player1Nickname.setText(player1Nickname);
    }

    private void setPlayersNicknames(String myNickname, String player1Nickname,String player2Nickname){
        playerNickname.setText(myNickname);
        this.player1Nickname.setText(player1Nickname);
        this.player2Nickname.setText(player2Nickname);
    }

    public void printMap(){
        //SHOULD Be updated only image views that refer to changed objects(workers, buildings)

    }
    @FXML
    private void menuClicked() {
        System.out.println("MENU");
    }


}