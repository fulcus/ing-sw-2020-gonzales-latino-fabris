package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Worker;

public class Pan implements God{

    private GodController godController;

    public Pan(GodController godController) {
        this.godController = godController;
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
            godController.winGame();
    }

    @Override
    public  GodController getGodController(){
        return godController;
    }

}
