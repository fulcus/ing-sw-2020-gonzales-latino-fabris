package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;

import java.util.ArrayList;
import java.util.Scanner;


public class Player {

    private Game game;
    //private ArrayList<PlayerObserver> playerObservers;
    private String nickname;
    private Color color;
    private God god;
    private ArrayList<Worker> workers;
    protected Worker chosenWorker;
    private boolean forbiddenToWinInPerimeter;  //true if restriction applied
    private boolean forbiddenToMoveUp;


    /**
     * Creates a Player.
     *
     * @param game     Represents the belonging game of the player.
     * @param nickname The name chosen by the user for the belonging game.
     */

    public Player(Game game, String nickname) {

        this.game = game;
        this.nickname = nickname;
        god = null;
        chosenWorker = null;
        forbiddenToWinInPerimeter = false;
        forbiddenToMoveUp = false;
        //playerObservers = new ArrayList<PlayerObserver>();
        workers = new ArrayList<Worker>(2);
        workers.add(new Worker(this, Sex.MALE));
        workers.add(new Worker(this, Sex.FEMALE));
    }

    public String getNickname() {
        return nickname;
    }


    public void setChosenWorker(Worker chosenWorker) {
        this.chosenWorker = chosenWorker;
    }


    public God getGod() {
        return god;
    }

    /**
     * Allows the player to choose a God from the available gods of the current game
     */
    public void chooseGod() {

        Scanner input = new Scanner(System.in);
        System.out.println("Choose one God among the following.");

        //todo exception

        while (god == null) {

            for (God god : game.getChosenGods())
                System.out.println("* " + god.getClass().getSimpleName());

            String s = input.nextLine();

            for (God god : game.getChosenGods()) {
                if (s.equals(god.getClass().getSimpleName()))
                    this.god = god;
            }
        }

    }

    /**
     * This method allows the user to choose the worker to be used for his turn
     */
    public void chooseWorker() {
        //con X e Y
        //chosenWorker = prendera il puntatore al Worker che ha selezionato l'utente

        Scanner input = new Scanner(System.in);


        do {

            System.out.println("Insert the position of the worker you wish to select.");
            int x = input.nextInt();
            int y = input.nextInt();
            Cell chosenCell = game.getMap().findCell(x, y);
            if (chosenCell.hasWorker() && chosenCell.getWorker().getPlayer() == this) {
                chosenWorker = game.getMap().findCell(x, y).getWorker();
                break;
            }

            System.out.println("The position is not valid");

        } while (true);


    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    /**
     * @return True if the Player can move up, false if this player can not move up for the current turn
     */
    public boolean isForbiddenToWinInPerimeter() {
        return forbiddenToWinInPerimeter;
    }


    /**
     * @return True if the Player can win with a worker on the perimeter, false otherwise
     */
    public boolean isForbiddenToMoveUp() {
        return forbiddenToMoveUp;
    }

    /**
     *
     */
    public void cannotMoveUp() {
        this.forbiddenToMoveUp = true;
    }

    /**
     *
     *
     */
    /*This method is called by a God of another player*/
    public void canMoveUp() {
        this.forbiddenToMoveUp = false;
    }


    /**
     * Resets restrictions of the Player.
     */
    //Once the moving and winning restrictions have been applied to the player's turn, these restrictions will be reset
    public void canWinInPerimeter() {
        forbiddenToWinInPerimeter = false;
    }

    public void cannotWinInPerimeter() {
        forbiddenToWinInPerimeter = true;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public Game getGame() {
        return game;
    }


    /*
    public void register(PlayerObserver playerObserver){

        this.playerObservers.add(playerObserver);

    }

    public void unregister(PlayerObserver playerObserver){

        this.playerObservers.remove(playerObserver);
    }

    public void notifyObservers(){

        for(PlayerObserver observer : this.playerObservers )
        {
            observer.update();
        }

    }

*/


}
