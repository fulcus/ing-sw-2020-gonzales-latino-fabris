package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.god.*;


import java.util.ArrayList;
import java.util.Scanner;

public class CLIMainView {

    Scanner input = new Scanner(System.in);
    int numberOfPlayers;
    TurnHandler myTurnHandler;
    Game myGame;
    GameController gameController = new GameController();


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
    public int askNumberOfPlayers() {

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

    public ArrayList<Integer> askPlayingGod(){
        int i=0, j=0;
        int chosenOne;
        ArrayList<Integer> choice = new ArrayList<Integer>(myGame.getNumberOfPlayers());

        myTurnHandler.nextPlayer();
        while(i<myGame.getNumberOfPlayers()){
            System.out.println(myTurnHandler.getCurrentPlayer().getNickname() + "! Choose one God among the following: ");
            gameController.printGameGods();
            chosenOne = input.nextInt();
            if (choice.contains(chosenOne) || !(chosenOne==1 || chosenOne==2 || chosenOne==3)){
                System.out.println("This god has already been chosen or your choice is not valid.\n Pick another!\n");
            }
            else{
                choice.add(i, chosenOne);
                myTurnHandler.nextTurn();
                i++;
            }
        }
        return choice;
    }

    public ArrayList<Integer> askGameGods(){
        ArrayList<Integer> chosenGods = new ArrayList<Integer>(myGame.getNumberOfPlayers());
        int select, i=0;

        System.out.println("Select " + myGame.getNumberOfPlayers() + "God cards to play the Game\n");
        gameController.printAllGods();
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
