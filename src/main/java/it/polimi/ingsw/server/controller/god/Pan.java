package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Worker;

public class Pan extends God{

    public final String description = "You also win if your Worker moves down two or more levels.";


    public Pan(GodController godController) {
        super(godController);
    }


    @Override
    public void win(Worker worker) throws WinException {

        boolean won;
        boolean normalCondition = worker.getLevel() == 3 && worker.getLevelVariation() == 1 || worker.getLevelVariation() <= -2;

        if (worker.getPlayer().getCanWinInPerimeter())
            won = normalCondition;
        else
            won = normalCondition && !worker.getPosition().isInPerimeter();

        if (won)
            throw new WinException();

    }

    @Override
    public  GodController getGodController(){
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
