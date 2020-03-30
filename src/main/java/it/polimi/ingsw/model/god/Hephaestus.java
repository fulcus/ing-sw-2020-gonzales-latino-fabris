package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class Hephaestus implements God{

    Cell firstBuildingCell;

    /**
     * This method calls the sequence of actions that can be done by the player who owns Hephaestus.
     * @param w This is the current worker.
     */
    public void evolveTurn(Worker w) {
        move(w);
        win(w);
        firstBuildingCell = build(w);
        secondBuild(w);
    }

    /**
     * This method allows the player to build in the same place twice.
     * @param w This is the player's current worker.
     */
    public void secondBuild(Worker w){

        Scanner input = new Scanner(System.in);
        String command;

        do {

            System.out.println("Hephaestus power: Do you wanna build again in the same place, type Yes or No");
            command = input.nextLine();

            if (command.equals("Yes")) {

                if(firstBuildingCell.getLevel()<3 && !(firstBuildingCell.hasDome())){

                    w.buildBlock(firstBuildingCell.getX(),firstBuildingCell.getY());
                    break;
                }

                else if(firstBuildingCell.getLevel()==3 && !(firstBuildingCell.hasDome()) ){

                    w.buildDome(firstBuildingCell.getX(),firstBuildingCell.getY());
                    break;
                }

                else{
                    System.out.println("You cannot build, building is already completed");
                    break;
                }
            }

            else if(command.equals("No")){

                break;
            }

            System.out.println("Incorrect input");

        }while(true);






    }
}
