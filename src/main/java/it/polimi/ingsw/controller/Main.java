package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.CLIMainView;

public class Main {

    public static void main(String args[]) {
        GameController gameController = new GameController();

        CLIMainView view = new CLIMainView(gameController);


        view.beginningView();
        //view.setGameController(gameController);
        gameController.setNumOfPlayers(view.askNumberOfPlayers());
        gameController.playerSetting(view.askPlayersNickname());
        gameController.initializeGame();

    }
}
