package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Worker;


/**
 * Represents the card of the God Pan.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Pan extends God{

    public final String description = "You also win if your Worker moves down two or more levels.";


    public Pan(GodController godController) {
        super(godController);
    }


    /**
     * Default condition is still valid, but now the selected worker of the player holding the Pan card can win also when reaches the level 2 of a building.
     *
     * @param worker Worker playing the turn.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
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
