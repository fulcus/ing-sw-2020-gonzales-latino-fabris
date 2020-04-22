package it.polimi.ingsw.server.controller.god;

import it.polimi.ingsw.server.controller.GodController;
import it.polimi.ingsw.server.controller.UnableToBuildException;
import it.polimi.ingsw.server.controller.UnableToMoveException;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.WorkerMoveMap;


public class Prometheus extends God {

    public String description =
            "If your Worker does not move up, it may build both before and after moving.";
    private boolean movingUp;

    public Prometheus(GodController godController) {
        super(godController);
    }

    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        //worker.getPlayer().setPermissionToMoveUp(true);

        //should not lose with optional build
        if (worker.getBuildMap().anyAvailableBuildPosition()) {

            if(!reachHigherLevel(worker)) {
                if (godController.wantToBuildAgain(this)){
                    build(worker);
                    //worker.getPlayer().setPermissionToMoveUp(false);
                }
                else
                    godController.errorBuildScreen();
                movingUp = false;
            }

            else {
                movingUp = worker.getPlayer().getCanMoveUp() && godController.wantToMoveUp();
                if (!movingUp && godController.wantToBuildAgain(this)) {
                    build(worker);
                    //worker.getPlayer().setPermissionToMoveUp(false);
                } else
                    godController.errorBuildScreen();
            }
        }
        move(worker);
        win(worker);
        build(worker);
    }


    /**
     * Default rules to move the worker.
     *
     * @param worker Selected worker that will move.
     */
    @Override
    public void move(Worker worker) throws UnableToMoveException {

        WorkerMoveMap moveMap = updateMoveMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();

        int initialLevel = worker.getPosition().getLevel();

        while (true) {
            int[] movePosition = getGodController().getInputMove();
            int xMove = movePosition[0] + worker.getPosition().getX();
            int yMove = movePosition[1] + worker.getPosition().getY();

            if (moveMap.isAllowedToMoveBoard(xMove, yMove) && !movingUp
                    && board.findCell(xMove, yMove).getLevel() <= initialLevel) {
                worker.setPosition(xMove, yMove);
                godController.displayBoard();
                return;
            }
            else if (moveMap.isAllowedToMoveBoard(xMove, yMove) && movingUp
                    && board.findCell(xMove, yMove).getLevel() > initialLevel) {
                worker.setPosition(xMove, yMove);
                godController.displayBoard();
                return;
            }
            else {
                getGodController().errorMoveScreen();
            }
        }
    }


    private boolean reachHigherLevel(Worker worker) throws UnableToMoveException {

        WorkerMoveMap moveMap = worker.getMoveMap();
        moveMap.resetMap();

        moveMap.updateCellsOutOfMap();
        moveMap.updateMoveUpRestrictions();

        moveMap.cannotStayStill();
        moveMap.cannotMoveInOccupiedCell();

        if (!moveMap.anyAvailableMovePosition())
            throw new UnableToMoveException();

        return moveMap.anyOneLevelHigherCell();
    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
