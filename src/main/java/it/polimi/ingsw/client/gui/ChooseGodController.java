package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Field;

public class ChooseGodController {

    @FXML
    ImageView apolloFrame;
    @FXML
    ImageView artemisFrame;
    @FXML
    ImageView athenaFrame;
    @FXML
    ImageView atlasFrame;
    @FXML
    ImageView charonFrame;
    @FXML
    ImageView demeterFrame;
    @FXML
    ImageView hephaestusFrame;
    @FXML
    ImageView heraFrame;
    @FXML
    ImageView hestiaFrame;
    @FXML
    ImageView minotaurFrame;
    @FXML
    ImageView panFrame;
    @FXML
    ImageView prometheusFrame;
    @FXML
    ImageView tritonFrame;
    @FXML
    ImageView zeusFrame;

    @FXML
    private Label godName;
    @FXML
    private TextArea godDescriptionArea;
    @FXML
    private ImageView selectedGodImage;

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

    private ImageView godFrame;

    public ChooseGodController() {
        whiteFrame = new Image("/frames/frame_white.png");
        blueFrame = new Image("/frames/frame_blue.png");
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

        //
        if(godFrame != null)
            godFrame.setImage(whiteFrame);

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

    }

    @FXML
    private void select() {
        System.out.println("Select");
    }


}
