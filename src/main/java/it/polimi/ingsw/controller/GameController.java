package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.god.*;
import it.polimi.ingsw.view.*;


import java.util.ArrayList;

/**
 * Handles the input given by the view and sends them to the model to change the Game status
 */
public class GameController {

    private Game game;
    private int numOfPlayers;
    private TurnHandler turnHandler;
    private CLIMainView view;
    private boolean endGame;

    public GameController() {
        game = null;
        numOfPlayers = 0;
        turnHandler = null;
        this.view = null;
    }

    public void createCLIView() {

        if (view != null)
            view = new CLIMainView();
        this.view = view;
        endGame = false;
    }

    public CLIMainView getView() {
        return view;
    }


    public void setNumOfPlayers(int players) {
        numOfPlayers = players;
    }


    public void playerSetting(ArrayList<String> playerUsernames){
        game = new Game(playerUsernames.size());
        turnHandler = new TurnHandler(game);
        game.addPlayer(playerUsernames.get(0));
        game.addPlayer(playerUsernames.get(1));
        if (playerUsernames.size() == 3)
            game.addPlayer(playerUsernames.get(2));
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

    /**
     * Allows to create the deck of God cards of the game
     * Allows to set the chosen Gods of the game thanks to the challenger
     * Allows to assign the God to every player of the game
     */
    public void initializeGame(){
        game.createDeckGods();
        game.getChallenger().chooseInitialGods(chooseInitialGod(view.askGameGods()));
        chooseGod(view.askPlayingGod());
    }

    /**
     * Allows to assign the God to every player of the game
     * @param choices Is the ordered list of gods to assign to the players
     */
    public void chooseGod(ArrayList<Integer> choices) {
        int i = 0;
        while (i < game.getNumberOfPlayers()) {
            turnHandler.getCurrentPlayer().setGod(choices.get(i));
        }
    }

    /**
     * Allows to add to the list of the chosenGods of the game the ones that the challenger decided to choose
     * @param chosen Contains the challenger inputs to the Gods he wants to play with
     * @return The list of the Gods
     */
    public ArrayList<God> chooseInitialGod(ArrayList<Integer> chosen){
        int i=0;
        ArrayList<God> gods = new ArrayList<God>(game.getNumberOfPlayers());
        while(i<game.getNumberOfPlayers()){
            switch (chosen.get(i)) {
                case 1 :
                    gods.add(i, new Apollo(this));
                    break;
                case 2 :
                    gods.add(i, new Artemis(this));
                    break;
                case 3:
                    gods.add(i, new Athena(this));
                    break;
                case 4 :
                    gods.add(i, new Atlas(this));
                    break;
                case 5 :
                    gods.add(i, new Charon(this));
                    break;
                case 6 :
                    gods.add(i, new Demeter(this));
                    break;
                case 7 :
                    gods.add(i, new Hephaestus(this));
                    break;
                case 8 :
                    gods.add(i, new Hera(this));
                    break;
                case 9 :
                    gods.add(i, new Hestia(this));
                    break;
                case 10 :
                    gods.add(i, new Minotaur(this));
                    break;
                case 11 :
                    gods.add(i, new Pan(this));
                    break;
                case 12 :
                    gods.add(i, new Prometheus(this));
                    break;
                case 13 :
                    gods.add(i, new Triton(this));
                    break;
                case 14 :
                    gods.add(i, new Zeus(this));
                    break;
                default:
                    break;
            }
            i++;
        }
        return gods;
    }

    /**
     * Increments the turn number and allows to the next player to play
     */
    public void endTurn(){
        turnHandler.nextTurn();
    }


    /**
     * Allows to print all the deck of Gods' cards
     */
    public void printAllGods(){
        System.out.println("Here's the Gods' card list:\n");
        for(int i=1; i<=game.getDeckGods().length; i++) {
            System.out.println(i + "]  " + game.getDeckGods()[i].getClass().getSimpleName());
        }
    }

    /**
     * Allows to print the Gods that the challenger has chosen for the game
     */
    public void printGameGods(){
        for(int i=1; i<=game.getChosenGods().size(); i++){
            System.out.println(i + "]  " + game.getChosenGods().get(i).getClass().getSimpleName());
        }
    }

    public void errorScreen() {
        view.printErrorScreen();
    }


    /**
     * This method translates compass directions (N,S,E,...) into coordinates.
     * @param compassInput Compass direction to be translated.
     * @return Variation in coordinates
     */
    public int[] getInputInCoordinates(String compassInput){

        int[] result = new int[2];

        switch (compassInput) {
            case "N" : {
                result[0] = -1;
                result[1] = 0;
                break;
            }
            case "NE" : {
                result[0] = -1;
                result[1] = -1;
                break;
            }
            case "NW" : {
                result[0] = -1;
                result[1] = 1;
                break;
            }
            case "S" : {
                result[0] = 1;
                result[1] = 0;
                break;
            }
            case "SE" : {
                result[0] = 1;
                result[1] = 1;
                break;
            }
            case "SW" : {
                result[0] = 1;
                result[1] = -1;
                break;
            }
            case "W" : {
                result[0] = 0;
                result[1] = -1;
                break;
            }
            case "E" : {
                result[0] = 0;
                result[1] = 1;
                break;
            }
            default : {
                result[0] = 0;
                result[1] = 0;
                break;
            }

        }

        return result;

    }

    /**
     * This method returns the coordinates' variation of the selected movement.
     * @return Coordinates' variation.
     */
    public int[] getMovementInput(){

        return getInputInCoordinates(view.askMovementDirection());
    }

    /**
     * This method returns the coordinates' variation of the selected building.
     * @return Coordinates' variation.
     */
    public int[] getBuildingInput(){

        return getInputInCoordinates(view.askBuildingDirection());
    }



    public void winGame() {
        view.winningView();
        endGame = true;
    }
}
