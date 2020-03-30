package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Zeus implements God{

    private Cell previousPosition;

    @Override
    public void evolveTurn(Worker worker) {
        previousPosition = worker.getPosition();
        move(worker);
        build(worker);
        if (previousPosition.equals(worker.getPosition()) && worker.getLevelVariation()==1)
            System.out.println("You reached an higher level! You are still not the winner!\n");
        else{
            win(worker);
        }
    }



}
