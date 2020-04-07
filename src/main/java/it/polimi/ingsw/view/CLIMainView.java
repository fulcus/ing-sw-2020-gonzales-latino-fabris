package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.Scanner;

public class CLIMainView implements ViewObserver {

    Scanner input = new Scanner(System.in);
    int numberOfPlayers;
    TurnHandler myTurnHandler;
    Game myGame;
    GameController myController;
    Board myBoard;// this will contain a copy of the Model's map and each cell will be update if there are any changes

    /**
     * This is the CLIMainView constructor.
     *
     * @param controller This is the controller that has created the game.
     */

    public CLIMainView(GameController controller) {

        myController = controller;
        input = new Scanner(System.in);
    }


    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {

        String startString;

        System.out.println("WELCOME TO SANTORINI");

        do {

            System.out.println("--write START to play--");
            startString = input.nextLine();

        } while (!startString.equals("START"));

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
        numberOfPlayers = insertedNumberOfPlayers;

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

            System.out.println("Player" + (i + 1) + ". Insert your nickname.");
            insertedNickname = input.nextLine();

            if (!(nicknames.contains(insertedNickname)) && insertedNickname != null) {
                nicknames.add(insertedNickname);
                i++;
            } else
                System.out.println("This nickname has been chosen by another player");

        }

        return nicknames;

    }

    //IO farei una funzione che permette ad 1 player di scegliere il suo dio,
    // poi è il controller che chiama questa funzione 2 o 3 volte, e il controller fa next turn.
    public ArrayList<Integer> askPlayingGod(){
        int i=0, j=0;
        int chosenOne;
        ArrayList<Integer> choice = new ArrayList<Integer>(myGame.getNumberOfPlayers());

        myTurnHandler.nextPlayer();
        while(i<myGame.getNumberOfPlayers()){
            System.out.println(myTurnHandler.getCurrentPlayer().getNickname() + "! Choose one God among the following: ");
            myController.printGameGods();
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
        myController.printAllGods();
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


    public String askMoveAgain() {

        String answer;
        System.out.println("\n" + myTurnHandler.getCurrentPlayer().getNickname() + ": Do you want to move again your Worker? (Y = 'Yes', N = 'No'");
        while (true) {
            answer = input.nextLine();
            if (answer.equals("Y") || answer.equals("N"))
                return answer;
            else
                System.out.println("NOT VALID ANSWER. \nRETRY:  ");
        }
    }


    public String askWantToMoveUp() {
        String answer;
        while(true) {
            System.out.println("Do you want to move up? \n Y for Yes, N for No\n");
            answer = input.nextLine();

            if (answer.equals("Y") || answer.equals("N")) {
                return answer;
            } else
                System.out.println("  ERROR: RETRY   \n  Type Y or N to answer");
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

            if (selectedBuildingDirection.equals("N") || selectedBuildingDirection.equals("NE") || selectedBuildingDirection.equals("E") || selectedBuildingDirection.equals("SE") || selectedBuildingDirection.equals("S") || selectedBuildingDirection.equals("SW") || selectedBuildingDirection.equals("W") || selectedBuildingDirection.equals("NW"))
                return selectedBuildingDirection;

            else
                System.out.println("Invalid Direction. You must use: N,NE,E,SE,S,SW,W,NW");


        }

    }




    /**
     * Allows to print the ERROR to the screen
     */
    public void printErrorScreen() {
        System.out.println("\n\n      ERROR      \n\n RETRY\n\n");
    }

    /**
     * Prints to screen that one of the player has won the game
     */
    public void winningView() {
        System.out.println("\n\n\n       " + myTurnHandler.getCurrentPlayer().getNickname() + "HAS WON THIS GAME!!!\n\n GAME ENDED\n\nSEE YOU!");
    }


    /**
     * This method prints an updated version of the Board, depending on the Class' parameter "mymap".
     */
    public void printMap() {

        final String LINE_SEPARATOR = "+------+------+------+------+------+%n";
        final String SPACESEPARATOR = "+      +      +      +      +      +%n";

        for (int i = 0; i < Board.SIDE; i++) {

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

        for (int i = 0; i < Board.SIDE; i++) {

            System.out.printf("+");
            System.out.printf(" ");//1

            //Place where eventual buildings will be printed

            if (myBoard.findCell(linenumber, i).hasDome())//if cell has dome
                System.out.printf("D");

            else {
                //if cell has not dome

                if (myBoard.findCell(linenumber, i).getLevel() == 0)
                    System.out.printf(" ");//if there is no building, prints nothing

                else
                    System.out.printf("%d", myBoard.findCell(linenumber, i).getLevel());   // if there is a building, prints its level
            }

            //SPACE

            System.out.printf(" ");//3


            //PLACE of cell (4) that prints eventual presence of a worker with its parameter(SEX;COLOR)


            if (myBoard.findCell(linenumber, i).getWorker() == null) {
                System.out.printf(" ");
                System.out.printf(" ");//5
            } else {

                if (myBoard.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.BLUE && myBoard.findCell(linenumber, i).getWorker().getSex() == Sex.MALE)
                    System.out.printf("BM");
                else if (myBoard.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.BLUE && myBoard.findCell(linenumber, i).getWorker().getSex() == Sex.FEMALE)
                    System.out.printf("BF");
                else if (myBoard.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.WHITE && myBoard.findCell(linenumber, i).getWorker().getSex() == Sex.MALE)
                    System.out.printf("WM");
                else if (myBoard.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.WHITE && myBoard.findCell(linenumber, i).getWorker().getSex() == Sex.FEMALE)
                    System.out.printf("WF");
                else if (myBoard.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.BEIGE && myBoard.findCell(linenumber, i).getWorker().getSex() == Sex.MALE)
                    System.out.printf("bM");
                else if (myBoard.findCell(linenumber, i).getWorker().getPlayer().getColor() == Color.BEIGE && myBoard.findCell(linenumber, i).getWorker().getSex() == Sex.FEMALE)
                    System.out.printf("bF");

            }


            System.out.printf(" ");//6


        }

        System.out.printf("+%n");

    }


    //TODO: in update si potrebbero passare inoltre dei parametri che specificano cosa ha fatto avvenire il cambiamento, ad esempio se per un movimento o per una costruzione. Intal modo posso far apparire all'utente: PLAYER1 si è mosso-->stampa mappa.
    @Override
    public void update(Cell toBeUpdatedCell){

        myBoard.findCell(toBeUpdatedCell.getX(),toBeUpdatedCell.getY()).setLevel(toBeUpdatedCell.getLevel());//update the level of the changed cell in the view
        myBoard.findCell(toBeUpdatedCell.getX(),toBeUpdatedCell.getY()).setDome(toBeUpdatedCell.hasDome());//update dome of changed cell in the view
        myBoard.findCell(toBeUpdatedCell.getX(),toBeUpdatedCell.getY()).setWorker(toBeUpdatedCell.getWorker());//update worker of the changed cell in the view

    }


    public void printAllGods(){




    }






}
