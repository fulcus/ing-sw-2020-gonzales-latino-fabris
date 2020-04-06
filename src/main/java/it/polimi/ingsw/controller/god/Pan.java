package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Worker;

public class Pan implements God{


    @Override
    public boolean win(Worker worker){

        boolean won;
        boolean normalCondition = worker.getLevel() == 3 && worker.getLevelVariation() == 1 || worker.getLevelVariation() <= -2;

        if (worker.getPlayer().getCanWinInPerimeter())
            won = normalCondition;
        else
            won = normalCondition && !worker.getPosition().isInPerimeter();


        if (won)
            //todo View + Controller call some method to win

    }



}
