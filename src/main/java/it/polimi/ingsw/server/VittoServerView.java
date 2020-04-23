package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Worker;

import java.io.IOException;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.TurnHandler;
import it.polimi.ingsw.server.model.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class VittoServerView implements Runnable{

    private final Socket socket;   //a virtual view instance for each client
    private Player player;
    private final GameController gameController;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private TurnHandler turnHandler;
    private boolean inGame;

    public VittoServerView(Socket socket, GameController gameController){
        this.socket = socket;
        this.gameController = gameController;
        inGame = true;

        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        try {

            startClient();

        } catch (IOException e) {
            System.out.println("client " + socket.getInetAddress() + " connection dropped");
        }
    }


    private void startClient() throws IOException {

        turnHandler = gameController.getTurnHandler();
        gameController.addPlayer(this);

        // scelta nome
        // scelta colore
        //
        while (inGame) {
            //waits until woken up by turnFlow
            do {
                try {
                    wait();
                } catch (InterruptedException e) { }

            } while (turnHandler.getCurrentPlayer() != player);

            //se sono il challenger devo scegliere i gods


            Worker chosenWorker = turnHandler.chooseWorker();


            turnHandler.turn(chosenWorker);

        }
        socket.close();
    }


    public String getGodFromChallenger(int numOfPlayers) {
        int i=0;
        String chosenGod;

        while(i<numOfPlayers){
            output.writeObject(new AskGodFromChallenger());
            chosenGod = (String) input.readObject()).handle(this);  //deve restituire la stringa con il nome del god
        }
    }


    public void AnswerGodFromChallengerHandle(){

    }
}


