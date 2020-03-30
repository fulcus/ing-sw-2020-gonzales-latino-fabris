package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import java.util.Scanner;

/**
 * This class is the one that describes the Minotaur behaviour
 */
public class Minotaur implements God{

    /**
     * Says how the player that owns the Minotaur card can move his selected worker
     * @param w It is the selected worker to move
     * @param m It's the map of the current game
     */
    @Override
    public void move(Worker w, Map m) {

        int[] delta = new int[2];
        Scanner input = new Scanner(System.in);
        System.out.println("Where do you want to move? (input coordinates)");
        int x = input.nextInt();
        int y = input.nextInt();

        if(possibleMove(w,m)[x][y])
            w.setPosition(x,y);
        else{
            if((x>=0 || y<5 || y>=0 || x<5) && m.findCell(x,y).hasWorker() && !m.findCell(x,y).getDome() && !m.findCell(x,y).getWorker().getPlayer().equals(w.getPlayer())) {
                delta[0] = x - w.getPosition().getX();
                delta[1] = y - w.getPosition().getY();
                if (x+delta[0]<=4 && x+delta[0]>=0 && y+delta[1]>=0 && y+delta[1]<=4) {
                    m.findCell(x,y).getWorker().setPosition(x+delta[0], y+delta[1]);
                    w.setPosition(x,y);
                }
            }
            else{
                System.out.println("\n Your move is not allowed here, retry with new coordinates\n");
                move(w,m);
            }
        }
    }


    /**
     * It explains where I can move with the selected worker
     * @param w It's the selected worker
     * @param m It's the map of the current game
     * @return The matrix with a true in the near position where the worker can move, false if the worker cannot move there
     */
    public boolean[][] possibleMove(Worker w, Map m) {
        boolean[][] matrix = new boolean[][];
        for(int i=w.getPosition().getX() - 1; i<=w.getPosition().getX()+1; i++ ){
            for(int j=w.getPosition().getY() -1; j<=w.getPosition().getY() +1; j++){
                // se è fuori dal perimetro, se il salto di livello è troppo alto
                // o se nella cella c'è una dome o un worker
                // allora non posso spostarmi in quella cella
                if ( (i<0 || i>4 || j<0 || j>4) || (m.findCell(i,j).getLevel() - w.getPosition().getLevel() > 1) || m.findCell(i,j).getDome() || m.findCell(i,j).hasWorker())
                    matrix[i][j] = false;
                else
                    matrix[i][j] = true;
            }
        }
        return matrix;
    }
}
