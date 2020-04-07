package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.god.*;
import it.polimi.ingsw.view.*;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the input given by the view and sends them to the model to change the Game status
 */
public class GameController {

    private Game game;
    private int numOfPlayers;
    private TurnHandler turnHandler;
    private CLIMainView view;
    private ViewSelecter viewSelecter;
    private boolean endGame;
    private GodController godController;
    private final ArrayList<God> godsDeck;
    private boolean endGame;

    public GameController() {
        game = null;
        numOfPlayers = 0;
        turnHandler = null;
        view = null;
        viewSelecter = new ViewSelecter();
        endGame = false;
        godsDeck = new ArrayList<>(14);
        endGame = false;
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.setUpGame();

    }


    //Asks the player whether he wants to play with a CLI or GUI
    private void setPreferredView() {

        if(viewSelecter.askTypeofView().equals("CLI"))
            this.view = new CLIMainView(this);

        /*else if(viewType.equals("GUI"))
            this.view = new GUIMainView(this);*/
        else
            System.out.println("Invalid input: type either CLI or GUI.");
    }


    /**
     * Sets up game and starts the logic flow.
     */
    public void setUpGame() {
        godController = new GodController(view, this);
        createDeckGods();
        
        view.beginningView();
        int numOfPlayers = view.askNumberOfPlayers();
        
        game = new Game(numOfPlayers);
        turnHandler = new TurnHandler(game, view, this);

        for(String nick : view.askPlayersNickname())
            game.addPlayer(nick);

        turnHandler.setUpTurns();
    }


    public CLIMainView getView() {
        return view;
    }


    public ArrayList<God> getGodsDeck() {
        return godsDeck;
    }

    /**
     * Creates the deck where we can find the God cards of the game.
     */
    public void createDeckGods(){
        godsDeck.add(new Apollo(godController));
        godsDeck.add(new Artemis(godController));
        godsDeck.add(new Athena(godController));
        godsDeck.add(new Atlas(godController));
        godsDeck.add(new Charon(godController));
        godsDeck.add(new Demeter(godController));
        godsDeck.add(new Hephaestus(godController));
        godsDeck.add(new Hera(godController));
        godsDeck.add(new Hestia(godController));
        godsDeck.add(new Minotaur(godController));
        godsDeck.add(new Pan(godController));
        godsDeck.add(new Prometheus(godController));
        godsDeck.add(new Triton(godController));
        godsDeck.add(new Zeus(godController));
    }

    public boolean getEndGame() {
        //todo
        return endGame;
    }

}
