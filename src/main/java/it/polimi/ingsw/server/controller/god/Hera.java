package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;


/**
 * Represents the card of the God Hera.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Hera extends God{

    public final String description = "An opponent cannot win by moving into a perimeter space.";


    public Hera(GodController godController){
        super(godController);
    }


    /**
     * The evolution of the turn for the player that holds the Hera God card is different from the abstract implementation.
     * With this evolution of the turn, the player(s) that doesn't hold Hera as God for his game cannot win reaching the win condition in a perimeter cell.
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
        cannotWinInPerimeterRestriction(worker);
    }


    /**
     * Sets the condition for what other players in the game cannot win on a perimeters cell of the board.
     * @param worker The worker selected for this turn.
     */
    private void cannotWinInPerimeterRestriction(Worker worker) {
        for(Player p : worker.getPlayer().getGame().getPlayers()) {

            if (p != worker.getPlayer()) {
                p.setPermissionToWinInPerimeter(false);
            }
        }
    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}

