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
    private CLIMainView view;
    private final ViewSelector viewSelector;
    private boolean endGame;
    private GodController godController;
    private final ArrayList<God> godsDeck;

    public GameController() {
        game = null;
        int numOfPlayers = 0;
        turnHandler = null;
        view = null;
        viewSelector = new ViewSelector();
        endGame = false;
        godsDeck = new ArrayList<>(14);
        endGame = false;
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.setUpGame();
    }


    /**
     * Sets up game and starts the logic flow.
     */
    public void setUpGame() {

        String viewType = viewSelector.askTypeofView();

        if (viewType.toUpperCase().equals("CLI"))
            view = new CLIMainView(this);
        /*
        else if(viewType.toUpperCase().equals("GUI"))
            view = new GUIMainView(this);
        else
            viewSelector.genericError();
        */

        godController = new GodController(view.getGodView(), this);
        createDeckGods();

        view.beginningView();

        int numOfPlayers = view.askNumberOfPlayers();

        game = new Game(numOfPlayers);

        turnHandler = new TurnHandler(game, view, this);

        setUpObserverView();

        setPlayersNicknames(numOfPlayers);

        setPlayersColors();

        turnHandler.setUpTurns();

        turnHandler.startTurnFlow();
    }

    public void setUpObserverView() {

        for (int i = 0; i < Board.SIDE; i++) {

            for (int j = 0; j < Board.SIDE; j++) {

                game.getBoard().findCell(i,j).register(view);

            }
        }


    }

    public void setPlayersColors() {

        for (Player player : game.getPlayers()) {

            boolean colorCorrectlyChosen = false;

            while (!colorCorrectlyChosen) {

                String chosenColor = view.askPlayerColor(player.getNickname());

                if (colorIsAvailable(chosenColor) && colorIsValid(chosenColor)) {
                    player.setColor(Color.StringToColor(chosenColor));
                    colorCorrectlyChosen = true;
                } else
                    view.notAvailableNickname();

            }
        }
    }

    public void setPlayersNicknames(int numberOfPlayers) {

        int i = 0;

        while (i < numberOfPlayers) {

            boolean nicknameCorrectlyChosen = false;

            while (!nicknameCorrectlyChosen) {

                String chosenNickname = view.askPlayerNickname();

                if (nicknameIsAvailable(chosenNickname) && chosenNickname.length() > 0) {
                    game.addPlayer(chosenNickname);
                    nicknameCorrectlyChosen = true;
                    i++;
                } else
                    view.notAvailableNickname();

            }
        }

    }

    public boolean colorIsValid(String chosenColor) {
        return chosenColor.equals(Color.BLUE.name())
                || chosenColor.equals(Color.BEIGE.name())
                || chosenColor.equals(Color.WHITE.name());
    }

    public boolean nicknameIsAvailable(String chosenNickname) {

        for (Player player : game.getPlayers()) {

            if (chosenNickname.equals(player.getNickname()))
                return false;
        }

        return true;

    }

    public boolean colorIsAvailable(String chosenColor) {

        for (Player player : game.getPlayers()) {

            if (player.getColor() != null
                    && chosenColor.equals(player.getColor().toString()))
                return false;
        }

        return true;
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
    public void createDeckGods() {
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

    public Game getGame() {
        return game;
    }

}


