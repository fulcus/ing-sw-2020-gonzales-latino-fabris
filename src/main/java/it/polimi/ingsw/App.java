package it.polimi.ingsw;

import it.polimi.ingsw.model.*;

import java.util.Random;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        getAbsolutePositionExample(1,0);
    }


    public static void getAbsolutePositionExample(int i, int j) {
        int[][] bigMatrix = new int[6][6];
        Random rand = new Random();

        for(int iEx = 0; iEx < 6; iEx++) {
            for(int jEx = 0; jEx < 6; jEx++) {
                bigMatrix[iEx][jEx] = rand.nextInt(99);
            }
        }

        printMatrix(bigMatrix);

        //in the bigMatrix
        int workersX = 3;
        int workersY = 2;
        System.out.println("center (3,2): " + bigMatrix[3][2]);
        System.out.println("in ("+i+","+j+") found " + bigMatrix[workersX - 1 + i][workersY - 1 + j]);
    }

    public static void printMatrix(int[][] matrix) {
        for(int iEx = 0; iEx < 6; iEx++) {
            for(int jEx = 0; jEx < 6; jEx++) {
                System.out.format("%d ",matrix[iEx][jEx]);
            }
            System.out.println("");
        }

    }
}
