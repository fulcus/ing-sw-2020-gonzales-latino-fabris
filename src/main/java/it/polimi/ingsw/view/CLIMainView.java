package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnHandler;
import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLIMainView implements ViewObserver {

    private Scanner input;
    private Scanner intInput;
    private TurnHandler myTurnHandler;
    private final GameController myController;
    private final GodView godView;
    private final Board myBoard;// this will contain a copy of the Model's map and each cell will be update if there are any changes

    /**
     * This is the CLIMainView constructor.
     *
     * @param controller This is the controller that has created the game.
     */


    public CLIMainView(GameController controller) {

        myBoard = new Board();
        myController = controller;
        input = new Scanner(System.in);
        intInput = new Scanner(System.in);
        godView = new GodView();
    }

    public GodView getGodView() {
        return godView;
    }

    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {

        String startString;

        System.out.println("WELCOME TO SANTORINI");

        do {

            System.out.println("--type START to play--");
            startString = input.nextLine().toUpperCase();

        } while (!startString.equals("START"));

    }


    /**
     * @return The number of players.
     */
    public int askNumberOfPlayers() {

        int insertedNumberOfPlayers;


        System.out.println("Insert the number of players.");
        while (true) {
            try {
                insertedNumberOfPlayers = intInput.nextInt();

                if (insertedNumberOfPlayers != 2 && insertedNumberOfPlayers != 3)
                    System.out.println("You can only play with 2 or 3 players. Try again.");
                else
                    return insertedNumberOfPlayers;

            } catch (InputMismatchException ex) {
                System.out.println("Invalid input: type 2 or 3. Try again.");
                intInput.next();
            }
        }
    }


    /**
     * This method asks the player to set his worker initial position.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    public int[] askInitialWorkerPosition(Sex workerSex, String currentPlayerNickname)
            throws InputMismatchException {
        while (true) {
            try {
                int[] initialWorkerPosition = new int[2];

                if (workerSex == Sex.MALE) {
                    System.out.println(currentPlayerNickname + ", set your male worker's position in coordinates.");

                    initialWorkerPosition[0] = intInput.nextInt();
                    initialWorkerPosition[1] = intInput.nextInt();
                    return initialWorkerPosition;
                } else if (workerSex == Sex.FEMALE) {
                    System.out.println(currentPlayerNickname +
                            ", set your female worker's position in coordinates.");
                    initialWorkerPosition[0] = intInput.nextInt();
                    initialWorkerPosition[1] = intInput.nextInt();
                    return initialWorkerPosition;
                } else
                    System.out.println("Invalid worker's sex"); //never executed

            } catch (InputMismatchException ex) {
                System.out.println("You must type the coordinates separated by a space.");
                intInput.next();
            }
        }
    }

    public void invalidInitialWorkerPosition() {
        System.out.println("Not valid or available position. Choose another place!");
        intInput.next();
        input.next();
    }


    public String askPlayerNickname() {
        System.out.println("Insert your nickname.");
        return input.nextLine();
    }

    public String askPlayerColor(String playerNickname) {

        System.out.println(playerNickname + ", choose your color.");
        return input.nextLine().toUpperCase();
    }


    /**
     * @return The name of the chosen God.
     */
    public String askPlayerGod(String playerNickname) {

        System.out.println(playerNickname + ", choose your god by inserting his name.");

        return input.nextLine();

    }

    public void playerChoseInvalidGod() {
        System.out.println("Your god is not available or has been already chosen.");
    }


    public String getGodFromChallenger(String nickname, int n) {
        int numOfPlayers = myController.getGame().getNumberOfPlayers();
        System.out.println(nickname + ", you are the Challenger.\nSelect "
                + numOfPlayers + " Gods for this game.");
        System.out.println(numOfPlayers - n + " Gods left to choose.");

        return input.nextLine();


    }

    public String challengerChooseStartPlayer(String challengerNickname) {

        System.out.println(challengerNickname + ", you can choose the first player to start! Insert his nickname:");

        return input.nextLine();

    }

    public void invalidStartPlayer() {
        System.out.println("Invalid nickname. It must be an existing nickname and different from the challenger's.");
    }

    public void notAvailableColor(){
        System.out.println("This color is not available!");
    }

    public void notAvailableNickname() {
        System.out.println("This nickname is not available!");
    }


    public String askChosenWorker(String currentPlayerNickname) {

        String chosenWorker;
        //Il fatto che la view per stampare il nickname del player debba andare chiamare
        // prima il controller che poi a sua volta chiama il model....boh?
        System.out.println(currentPlayerNickname + ", it's your turn!");

        while (true) {
            System.out.println("Insert MALE or FEMALE to choose one of your workers.");

            chosenWorker = input.nextLine().toUpperCase();

            if (chosenWorker.equals("MALE") || chosenWorker.equals("FEMALE"))
                return chosenWorker;

            else
                System.out.println("Invalid input.");

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

    public void currentPlayerLoses(String loserNickname){
        System.out.println(loserNickname+ ", GameOver for you!");
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
        int xUpdated = toBeUpdatedCell.getX();
        int yUpdated = toBeUpdatedCell.getY();
        Cell cellUpdatedView = myBoard.findCell(xUpdated, yUpdated);

        //update the level of the changed cell in the view
        cellUpdatedView.setLevel(toBeUpdatedCell.getLevel());
        //update dome of changed cell in the view
        cellUpdatedView.setDome(toBeUpdatedCell.hasDome());
        //update worker of the changed cell in the view
        cellUpdatedView.setWorker(toBeUpdatedCell.getWorker());

    }


    public void printAllGods(ArrayList<God> godsDeck) {

        for (God god : godsDeck) {

            System.out.println(god.getClass().getSimpleName() + ":");
            System.out.println(god.getDescription() + "\n");

        }


    }

    public void challengerError() {

        System.out.println("This god doesn't exist.");
    }


    public void printChosenGods() {

        System.out.print("Available Gods: ");

        for (God god : myController.getGame().getChosenGods()) {

            System.out.print(god.getClass().getSimpleName() + " ");
        }
        System.out.println("\n");
    }


}
