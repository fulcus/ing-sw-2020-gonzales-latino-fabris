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

/**
 * Controller class of the GUI choose god scene.
 */
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

    protected static final String apolloDescription = "Your Worker may move into an opponent Worker’s space\n by forcing their Worker to the space yours just vacated.";
    protected static final String artemisDescription = "Your Worker may move one additional time, but not back to its initial space.";
    protected static final String athenaDescription = "If one of your Workers moved up on your last turn, \nopponent Workers cannot move up this turn.";
    protected static final String atlasDescription = "Your Worker may build a dome at any level.";
    protected static final String charonDescription = "Before your Worker moves, you may force a neighboring opponent Worker\n to the space directly on the other side of your Worker, if that space is unoccupied.";
    protected static final String demeterDescription = "Your Worker may build one additional time, but not on the same space.";
    protected static final String hephaestusDescription = "Your Worker may build one additional block (not dome) \non top of your first block.";
    protected static final String heraDescription = "An opponent cannot win by moving into a perimeter space.";
    protected static final String hestiaDescription = "Your Worker may build one additional time,\nbut this cannot be on a perimeter space.";
    protected static final String minotaurDescription = "Your Worker may move into an opponent Worker’s space, if their Worker\n can be forced one space straight backwards to an unoccupied space at any level.";
    protected static final String panDescription = "You also win if your Worker moves down two or more levels.";
    protected static final String prometheusDescription = "If your Worker does not move up, \nit may build both before and after moving.";
    protected static final String tritonDescription = "Each time your Worker moves into a perimeter space, \nit may immediately move again.";
    protected static final String zeusDescription = "Your Worker may build a block under itself.";

    private final Image whiteFrame;
    private final Image blueFrame;
    private final Image redFrame;

    private ImageView godFrame;
    private String selectedGodID;

    private boolean challenger;
    protected  ArrayList<ImageView> redFrames;


    public ChooseGodController() {
        redFrames = new ArrayList<>(3);
        whiteFrame = new Image("/frames/frame_white.png");
        blueFrame = new Image("/frames/frame_blue.png");
        redFrame = new Image("/frames/frame_coral.png");
        selectedGodID = null;
        challenger = false;

    }


    protected void init() {
        //  select.setDisable(true);
    }


    @FXML
    private void selectedGod(MouseEvent event) {

        String godId = ((ImageView) event.getSource()).getId();

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

/*
        if (godFrame != null) {

            if (godFrame.getImage().equals(redFrame))
                previouslyRed = true;

            if (keepRedFrame && godFrame.getImage().equals(blueFrame) && previouslyRed) {
                godFrame.setImage(redFrame);
                previouslyRed = false;
            } else
                godFrame.setImage(whiteFrame);
        }
*/

        if(godFrame != null){

            //if the clicked god, was part of selected gods, the frame is reset to red
            if(redFrames.contains(godFrame))
                godFrame.setImage(redFrame);

            else
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
            mainText.setText("You are the Challenger! Select " + godsLeftToChoose + " gods");

        else
            mainText.setText("You are the Challenger! Select " + godsLeftToChoose + " god");

    }


    public void setChallenger() {
        challenger = true;
    }


    /**
     * Lets the player know that the other player is choosing his god for the game.
     * @param otherPlayer The nickname of the other player who's choosing his god.
     */
    public void waitOtherPlayerChooseGod(String otherPlayer) {

        select.setDisable(true);
        mainText.setText(otherPlayer + " is choosing his god...");
    }


    /**
     * Lets the player know that the challenger is choosing the gods for the game.
     * @param challenger The nickname of the challenger.
     */
    public void waitChallengerChooseGods(String challenger) {

        mainText.setText(challenger + " is the Challenger and is choosing the gods...");
    }


    /**
     * Gives the player the order to choose the god for the game.
     * The player must choose an available god chosen by the challenger.
     */
    public void askPlayerGod() {
        mainText.setText("Choose your god!.");
        select.setDisable(false);
    }


    /**
     * Shows with a frame the gods that the challenger has chosen for the game.
     * @param chosenGods The list of the chosen gods.
     */
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


                redFrames.add(frame);//creates arraylist with frames of chosen gods
                //when receive gods chosen by other players, remove from arraylist

                //setKeepRedFrame();

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * If a player has already chosen the god for the current game, the other players are notified of his choice.
     * @param otherPlayer The player who has already made his choice.
     * @param chosenGod The god that the other player has chosen.
     */
    public void otherPlayerChoseGod(String otherPlayer, String chosenGod) {

        Class<?> c = getClass();
        Field godFrame;
        ImageView frame;

        try {
            godFrame = c.getDeclaredField(chosenGod.toLowerCase() + "Frame");
            godFrame.setAccessible(true);
            frame = (ImageView) godFrame.get(this);

            //when receive gods chosen by other players, remove from arraylist
            redFrames.remove(frame);

            frame.setImage(whiteFrame); //set to white


        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


        mainText.setText(otherPlayer + " has chosen " + chosenGod);

    }


    /**
     * Allows to print into the main text field that the god was already chosen.
     */
    public void playerChoseInvalidGod() {
        mainText.setText("This god has already been chosen");
    }


}
