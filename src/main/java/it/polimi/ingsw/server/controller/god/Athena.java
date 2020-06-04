package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;


/**
 * Represents the card of the God Athena.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Athena extends God {

    public final String description = "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";


    public Athena(GodController godController) {
        super(godController);
    }


    /**
     * The evolution of the turn for the player that holds the Athena God card is different from the abstract implementation.
     * Here we can set the restriction for the enemy workers to not move up.
     *
     * @param worker Selected worker that will act in the current turn.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException, WinException {
        move(worker);
        win(worker);
        build(worker);
        cannotMoveUpRestriction(worker);
    }

    /**
     * Forbids other players to move up if worker has moved up this turn.
     *
     * @param worker Worker selected to act in the turn.
     */
    private void cannotMoveUpRestriction(Worker worker) {


        for (Player p : worker.getPlayer().getGame().getPlayers()) {

            //if worker moved up, other workers can't move up
            if (worker.getLevelVariation() > 0 && p != worker.getPlayer()) {
                p.setPermissionToMoveUp(false);
            } else
                p.setPermissionToMoveUp(true);
        }

    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
