package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerMoveMap;


public class Triton implements God{

    private GameController gameController;
    private Cell initialPosition;

    public Triton(GameController gameController) {
        this.gameController = gameController;
    }

    public void evolveTurn(Worker worker) {
        initialPosition = worker.getPosition();
        move(worker);
        win(worker);
        while(worker.getPosition().isInPerimeter()) {
            initialPosition = worker.getPosition();
            moveAgain(worker);
            win(worker);
        }
        win(worker);
        build(worker);
    }


    private void moveAgain(Worker worker) {

        while (true) {
            int[] secondMovePosition = getInputMoveAgain();
            if (secondMovePosition == null)
                return;

            WorkerMoveMap workersMoveMap = updateMoveMap(worker);

            int xMove = secondMovePosition[0] + worker.getPosition().getX();
            int yMove = secondMovePosition[1] + worker.getPosition().getY();

            Cell secondMoveCell = worker.getPlayer().getGame().getBoard().findCell(xMove, yMove);

            if (secondMoveCell != initialPosition) {
                worker.setPosition(xMove, yMove);
            } else {
                gameController.errorScreen();
            }
        }
    }


    public int[] getInputMoveAgain(){
        String answer = gameController.getView().askMoveAgain();
        int[] input = new int[2];
        if (answer.equals("Y")) {

            //TODO vedere se questo codice ripetuto si può mettere come metodo di default dell'interfaccia God

            String playerInput = gameController.getView().askMovementDirection();
            switch (playerInput) {
                case "N" : {
                    input[0] = -1;
                    input[1] = 0;
                    break;
                }
                case "NE" : {
                    input[0] = -1;
                    input[1] = -1;
                    break;
                }
                case "NW" : {
                    input[0] = -1;
                    input[1] = 1;
                    break;
                }
                case "S" : {
                    input[0] = 1;
                    input[1] = 0;
                    break;
                }
                case "SE" : {
                    input[0] = 1;
                    input[1] = 1;
                    break;
                }
                case "SW" : {
                    input[0] = 1;
                    input[1] = -1;
                    break;
                }
                case "W" : {
                    input[0] = 0;
                    input[1] = -1;
                    break;
                }
                case "E" : {
                    input[0] = 0;
                    input[1] = 1;
                    break;
                }
                default : {
                    input[0] = 0;
                    input[1] = 0;
                    break;
                }
            }
        }
        else
            input = null;

        return input;
    }
}

