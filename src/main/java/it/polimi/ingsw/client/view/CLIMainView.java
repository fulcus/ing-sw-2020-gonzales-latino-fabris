package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.god.God;
import it.polimi.ingsw.server.model.*;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLIMainView implements ViewObserver {

    private Scanner input;
    private Scanner intInput;
    private final GameController myController;
    private final GodView godView;
    private final ClientCell[][] myBoard;// this will contain a copy of the Model's map and each cell will be update if there are any changes
    private final int boardSize;
    private String playerNickname;
    //to be assigned when setPlayer of ClientView is deserialized

    /**
     * This is the CLIMainView constructor.
     *
     * @param controller This is the controller that has created the game.
     */


    public CLIMainView(GameController controller) {

        boardSize = 5;//TODO it should be passed as a parameter by the server

        myBoard = new ClientCell[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++) {
                myBoard [i][j] = new ClientCell(i,j);
            }

        myController = controller;
        input = new Scanner(System.in);
        intInput = new Scanner(System.in);
        godView = new GodView();
    }

    //called by clientView
    public void setPlayer(String nickname) {
        this.playerNickname = nickname;
    }

    public GodView getGodView() {
        return godView;
    }

    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {

        String startString;

        System.out.println("WELCOME TO ");

        santoriniASCII();

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


        System.out.println("Choose the number of players.");
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


    private void santoriniASCII() {

        System.out.println("███████╗ █████╗ ███╗   ██╗████████╗ ██████╗ ██████╗ ██╗███╗   ██╗██╗");
        System.out.println("██╔════╝██╔══██╗████╗  ██║╚══██╔══╝██╔═══██╗██╔══██╗██║████╗  ██║██║");
        System.out.println("███████╗███████║██╔██╗ ██║   ██║   ██║   ██║██████╔╝██║██╔██╗ ██║██║");
        System.out.println("╚════██║██╔══██║██║╚██╗██║   ██║   ██║   ██║██╔══██╗██║██║╚██╗██║██║");
        System.out.println("███████║██║  ██║██║ ╚████║   ██║   ╚██████╔╝██║  ██║██║██║ ╚████║██║");
        System.out.println("╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚═╝");
        System.out.println();

    }


    /**
     * This method asks the player to set his worker initial position.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    public int[] askInitialWorkerPosition(Sex workerSex)
            throws InputMismatchException {
        while (true) {
            try {
                int[] initialWorkerPosition = new int[2];

                if (workerSex == Sex.MALE) {
                    System.out.println(playerNickname + ", set your male worker's position in coordinates.");

                    initialWorkerPosition[0] = intInput.nextInt();
                    initialWorkerPosition[1] = intInput.nextInt();
                    return initialWorkerPosition;
                } else if (workerSex == Sex.FEMALE) {
                    System.out.println(playerNickname +
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

        System.out.println("Choose your nickname.");
        return input.nextLine();
    }

    public String askPlayerColor() {

        System.out.println(playerNickname + ", choose your color.");
        return input.nextLine().toUpperCase();
    }


    /**
     * @return The name of the chosen God.
     */
    public String askPlayerGod() {

        System.out.println(playerNickname + ", choose your god by typing his name.");
        return input.nextLine();
    }

    public void playerChoseInvalidGod() {
        System.out.println("Your god is not available or has already been chosen.");
    }

    private void printChallenger(int numOfPlayers) {
        System.out.println(playerNickname + ", you are the Challenger. Select "
                + numOfPlayers + " Gods for this game.");

    }

    public String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods) {
        printChallenger(numOfPlayers);
        System.out.println(numOfPlayers - alreadyChosenGods + " Gods left to choose.");

        return input.nextLine();

    }

    public String challengerChooseStartPlayer() {

        System.out.println("\n" + playerNickname + ", choose the first player to start! Type his nickname:");

        return input.nextLine();

    }

    public void invalidStartPlayer() {
        System.out.println("Invalid nickname. It must be an existing nickname.");
    }

    public void notAvailableColor() {
        System.out.println("This color is not available!");
    }

    public void notAvailableNickname() {
        System.out.println("This nickname is not available!");
    }


    public String askChosenWorker() {

        String chosenWorkerSex;
        //Il fatto che la view per stampare il nickname del player debba andare chiamare
        // prima il controller che poi a sua volta chiama il model....boh?
        System.out.println(playerNickname + ", it's your turn!");

        while (true) {
            System.out.println("Type MALE or FEMALE to choose one of your workers.");

            chosenWorkerSex = input.nextLine().toUpperCase();

            if (chosenWorkerSex.equals("MALE") || chosenWorkerSex.equals("FEMALE"))
                return chosenWorkerSex;

            else
                System.out.println("Invalid input.");

        }

    }


    /**
     * Allows to print the ERROR to the screen
     */
    public void printErrorScreen() {
        System.out.println("An error has occurred. Retry.");
    }

    /**
     * Prints to screen that one of the player has won the game
     */
    public void winningView(String winnerNickname) {
        System.out.println("\n\n\n"
                + winnerNickname + " HAS WON THIS GAME!!!\n\nGAME ENDED\n\nSEE YOU!");
    }

    public void unableToMoveLose() {
        System.out.println(playerNickname + ", Game Over for you!");
    }

    public void unableToBuildLose() {
        System.out.println(playerNickname + ", both of your workers can't move anywhere.");
        System.out.println("You have lose the game.");
    }


    /**
     * This method prints an updated version of the Board, depending on the Class' parameter "mymap".
     */
    public void printMap() {

        final String LINE_SEPARATOR = CliColor.ANSI_GREEN + "+-------+-------+-------+-------+-------+" + CliColor.COLOR_RESET + "%n";
        final String SPACE_SEPARATOR = CliColor.ANSI_GREEN + "+       +       +       +       +       +" + CliColor.COLOR_RESET + "%n";

        for (int i = 0; i < Board.SIDE; i++) {

            System.out.printf(LINE_SEPARATOR);//Border
            printMapLine(i);//content of game
            System.out.printf(SPACE_SEPARATOR);//space
        }

        System.out.printf(LINE_SEPARATOR);

    }

    /**
     * Prints a line of the map, showing eventual buildings and workers of the line.
     *
     * @param lineNumber Represents the line which content will be displayed.
     */
    private void printMapLine(int lineNumber) {

        for (int i = 0; i < Board.SIDE; i++) {

            boolean additionalSpace = true;
            System.out.printf(CliColor.ANSI_GREEN + "+" + CliColor.COLOR_RESET);
            System.out.printf(" ");//1

            //Place where eventual buildings will be printed

            if (myBoard[lineNumber][i].HasDome())//if cell has dome
                System.out.printf("D");

            else {
                //if cell has not dome

                if (myBoard[lineNumber][i].getCellLevel() == 0)
                    System.out.printf(" ");//if there is no building, prints nothing

                else
                    System.out.printf("%d",myBoard[lineNumber][i].getCellLevel() );   // if there is a building, prints its level
            }

            //SPACE

            System.out.printf(" ");//3
            System.out.printf(" ");//3bis


            //PLACE of cell (4) that prints eventual presence of a worker with its parameter(SEX;COLOR)


            if (!myBoard[lineNumber][i].HasWorker()) {
                System.out.printf(" ");
                System.out.printf(" ");//5
            } else {

                additionalSpace = false;
                String workerColor = myBoard[lineNumber][i].getWorkerColor();
                String workerSex = myBoard[lineNumber][i].getWorkerSex();

                if (workerColor.equals("BLUE") && workerSex.equals("MALE"))
                    System.out.printf(CliColor.ANSI_BLUE + " M⃣ " + CliColor.COLOR_RESET);
                else if (workerColor.equals("BLUE") && workerSex.equals("FEMALE"))
                    System.out.printf(CliColor.ANSI_BLUE + " F⃣ " + CliColor.COLOR_RESET);
                else if (workerColor.equals("WHITE") && workerSex.equals("MALE"))
                    System.out.printf(CliColor.ANSI_WHITE + " M⃣ " + CliColor.COLOR_RESET);
                else if (workerColor.equals("WHITE") && workerSex.equals("FEMALE"))
                    System.out.printf(CliColor.ANSI_WHITE + " F⃣ " + CliColor.COLOR_RESET);
                else if (workerColor.equals("BEIGE") && workerSex.equals("MALE"))
                    System.out.printf(CliColor.ANSI_BEIGE + " M⃣ " + CliColor.COLOR_RESET);
                else if (workerColor.equals("BEIGE") && workerSex.equals("FEMALE"))
                    System.out.printf(CliColor.ANSI_BEIGE + " F⃣ " + CliColor.COLOR_RESET);

            }

            if (additionalSpace)
                System.out.printf(" ");//6


        }

        System.out.printf(CliColor.ANSI_GREEN + "+" + CliColor.COLOR_RESET);
        System.out.printf("%n");

    }


    //TODO: in update si potrebbero passare inoltre dei parametri che specificano cosa ha fatto avvenire il cambiamento, ad esempio se per un movimento o per una costruzione. Intal modo posso far apparire all'utente: PLAYER1 si è mosso-->stampa mappa.
    @Override
    public void update(ClientCell toBeUpdatedCell) {

        int xToUpdate = toBeUpdatedCell.getX();
        int yToUpdate = toBeUpdatedCell.getY();
        ClientCell cellUpdatedView = myBoard[xToUpdate][yToUpdate];

        //update the level of the changed cell in the view
        cellUpdatedView.setCellLevel(toBeUpdatedCell.getCellLevel());
        //update dome of changed cell in the view
        cellUpdatedView.setHasDome(toBeUpdatedCell.HasDome());
        //update worker of the changed cell in the view
        cellUpdatedView.setHasWorker(toBeUpdatedCell.HasWorker());

        cellUpdatedView.setWorkerColor(toBeUpdatedCell.getWorkerColor());

        cellUpdatedView.setWorkerSex(toBeUpdatedCell.getWorkerSex());

    }


    public void printAllGods(ArrayList<String> godsNameAndDescription) {

        for (String god : godsNameAndDescription)
            System.out.println(god);

    }

    public void challengerError() {
        System.out.println("This god doesn't exist.");
    }


    public void printChosenGods(ArrayList<String> chosenGods) {

        System.out.print("Available Gods: ");

        int index = 0;

        for (String god : chosenGods) {
            index++;
            System.out.print(god);
            if (index < chosenGods.size())
                System.out.print(", ");
            else
                System.out.println();
        }
        System.out.println("");
    }


    public void selectedWorkerCannotMove(String sex) {
        sex = sex.toLowerCase();
        String otherSex;

        if (sex.equals("male"))
            otherSex = "female";
        else
            otherSex = "male";

        System.out.println("Your " + sex + " worker cannot move anywhere. You must move with your "
                + otherSex + " worker.");
    }

    public void selectedWorkerCannotBuild(String sex) {
        sex = sex.toLowerCase();
        String otherSex;

        if (sex.equals("male"))
            otherSex = "female";
        else
            otherSex = "male";

        System.out.println("Your " + sex + " worker cannot build anywhere. You must build with your "
                + otherSex + " worker.");
    }
/*
    public String askTypeofView() {

        String selectedView;

        System.out.println("What kind of interface would you like to play with? CLI or GUI");

        while (true) {

            selectedView = input.nextLine().toUpperCase();

            if (!(selectedView.equals("CLI") || selectedView.equals("GUI")))
                System.out.println("Invalid interface. Type CLI or GUI");

            else
                break;
        }

        return selectedView;

    }
*/

}
