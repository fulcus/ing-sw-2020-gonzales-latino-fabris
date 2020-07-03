package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.controller.WinException;
import it.polimi.ingsw.server.model.Cell;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;


/**
 * Represents the card of the God Triton.
 * Allows to follow the instructions and to apply the effect of this specific God.
 */
public class Triton extends God {

    public final String description = "Each time your Worker moves into a perimeter space, it may immediately move again.";
    private Cell initialPosition;


    public Triton(GodController godController) {
        super(godController);
    }


    /**
     * The evolution of the turn for the player that holds the Triton God card is different from the abstract implementation.
     * The player, if the selected worker stands on the perimeter of the board, can choose if he wants to move again his worker.
     *
     * @param worker Selected worker that will act in the current turn.
     * @throws UnableToBuildException The worker isn't allowed to build anywhere.
     * @throws UnableToMoveException The worker isn't allowed to move anywhere.
     * @throws WinException The worker has reached the third level of a building and so wins the game.
     */
    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException, WinException {

        move(worker);
        win(worker);

        initialPosition = worker.getPosition();

        while (initialPosition.isInPerimeter()) {

            if (!godController.wantToMoveAgain())
                break;

            moveAgain(worker);
            initialPosition = worker.getPosition();
            win(worker);
        }

        build(worker);
    }


    /**
     *Allows the selected worker to move another time if it is standing on the border of the board.
     * @param worker The selected worker of this turn.
     */
    private void moveAgain(Worker worker) {

        WorkerMoveMap moveMap;
        try {
            moveMap = updateMoveMap(worker);
        } catch (UnableToMoveException ex) {
            godController.errorMoveScreen();
            return;
        }

        while (true) {

            int[] secondMovePosition = godController.getMoveInput();
            int xMove = secondMovePosition[0] + worker.getPosition().getX();
            int yMove = secondMovePosition[1] + worker.getPosition().getY();

            Cell secondMoveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

            if (secondMoveCell != initialPosition && moveMap.isAllowedToMoveBoard(xMove, yMove)) {

                worker.setPosition(xMove, yMove);
                godController.displayBoard();
                return;
            } else
                godController.errorMoveScreen();

            //Asking if the player is sure to keep going on moving on the board
            if (!godController.errorMoveDecisionScreen())
                return;
        }
    }


    public GodController getGodController() {
        return godController;
    }


    public String getDescription() {
        return description;
    }

}

