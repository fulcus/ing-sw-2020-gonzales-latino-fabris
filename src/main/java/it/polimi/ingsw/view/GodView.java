package it.polimi.ingsw.view;

import java.util.Scanner;


public class GodView {

    private Scanner input;

    public GodView(){
        input  = new Scanner(System.in);
    }


    /**
     * This method asks the user to insert the position where he wants to build.
     * @return The compass direction of the place where to build.
     */
    //TODO devo chiedere che tipo di edificio vuole costruire? perchè nel caso del dio Atlas
    public String[] askBuildingDirection() {

        String[] selectedBuildingDirection = new String[2];

        System.out.println("Where do you want to build? Insert compass points!");

        while (true) {
            selectedBuildingDirection[0] = input.nextLine();

            if (selectedBuildingDirection[0].equals("N") || selectedBuildingDirection[0].equals("NE") || selectedBuildingDirection[0].equals("E") || selectedBuildingDirection[0].equals("SE") || selectedBuildingDirection[0].equals("S") || selectedBuildingDirection[0].equals("SW") || selectedBuildingDirection[0].equals("W") || selectedBuildingDirection[0].equals("NW")) {

                while (true){
                    System.out.println("Now tell me what do you want to build:  Block <B>  or  Dome <D>  : ");
                    selectedBuildingDirection[1] = input.nextLine();

                    if (selectedBuildingDirection[1].equals("B") || selectedBuildingDirection[1].equals("D"))
                        return selectedBuildingDirection;

                    else
                        System.out.println("Invalid Building. You must build B or D\n");
                }
            }

            else
                System.out.println("Invalid Direction. You must use: N, NE, E, SE, S, SW, W, NW   ");

        }
    }


    /**
     * This method asks the user to insert the direction of his next movement.
     * @return The compass direction of the movement.
     */
    public String askMovementDirection() {

        String selectedDirection;
        System.out.println("Where do you want to move your worker?");

        while (true) {
            selectedDirection = input.nextLine();

            if (selectedDirection.equals("N") || selectedDirection.equals("NE") || selectedDirection.equals("E") || selectedDirection.equals("SE") || selectedDirection.equals("S") || selectedDirection.equals("SO") || selectedDirection.equals("O") || selectedDirection.equals("NO"))
                return selectedDirection;

            else
                System.out.println("Invalid Direction. You must use: N, NE, E, SE, S, SW, W, NW   ");

        }

    }


    public String askMoveAgain() {

        System.out.println("Do you want to move again your Worker? (Y = 'Yes', N = 'No'");
        return playerAnswerYN();
    }


    public String askWantToMoveUp() {

        System.out.println("Do you want to move up? \n Y for Yes, N for No\n");
        return playerAnswerYN();
    }


    public String askBuildAgainHephaestus(){

        System.out.println("You are allowed to build another time, but ONLY in the same position you built before\n <Y> for Yes,  <N> for No :   ");
        return playerAnswerYN();
    }


    public String askBuildAgainDemeter() {

        System.out.println("You are allowed to build another time, but NOT in the same position you built before\n <Y> for Yes,  <N> for No :   ");
        return playerAnswerYN();
    }


    public String playerAnswerYN(){
        String answer;

        while (true) {
            answer = input.nextLine();
            if (answer.equals("Y") || answer.equals("N"))
                return answer;
            System.out.println("Incorrect answer: You need to type 'Y' or 'N'");
        }
    }
}
