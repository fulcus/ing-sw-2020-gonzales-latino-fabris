package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.Scanner;

public class CLIMainView {

    Scanner input = new Scanner(System.in);
    int numberOfPlayers;
    TurnHandler myTurnHandler;
    Game myGame;


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
    //da cambiare in base a come VICTOR fara controller
    public ArrayList<String> askPlayersNickname(){

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

    public String askMovementDirection() {

        String selectedDirection;

        while (true) {
            System.out.println(myTurnHandler.getCurrentPlayer().getNickname() + "where do you want to move your worker?");

            selectedDirection = input.nextLine();

            if (selectedDirection.equals("N") || selectedDirection.equals("NE") || selectedDirection.equals("E") || selectedDirection.equals("SE") || selectedDirection.equals("S") || selectedDirection.equals("SO") || selectedDirection.equals("O") || selectedDirection.equals("NO"))
                return selectedDirection;

            else
                System.out.println("Invalid Direction");

        }


    }


}
