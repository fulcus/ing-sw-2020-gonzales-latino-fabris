package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.BoardClient;
import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.model.Board;
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

    private Image blueMale;
    private Image blueFemale;
    private Image whiteMale;
    private Image whiteFemale;
    private Image beigeMale;
    private Image beigeFemale;
    private Image level1;
    private Image level2;
    private Image level3;
    private Image dome;


    public BoardController() {
        selectedWorker = null;
        cellRequested = false;
        blueMale = new Image("/board/workers/male_worker_blue.png");
        blueFemale = new Image("/board/workers/female_worker_blue.png");
        whiteMale = new Image("/board/workers/male_worker_white.png");
        whiteFemale = new Image("/board/workers/female_worker_white.png");
        beigeMale = new Image("/board/workers/male_worker_beige.png");
        beigeFemale = new Image("/board/workers/female_worker_beige.png");
        level1 = new Image("/board/level1/alto/level1_light.png");
        level2 = new Image("/board/level2/alto/level2_light.png");
        level3 = new Image("/board/level3/alto/level3_light.png");
        dome = new Image("/board/dome/alto/dome_light.png");
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

    public void update(CellClient toUpdateCell) {

    }

    protected void printMap() {

        for (int i = 0; i < Board.SIDE; i++) {
            for (int j = 0; j < Board.SIDE; i++) {

                //TODO SHOW IMAGES

            }
        }


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