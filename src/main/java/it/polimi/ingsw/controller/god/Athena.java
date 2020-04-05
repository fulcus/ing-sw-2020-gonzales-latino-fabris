package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class Athena implements God{


    @Override
    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);
        cannotMoveUpRestriction(worker);
    }

    /**
     * Forbids other players to move up if worker has moved up this turn.
     * @param worker Worker selected to act in the turn.
     */
    private void cannotMoveUpRestriction(Worker worker){

        if(worker.getLevelVariation() > 0) {
            for(Player p : worker.getPlayer().getGame().getPlayers()) {

                if(p != worker.getPlayer()) {
                    p.setPermissionToMoveUp(false);
                } else
                    p.setPermissionToMoveUp(true);
            }
        }
    }

}
