package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Worker;

public class Pan implements God{

    private GameController gameController;

    public Pan(GameController gameController) {
        this.gameController = gameController;
    }


    public void win(Worker worker){

        boolean won;
        boolean normalCondition = worker.getLevel() == 3 && worker.getLevelVariation() == 1 || worker.getLevelVariation() <= -2;

        if (worker.getPlayer().getCanWinInPerimeter())
            won = normalCondition;
        else
            won = normalCondition && !worker.getPosition().isInPerimeter();
           //chiedere a fra perchè ha messo la condizione per cui per vincere non può stare nel perimetro

        if (won)
            gameController.winGame();
    }

    @Override
    public  GameController getGameController(){
        return gameController;
    }

}
