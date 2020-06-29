package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.serializable.CellClient;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.lang.reflect.Field;

import static it.polimi.ingsw.client.gui.GuiManager.*;

/**
 * Controller of the board GUI scene.
 */
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
    @FXML
    private AnchorPane menu;
    @FXML
    private Button confirmButton;
    @FXML
    private TextArea godPowerText;

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
    private String godPowerOffAnswer;
    private String godPowerOnAnswer;
    private int godClicked;


    public BoardController() {
        cellRequested = false;
        bluemale = new Image("/board/workers/male_worker_blue.png");
        bluefemale = new Image("/board/workers/female_worker_blue.png");
        whitemale = new Image("/board/workers/male_worker_white.png");
        whitefemale = new Image("/board/workers/female_worker_white.png");
        beigemale = new Image("/board/workers/male_worker_beige.png");
        beigefemale = new Image("/board/workers/female_worker_beige.png");
        level1 = new Image("/board/buildings/level1.png");
        level2 = new Image("/board/buildings/level2.png");
        level3 = new Image("/board/buildings/level3.png");
        dome = new Image("/board/buildings/dome_light.png");
        godClicked = 0;
    }


    /**
     * Initializes values for the scene.
     */
    protected void init() {
        menu.setVisible(false);
        String nickname1 = players.get(0).getNickname();
        String god1 = players.get(0).getGod();
        String nickname2 = players.get(1).getNickname();
        String god2 = players.get(1).getGod();

        //right: player 2, left: player 3 (if
        myNickname.setText(nickname1);
        otherNicknameRight.setText(nickname2);
        Image myGodImage = new Image("/gods/full_" + god1.toLowerCase() + ".png");
        Image godRightImage = new Image("/gods/full_" + god2.toLowerCase() + ".png");
        myGod.setImage(myGodImage);
        otherGodRight.setImage(godRightImage);

        myGod.setOnMouseClicked(e -> showGodDescription(god1));
        otherGodRight.setOnMouseClicked(e -> showGodDescription(god2));

        if (numberOfPlayers.get() == 2) {
            godLeftFrame.setVisible(false);
            godLeftBar.setVisible(false);
            otherNicknameLeft.setVisible(false);
            otherGodLeft.setVisible(false);
        } else {
            String nickname3 = players.get(2).getNickname();
            String god3 = players.get(2).getGod();

            otherNicknameLeft.setText(nickname3);
            Image godLeftImage = new Image("/gods/full_" + god3.toLowerCase() + ".png");
            otherGodLeft.setImage(godLeftImage);

            otherGodLeft.setOnMouseClicked(e -> showGodDescription(god3));
        }

        showGodPowers();

        mainText.setText("WELCOME");
    }


    protected void update(CellClient cell) {
        System.out.println("update");
        boardClient.get().update(cell);
    }


    /**
     * Allows to show the powers of the god on the text bar.
     */
    private void showGodPowers() {

        godPowerOnImage.setVisible(false);
        godPowerOffImage.setVisible(false);

        disableGodPower();

        String godPowerOnName = "";
        String godPowerOffName = "";

        //always Y N except for atlas
        godPowerOnAnswer = "Y";
        godPowerOffAnswer = "N";

        switch (players.get(0).getGod().toLowerCase()) {
            case "apollo":
                break;
            case "artemis":
                godPowerOnName = "movefast";
                godPowerOffName = "movefast_negate";
                break;
            case "athena":
                break;
            case "atlas":
                godPowerOnName = "builddome";
                godPowerOffName = "normalBuild";
                godPowerOnAnswer = "D";
                godPowerOffAnswer = "B";
                break;
            case "charon": //todo
                godPowerOnName = "selectWorker";
                godPowerOffName = "selectWorker_negate";
                godPowerOnAnswer = "Y";
                godPowerOffAnswer = "N";
                break;
            case "demeter":
                godPowerOnName = "bothbuild";
                godPowerOffName = "bothbuild_negate";
                break;
            case "hephaestus":
                godPowerOnName = "bothbuild";
                godPowerOffName = "bothbuild_negate";
                break;
            case "hera":
                break;
            case "hestia":
                godPowerOnName = "bothbuild";
                godPowerOffName = "bothbuild_negate";
                break;
            case "minotaur":
                break;
            case "pan":
                break;
            case "prometheus":
                godPowerOnName = "buildbeforemove";
                godPowerOffName = "buildbeforemove_negate";
                break;
            case "triton":
                godPowerOnName = "movefast";
                godPowerOffName = "movefast_negate";
                break;
            case "zeus":
                break;
        }

        if (!godPowerOnName.equals("")) {
            godPowerOnImage.setImage(new Image("/board/god_powers/gp_" + godPowerOnName + "_white.png"));
            godPowerOnImage.setVisible(true);
        }

        if (!godPowerOffName.equals("")) {
            godPowerOffImage.setImage(new Image("/board/god_powers/gp_" + godPowerOffName + "_white.png"));
            godPowerOffImage.setVisible(true);
        }
    }

    /**
     * Sets enabled the power of the god on to use during a turn of the game.
     */
    protected void enableGodPower() {
        godPowerOnImage.setDisable(false);
        godPowerOffImage.setDisable(false);
    }

    /**
     * Sets disabled the power of the god on to use during a turn of the game.
     */
    protected void disableGodPower() {
        godPowerOnImage.setDisable(true);
        godPowerOffImage.setDisable(true);
    }

    /**
     * The map is printed again, updating the older version of the cells and what was inside them.
     */
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


    /**
     * Allows to print something on the main central text bar.
     *
     * @param text The text to print.
     */
    protected void printToMainText(String text) {
        mainText.setText(text);
    }

    /**
     * Allows to print the god description in its specific area.
     *
     * @param text
     */
    protected void printToGodTextArea(String text) {
        godPowerText.setText(text);
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
    protected void showGodDescription(String god) {

        if (godClicked == 0) {

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

            printToGodTextArea(godDescription);

            godClicked++;

            System.out.println("God description");
        } else {
            printToGodTextArea("");
            godClicked = 0;
        }

    }


    @FXML
    private void openMenu() {
        menu.setVisible(true);
    }

    @FXML
    private void resume() {
        menu.setVisible(false);
    }

    @FXML
    private void exit() {
        System.exit(0);
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
    public void confirmRead() {
        try {
            GuiManager.queue.put(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        confirmButton.setVisible(false);
    }


    public void setConfirmButtonVisible() {
        confirmButton.setVisible(true);
    }


    private void glowLevel(double value, Node source) {
        Glow glow = new Glow(value);

        source.setEffect(glow);

    }


    @FXML
    private void unglow(MouseEvent event) {
        Node source = (Node) event.getSource();

        //for imageview of worker
        if (source instanceof ImageView)
            source = source.getParent();

        glowLevel(0, source);

    }


    @FXML
    private void glow(MouseEvent event) {
        Node source = (Node) event.getSource();

        //for imageview of worker
        if (source instanceof ImageView)
            source = source.getParent();

        glowLevel(0.3, source);

    }

    protected void removeGodFrame(String loserNickname) {

        String nickname2 = players.get(1).getNickname();
        String god2 = players.get(1).getGod();
        String nickname3 = players.get(2).getNickname();

        //if the player in the right corner has lost,swap info between player 2 and 3
        if (nickname2.equals(loserNickname)) {
            otherNicknameRight.setText(nickname3);
            Image godRightImage = new Image("/gods/full_" + god2.toLowerCase() + ".png");
            otherGodRight.setImage(godRightImage);
        }

        //removes the other player left data
        godLeftFrame.setVisible(false);
        godLeftBar.setVisible(false);
        otherNicknameLeft.setVisible(false);
        otherGodLeft.setVisible(false);

    }

}