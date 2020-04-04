package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.god.God;

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
    private boolean canWinInPerimeter;  //true if can win on perimeter
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
        god = null;
        chosenWorker = null;
        canWinInPerimeter = true;
        canMoveUp = true;
        //playerObservers = new ArrayList<PlayerObserver>();
        workers = new ArrayList<Worker>(2);
        workers.add(new Worker(this, Sex.MALE));
        workers.add(new Worker(this, Sex.FEMALE));
    }


    public String getNickname() {
        return nickname;
    }

    public Worker getChosenWorker() {return chosenWorker;}

    public void setChosenWorker(Worker chosenWorker) {
        this.chosenWorker = chosenWorker;
    }


    public God getGod() {
        return god;
    }

    /**
     * Assigns to this player the God,
     * giving the possibility to choose between the God cards of this game
     */
    public void chooseMyGod(){
        god = chooseGod(game.getChosenGods());
    }


    /**
     * Allows to choose the God cards that will be used by the players during the Game
     * @param deckGods It's all the possible Gods the challenger can choose between
     * @return The chosen Gods are listed in this element
     */
    public ArrayList<God> chooseInitialGods(God[] deckGods){
        ArrayList<God> chosenGods = new ArrayList<God>(game.getNumberOfPlayers());
        God chosen;
        int i = 0;
        while(i < game.getNumberOfPlayers()){
            chosen = chooseGod(chosenGods);
            int j=0;
            while (j<=i) {
                if (chosen.equals(chosenGods.get(j))) {
                    System.out.println("This God has already been chosen. Pick another!\n");
                    i--;
                } else {
                    j++;
                }
            }
            chosenGods.add(i, chosen);
            i++;
        }
        return chosenGods;
    }


    /**
     * Allows the player to choose a God from the available gods of the current game
     */
    public God chooseGod(ArrayList<God> gameGods) {
        int i=0, chosenOne;
        God chosenGod;
        //dalla lista dei chosenGods del gioco sceglierne uno dei 3 e assegnarlo al God di questo player
        //todo:
        //dal controller ricevo l'input che la view deve dare per poter scegliere uno dei god tra cui scegliere
        //intanto lo faccio qui per semplicità

        System.out.println("Choose one God among the following.");
        Scanner input = new Scanner(System.in);
        chosenOne = input.nextInt();   //scelgo il god della lista con un numero da 1 a 3
        chosenGod = gameGods.get(chosenOne-1);
        while(i<game.getNumberOfPlayers()){
            //Se il giocatore è diverso e il dio scelto è lo stesso allora faccio riscegliere il dio
            if(!this.equals(game.getPlayers().get(i)) && chosenGod.equals(game.getPlayers().get(i).getGod())){
                System.out.println("This god has already been chosen. Pick another!\n");
                chosenOne = input.nextInt();   //scelgo il god della lista con un numero da 1 a 3
                chosenGod = gameGods.get(chosenOne-1);
            }
            else{
                i++;
            }
        }
        return chosenGod;
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


    public void setColor(Color color) { this.color = color; }

    /**
     *
     * @param value true allows the player to win in perimeter, false otherwise
     */
    public void setPermissionToWinInPerimeter(boolean value) { canWinInPerimeter = value;}

    /**
     *
     * @param value true allows the player to move up, false otherwise
     */
    public void setPermissionToMoveUp(boolean value) {canMoveUp = value;}

    /**
     * @return True if the Player can move up, false if this player can not move up for the current turn
     */
    public boolean getCanMoveUp() { return canMoveUp;}


    /**
     * @return True if the Player can win with a worker on the perimeter, false otherwise
     */
    public boolean getCanWinInPerimeter() { return canWinInPerimeter; }


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
