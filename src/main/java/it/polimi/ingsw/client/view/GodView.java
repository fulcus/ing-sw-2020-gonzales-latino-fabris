package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.model.Worker;

import java.util.ArrayList;
import java.util.Scanner;


public class GodView {

    private Scanner input;

    public GodView() {
        input = new Scanner(System.in);
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
                System.out.println("Invalid Direction. You must use: N, NE, E, SE, S, SW, W, NW (or in special case 'U'");

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

        System.out.println("Do you want to move again your Worker? (Y = 'Yes', N = 'No')");
        return playerAnswerYN();
    }


    /**
     * Allows to get the input of the player to jump to an higher level.
     *
     * @return The will of the player to reach an higher level.
     */
    public String askWantToMoveUp() {

        System.out.println("Do you want to move up? \n Y for Yes, N for No\n");
        return playerAnswerYN();
    }


    /**
     * Allows to get the input of the player to move an enemy's worker.
     *
     * @return The will of the player to move an enemy's worker
     */
    public String askWantToMoveEnemy() {

        System.out.println("Do you want to force your near enemy to move? \n Y for Yes, N for No\n");
        return playerAnswerYN();
    }


    /**
     * Allows to move one worker's enemy.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The Worker to move selected by the player.
     */
    public String askWorkerToMove(ArrayList<Worker> enemyWorkers, Worker myWorker) {
        System.out.println("If there are enemy's workers around you, these are their directions.Choose one:  ");

        ArrayList<String> presentPositions = printFoundEnemiesPosition(enemyWorkers, myWorker);
        if (presentPositions.size() == 0) {
            System.out.println("No enemy workers around...");
            return null;
        }

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
     * Helps to show the positions of the neighboring workers
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The position of the selected worker to move.
     */
    public ArrayList<String> printFoundEnemiesPosition(ArrayList<Worker> enemyWorkers, Worker myWorker) {
        int myWorkerX = myWorker.getPosition().getX();
        int myWorkerY = myWorker.getPosition().getY();
        ArrayList<String> presentPositions = new ArrayList<>();

        for (Worker enemyWorker : enemyWorkers) {
            if (myWorkerX > enemyWorker.getPosition().getX()) {
                if (myWorkerY > enemyWorker.getPosition().getY()) {
                    System.out.println("NW");
                    presentPositions.add("NW");
                } else if (myWorkerY == enemyWorker.getPosition().getY()) {
                    System.out.println("N");
                    presentPositions.add("N");
                } else {
                    System.out.println("NE");
                    presentPositions.add("NE");
                }
            } else if (myWorkerX == enemyWorker.getPosition().getX()) {
                if (myWorkerY > enemyWorker.getPosition().getY()) {
                    System.out.println("W");
                    presentPositions.add("W");
                } else if (myWorkerY < enemyWorker.getPosition().getY()) {
                    System.out.println("E");
                    presentPositions.add("E");
                } else {
                    System.out.println("ERROR : IT'S THE SAME POSITION OF WHERE I AM!!!");
                    return null;
                }
            } else {
                if (myWorkerY > enemyWorker.getPosition().getY()) {
                    System.out.println("SW");
                    presentPositions.add("SW");
                } else if (myWorkerY == enemyWorker.getPosition().getY()) {
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
    public void printBuildUnderneath(){
        System.out.println("Remember that you can build underneath!\n" +
                "To do that, the direction for the build is 'U'\n");
    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHephaestus() {

        System.out.println("You are allowed to build another time, " +
                "but ONLY in the same position you built before\n <Y> for Yes,  <N> for No :");
        return playerAnswerYN();
    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainDemeter() {

        System.out.println("You are allowed to build another time, but NOT in the same position you built before\n <Y> for Yes,  <N> for No :   ");
        return playerAnswerYN();
    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHestia() {

        System.out.println("You are allowed to build another time, but NOT in the border position\n <Y> for Yes,  <N> for No :   ");
        return playerAnswerYN();
    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build before moving.
     */
    public String askBuildPrometheus() {

        System.out.println("You are allowed to build before you move\n <Y> for Yes,  <N> for No :   ");
        return playerAnswerYN();
    }


    /**
     * Allows to get the answer of a player to a question.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    private String playerAnswerYN() {
        String answer;

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
        System.out.println("Do you still want to move? Y for yes, N for No:   ");
        return playerAnswerYN();
    }


    /**
     * Asks the player if he still wants to build during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printBuildDecisionError() {
        System.out.println("Do you still want to build? Y for yes, N for No:   ");
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
}
