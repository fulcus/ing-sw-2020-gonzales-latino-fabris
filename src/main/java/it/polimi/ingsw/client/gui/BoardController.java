package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.serializableObjects.CellClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.lang.reflect.Field;

import static it.polimi.ingsw.client.gui.GuiManager.*;

public class BoardController {

    @FXML
    public ImageView c0r0;
    @FXML
    public ImageView c1r0;
    @FXML
    public ImageView c2r0;
    @FXML
    public ImageView c3r0;
    @FXML
    public ImageView c4r0;
    @FXML
    public ImageView c0r1;
    @FXML
    public ImageView c1r1;
    @FXML
    public ImageView c2r1;
    @FXML
    public ImageView c3r1;
    @FXML
    public ImageView c4r1;
    @FXML
    public ImageView c0r2;
    @FXML
    public ImageView c1r2;
    @FXML
    public ImageView c2r2;
    @FXML
    public ImageView c3r2;
    @FXML
    public ImageView c4r2;
    @FXML
    public ImageView c0r3;
    @FXML
    public ImageView c1r3;
    @FXML
    public ImageView c2r3;
    @FXML
    public ImageView c3r3;
    @FXML
    public ImageView c4r3;
    @FXML
    public ImageView c0r4;
    @FXML
    public ImageView c1r4;
    @FXML
    public ImageView c2r4;
    @FXML
    public ImageView c3r4;
    @FXML
    public ImageView c4r4;

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
    private ImageView godPowerOnImage;
    @FXML
    private ImageView godPowerOffImage;
    @FXML
    private Text mainText;
    @FXML
    private GridPane boardGrid;

    private final Image bluemale;
    private final Image bluefemale;
    private final Image whitemale;
    private final Image whitefemale;
    private final Image beigemale;
    private final Image beigefemale;
    private final Image level1;
    private final Image level2;
    private final Image level3;
    private final Image dome;

    private boolean cellRequested;
    private boolean godPowerRequested;
    private String myGodDescription;
    private String godRightDescription;
    private String godLeftDescription;
    private String godPowerOffAnswer;
    private String godPowerOnAnswer;

    public BoardController() {
        cellRequested = false;
        bluemale = new Image("/board/workers/male_worker_blue.png");
        bluefemale = new Image("/board/workers/female_worker_blue.png");
        whitemale = new Image("/board/workers/male_worker_white.png");
        whitefemale = new Image("/board/workers/female_worker_white.png");
        beigemale = new Image("/board/workers/male_worker_beige.png");
        beigefemale = new Image("/board/workers/female_worker_beige.png");
        level1 = new Image("/board/board_buildings/level1.png");
        level2 = new Image("/board/board_buildings/level2.png");
        level3 = new Image("/board/board_buildings/level3.png");
        dome = new Image("/board/board_buildings/dome_light.png");
    }

    protected void init() {

        myNickname.setText(nickname1.get());
        otherNicknameRight.setText(nickname2.get());
        Image myGodImage = new Image("/gods/full_" + god1.get().toLowerCase() + ".png");
        Image godRightImage = new Image("/gods/full_" + god2.get().toLowerCase() + ".png");
        myGod.setImage(myGodImage);
        otherGodRight.setImage(godRightImage);

        myGod.setOnMouseClicked(e -> showGodDescription(god1.get()));
        otherGodRight.setOnMouseClicked(e -> showGodDescription(god2.get()));

        if (numberOfPlayers.get() == 2) {
            godLeftFrame.setVisible(false);
            godLeftBar.setVisible(false);
            otherNicknameLeft.setVisible(false);
            otherGodLeft.setVisible(false);
        } else {
            otherNicknameRight.setText(nickname3.get());
            Image godLeftImage = new Image("/gods/full_" + god3.get().toLowerCase() + ".png");
            otherGodLeft.setImage(godLeftImage);

            otherGodLeft.setOnMouseClicked(e -> showGodDescription(god3.get()));
        }

        showGodPowers();

        mainText.setText("WELCOME");
    }

    protected void update(CellClient cell) {
        System.out.println("update");
        boardClient.get().update(cell);
    }

    private void showGodPowers() {

        godPowerOnImage.setVisible(false);
        godPowerOffImage.setVisible(false);

        String godPowerOnName = "";
        String godPowerOffName = "";

        //always Y N except for atlas
        godPowerOnAnswer = "Y";
        godPowerOffAnswer = "N";

        switch (god1.get().toLowerCase()) {
            case "apollo":
                break;
            case "artemis":
                godPowerOnName = "movefast";
                godPowerOffName = "skipmove";
                break;
            case "athena":
                break;
            case "atlas":
                godPowerOnName = "builddome";
                godPowerOffName = "bothbuild";
                godPowerOnAnswer = "D";
                godPowerOffAnswer = "B";
                break;
            case "charon":
                break;
            case "demeter":
                godPowerOnName = "bothbuild";
                godPowerOffName = "skipbuild";
                break;
            case "hephaestus":
                godPowerOnName = "bothbuild";
                godPowerOffName = "skipbuild";
                break;
            case "hera":
                break;
            case "hestia":
                godPowerOnName = "bothbuild";
                godPowerOffName = "skipbuild";
                break;
            case "minotaur":
                break;
            case "pan":
                break;
            case "prometheus": //buildbeforemove
                godPowerOnName = "buildbeforemove";
                godPowerOffName = "buildbeforemove"; //todo change to striked out image
                break;
            case "triton":
                godPowerOnName = "movefast";
                godPowerOffName = "skipmove";
                break;
            case "zeus":
                break;
        }

        if(!godPowerOnName.equals("")) {
            godPowerOnImage.setImage(new Image("/board/god_powers/gp_" + godPowerOnName + ".png"));
            godPowerOnImage.setVisible(true);
        }

        if(!godPowerOffName.equals("")) {
            godPowerOffImage.setImage(new Image("/board/god_powers/gp_" + godPowerOffName + ".png"));
            godPowerOffImage.setVisible(true);
        }
    }

    protected void printMap() {

        //iterate on all panes of boardGrid and render them correctly
        //based on the corresponding content of boardClient
        for (Node node : boardGrid.getChildren()) {

            Pane pane = (Pane) node;

            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);

            CellClient cell = boardClient.get().findCell(row, col);

            //cell contains building
            switch (cell.getCellLevel()) {
                case 0:
                    //nothing, ground level
                    break;
                case 1:
                    ImageView building1 = new ImageView(level1);
                    building1.setFitWidth(80);
                    building1.setFitHeight(80);
                    pane.getChildren().add(building1);
                    break;
                case 2:
                    ImageView building2 = new ImageView(level2);
                    building2.setFitWidth(80);
                    building2.setFitHeight(80);
                    pane.getChildren().add(building2);
                    break;
                case 3:
                    ImageView building3 = new ImageView(level3);
                    building3.setFitWidth(56);
                    building3.setFitHeight(56);
                    building3.setX(11.8);
                    building3.setY(11.8);
                    pane.getChildren().add(building3);
                    break;

                default:
                    System.out.println("building level error");
            }

            //cell contains dome
            if (cell.hasDome()) {
                ImageView domeBuilding = new ImageView(dome);
                domeBuilding.setFitWidth(43.7);
                domeBuilding.setFitHeight(43.7);
                domeBuilding.setX(18.1);
                domeBuilding.setY(18.1);
                pane.getChildren().add(domeBuilding);
            }

            //get ImageView attribute of cell
            Class<?> c = getClass();
            Field field;
            ImageView workerImageView = null;

            try {

                field = c.getDeclaredField("c" + col + "r" + row);
                workerImageView = (ImageView) field.get(this);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            //cell contains worker
            if (cell.hasWorker()) {

                String sex = cell.getWorkerClient().getWorkerSex();
                String color = cell.getWorkerClient().getWorkerColor();

                //get image of worker in cell
                Field field2;
                Image workerImage = null;

                try {
                    String workerId = color + sex;
                    field2 = c.getDeclaredField(workerId.toLowerCase());
                    workerImage = (Image) field2.get(this);

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                workerImageView.setViewOrder(-1.0);
                workerImageView.setImage(workerImage);
                workerImageView.setFitWidth(48);
                workerImageView.setFitHeight(48);
                workerImageView.setX(18);
                workerImageView.setY(15);

            } else {
                workerImageView.setImage(null);
            }
        }
    }

    protected void printToMainText(String text) {
        mainText.setText(text);
    }

    protected void setCellRequested(boolean cellRequested) {
        this.cellRequested = cellRequested;
    }

    protected void setGodPowerRequested(boolean godPowerRequested) {
        this.godPowerRequested = godPowerRequested;
    }


    @FXML
    private void chooseCell(MouseEvent event) {
        Node source = (Node) event.getSource();

        //for imageview of worker
        if (source instanceof ImageView)
            source = source.getParent();

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
    private void showGodDescription(String god) {

        String godDescriptionField = god.toLowerCase() + "Description";

        Class<?> c = ChooseGodController.class;
        Field field;
        String godDescription = null;

        try {

            field = c.getDeclaredField(godDescriptionField);
            godDescription = (String) field.get(null);  //null because the field retrieved is static

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        printToMainText(godDescription);

        System.out.println("God description");
    }

    @FXML
    private void menu() {
        System.out.println("MENU");


        AnchorPane win = null;
        try {
            win = FXMLLoader.load(getClass().getResource("/scenes/win.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((GridPane)boardRoot).getChildren().add(win);
    }

    @FXML
    private void godPowerOnClick() {

        if (godPowerRequested) {
            try {
                GuiManager.queue.put(godPowerOnAnswer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printToMainText("You used your god power");
        }

        setGodPowerRequested(false);
    }

    @FXML
    private void godPowerOffClick() {

        if (godPowerRequested) {
            try {
                GuiManager.queue.put(godPowerOffAnswer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printToMainText("You didn't use your god power");
        }
        setGodPowerRequested(false);
    }


    @FXML
    private void glowWorker00() {
        Glow glow = new Glow(0.3);
        c0r0.setEffect(glow);
    }

    @FXML
    private void normalWorker00() {
        Glow glow = new Glow(0);
        c0r0.setEffect(glow);
    }

    @FXML
    private void glowWorker10() {
        Glow glow = new Glow(0.3);
        c1r0.setEffect(glow);
    }

    @FXML
    private void normalWorker10() {
        Glow glow = new Glow(0);
        c1r0.setEffect(glow);
    }

    @FXML
    private void glowWorker20() {
        Glow glow = new Glow(0.3);
        c2r0.setEffect(glow);
    }

    @FXML
    private void normalWorker20() {
        Glow glow = new Glow(0);
        c2r0.setEffect(glow);
    }

    @FXML
    private void glowWorker30() {
        Glow glow = new Glow(0.3);
        c3r0.setEffect(glow);
    }

    @FXML
    private void normalWorker30() {
        Glow glow = new Glow(0);
        c3r0.setEffect(glow);
    }

    @FXML
    private void glowWorker40() {
        Glow glow = new Glow(0.3);
        c4r0.setEffect(glow);
    }

    @FXML
    private void normalWorker40() {
        Glow glow = new Glow(0);
        c4r0.setEffect(glow);
    }

    @FXML
    private void glowWorker01() {
        Glow glow = new Glow(0.3);
        c0r1.setEffect(glow);
    }

    @FXML
    private void normalWorker01() {
        Glow glow = new Glow(0);
        c0r1.setEffect(glow);
    }

    @FXML
    private void glowWorker11() {
        Glow glow = new Glow(0.3);
        c1r1.setEffect(glow);
    }

    @FXML
    private void normalWorker11() {
        Glow glow = new Glow(0);
        c1r1.setEffect(glow);
    }

    @FXML
    private void glowWorker21() {
        Glow glow = new Glow(0.3);
        c2r1.setEffect(glow);
    }

    @FXML
    private void normalWorker21() {
        Glow glow = new Glow(0);
        c2r1.setEffect(glow);
    }

    @FXML
    private void glowWorker31() {
        Glow glow = new Glow(0.3);
        c3r1.setEffect(glow);
    }

    @FXML
    private void normalWorker31() {
        Glow glow = new Glow(0);
        c3r1.setEffect(glow);
    }

    @FXML
    private void glowWorker41() {
        Glow glow = new Glow(0.3);
        c4r1.setEffect(glow);
    }

    @FXML
    private void normalWorker41() {
        Glow glow = new Glow(0);
        c4r1.setEffect(glow);
    }

    @FXML
    private void glowWorker02() {
        Glow glow = new Glow(0.3);
        c0r2.setEffect(glow);
    }

    @FXML
    private void normalWorker02() {
        Glow glow = new Glow(0);
        c0r2.setEffect(glow);
    }

    @FXML
    private void glowWorker12() {
        Glow glow = new Glow(0.3);
        c1r2.setEffect(glow);
    }

    @FXML
    private void normalWorker12() {
        Glow glow = new Glow(0);
        c1r2.setEffect(glow);
    }

    @FXML
    private void glowWorker22() {
        Glow glow = new Glow(0.3);
        c2r2.setEffect(glow);
    }

    @FXML
    private void normalWorker22() {
        Glow glow = new Glow(0);
        c2r2.setEffect(glow);
    }

    @FXML
    private void glowWorker32() {
        Glow glow = new Glow(0.3);
        c3r2.setEffect(glow);
    }

    @FXML
    private void normalWorker32() {
        Glow glow = new Glow(0);
        c3r2.setEffect(glow);
    }

    @FXML
    private void glowWorker42() {
        Glow glow = new Glow(0.3);
        c4r2.setEffect(glow);
    }

    @FXML
    private void normalWorker42() {
        Glow glow = new Glow(0);
        c4r2.setEffect(glow);
    }

    @FXML
    private void glowWorker03() {
        Glow glow = new Glow(0.3);
        c0r3.setEffect(glow);
    }

    @FXML
    private void normalWorker03() {
        Glow glow = new Glow(0);
        c0r3.setEffect(glow);
    }

    @FXML
    private void glowWorker13() {
        Glow glow = new Glow(0.3);
        c1r3.setEffect(glow);
    }

    @FXML
    private void normalWorker13() {
        Glow glow = new Glow(0);
        c1r3.setEffect(glow);
    }

    @FXML
    private void glowWorker23() {
        Glow glow = new Glow(0.3);
        c2r3.setEffect(glow);
    }

    @FXML
    private void normalWorker23() {
        Glow glow = new Glow(0);
        c2r3.setEffect(glow);
    }

    @FXML
    private void glowWorker33() {
        Glow glow = new Glow(0.3);
        c3r3.setEffect(glow);
    }

    @FXML
    private void normalWorker33() {
        Glow glow = new Glow(0);
        c3r3.setEffect(glow);
    }

    @FXML
    private void glowWorker43() {
        Glow glow = new Glow(0.3);
        c4r3.setEffect(glow);
    }

    @FXML
    private void normalWorker43() {
        Glow glow = new Glow(0);
        c4r3.setEffect(glow);
    }

    @FXML
    private void glowWorker04() {
        Glow glow = new Glow(0.3);
        c0r4.setEffect(glow);
    }

    @FXML
    private void normalWorker04() {
        Glow glow = new Glow(0);
        c0r4.setEffect(glow);
    }

    @FXML
    private void glowWorker14() {
        Glow glow = new Glow(0.3);
        c1r4.setEffect(glow);
    }

    @FXML
    private void normalWorker14() {
        Glow glow = new Glow(0);
        c1r4.setEffect(glow);
    }

    @FXML
    private void glowWorker24() {
        Glow glow = new Glow(0.3);
        c2r4.setEffect(glow);
    }

    @FXML
    private void normalWorker24() {
        Glow glow = new Glow(0);
        c2r4.setEffect(glow);
    }

    @FXML
    private void glowWorker34() {
        Glow glow = new Glow(0.3);
        c3r4.setEffect(glow);
    }

    @FXML
    private void normalWorker34() {
        Glow glow = new Glow(0);
        c3r4.setEffect(glow);
    }

    @FXML
    private void glowWorker44() {
        Glow glow = new Glow(0.3);
        c4r4.setEffect(glow);
    }

    @FXML
    private void normalWorker44() {
        Glow glow = new Glow(0);
        c4r4.setEffect(glow);
    }



}