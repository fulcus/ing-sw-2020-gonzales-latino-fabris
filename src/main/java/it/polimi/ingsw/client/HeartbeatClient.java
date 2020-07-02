package it.polimi.ingsw.client;

import it.polimi.ingsw.serializable.Message;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Allows the client to send a ping message to the server, to let him know that the client is still alive.
 * The Ping message is sent at fixed rate.
 */
public class HeartbeatClient extends TimerTask implements Runnable {

    private final NetworkHandler networkHandler;
    private final Message pingMessage;


    public HeartbeatClient(NetworkHandler clientNetworkHandler) {

        networkHandler = clientNetworkHandler;
        pingMessage = new Message("PING");

    }


    /**
     * Until the connection is alive, sends the ping message to the server at fixed rate.
     * Otherwise stops when connection is shut down.
     */
    @Override
    public void run() {

        InputReader inputReader = networkHandler.getInputReader();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::sendPing, 0, 10, TimeUnit.SECONDS);

        boolean stop = false;

        while (!stop) {
            if (!inputReader.isConnected()) {
                scheduledExecutorService.shutdownNow();
                stop = true;
            }
        }
    }


    /**
     * Sends to the server teh ping message.
     */
    public void sendPing() {

        InputReader inputReader = networkHandler.getInputReader();

        if(inputReader.isConnected()) {
            try {
                networkHandler.handleClientResponse(pingMessage);
            } catch (IOException e) {
                System.out.println("Server unreachable");
                //CAUGHT WHEN TRYING TO PING WHILE THE SOCKED HAS BEEN ALREADY CLOSED BY SHUTDOWN
            }
        }
    }


}
