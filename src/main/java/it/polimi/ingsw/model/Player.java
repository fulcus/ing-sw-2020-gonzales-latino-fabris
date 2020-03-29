package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Scanner;


public class Player {

    private Game game;
    //private ArrayList<PlayerObserver> playerObservers;
    private String nickname;
    private Color color;
    private God god;
    private ArrayList<Worker> workers;
    private Worker chosenWorker;
    private boolean canWinPerimetric;
    private boolean canMoveUp;


    /**
     * Creates a Player.
     *
     * @param game     Represents the belonging game of the player.
     * @param nickname The name chosen by the user for the belonging game.
     */

    public Player(Game game, String nickname) {

        this.game = game;
        this.nickname = nickname;
        this.god = null;

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
        System.out.println("List of available Gods:");

        //todo excepiton

        while (god == null) {

            for (God god : game.getChosenGods()) {
                System.out.println("*  " + game.getChosenGods().getName());
            }

            String s = input.nextLine();

            for (God god : game.getChosenGods()) {
                if (s.equals(god.getName())) {
                    this.god = god;
                }

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

        int x;
        int y;

        do {

            System.out.println("Insert Worker's position");
            x = input.nextInt();
            y = input.nextInt();

            if (game.getMap().findCell(x, y).worker != null)
                setChosenWorker(game.getMap().findCell(x, y).worker);
            else
                System.out.println("Insert a cell with a Worker on it");


        } while (game.getMap().findCell(x, y).worker == null);


    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return True if the Player can moveup, false if this player can not move up for the current turn
     */
    public boolean CanMoveUp() {
        return canMoveUp;
    }

    /**
     *
     * @param canMoveUp is setted to false, if the player can not move up anymore in his next turn
     */
    /*This method is called by a God of another player*/
    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    /**
     * @return True if the Player can win with a worker on the perimeter, false otherwise
     */
    public boolean CanWinPerimetric() {
        return canWinPerimetric;
    }

    /**
     * @param canWinPerimetric is eventually set to false by a God of another Player
     */
    public void setCanWinPerimetric(boolean canWinPerimetric) {
        this.canWinPerimetric = canWinPerimetric;
    }

    /**
     * Reset restrictions of the Player
     */

    /*Once the moving and winning restricions have been eventually applied to the player's turn,
     * these restrictions will be resetted*/

    public void resetRestrictions() {
        canMoveUp = true;
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
