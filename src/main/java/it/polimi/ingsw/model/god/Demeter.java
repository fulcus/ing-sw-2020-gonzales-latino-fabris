package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class Demeter implements God {

    Cell firstBuildingCell;

    public void evolveTurn(Worker w) {
        lose(w);
        move(w);
        build(w);
        secondBuild(w);
        win(w);
    }

    public void move(Worker w) {
    }

    public void build(Worker w) {

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
            if (buildingX < Map.SIDE && buildingY < Map.SIDE && !(w.getPlayer().getGame().getMap().findCell(buildingX, buildingY).isOccupied())) {
                firstBuildingCell = w.getPlayer().getGame().getMap().findCell(buildingX, buildingY);
                break;
            }

            System.out.println("Invalid position or occupied cell! It must be in a 5X5 map.TRY AGAIN");

        } while (true);


        do {

            if (w.getPlayer().getGame().getMap().findCell(buildingX, buildingY).getLevel() == 3) {
                System.out.println("You can build a Dome here, type Dome to build");
                buildingName = input.nextLine();

                if (buildingName.equals("Dome")) {
                    w.buildDome(buildingX, buildingY);
                    break;
                }

            } else {

                System.out.println("You can build a Block here, type Block to build");
                buildingName = input.nextLine();

                if (buildingName.equals("Block")) {
                    w.buildBlock(buildingX, buildingY);
                    break;
                }

            }

        } while (true);

        System.out.println(buildingName + "successfully built");


    }

    public boolean win(Worker w) {
    }

    public boolean lose(Worker w) {
    }

    public void secondBuild(Worker w) {

        Scanner input = new Scanner(System.in);
        String buildingName;
        String command;
        int buildingX;
        int buildingY;

        do {


            System.out.println("You can build again, type Build or Endturn");
            command = input.nextLine();


            if (command.equals("Build")) {
                do {

                    System.out.println("Insert another x y position where you want to build in");

                    buildingX = input.nextInt();
                    buildingY = input.nextInt();


                    if (!(w.getPlayer().getGame().getMap().findCell(buildingX, buildingY).equals(firstBuildingCell)) && buildingX < Map.SIDE && buildingY < Map.SIDE && !(w.getPlayer().getGame().getMap().findCell(buildingX, buildingY).isOccupied())) {

                        if (w.getPlayer().getGame().getMap().findCell(buildingX, buildingY).getLevel() == 3) {

                            System.out.println("You can build a Dome here, type Dome to build");
                            buildingName = input.nextLine();

                            if (buildingName.equals("Dome")) {
                                w.buildDome(buildingX, buildingY);
                                break;
                            }

                        } else {

                            System.out.println("You can build a Block here, type Block to build");
                            buildingName = input.nextLine();

                            if (buildingName.equals("Block")) {
                                w.buildBlock(buildingX, buildingY);
                                break;
                            }

                        }

                    }

                    System.out.println("It must be a different place");

                } while (true)


            } else if (command.equals("Endturn")) {

                break;
            }
        } while (true);
    }
}
