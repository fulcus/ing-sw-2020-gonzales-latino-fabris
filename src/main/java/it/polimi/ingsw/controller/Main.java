package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.CLIMainView;

public class Main {

    public static void main(String args[]) {
        CLIMainView view = new CLIMainView();
        GameController gameController = new GameController(view);


        view.beginningView();
        view.setGameController(gameController);
        gameController.setNumOfPlayers(view.askNumberOfPlayers());
        gameController.playerSetting(view.askPlayersNickname());
        gameController.initializeGame();

    }
}
