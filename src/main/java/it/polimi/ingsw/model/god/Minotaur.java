package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;
import java.util.Scanner;

public class Minotaur implements God{

    public void evolveTurn(Worker w) {
        lose(w);
        move(w);
        win(w);
        build(w);
    }

    public void move(Worker w, Map m) {
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();
        int y = input.nextInt();
        if (possibleMove(w,m)[0][0] && w.getPlayer().getGame().getMap().findCell(x,y).hasWorker() && ()

    }

    public void build(Worker w) {
    }

    public boolean win(Worker w){
    }

    public boolean lose(Worker w) {
    }

    public boolean[][] possibleMove(Worker w, Map m) {
        boolean[][] matrix = new boolean[][];
        for(int i=w.getPosition().getX() - 1; i<=w.getPosition().getX()+1; i++ ){
            for(int j=w.getPosition().getY() -1; j<=w.getPosition().getY() +1; j++){
                // se è fuori dal perimetro, se il salto di livello è troppo alto
                // o se nella cella c'è una dome
                // allora non posso spostarmi in quella cella
                if ( (i<0 || i>4 || j<0 || j>4) || (m.findCell(i,j).getLevel() - w.getPosition().getLevel() > 1) || m.findCell(i,j).getDome()) )
                    matrix[i][j] = false;
                else
                    matrix[i][j] = true;
            }
        }
        return matrix;
    }
}
