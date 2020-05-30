/*package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class InputReader implements Runnable {

    private volatile boolean connected;
    private final Socket server;
    private ObjectOutputStream outputStm;
    private ObjectInputStream inputStm;
    private final NetworkHandler networkHandler;

    public InputReader(Socket server, NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
        this.server = server;
        connected = true;
    }


    protected void init() {

        try {
            outputStm = new ObjectOutputStream(server.getOutputStream());
            inputStm = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException e) {
            System.out.println("protocol violation");
        }

    }


    @Override
    public void run() {

        while (connected) {

            try {

                Object readObject = inputStm.readObject();

                Message readMessage = (Message) readObject;

                if (readMessage.getMethod().toUpperCase().equals("shutdownClient")) {
                    //ShutdownClient
                } else {

                   // queue.add(readMessage);
                }


            } catch (IOException e) {

                connected = false;

                client.setInGame(false);

                client.getGameController().handleGameDisconnection();

                if (client.getPlayer() != null)
                    System.out.println(client.getPlayer().getNickname() + " disconnected");


                //TODO VERIFICARE SE RIMUOVERE LA SOUT PERCHè IN INIZIALIZZAZIONE DEL GIOCO, NULL POINTER PERCHè NICK ANCORA NON ESISTE.


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
*/