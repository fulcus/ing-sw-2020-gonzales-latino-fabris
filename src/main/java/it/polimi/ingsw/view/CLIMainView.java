package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CLIMainView implements ViewObserver {

    Scanner input;
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

    /**
     *
     * @return The name of the chosen God.
     */
    public String askPlayerGod() {

            System.out.println("Choose your god by inserting its name!");

             return input.nextLine();

    }

    public void playerChoseInvalidGod(){ System.out.println("Your god is not available or has been already chosen"); }


    public String getGodFromChallenger(int n){

        System.out.println("Select Gods for the Game!" +(myGame.getNumberOfPlayers()-n)+ "remaining" );

       return input.nextLine();


    }

    public String challengerChooseStartPlayer(String challengerNickname){

        System.out.println(challengerNickname + "you can choose the first player to start! Insert a nickname");

        return input.nextLine();

    }

    public void invalidStartPlayer(){
        System.out.println("This is an invalid nickname, insert another one!");
    }


    /**
     *
     * @param availableGodsFromDeck This is the deck with available.
     * @return The 3 names of chosen gods.
     */
    /*public ArrayList askGameGods(ArrayList<God> availableGodsFromDeck){

        List<String> nameOfAvailableGods = availableGodsFromDeck.stream().map(object -> Objects.toString(object, null)).collect(Collectors.toList());

        ArrayList<String> selectedGods = new ArrayList<String>(myGame.getNumberOfPlayers());

        String selectedGod;

        int i = 0;

        System.out.println("Select " + (myGame.getNumberOfPlayers()) + "God cards to play the Game");

        while(i < myGame.getNumberOfPlayers()) {

            selectedGod = input.nextLine();

            if (nameOfAvailableGods.contains(selectedGod) && !(selectedGods.contains(selectedGod)))
            {
                selectedGods.add(selectedGod);
                nameOfAvailableGods.remove(selectedGod);
                i++;
            }

            else
                System.out.println("This God is not among available gods! Insert another");

        }

        return selectedGods;

    }*/





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
     *
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
    public void update(Cell toBeUpdatedCell) {

        myBoard.findCell(toBeUpdatedCell.getX(), toBeUpdatedCell.getY()).setLevel(toBeUpdatedCell.getLevel());//update the level of the changed cell in the view
        myBoard.findCell(toBeUpdatedCell.getX(), toBeUpdatedCell.getY()).setDome(toBeUpdatedCell.hasDome());//update dome of changed cell in the view
        myBoard.findCell(toBeUpdatedCell.getX(), toBeUpdatedCell.getY()).setWorker(toBeUpdatedCell.getWorker());//update worker of the changed cell in the view

    }


    public void printAllGods(ArrayList<God> godsDeck) {

        for (God god : godsDeck) {

            System.out.println(god.toString() + ":" + god.description);

        }


    }

    public void challengerError() {

        System.out.println("This god doesn't exist");
    }


    public void printChosenGods() {

        System.out.println("Available Gods:");

        for (God god : myGame.getChosenGods()) {

            System.out.println(god.toString());
        }

    }


}
