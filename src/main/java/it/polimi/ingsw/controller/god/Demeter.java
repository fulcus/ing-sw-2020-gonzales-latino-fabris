package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class Demeter implements God {

    Cell firstBuildingCell;
    private static final String name = "DEMETER";

    @Override
    public void evolveTurn(Worker w) {
        move(w);
        win(w);
        firstBuildingCell = build(w);
        buildAgain(w);
    }

    private void buildAgain(Worker worker) {

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


                    if (!(worker.getPlayer().getGame().getMap().findCell(buildingX, buildingY).equals(firstBuildingCell)) && buildingX < Map.SIDE && buildingY < Map.SIDE && !(worker.getPlayer().getGame().getMap().findCell(buildingX, buildingY).isOccupied())) {

                        if (worker.getPlayer().getGame().getMap().findCell(buildingX, buildingY).getLevel() == 3) {

                            System.out.println("You can build a Dome here, type Dome to build");
                            buildingName = input.nextLine();

                            if (buildingName.equals("Dome")) {
                                worker.buildDome(buildingX, buildingY);
                                break;
                            }

                        } else {

                            System.out.println("You can build a Block here, type Block to build");
                            buildingName = input.nextLine();

                            if (buildingName.equals("Block")) {
                                worker.buildBlock(buildingX, buildingY);
                                break;
                            }

                        }

                    }

                    System.out.println("It must be a different place");

                } while (true);

            } else if (command.equals("Endturn")) {

                break;
            }
        } while (true);
    }


}
