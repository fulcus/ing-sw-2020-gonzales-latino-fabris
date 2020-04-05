package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.model.*;

public class Charon implements God{


    private static String name = "CHARON";


    @Override
    public void evolveTurn(Worker worker) {
        swapOther(worker);
        move(worker);
        win(worker);
        build(worker);
    }

    public void swapOther(Worker worker) {
        int i, j;
        int deltaX, deltaY;

        for(i=worker.getPosition().getX()-1; i<worker.getPosition().getX()+2; i++){
            for (j=worker.getPosition().getY()-1; j<worker.getPosition().getY()+2; j++){
                if (i>=0 && i<5 && j>=0 && j<5) {
                    // se la cella che sto analizzando ha un worker che non è dello stesso giocatore che ha caronte
                    if(worker.getPlayer().getGame().getMap().findCell(i,j).hasWorker() && !worker.getPlayer().getGame().getMap().findCell(i,j).getWorker().getPlayer().equals(worker.getPlayer())) {
                        // se la cella opposta ad essa rispetto alla posizione del mio worker non è occupata
                        // e non ha la dome costruita sopra allora posso decidere di spostare il worker avversario
                        deltaX = worker.getPosition().getX() - i;
                        deltaY = worker.getPosition().getY() - j;
                        if (!worker.getPlayer().getGame().getMap().findCell(worker.getPosition().getX()-deltaX, worker.getPosition().getY()-deltaY).hasWorker() && !worker.getPlayer().getGame().getMap().findCell(worker.getPosition().getX()-deltaX, worker.getPosition().getY()-deltaY).hasDome()) {
                            //chiedo al player se vuole applicare l'effetto di caronte per spostare il worker avversario
                            if (askApplyCharon().equals("y") || askApplyCharon().equals("Y")) {
                                worker.getPlayer().getGame().getMap().findCell(i, j).getWorker().setPosition(worker.getPosition().getX() - deltaX, worker.getPosition().getY() - deltaY);
                                return;
                            }
                            else
                                System.out.println("You decided to not move the opponent's worker\n");
                        }
                    }

                }
            }
        }
    }

    public String askApplyCharon(){
        //chiamare il metodo della view che farà la richiesta per poter spostare o meno il worker avversario
        return "Y"; // il valore di ritorno è la stringa data dal metodo della view
    }


    public String getName(){
        return name;
    }

}
