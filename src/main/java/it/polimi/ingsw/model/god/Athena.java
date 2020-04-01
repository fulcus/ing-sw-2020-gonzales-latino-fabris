package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class Athena implements God{

    public void evolveTurn(Worker worker) {
        move(worker);
        win(worker);
        build(worker);
        cannotMoveUpRestriction(worker);
    }

    private void cannotMoveUpRestriction(Worker worker){
        if(worker.getLevelVariation() > 0) {
            for(Player p : worker.getPlayer().getGame().getPlayers()) {
                if(p != worker.getPlayer()) {
                    p.setPermissionToMoveUp(false);
                }
                else
                    p.canMoveUp();
            }
        }
    }
}
