package it.polimi.ingsw.client;

import it.polimi.ingsw.server.model.Worker;

import java.io.IOException;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.TurnHandler;
import it.polimi.ingsw.server.controller.god.God;
import it.polimi.ingsw.server.model.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class VittoClientView{

    private final Socket socket;   //a virtual view instance for each client
    private Player player;
    private final GameController gameController;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private TurnHandler turnHandler;
    private boolean inGame;

    public VittoClientView(Socket socket, GameController gameController){
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

    public void run() {
        while (true) {

            try {

                //request è una intefaccia che viene implementata da tutti gli oggetti serializable
                //Macro invece sarà una classe che gestisce tutte le possibili risposte per il server
                question = ((Question) input.readObject()).handle(this);
                if(question != null){
                    //prendo input dalla cli dopo avergli stampato la domanda
                    //faccio quindi una cosa del tipo cli.answerToQuestion(question)
                    //e la mando in output con la writeObject
                    output.writeObject(cli.answerToQuestion(question));
                    System.out.println("Sending response to: "+ output.toString());
                    output.reset();
                    notifyAll();
                }

            }

            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try{
            input.close();
            output.close();
            socket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public Answer handle(Request AskGodFromChallenger()) {
        return AnsweGodFromChallenger();
    }

}
