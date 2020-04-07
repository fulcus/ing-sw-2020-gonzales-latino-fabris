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
    private TurnHandler turnHandler;
    private final CLIMainView view; //will be refactored to include GUI
    private GodController godController;
    private final ArrayList<God> godsDeck;

    public GameController() {
        game = null;
        turnHandler = null;
        view = new CLIMainView(this);
        godsDeck = new ArrayList<>(14);

    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.setUpGame();

    }



    /**
     * Sets up game and starts flow
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

        turnHandler.start();
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

    public void endGame() {
        //todo
    }

}
