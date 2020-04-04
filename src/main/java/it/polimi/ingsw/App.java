package it.polimi.ingsw;

import java.util.Random;
import java.util.Scanner;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {


        int[][] bigMatrix = new int[6][6];
        Random rand = new Random();

        for (int iEx = 0; iEx < 6; iEx++) {
            for (int jEx = 0; jEx < 6; jEx++) {
                bigMatrix[iEx][jEx] = rand.nextInt(99);
            }
        }

        printMatrix(bigMatrix);

        Scanner in = new Scanner(System.in);

        //in the bigMatrix
        System.out.println("input workers coordinates");
        int workersX = in.nextInt();
        int workersY = in.nextInt();
        System.out.println("input enemy coordinates");
        int enemX = in.nextInt();
        int enemY = in.nextInt();

        int i = 2*enemX - workersX;
        int j = 2*enemY - workersY;

        System.out.println("worker in (" + workersX + "," + workersY + "): " + bigMatrix[workersX][workersY]);
        System.out.println("in (" + i + "," + j + ") found " + bigMatrix[i][j]);
    }

    public static void printMatrix(int[][] matrix) {
        for (int iEx = 0; iEx < 6; iEx++) {
            for (int jEx = 0; jEx < 6; jEx++) {
                System.out.format("%d ", matrix[iEx][jEx]);
            }
            System.out.println("");
        }

    }
}
