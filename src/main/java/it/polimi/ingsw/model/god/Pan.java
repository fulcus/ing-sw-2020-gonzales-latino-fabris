package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Worker;

public class Pan implements God{

    @Override
    public boolean win(Worker worker){
        return worker.getLevel() == 3 && worker.getLevelVariation() == 1 || worker.getLevelVariation() <= -2;
    }

}
