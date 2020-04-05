package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.*;

public class Zeus implements God{

    @Override
    public void evolveTurn(Worker worker) {
        Cell previousPosition = worker.getPosition();
        move(worker);
        build(worker);
        if (previousPosition.equals(worker.getPosition()) && worker.getLevelVariation()==1)
            System.out.println("You reached an higher level! You are still not the winner!\n");
        else{
            win(worker);
        }
    }

}
