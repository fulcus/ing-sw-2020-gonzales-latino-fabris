package it.polimi.ingsw.model;

import java.util.Random;

/**
 * Matrix that represents the adjacent positions the player can move to.
 */
public class MoveMatrix {
    private Worker worker;
    private boolean[][] matrix;
    private static final int N = 3;

    public MoveMatrix(Worker worker) {
        this.worker = worker;
        matrix = new boolean[N][N];

        //initialized standard matrix
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                matrix[i][j] = true;
            }
        }
    }

    public void cannotMoveInDomeCell() {
        Map map = worker.getPlayer().getGame().getMap();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(getAbsolutePosition(i,j).hasDome())
                    matrix[i][j] = false;
            }
        }
    }

    public void cannotMoveInWorkerCell() {
        Map map = worker.getPlayer().getGame().getMap();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(getAbsolutePosition(i,j).hasWorker())
                    matrix[i][j] = false;
            }
        }
    }

    public void cannotMoveInOccupiedCell() {
        Map map = worker.getPlayer().getGame().getMap();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(getAbsolutePosition(i,j).isOccupied())
                    matrix[i][j] = false;
            }
        }
    }

    public void cannotMoveInFriendlyWorkerCell() {
        Map map = worker.getPlayer().getGame().getMap();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(getAbsolutePosition(i,j).hasWorker() &&
                        getAbsolutePosition(i,j).getWorker().getPlayer() == worker.getPlayer())
                    matrix[i][j] = false;
            }
        }
    }


    public void cannotStayStill() {
        matrix[1][1] = false;
    }

    public void allowedToMoveInPosition(int i, int j) {
        matrix[i][j] = true;
    }

    public void notAllowedToMoveInPosition(int i, int j) {
        matrix[i][j] = false;
    }

    public boolean getAllowedToMove(int i, int j) {
        return matrix[i][j];
    }


    public Cell getAbsolutePosition(int i, int j) {
        int workersX = worker.getPosition().getX();
        int workersY = worker.getPosition().getY();
        return worker.getPlayer().getGame().getMap().findCell(workersX - 1 + i,workersY - 1 + j);
    }

    public Worker getWorker() {
        return worker;
    }
}
