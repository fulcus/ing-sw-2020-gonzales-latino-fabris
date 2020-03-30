package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class Athena implements God{

    public void evolveTurn(Worker worker) {
        loseCannotMove(worker);
        move(worker);
        win(worker);
        loseCannotBuild(worker);
        build(worker);
        applyRestriction(worker);
    }

    private void applyRestriction(Worker worker){
        if(worker.hasMovedUp()) {
            for(Player p : worker.getPlayer().getGame().getPlayers()) {
                if(p != worker.getPlayer()) {
                    p.cantMoveUp();
                }
                else
                    p.canMoveUp();
            }
        }
    }
}
