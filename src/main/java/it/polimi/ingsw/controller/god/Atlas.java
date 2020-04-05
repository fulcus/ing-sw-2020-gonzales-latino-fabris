package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class Atlas implements God{

    /**
     *
     * @param worker This the current worker.
     * @return It returns the cell wherein the worker has just built.
     */
    @Override
    public Cell build(Worker worker) {
       return buildAllowDome(worker);
    }

    private Cell buildAllowDome(Worker worker) {

        System.out.println("Atlas additional power: You can also build a Dome at any level");

        //TODO evitare che una volta scelta la cella, non può più cambiare fare(while nel while)
        Scanner input = new Scanner(System.in);
        String buildingName;
        Cell buildingCell;
        int buildingX;
        int buildingY;

        System.out.println("Insert the x y position where you want to build in");

        do {
            buildingX = input.nextInt();
            buildingY = input.nextInt();

            //questo controllo va fatto con un metodo static in Map
            if (buildingX < Map.SIDE && buildingY < Map.SIDE && !(worker.getPlayer().getGame().getMap().findCell(buildingX,buildingY).isOccupied())){
                buildingCell = worker.getPlayer().getGame().getMap().findCell(buildingX,buildingY);
                break;
            }


            System.out.println("Invalid position or occupied cell! It must be in a 5X5 map.TRY AGAIN");

        } while (true);



        do {

            if(buildingCell.getLevel() == 3 && !(buildingCell.hasDome()))
            {
                System.out.println("You can only build a Dome here, type Dome to build");
                buildingName = input.nextLine();

                if(buildingName.equals("Dome")) {
                    worker.buildDome(buildingX, buildingY);
                    break;
                }

            }

            else if (buildingCell.getLevel()<3 && !(buildingCell.hasDome())){

                System.out.println("You can build both a Block and a Dome here, type the name of building you want here");
                buildingName = input.nextLine();

                if(buildingName.equals("Block")) {

                    worker.buildBlock(buildingX, buildingY);
                    break;
                }

                else if(buildingName.equals("Dome")){

                    worker.buildDome(buildingX, buildingY);
                    break;
                }

            }

            else{
                System.out.println("You cannot build here");
            }

            System.out.println("TRY AGAIN");

        } while (true);


        System.out.println(buildingName + "successfully built");
        return buildingCell;

    }

}
