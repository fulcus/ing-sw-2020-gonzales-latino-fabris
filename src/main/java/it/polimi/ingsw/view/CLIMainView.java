package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.god.*;


import java.util.ArrayList;
import java.util.Scanner;

public class CLIMainView implements ViewObserver {

    Scanner input = new Scanner(System.in);
    int numberOfPlayers;
    TurnHandler myTurnHandler;
    Game myGame;
    Map myMap;// this will contain a copy of the Model's map and each cell will be update if there are any changes

    /**
     * This is the CLIMainView constructor.
     *
     * @param //controller This is the controller that has created the game.
     */
    //devo passare controller
    public void CLIMainView() {

        //mycontroller = controller;
        input = new Scanner(System.in);
    }

    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {

        String startString;

        System.out.println("WELCOME TO SANTORINI");

        while (true) {

            System.out.println("--write START to play--");
            startString = input.nextLine();

            if (startString.equals("START")) ;
            break;

        }


    }


    /**
     * @return The number of players.
     */
    public int askNumberofPlayers() {

        int insertedNumberOfPlayers;

        while (true) {

            System.out.println("Insert the number of players");
            insertedNumberOfPlayers = input.nextInt();

            if (insertedNumberOfPlayers != 2 && insertedNumberOfPlayers != 3)
                System.out.println("You can play only with 2 or 3 players!");

            else
                break;

        }

        return insertedNumberOfPlayers;

    }

    /**
     * This method registers all the players' nicknames.
     *
     * @return The ArrayList with the nicknames.
     */
    public ArrayList<String> askPlayersNickname() {

        ArrayList<String> nicknames = new ArrayList<String>(numberOfPlayers);
        String insertedNickname;
        int i = 0;

        while (i < numberOfPlayers) {

            System.out.println("Player" + (i + 1) + "insert your nickname");
            insertedNickname = input.nextLine();

            if (!(nicknames.contains(insertedNickname)) && insertedNickname != null) {
                nicknames.add(insertedNickname);
                i++;
            } else
                System.out.println("This nickname has been chosen by another player");

        }

        return nicknames;

    }

    public ArrayList<Integer> askPlayingGod(){
        int i=0, j=0;
        int chosenOne;
        ArrayList<Integer> choice = new ArrayList<Integer>(myGame.getNumberOfPlayers());

        while(i<myGame.getNumberOfPlayers()){
            System.out.println("Choose one God among the following: ");
            //stampare il nome degli dei
            chosenOne = input.nextInt();
            if (choice.contains(chosenOne) || !(chosenOne==1 || chosenOne==2 || chosenOne==3)){
                System.out.println("This god has already been chosen or your choice is not valid.\n Pick another!\n");
            }
            else{
                choice.add(i, chosenOne);
                i++;
            }
        }
        return choice;
    }

    public ArrayList<Integer> askGameGods(){
        ArrayList<Integer> chosenGods = new ArrayList<Integer>(myGame.getNumberOfPlayers());
        int select, i=0;
        System.out.println("Select " + myGame.getNumberOfPlayers() + "God cards to play the Game\n");
        //fare la print degli dèi, affiancarli ad un numero per poterli selezionare;

        while( i < myGame.getNumberOfPlayers() ) {
            select = input.nextInt();
            if (chosenGods.contains(select) || select<1 || select>14 )
                System.out.println("You have already chosen this God. Pick another!\n");
            else {
                chosenGods.add(select);
                i++;
            }

        }
        return chosenGods;
    }

    public String askChosenWorker() {

        String chosenWorker;

        System.out.println(myTurnHandler.getCurrentPlayer().getNickname() + "is your turn!");//Il fatto che la view per stampare il nickname del player debba andare chiamare prima il controller che poi a sua volta chiama il model....boh?

        while (true) {
            System.out.println("Insert MALE or FEMALE to choose one of your workers");

            chosenWorker = input.nextLine();

            if (chosenWorker.equals("MALE") || chosenWorker.equals("FEMALE"))
                return chosenWorker;

            else
                System.out.println("Invalid input");

        }


    }

    /**
     * This method asks the user to insert the direction of his next movement.
     * @return The compass direction of the movement.
     */
    public String askMovementDirection() {

        String selectedDirection;

        while (true) {
            System.out.println(myTurnHandler.getCurrentPlayer().getNickname() + "where do you want to move your worker?");

            selectedDirection = input.nextLine();

            if (selectedDirection.equals("N") || selectedDirection.equals("NE") || selectedDirection.equals("E") || selectedDirection.equals("SE") || selectedDirection.equals("S") || selectedDirection.equals("SO") || selectedDirection.equals("O") || selectedDirection.equals("NO"))
                return selectedDirection;

            else
                System.out.println("Invalid Direction. You must use: N,NE,E,SE,S,SO,O,NO");

        }


    }

    /**
     * This method asks the user to insert the position where he wants to build.
     * @return The compass direction of the place where to build.
     */
    //TODO devo chiedere che tipo di edificio vuole costruire? perchè nel caso del dio Atlas
    public String askBuildingDirection() {

        String selectedBuildingDirection;

        while (true) {

            System.out.println(myTurnHandler.getCurrentPlayer().getNickname() + "where do you want to build? Insert compass points!");

            selectedBuildingDirection = input.nextLine();

            if (selectedBuildingDirection.equals("N") || selectedBuildingDirection.equals("NE") || selectedBuildingDirection.equals("E") || selectedBuildingDirection.equals("SE") || selectedBuildingDirection.equals("S") || selectedBuildingDirection.equals("SO") || selectedBuildingDirection.equals("O") || selectedBuildingDirection.equals("NO"))
                return selectedBuildingDirection;

            else
                System.out.println("Invalid Direction. You must use: N,NE,E,SE,S,SO,O,NO");


        }

    }

    /**
     * This method prints an updated version of the Map, depending on the Class' parameter "mymap".
     */
    public void printMap() {

        final String LINE_SEPARATOR = "+------+------+------+------+------+%n";
        final String SPACESEPARATOR = "+      +      +      +      +      +%n";

        for (int i = 0; i < Map.SIDE; i++) {

            System.out.printf(LINE_SEPARATOR);//Border
            System.out.printf(SPACESEPARATOR);//space
            printMapLine(i);//contenuto game
            System.out.printf(SPACESEPARATOR);//space
        }

        System.out.printf(LINE_SEPARATOR);

    }

    /**
     * Prints a line of the map, showing eventual buildings and workers of the line.
     * @param linenumber Represents the line which content will be displayed.
     */
    public void printMapLine(int linenumber) {

        for (int i = 0; i < Map.SIDE; i++) {

            System.out.printf("+");
            System.out.printf(" ");//1

            //Place where eventual buildings will be printed

            if (myMap.findCell(linenumber, i).hasDome())//if cell has dome
                System.out.printf("D");

            else {
                //if cell has not dome

                if (myMap.findCell(linenumber, i).getLevel() == 0)
                    System.out.printf(" ");//if there is no building, prints nothing

                else
                    System.out.printf("%d", myMap.findCell(linenumber, i).getLevel());   // if there is a building, prints its level
            }

            //SPACE

            System.out.printf(" ");//3


            //PLACE of cell (4) that prints eventual presence of a worker with its parameter(SEX;COLOR)


            if (myMap.findCell(linenumber, i).getWorker() == null) {
                System.out.printf(" ");
                System.out.printf(" ");//5
            } else {

                if (myMap.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.BLUE && myMap.findCell(linenumber, i).getWorker().getSex() == Sex.MALE)
                    System.out.printf("BM");
                else if (myMap.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.BLUE && myMap.findCell(linenumber, i).getWorker().getSex() == Sex.FEMALE)
                    System.out.printf("BF");
                else if (myMap.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.WHITE && myMap.findCell(linenumber, i).getWorker().getSex() == Sex.MALE)
                    System.out.printf("WM");
                else if (myMap.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.WHITE && myMap.findCell(linenumber, i).getWorker().getSex() == Sex.FEMALE)
                    System.out.printf("WF");
                else if (myMap.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.BEIGE && myMap.findCell(linenumber, i).getWorker().getSex() == Sex.MALE)
                    System.out.printf("bM");
                else if (myMap.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.BEIGE && myMap.findCell(linenumber, i).getWorker().getSex() == Sex.FEMALE)
                    System.out.printf("bF");

            }


            System.out.printf(" ");//6


        }

        System.out.printf("+%n");

    }


    // TODO: è vero che è il worker a cambiare la mappa, però l'oggetto che cambia è la mappa(o la cella)
    //TODO: in update si potrebbero passare inoltre dei parametri che specificano cosa ha fatto avvenire il cambiamento, ad esempio se per un movimento o per una costruzione. Intal modo posso far apparire all'utente: PLAYER1 si è mosso-->stampa mappa.
    @Override
    public void update(Worker observedWorker) {

        printMap();

    }
}
