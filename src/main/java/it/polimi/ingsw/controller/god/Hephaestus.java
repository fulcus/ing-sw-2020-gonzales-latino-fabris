package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class Hephaestus implements God{

    Cell firstBuildingCell;
    private static final String name = "HEPHAESTUS";

    /**
     * This method calls the sequence of actions that can be done by the player who owns Hephaestus.
     * @param worker This is the current worker.
     */
    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        firstBuildingCell = build(worker);
        secondBuild(worker);
    }

    /**
     * This method allows the player to build in the same place twice.
     * @param worker This is the player's current worker.
     */
    public void secondBuild(Worker worker){

        Scanner input = new Scanner(System.in);
        String command;

        do {

            System.out.println("Hephaestus power: Do you wanna build again in the same place, type Yes or No");
            command = input.nextLine();

            if (command.equals("Yes")) {

                if(firstBuildingCell.getLevel()<3 && !(firstBuildingCell.hasDome())){

                    worker.buildBlock(firstBuildingCell.getX(),firstBuildingCell.getY());
                    break;
                }

                else if(firstBuildingCell.getLevel()==3 && !(firstBuildingCell.hasDome()) ){

                    worker.buildDome(firstBuildingCell.getX(),firstBuildingCell.getY());
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



    public String getName(){
        return name;
    }
}
