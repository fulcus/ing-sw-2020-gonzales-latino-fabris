package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.model.Worker;

public class Pan extends God{

    public String description = "You also win if your Worker moves down two or more levels.";


    public Pan(GodController godController) {
        super(godController);
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
