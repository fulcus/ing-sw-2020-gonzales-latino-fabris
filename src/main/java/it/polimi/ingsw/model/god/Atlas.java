package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class Atlas implements God{

    public void evolveTurn(Worker w) {
        lose(w);
        move(w);
        win(w);
        build(w);
        buildDome(w);
    }

    public void move(Worker w) {
    }


    @Override
    public void build(Worker w) {

        System.out.println("Atlas additional power: You can also build a Dome at any level");

        //TODO evitare che una volta scelta la cella, non può più cambiare fare(while nel while)
        Scanner input = new Scanner(System.in);
        String buildingName;
        int buildingX;
        int buildingY;

        System.out.println("Insert the x y position where you want to build in");

        do {
            buildingX = input.nextInt();
            buildingY = input.nextInt();

            //questo controllo va fatto con un metodo static in Map
            if (buildingX < Map.SIDE && buildingY < Map.SIDE && !(w.getPlayer().getGame().getMap().findCell(buildingX,buildingY).isOccupied()))
                break;

            System.out.println("Invalid position or occupied cell! It must be in a 5X5 map.TRY AGAIN");

        } while (true);



        do {

            if(w.getPlayer().getGame().getMap().findCell(buildingX,buildingY).getLevel() == 3)
            {
                System.out.println("You can only build a Dome here, type Dome to build");
                buildingName = input.nextLine();

                if(buildingName.equals("Dome")) {
                    w.buildDome(buildingX, buildingY);
                    break;
                }

            }

            else {

                System.out.println("You can build both a Block and a Dome here, type the name of building you want here");
                buildingName = input.nextLine();

                if(buildingName.equals("Block")) {

                    w.buildBlock(buildingX, buildingY);
                    break;
                }

                else if(buildingName.equals("Dome")){

                    w.buildDome(buildingX, buildingY);
                    break;
                }

            }

            System.out.println("Incorrect building's name.TRY AGAIN");

        } while (true);

        System.out.println(buildingName + "successfully built");

    }

    public boolean win(Worker w){
    }

    public boolean lose(Worker w) {
    }

    public void buildDome(Worker w){

    }
}