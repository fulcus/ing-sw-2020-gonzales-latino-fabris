package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChooseGodController {

    String apolloString = "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";
    String artemisString = "Your Worker may move one additional time, but not back to its initial space.";
    String athenaString = "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    String atlasString = "Your Worker may build a dome at any level.";
    String charonString = "Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if that space is unoccupied.";
    String demeterString = "Your Worker may build one additional time, but not on the same space.";
    String hephaestusString = "Your Worker may build one additional block (not dome) on top of your first block.";
    String heraString = "An opponent cannot win by moving into a perimeter space.";
    String hestiaString = "Your Worker may build one additional time, but this cannot be on a perimeter space.";
    String minotaurString = "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";
    String panString = "You also win if your Worker moves down two or more levels.";
    String prometheusString = "If your Worker does not move up, it may build both before and after moving.";
    String tritonString = "Each time your Worker moves into a perimeter space, it may immediately move again.";
    String zeusString = "Your Worker may build a block under itself.";
    String description;
    String name;

    @FXML
    private Label godName;
    @FXML
    private TextArea godDescription;
    @FXML
    private ImageView selectedGodImage;

    public ChooseGodController(){
    }

    @FXML
    private void selectedGod(MouseEvent event) {

        String godId = ((ImageView)event.getSource()).getId();
        String imagePath = "/gods/full_" + "apollo" + ".png";


        System.out.println(godId);


            if(godId.equals("apolloButton")){
                imagePath = "/gods/full_" + "apollo" + ".png";
                description = apolloString;
                name = "APOLLO";

            }
            else if (godId.equals("artemisButton")){
                imagePath = "/gods/full_" + "artemis" + ".png";
                description = artemisString;
                name = "ARTEMIS";
            }

            else if(godId.equals("athenaButton")){
                imagePath = "/gods/full_" + "athena" + ".png";
                description = athenaString;
                name = "ATHENA";
            }

            else if(godId.equals("atlasButton")){
                imagePath = "/gods/full_" + "atlas" + ".png";
                description = atlasString;
                name = "ATLAS";
            }

            else if (godId.equals("charonButton")){
                imagePath = "/gods/full_" + "charon" + ".png";
                description = charonString;
                name = "CHARON";
            }

            else if (godId.equals("demeterButton")){
                imagePath = "/gods/full_" + "demeter" + ".png";
                description = demeterString;
                name = "DEMETER";
            }

            else if(godId.equals("hephaestusButton")){
                imagePath = "/gods/full_" + "hephaestus" + ".png";
                description = hephaestusString;
                name = "HEPHAESTUS";
            }

            else if(godId.equals("heraButton")){
                imagePath = "/gods/full_" + "hera" + ".png";
                description = heraString;
                name = "HERA";
            }

            else if(godId.equals("hestiaButton")){
                imagePath = "/gods/full_" + "hestia" + ".png";
                description = hestiaString;
                name = "HESTIA";
            }

            else if(godId.equals("minotaurButton")){
                imagePath = "/gods/full_" + "minotaur" + ".png";
                description = minotaurString;
                name = "MINOTAUR";
            }

            else if(godId.equals("panButton")){
                imagePath = "/gods/full_" + "pan" + ".png";
                description = panString;
                name = "PAN";
            }

            else if (godId.equals("tritonButton")){
                imagePath = "/gods/full_" + "triton" + ".png";
                description = tritonString;
                name = "TRITON";
            }

            else if(godId.equals("prometheusButton")){
                imagePath = "/gods/full_" + "prometheus" + ".png";
                description = prometheusString;
                name = "PROMETHEUS";
            }

            else if(godId.equals("zeusButton"))
                {
                imagePath = "/gods/full_" + "zeus" + ".png";
                description = zeusString;
                name = "ZEUS";
            }


        Image selectedGod = new Image(imagePath);
        selectedGodImage.setImage(selectedGod);
        godName.setText(name);
        godDescription.clear();
        godDescription.appendText(description);

    }

    @FXML
    private void clickedNext(MouseEvent event) {

        System.out.println("Next");
    }


}
