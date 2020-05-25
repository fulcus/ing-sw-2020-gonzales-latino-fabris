package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.serializableObjects.WorkerClient;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import static it.polimi.ingsw.client.gui.GuiManager.*;

public class BoardController {

    @FXML
    private Text myNickname;
    @FXML
    private Text otherNicknameLeft;
    @FXML
    private Text otherNicknameRight;
    @FXML
    private ImageView myGod;
    @FXML
    private ImageView otherGodLeft;
    @FXML
    private ImageView otherGodRight;
    @FXML
    private ImageView godLeftBar;
    @FXML
    private ImageView godLeftFrame;
    @FXML
    private Text mainText;
    @FXML
    private GridPane board;

    private boolean cellRequested;

    private WorkerClient selectedWorker;//Useful to make conversion from coordinates to compass points


    public BoardController() {
        selectedWorker = null;
        cellRequested = false;
    }

    protected void init() {

        myNickname.setText(nickname1.get());
        otherNicknameRight.setText(nickname2.get());
        Image myGodImage = new Image("/gods/full_" + god1.get().toLowerCase() + ".png");
        Image godRightImage = new Image("/gods/full_" + god2.get().toLowerCase() + ".png");
        myGod.setImage(myGodImage);
        otherGodRight.setImage(godRightImage);

        if (numberOfPlayers.get() == 2) {
            godLeftFrame.setVisible(false);
            godLeftBar.setVisible(false);
            otherNicknameLeft.setVisible(false);
            otherGodLeft.setVisible(false);
        } else {
            otherNicknameRight.setText(nickname3.get());
            Image godLeftImage = new Image("/gods/full_" + god3.get().toLowerCase() + ".png");
            otherGodLeft.setImage(godLeftImage);
        }

        mainText.setText("WELCOME");
    }

    @FXML
    private void chooseCell(MouseEvent event) {
        Node source = (Node) event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse clicked cell in [%d, %d]%n", rowIndex, colIndex);

        //if user clicks on a cell, and a method has requested a cell,
        //coordinates are sent to gui manager
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
    private void showGodDescription() {
        System.out.println("God description");
    }

    @FXML
    private void menu() {
        System.out.println("MENU");
    }

    protected void printMap() {
        //SHOULD Be updated only image views that refer to changed objects(workers, buildings)

    }

    protected void askInitialWorkerPosition(String workerSex) {
        mainText.setText("Place your " + workerSex.toLowerCase() + " worker!");
    }

    protected void askBuildingDirection() {
        mainText.setText("Move your worker!");
    }

    protected void setCellRequested(boolean cellRequested) {
        this.cellRequested = cellRequested;
    }

    protected void waitChallengerStartPlayer() {

    }

}