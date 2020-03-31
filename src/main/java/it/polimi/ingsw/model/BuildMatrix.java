package it.polimi.ingsw.model;

/**
 * Matrix that represents the adjacent positions the player can move to.
 */
public class BuildMatrix {
    private Worker worker;
    private boolean[][] matrix;
    private static final int N = 3;

    public BuildMatrix(Worker worker) {
        this.worker = worker;
        matrix = new boolean[N][N];

        //initialized standard matrix
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                matrix[i][j] = true;
            }
        }
        matrix[2][2] = false;

    }

    public void allowedToBuildInPosition(int i, int j) {
        matrix[i][j] = true;
    }

    public void notAllowedToBuildInPosition(int i, int j) {
        matrix[i][j] = false;
    }

    public boolean isAllowedToBuild(int i, int j) {
        return matrix[i][j];
    }

    public Worker getWorker() {
        return worker;
    }
}
