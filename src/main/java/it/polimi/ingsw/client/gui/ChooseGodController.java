package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ChooseGodController {

    @FXML
    private ImageView apolloFrame;
    @FXML
    private ImageView artemisFrame;
    @FXML
    private ImageView athenaFrame;
    @FXML
    private ImageView atlasFrame;
    @FXML
    private ImageView charonFrame;
    @FXML
    private ImageView demeterFrame;
    @FXML
    private ImageView hephaestusFrame;
    @FXML
    private ImageView heraFrame;
    @FXML
    private ImageView hestiaFrame;
    @FXML
    private ImageView minotaurFrame;
    @FXML
    private ImageView panFrame;
    @FXML
    private ImageView prometheusFrame;
    @FXML
    private ImageView tritonFrame;
    @FXML
    private ImageView zeusFrame;
    @FXML
    private Label godName;
    @FXML
    private TextArea godDescriptionArea;
    @FXML
    private ImageView selectedGodImage;

    @FXML
    private Label mainText;

    @FXML
    private Button select;

    private final String apolloDescription = "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";
    private final String artemisDescription = "Your Worker may move one additional time, but not back to its initial space.";
    private final String athenaDescription = "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    private final String atlasDescription = "Your Worker may build a dome at any level.";
    private final String charonDescription = "Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if that space is unoccupied.";
    private final String demeterDescription = "Your Worker may build one additional time, but not on the same space.";
    private final String hephaestusDescription = "Your Worker may build one additional block (not dome) on top of your first block.";
    private final String heraDescription = "An opponent cannot win by moving into a perimeter space.";
    private final String hestiaDescription = "Your Worker may build one additional time, but this cannot be on a perimeter space.";
    private final String minotaurDescription = "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";
    private final String panDescription = "You also win if your Worker moves down two or more levels.";
    private final String prometheusDescription = "If your Worker does not move up, it may build both before and after moving.";
    private final String tritonDescription = "Each time your Worker moves into a perimeter space, it may immediately move again.";
    private final String zeusDescription = "Your Worker may build a block under itself.";

    private final Image whiteFrame;
    private final Image blueFrame;
    private final Image redFrame;

    private ImageView godFrame;
    private String selectedGodID;

    private boolean challenger;
    private boolean keepRedFrame;
    private boolean previouslyRed;

    public ChooseGodController() {
        whiteFrame = new Image("/frames/frame_white.png");
        blueFrame = new Image("/frames/frame_blue.png");
        redFrame = new Image("/frames/frame_coral.png");
        selectedGodID = null;
        challenger = false;
        keepRedFrame = false;
        previouslyRed = false;
    }

    protected void init() {
        //  select.setDisable(true);
    }

    public void setKeepRedFrame() {
        keepRedFrame = true;
    }

    @FXML
    private void selectedGod(MouseEvent event) {

        String godId = ((ImageView) event.getSource()).getId();
        System.out.println(godId);

        String imagePath = "/gods/full_" + godId + ".png";
        String name = godId.toUpperCase();

        //get the description attribute for the clicked god
        Class<?> c = getClass();
        Field godDescription;
        String description = null;

        try {
            godDescription = c.getDeclaredField(godId + "Description");
            godDescription.setAccessible(true);
            description = (String) godDescription.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


        if (godFrame != null) {

            if (godFrame.getImage().equals(redFrame))
                previouslyRed = true;

            if (keepRedFrame && godFrame.getImage().equals(blueFrame) && previouslyRed) {
                godFrame.setImage(redFrame);
                previouslyRed = false;
            } else
                godFrame.setImage(whiteFrame);
        }

        //get the ImageView godFrame attribute for the clicked god
        Field frame;

        try {
            frame = c.getDeclaredField(godId + "Frame");
            frame.setAccessible(true);
            godFrame = (ImageView) frame.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (godFrame.getImage().equals(redFrame))
            previouslyRed = true;


        godFrame.setImage(blueFrame);


        Image selectedGod = new Image(imagePath);
        selectedGodImage.setImage(selectedGod);

        godName.setText(name);

        godDescriptionArea.clear();
        godDescriptionArea.appendText(description);

        selectedGodID = godId;

    }

    @FXML
    private void select() {

        if (selectedGodID == null)
            mainText.setText("Select a God!");

        else {
            try {
                GuiManager.queue.put(selectedGodID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getGodFromChallenger(int numOfPlayers, int alreadyChosenGods) {

        setChallenger();

        select.setDisable(false);

        int godsLeftToChoose = numOfPlayers - alreadyChosenGods;

        if (godsLeftToChoose == 2 || godsLeftToChoose == 3)
            mainText.setText("You are the Challenger! Select " + godsLeftToChoose + "gods");

        else
            mainText.setText("You are the Challenger! Select " + godsLeftToChoose + "god");

    }


    public void setChallenger() {
        challenger = true;
    }

    public void waitOtherPlayerChooseGod(String otherPlayer) {

        select.setDisable(true);

        mainText.setText(otherPlayer + " is choosing his god...");
    }

    public void waitChallengerChooseGods(String challenger) {

        mainText.setText(challenger + " is the Challenger and is choosing gods...");
    }

    public void askPlayerGod() {
        mainText.setText("Choose your god!.");
        select.setDisable(false);
    }


    public void printChosenGods(ArrayList<String> chosenGods) {

        Class<?> c = getClass();
        Field godFrame;
        ImageView frame;

        for (String godId : chosenGods) {


            try {
                godFrame = c.getDeclaredField(godId.toLowerCase() + "Frame");
                godFrame.setAccessible(true);
                frame = (ImageView) godFrame.get(this);
                frame.setImage(redFrame);
                setKeepRedFrame();

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    public void otherPlayerChoseGod(String otherPlayer, String chosenGod) {

        mainText.setText(otherPlayer + " has chosen " + chosenGod);

    }

    public void playerChoseInvalidGod() {
        mainText.setText("This god has already been chosen");
    }


}
