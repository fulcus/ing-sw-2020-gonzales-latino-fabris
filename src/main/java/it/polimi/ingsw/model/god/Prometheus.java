package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Prometheus implements God{

    public void evolveTurn(Worker w, Map m) {
        boolean canBuildBeforeMove=true;
        for(int i=-1; i<2; i++){
            for(int j=-1; j<2; j++){
                //se la mossa è possibile in quella cella
                // e se la mossa comporta salire di livello
                // allora non posso costruire prima di muovermi
                if (possibleMove(w,m)[i+1][j+1] && (m.findCell(w.getPosition().getX()+i, w.getPosition().getY()+j).getLevel() - w.getPosition().getLevel() ==1))
                    canBuildBeforeMove = false;
            }
        }
        if (canBuildBeforeMove)
            build(w);
        move(w);
        win(w);
        build(w);
    }

    public void move(Worker w) {
    }

    public void build(Worker w) {
    }

    public boolean win(Worker w){
    }

    public boolean loose(Worker w) {
    }

    public boolean[][] possibleMove(Worker w, Map m) {
        boolean[][] matrix = new boolean[][];
        for(int i=w.getPosition().getX() - 1; i<=w.getPosition().getX()+1; i++ ){
            for(int j=w.getPosition().getY() -1; j<=w.getPosition().getY() +1; j++){
                // se è fuori dal perimetro, se il salto di livello è troppo alto
                // o se nella cella c'è un altro worker o una dome
                // allora non posso spostarmi in quella cella
                if ( (i<0 || i>4 || j<0 || j>4) || (m.findCell(i,j).getLevel() - w.getPosition().getLevel() > 1) || (m.findCell(i,j).hasWorker() || m.findCell(i,j).getDome()) )
                    matrix[i][j] = false;
                else
                    matrix[i][j] = true;
            }
        }
        return matrix;
    }

}
