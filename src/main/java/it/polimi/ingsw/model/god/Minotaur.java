package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import java.util.Scanner;

/**
 * This class is the one that describes the Minotaur behaviour
 */
public class Minotaur implements God{

    /**
     * Says how the player that owns the Minotaur card can move his selected worker
     * @param worker It is the selected worker to move
     */
    @Override
    public void move(Worker worker) {

        Map map = worker.getPlayer().getGame().getMap();
        int[] delta = new int[2];
        Scanner input = new Scanner(System.in);
        System.out.println("Where do you want to move? (input coordinates)");
        int x = input.nextInt();
        int y = input.nextInt();

        if(possibleMove(worker,map)[x][y])
            worker.setPosition(x,y);
        else{
            if((x<5 && y<5) && map.findCell(x,y).hasWorker() && !map.findCell(x,y).hasDome() && !map.findCell(x,y).getWorker().getPlayer().equals(worker.getPlayer())) {
                delta[0] = x - worker.getPosition().getX();
                delta[1] = y - worker.getPosition().getY();
                if (x+delta[0]<=4 && x+delta[0]>=0 && y+delta[1]>=0 && y+delta[1]<=4) {
                    map.findCell(x,y).getWorker().setPosition(x+delta[0], y+delta[1]);
                    worker.setPosition(x,y);
                }
            }
            else{
                System.out.println("\n Your move is not allowed here, retry with new coordinates\n");
                move(worker);
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
        boolean[][] matrix = new boolean[3][3];
        for(int i=w.getPosition().getX() - 1; i<=w.getPosition().getX()+1; i++ ){
            for(int j=w.getPosition().getY() -1; j<=w.getPosition().getY() +1; j++){
                // se è fuori dal perimetro, se il salto di livello è troppo alto
                // o se nella cella c'è una dome o un worker
                // allora non posso spostarmi in quella cella
                if ( (i<0 || i>4 || j<0 || j>4) || (m.findCell(i,j).getLevel() - w.getPosition().getLevel() > 1) || m.findCell(i,j).hasDome() || m.findCell(i,j).hasWorker())
                    matrix[i][j] = false;
                else
                    matrix[i][j] = true;
            }
        }
        return matrix;
    }
}
