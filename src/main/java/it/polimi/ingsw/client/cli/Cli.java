package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.BoardClient;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Allows to print to the client what the game asks and wants to show to him.
 */
public class Cli implements View {

    private Scanner input;
    private Scanner intInput;
    private final BoardClient board;// this will contain a copy of the Model's map and each cell will be update if there are any changes
    private String playerNickname; //to be assigned when setPlayer of ViewClient is deserialized
    private String myColor;
    private String challenger;

    //Help to print info besides the board
    private String bluePlayer;
    private String whitePlayer;
    private String beigePlayer;
    private String blueGod;
    private String whiteGod;
    private String beigeGod;

    //actually used attributes among the class
    private String nickname1;
    private String nickname2;
    private String nickname3;
    private String color1;
    private String color2;
    private String color3;
    private String myGod;
    private String god2;
    private String god3;


    /**
     * This is the Cli constructor.
     */
    public Cli() {
        board = new BoardClient();
        input = new Scanner(System.in);
        intInput = new Scanner(System.in);

        bluePlayer = null;
        whitePlayer = null;
        beigePlayer = null;
        blueGod = null;
        whiteGod = null;
        beigeGod = null;
    }


    /**
     * Assigns the nickname of the player to the Cli
     *
     * @param nickname nickname of the player associated with this instance of Cli
     */
    public void setPlayer(String nickname) {
        // the method is called by clientView
        this.playerNickname = nickname;
    }


    /**
     * Asks the IP of the server where the client wants to connect to.
     * @return The IP of the server to connect to.
     */
    public String getServerAddress() {

        System.out.println("Insert Server IP");
        return input.nextLine();

    }


    /**
     * Shows if the connection to the server was successful or not
     * @param connected True if the connection was established, false otherwise.
     */
    public void connectionOutcome(boolean connected) {
        if (connected)
            System.out.println("Connected to the server.\n");
        else
            System.out.println("Server unreachable.\n");
    }


    /**
     * Lets the player know the number of players for the game he's been assigned.
     * The player in this specific case did not choose the number of players for the game, but someone else did (the so called "creator").
     * @param numberOfPlayers The number of players of the game the player has been assigned to.
     */
    public void joinGame(int numberOfPlayers) {
        System.out.print("You are joining a game for " + numberOfPlayers + " players.");
    }


    /**
     * Lets the player know he is the creator of a new game.
     */
    public void createGame() {
        System.out.println("You are creating a new game.");
    }


    /**
     * The joiner player is being shown that the creator of a new game is choosing the number of players for the game.
     */
    public void waitCreatorChooseNumOfPlayers() {
        System.out.println("The creator of the game is choosing the number of players for the game. Wait...");
    }


    /**
     * Displays that a player has been disconnected and reason.
     * @param disconnectedPlayer The name of the disconnected player.
     */
    public void notifyOtherPlayerDisconnection(String disconnectedPlayer) {

        System.out.println("\n"+ disconnectedPlayer + " has disconnected, your game ends now!");
        System.out.println("Goodbye");
    }


    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {

        String startString;

        System.out.println("\nWELCOME TO ");

        santoriniASCII();

        do {

            System.out.println("--type START to play--");
            startString = input.nextLine().toUpperCase();

        } while (!startString.equals("START"));

    }


    /**
     * Asks to the creator of a game how many players will the game hold.
     * @return The number of players decided by the creator player.
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


    /**
     * Representation of the game's title with ASCII characters.
     */
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
     * It will be invoked both for the male worker and the female worker of every player.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    public int[] askInitialWorkerPosition(String workerSex)
            throws InputMismatchException {

        while (true) {

            try {
                int[] initialWorkerPosition = new int[2];
                System.out.println();

                if (workerSex.equals("MALE")) {
                    System.out.println(playerNickname + ", set your male worker's position in coordinates.");

                    initialWorkerPosition[0] = intInput.nextInt();
                    initialWorkerPosition[1] = intInput.nextInt();
                    return initialWorkerPosition;
                } else if (workerSex.equals("FEMALE")) {
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


    /**
     * Shows to the player that another player registered to the current game is choosing his color.
     * @param choosingPlayer The name of the player that is choosing his color.
     */
    public void printChoosingColor(String choosingPlayer) {
        System.out.print("\n" + choosingPlayer + " is choosing his color\n");
    }


    /**
     * Shows to the player that another player registered to the current game is choosing his nickname.
     */
    public void printChoosingNickname() {
        System.out.print("Another player is choosing his nickname\n");
    }


    /**
     * Allows to set and to store in the local cli memory the general settings info of other players.
     * Allows also to store into specific colored class attributes the info of the players,
     * so that a better representation when printing the game's board can be done.
     *
     * @param nickname Nickname of the player to register.
     * @param color Color chosen by that specific player for the current game.
     */
    public void setOtherPlayersInfo(String nickname, String color) {
        if (nickname2 == null) {
            nickname2 = nickname;
            color2 = color;

            switch (color2) {
                case "BLUE":
                case "blue":
                    bluePlayer = nickname2;
                    break;
                case "WHITE":
                case "white":
                    whitePlayer = nickname2;
                    break;
                case "BEIGE":
                case "beige":
                    beigePlayer = nickname2;
                    break;
            }
        } else if (nickname3 == null) {
            nickname3 = nickname;
            color3 = color;

            switch (color3) {
                case "BLUE":
                case "blue":
                    bluePlayer = nickname3;
                    break;
                case "WHITE":
                case "white":
                    whitePlayer = nickname3;
                    break;
                case "BEIGE":
                case "beige":
                    beigePlayer = nickname3;
                    break;
            }
        }
    }


    /**
     * Allows to set and to store in the local cli memory the general settings info of the local client.
     * Allows also to store into specific colored class attributes the info of the local player,
     * so that a better representation when printing the game's board can be done.
     *
     * @param nickname Nickname of the player to register.
     * @param color Color chosen by that specific player for the current game.
     */
    private void setMyInfo(String nickname, String color) {
        nickname1 = nickname;
        color1 = color;

        if (color1.equals("BLUE") || color1.equals("blue"))
            bluePlayer = nickname1;
        else if (color1.equals("WHITE") || color1.equals("white"))
            whitePlayer = nickname1;
        else if (color1.equals("BEIGE") || color1.equals("beige"))
            beigePlayer = nickname1;
    }


    /**
     * Allows to set and to store in the local cli memory the god of another player.
     * Allows also to store into specific colored class attributes the info of the god,
     * so that a better representation when printing the game's board can be done.
     * @param nickname
     * @param god
     */
    private void setPlayerGod(String nickname, String god) {

        if (nickname.equals(nickname2)) {
            god2 = god;
        }
        else
            god3 = god;

        if (nickname.equals(bluePlayer))
            blueGod = god;
        else if (nickname.equals(whitePlayer))
            whiteGod = god;
        else if (nickname.equals(beigePlayer))
            beigePlayer = god;
    }


    /**
     * Lets the player know the position he wrote for the initial worker position was wrong.
     */
    public void invalidInitialWorkerPosition() {
        System.out.println("Not valid or available position. Choose another place!");
        intInput.next();
        input.next();
    }


    /**
     * Asks to the player the nickname for the game.
     *
     * @return The nickname chosen by the player.
     */
    public String askPlayerNickname() {

        System.out.println("\nChoose your nickname.");
        String nickname = input.nextLine();
        setPlayer(nickname);

        return nickname;
    }


    /**
     * Asks to the player the color for the game.
     * Only three colors are available: blue, white and beige.
     *
     * @return The color chosen by the player.
     */
    public String askPlayerColor() {

        System.out.println("\n" + playerNickname + ", choose your color.");
        System.out.println("The available colors are Blue, White and Beige.");

        String color = input.nextLine().toUpperCase();
        myColor = color;

        return color;
    }


    /**
     * Notifies the player that his nickname was accepted by the server.
     */
    public void notifyValidNick() {
        System.out.println("Nickname accepted");
    }


    /**
     * Notifies the player that his color choice was accepted by the server.
     * Sets also the local info about the player (his nickname and color).
     */
    public void notifyValidColor() {
        //adding this players info to "database"
        setMyInfo(playerNickname, myColor);

        System.out.println("Color accepted");
    }


    /**
     * Asks to the player which God among the available ones wants to play with during the current game.
     * @return The name of the chosen God.
     */
    public String askPlayerGod() {
        System.out.println(playerNickname + ", choose your god by typing his name.");

        String god = input.nextLine();

        myGod = god;
        return god;
    }


    /**
     * Lets the player know that his God's choice was rejected by the server.
     */
    public void playerChoseInvalidGod() {
        System.out.println("Your god is not available or has already been chosen.\n");
    }


    /**
     * Lets the challenger of the game know that he is the challenger and asks him to select the Gods for the game.
     * @param numOfPlayers The challenger must choose as many Gods as many are the players of the game.
     */
    private void printChallenger(int numOfPlayers) {

        System.out.println();
        System.out.println(playerNickname + ", you are the Challenger. Select "
                + numOfPlayers + " gods for this game.");

    }


    /**
     * Lets the challenger know how many gods he still has to choose.
     * Then the challenger, if other gods need to be selected, chooses another god for the game.
     *
     * @param numOfPlayers The number of players of the current game.
     * @param alreadyChosenGods The number of gods that the challenger has already chosen for the game.
     * @return Another name of the God the challenger chooses for the current game.
     */
    public String getGodFromChallenger(int numOfPlayers, int alreadyChosenGods) {

        int godsLeftToChoose = numOfPlayers - alreadyChosenGods;

        if (alreadyChosenGods == 0)
            printChallenger(numOfPlayers);
        else {
            System.out.print(godsLeftToChoose);

            if (godsLeftToChoose == 1)
                System.out.print(" god");
            else
                System.out.print(" gods");

            System.out.println(" left to choose.");
        }

        return input.nextLine();
    }


    /**
     * Asks to the challenger which player will be the starting one.
     * @return The nickname of the starting player.
     */
    public String challengerChooseStartPlayer() {

        System.out.println("\n" + playerNickname + ", choose the first player to start! Type his nickname:");

        return input.nextLine();
    }


    /**
     * Lets the challenger know that was an error occurred choosing the starting player.
     * The challenger must choose among the nicknames of the players registered in the current game.
     */
    public void invalidStartPlayer() {
        System.out.println("Invalid nickname. It must be an existing nickname.");
    }


    /**
     * Lets the player know that the chosen color was not available,
     * maybe because another player had already chosen it before or
     * maybe because that was not a color defined by the game.
     */
    public void notAvailableColor() {
        System.out.println("This color is not available!");
    }


    /**
     * Tells the player that the inserted nickname was not available.
     * This error can occur when the length of the nickname is too long or when the same nick was already chosen by another player.
     */
    public void notAvailableNickname() {
        System.out.println("This nickname is not available!");
    }


    /**
     * Asks to the player which one of his worker wants to play with during the current turn.
     *
     * @return The sex of the worker the player wants to play with.
     */
    public String askChosenWorker() {

        String chosenWorkerSex;
        System.out.println();
        System.out.println(playerNickname + ", it's your turn!");

        while (true) {

            System.out.println("\nType MALE or FEMALE to choose one of your workers.");

            chosenWorkerSex = input.nextLine().toUpperCase();

            if (chosenWorkerSex.equals("MALE") || chosenWorkerSex.equals("FEMALE"))
                return chosenWorkerSex;

            else
                System.out.println("Invalid input.");

        }
    }


    /**
     * Allows to print a general ERROR to the screen.
     */
    public void printErrorScreen() {
        System.out.println("An error has occurred. Retry.");
    }


    /**
     * Prints to screen that the player has won the game.
     * @return True.
     */
    public boolean winningView() {
        System.out.println("You have won this game!");
        System.out.println("Goodbye");
        return true;
    }


    /**
     * Lets the player know he has lost the game because both of his workers cannot move.
     */
    public void unableToMoveLose() {
        System.out.println("\nNone of your workers can move. You have lost this game.\nGoodbye");
    }


    /**
     * Lets the player know he has lost the game because both of his workers cannot build.
     */
    public void unableToBuildLose() {
        System.out.println("\nNone of your workers can build. You have lost this game.\nGoodbye");
    }


    /**
     * In a 3 players game, this method notifies the other players that a player has lost the game.
     * @param loserNickname The nickname of the player that has lost the game.
     */
    public void notifyPlayersOfLoss(String loserNickname) {
        System.out.println(loserNickname + " has lost, 2 players remained!");
    }


    /**
     * This method prints an updated version of the Board, depending on the Class parameter "mymap".
     */
    public void printMap() {
        System.out.println("\n");

        final String LINE_SEPARATOR = CliColor.ANSI_GREEN + "+-------+-------+-------+-------+-------+" + CliColor.COLOR_RESET + "%n";
        final String SPACE_SEPARATOR = CliColor.ANSI_GREEN + "|       |       |       |       |       |" + CliColor.COLOR_RESET + "%n";
        final String LINE_SEPARATOR_PLAYER_BLUE = CliColor.ANSI_GREEN + "+-------+-------+-------+-------+-------+         " + CliColor.COLOR_RESET + CliColor.Background_Blue + CliColor.BLACK_BOLD + bluePlayer + " plays with " + blueGod + CliColor.RESET + CliColor.BACKGROUND_RESET + "%n";
        final String LINE_SEPARATOR_PLAYER_WHITE = CliColor.ANSI_GREEN + "+-------+-------+-------+-------+-------+         " + CliColor.COLOR_RESET + CliColor.Background_White + CliColor.BLACK_BOLD + whitePlayer + " plays with " + whiteGod + CliColor.RESET + CliColor.BACKGROUND_RESET + "%n";
        final String LINE_SEPARATOR_PLAYER_BEIGE = CliColor.ANSI_GREEN + "+-------+-------+-------+-------+-------+         " + CliColor.COLOR_RESET + CliColor.Background_Beige + CliColor.WHITE_BOLD + beigePlayer + " plays with " + beigeGod + CliColor.RESET + CliColor.BACKGROUND_RESET + "%n";


        for (int i = 0; i < Board.SIDE; i++) {

            if (i == 0 && bluePlayer != null) {
                System.out.printf(LINE_SEPARATOR_PLAYER_BLUE);
            } else if (i == 1 && whitePlayer != null) {
                System.out.printf(LINE_SEPARATOR_PLAYER_WHITE);
            } else if (i == 2 && beigePlayer != null) {
                System.out.printf(LINE_SEPARATOR_PLAYER_BEIGE);
            } else
                System.out.printf(LINE_SEPARATOR);//Border

            printMapLine(i);//content of game
            System.out.printf(SPACE_SEPARATOR);//space
        }

        System.out.printf(LINE_SEPARATOR);
        System.out.println();
    }


    /**
     * Prints a line of the map, showing eventual buildings and workers of the line.
     *
     * @param lineNumber Represents the line which content will be displayed.
     */
    private void printMapLine(int lineNumber) {

        for (int i = 0; i < Board.SIDE; i++) {

            boolean additionalSpace = true;
            System.out.printf(CliColor.ANSI_GREEN + "|" + CliColor.COLOR_RESET);
            System.out.printf(" ");//1

            //Place where eventual buildings will be printed

            if (board.findCell(lineNumber, i).hasDome())//if cell has dome
                System.out.printf(CliColor.ANSI_BLUE + "D" + CliColor.COLOR_RESET);

            else {
                //if cell has not dome

                if (board.findCell(lineNumber, i).getCellLevel() == 0)
                    System.out.printf(" ");//if there is no building, prints nothing

                else
                    System.out.printf("%d", board.findCell(lineNumber, i).getCellLevel());   // if there is a building, prints its level
            }

            //SPACE

            System.out.printf(" ");//3
            System.out.printf(" ");//3bis


            //PLACE of cell (4) that prints eventual presence of a worker with its parameter(SEX;COLOR)


            if (!board.findCell(lineNumber, i).hasWorker()) {
                System.out.printf(" ");
                System.out.printf(" ");//5
            } else {

                additionalSpace = false;
                String workerColor = board.findCell(lineNumber, i).getWorkerClient().getWorkerColor();
                String workerSex = board.findCell(lineNumber, i).getWorkerClient().getWorkerSex();

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

        System.out.printf(CliColor.ANSI_GREEN + "|" + CliColor.COLOR_RESET);
        System.out.printf("%n");

    }


    /**
     * Updates the cell of the board that has changed its contents.
     * @param toUpdateCell The cell that needs to be updated.
     */
    public void update(CellClient toUpdateCell) {
        board.update(toUpdateCell);
    }


    /**
     * Prints all the available gods of the game and their description.
     * @param godsNameAndDescription The gods available for the game, the challenger will chose among this ones.
     */
    public void printAllGods(ArrayList<String> godsNameAndDescription) {
        System.out.println("\nThese are all the available gods:\n");

        for (String god : godsNameAndDescription)
            System.out.println(god);

        System.out.println();
    }


    /**
     * Lets the player know the selected god does not exist in this game.
     */
    public void challengerError() {
        System.out.println("This god doesn't exist.");
    }


    /**
     * Prints all the Gods chosen by the challenger for the current game.
     * @param chosenGods The list of the chosen gods.
     */
    public void printChosenGods(ArrayList<String> chosenGods) {

        System.out.print("\n\nAvailable gods: ");

        int index = 0;

        for (String god : chosenGods) {
            index++;
            System.out.print(god);
            if (index < chosenGods.size())
                System.out.print(", ");
            else
                System.out.println();
        }
        System.out.println();
    }


    /**
     * Lets the player know the selected worker cannot move.
     * @param sex The sex of the selected worker
     */
    public void selectedWorkerCannotMove(String sex) {
        sex = sex.toLowerCase();
        String otherSex;

        if (sex.equals("male"))
            otherSex = "female";
        else
            otherSex = "male";

        System.out.println("Your " + sex + " worker cannot move anywhere. You must move with your "
                + otherSex + " worker.\n");
    }


    /**
     * Lets the player know the selected worker cannot build.
     * @param sex The sex of the selected worker.
     */
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


    /**
     * Asks to the player if he prefers the CLI or the GUI.
     * @return The type of interface chosen by the player.
     */
    public String askTypeofView() {

        String selectedView;

        System.out.println("What kind of interface would you like to play with? CLI or Gui");

        while (true) {

            selectedView = input.nextLine().toUpperCase();

            if (!(selectedView.equals("CLI") || selectedView.equals("Gui")))
                System.out.println("Invalid interface. Type CLI or Gui");

            else
                break;
        }

        return selectedView;

    }


    /**
     * This method asks the user to insert the position where he wants to build.
     *
     * @return The compass direction of the place where to build.
     */
    public String askBuildingDirection() {

        String selectedBuildingDirection;

        System.out.println("Where do you want to build? Insert a cardinal point!");

        while (true) {
            selectedBuildingDirection = input.nextLine().toUpperCase();

            if (selectedBuildingDirection.equals("N")
                    || selectedBuildingDirection.equals("NE")
                    || selectedBuildingDirection.equals("E")
                    || selectedBuildingDirection.equals("SE")
                    || selectedBuildingDirection.equals("S")
                    || selectedBuildingDirection.equals("SW")
                    || selectedBuildingDirection.equals("W")
                    || selectedBuildingDirection.equals("NW")
                    || selectedBuildingDirection.equals("U")) {

                return selectedBuildingDirection;

            } else
                System.out.println("Invalid Direction. You must use: N, NE, E, SE, S, SW, W, NW (or 'U' to build underneath");
        }
    }


    /**
     * This method asks to Atlas' owner to insert the position where he wants to build and what type of building.
     *
     * @return The compass direction of the place where to build.
     */
    public String[] askBuildingDirectionAtlas() {

        String[] selectedBuildingDirection = new String[2];

        System.out.println("Where do you want to build? Insert a cardinal point!");

        while (true) {
            selectedBuildingDirection[0] = input.nextLine().toUpperCase();

            if (selectedBuildingDirection[0].equals("N")
                    || selectedBuildingDirection[0].equals("NE")
                    || selectedBuildingDirection[0].equals("E")
                    || selectedBuildingDirection[0].equals("SE")
                    || selectedBuildingDirection[0].equals("S")
                    || selectedBuildingDirection[0].equals("SW")
                    || selectedBuildingDirection[0].equals("W")
                    || selectedBuildingDirection[0].equals("NW")) {

                while (true) {
                    System.out.println("Now tell me what do you want to build: type  <B> for Block or <D> for Dome: ");
                    selectedBuildingDirection[1] = input.nextLine().toUpperCase();

                    if (selectedBuildingDirection[1].equals("B") || selectedBuildingDirection[1].equals("D"))
                        return selectedBuildingDirection;

                    else
                        System.out.println("Invalid Building. You must type B or D.\n");
                }
            } else
                System.out.println("Invalid Direction. You must use: N, NE, E, SE, S, SW, W, NW.");

        }
    }


    /**
     * This method asks the user to insert the direction of his next movement.
     *
     * @return The compass direction of the movement.
     */
    public String askMovementDirection() {

        String selectedDirection;
        System.out.println("Where do you want to move your worker?");

        while (true) {
            selectedDirection = input.nextLine().toUpperCase();

            if (selectedDirection.equals("N") || selectedDirection.equals("NE") || selectedDirection.equals("E") || selectedDirection.equals("SE") || selectedDirection.equals("S") || selectedDirection.equals("SW") || selectedDirection.equals("W") || selectedDirection.equals("NW"))
                return selectedDirection;

            else
                System.out.println("Invalid Direction. You must type: N, NE, E, SE, S, SW, W, NW");
        }
    }


    /**
     * Allows to get the input of the player to move again.
     *
     * @return The will of the player on keeping going moving his worker on the board.
     */
    public String askMoveAgain() {
        System.out.println("Do you want to move again your Worker?");
        return playerAnswerYN();
    }


    /**
     * Allows to get the input of the player to move an enemy's worker.
     *
     * @return The will of the player to move an enemy's worker
     */
    public String askWantToMoveEnemy() {
        System.out.println("Do you want to force your near enemy to move?");
        return playerAnswerYN();
    }


    /**
     * Allows to move one worker's enemy.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The Worker to move selected by the player, null if there aren't enemies around.
     */
    public String askWorkerToMove(ArrayList<WorkerClient> enemyWorkers, WorkerClient myWorker) {

        ArrayList<String> presentPositions = printFoundEnemiesPosition(enemyWorkers, myWorker);

        if (presentPositions.size() == 0) {
            System.out.println("There are no enemy workers around...");
            return null;
        }

        System.out.println("These are the positions of the enemy workers nearby. Choose one:");

        while (true) {
            String chosenEnemyDirection = input.nextLine();

            if (presentPositions.contains(chosenEnemyDirection))
                return chosenEnemyDirection;

            System.out.println("Your choice must be between the ones above!\nOtherwise you can choose to let them stay where they are:\n'Y' to retry, 'N' to quit the forced move ");
            String playerAnswer = input.nextLine().toUpperCase();

            if (playerAnswer.equals("N"))
                return null;

            System.out.println("Type again your choice: ");
        }
    }


    /**
     * Helps to show the positions of the neighboring workers.
     * Then creates a list of the position of these workers.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The positions of the neighboring enemies.
     */
    private ArrayList<String> printFoundEnemiesPosition(ArrayList<WorkerClient> enemyWorkers, WorkerClient myWorker) {
        int myWorkerX = myWorker.getXPosition();
        int myWorkerY = myWorker.getYPosition();
        ArrayList<String> presentPositions = new ArrayList<>();

        for (WorkerClient enemyWorker : enemyWorkers) {
            if (myWorkerX > enemyWorker.getXPosition()) {
                if (myWorkerY > enemyWorker.getYPosition()) {
                    System.out.println("NW");
                    presentPositions.add("NW");
                } else if (myWorkerY == enemyWorker.getYPosition()) {
                    System.out.println("N");
                    presentPositions.add("N");
                } else {
                    System.out.println("NE");
                    presentPositions.add("NE");
                }
            } else if (myWorkerX == enemyWorker.getXPosition()) {
                if (myWorkerY > enemyWorker.getYPosition()) {
                    System.out.println("W");
                    presentPositions.add("W");
                } else if (myWorkerY < enemyWorker.getYPosition()) {
                    System.out.println("E");
                    presentPositions.add("E");
                } else {
                    System.out.println("ERROR : IT'S THE SAME POSITION OF WHERE I AM!!!");
                    return null;
                }
            } else {
                if (myWorkerY > enemyWorker.getYPosition()) {
                    System.out.println("SW");
                    presentPositions.add("SW");
                } else if (myWorkerY == enemyWorker.getYPosition()) {
                    System.out.println("S");
                    presentPositions.add("S");
                } else {
                    System.out.println("SE");
                    presentPositions.add("SE");
                }
            }
        }
        return presentPositions;
    }


    /**
     * Says that the worker can build under himself/herself.
     * This is allowed only when playing with Zeus.
     */
    public void printBuildUnderneath() {
        System.out.println("Remember that you can build underneath!\n" +
                "To do that, the direction for the build is 'U'\n");
    }


    /**
     * Asks to the player that holds Hephaestus as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHephaestus() {
        System.out.println("You can build another time, " +
                "but ONLY on the same space you built before");
        return playerAnswerYN();
    }


    /**
     * Asks to the player that holds Demeter as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainDemeter() {
        System.out.println("You can build another time, but NOT on the same space you built before");
        return playerAnswerYN();
    }


    /**
     * Asks to the player that holds Hestia as a God if he wants to build again.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHestia() {
        System.out.println("You can build another time, but NOT on a perimeter space");
        return playerAnswerYN();
    }


    /**
     * Asks to the player that holds Prometheus as a God if he wants to build before moving.
     *
     * @return The will of the player to build before moving.
     */
    public String askBuildPrometheus() {
        System.out.println("Do you want to build before moving?\n" +
                "Remember that if you do so, you won't be able to move up.");
        return playerAnswerYN();
    }


    /**
     * Allows to get the answer of a player to a question.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    private String playerAnswerYN() {
        String answer;
        System.out.println("Type <Y> for Yes,  <N> for No :");

        while (true) {
            answer = input.nextLine().toUpperCase();
            if (answer.equals("Y") || answer.equals("N"))
                return answer;
            System.out.println("Incorrect answer: You need to type 'Y' or 'N'");
        }
    }


    /**
     * Points out the player cannot move in a certain position.
     */
    public void printMoveErrorScreen() {
        System.out.println("You're not allowed to move there.");
    }


    /**
     * Asks the player if he still wants to move during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printMoveDecisionError() {
        printMoveErrorScreen();
        System.out.println("Do you still want to move?");
        return playerAnswerYN();
    }


    /**
     * Asks the player if he still wants to build during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printBuildDecisionError() {
        System.out.println("Do you still want to build?");
        return playerAnswerYN();
    }


    /**
     * Points out a player is not allowed to build.
     */
    public void printBuildGeneralErrorScreen() {
        System.out.println("You're not allowed to build!");

    }


    /**
     * Points out a player is not allowed to build a block in a certain position.
     */
    public void printBuildBlockErrorScreen() {
        System.out.println("You're not allowed to build a BLOCK there.");
    }


    /**
     * Points out that a player is not allowed to build again in a certain position.
     */
    public void printBuildInSamePositionScreen() {
        System.out.println("You're not allowed to build again there.");
    }


    /**
     * Lets player know that the challenger is choosing the gods for the game.
     *
     * @param challenger nickname of the challenger.
     */
    public void waitChallengerChooseGods(String challenger) {

        this.challenger = challenger;
        System.out.print(challenger + " is the Challenger and is choosing the gods for this game...");
    }


    /**
     * Lets player know that another player is choosing his god.
     *
     * @param otherPlayer the player that is choosing his god.
     */
    public void waitOtherPlayerChooseGod(String otherPlayer) {
        System.out.println();

        System.out.print(otherPlayer + " is choosing his god...");
    }


    /**
     * Lets player know the god chosen by another player.
     *
     * @param otherPlayer player who chose the god.
     * @param chosenGod   god chosen by the otherPlayer.
     */
    public void otherPlayerChoseGod(String otherPlayer, String chosenGod) {

        System.out.println();
        System.out.println(otherPlayer + " has chosen " + chosenGod + ".\n");

        setPlayerGod(otherPlayer, chosenGod);
    }


    /**
     * Lets player know that the challenger is choosing the start player.
     */
    public void waitChallengerStartPlayer() {
        System.out.println();
        System.out.print(challenger + " is choosing the start player...");
    }


    /**
     * Lets player know who is the start player.
     *
     * @param startPlayer start player nickname.
     */
    public void printStartPlayer(String startPlayer) {
        System.out.println();
        System.out.println(startPlayer + " is the start player.");
    }


    /**
     * Lets  player know that another player is choosing the initial position for his workers.
     *
     * @param player player who is performing the action.
     */
    public void otherPlayerSettingInitialWorkerPosition(String player) {
        System.out.println();
        System.out.print(player + " is placing his workers on the board...");
    }


    /**
     * Lets player know that it's another player's turn.
     *
     * @param currentPlayer nickname of the player that is playing his turn.
     */
    public void otherPlayerTurn(String currentPlayer) {
        System.out.println();
        System.out.print(currentPlayer + " is playing his turn...");
    }


    /**
     * Lets player know that he has lost, and who is the winner.
     *
     * @param winner nickname of the winner.
     * @return Always returns true.
     */
    public boolean losingView(String winner) {
        System.out.println("\nYou have lost this game. The winner is " + winner + ".");
        System.out.println("Goodbye.");
        return true;
    }


}
