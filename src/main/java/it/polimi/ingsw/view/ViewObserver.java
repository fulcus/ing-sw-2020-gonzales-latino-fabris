package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Worker;

public interface ViewObserver {

    /**
     * This method is used by the view to updates its parts depending on the parameter.
     */
    //POI LA VIEW FARE UPDATE DIVERSE SULLA BASE DEL PARAMETRO PASSATO,CIOE' SULLA BASE DELLE DIVERSE COSE CHE SONO CAMBIATE.
    public void update(Worker observedWorker);
}
