package it.polimi.ingsw.server;

import it.polimi.ingsw.serializable.Message;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Useful to let clients know that the server is still alive, due to pong messages sent at fixed rate.
 */
public class HeartbeatServer implements Runnable {

    private final Message pongMessage;
    private final ViewClient client;


    public HeartbeatServer(ViewClient client) {
        this.client = client;
        pongMessage = new Message("PONG");
    }


    /**
     * Schedules at fixed rate, while the client is still playing the game, the task to send the pong message to the client.
     */
    @Override
    public void run() {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::sendPong, 0, 8, TimeUnit.SECONDS);

        boolean stop = false;

        while (!stop) {
            if (!client.isInGame()) {
                scheduledExecutorService.shutdownNow();
                stop = true;
            }
        }

    }


    /**
     * Sends the pong message to the client.
     */
    public void sendPong() {
        if (client.isInGame()) {
          //  System.out.println("SENDING PONG to" + client);
            client.sendMessage(pongMessage);
        }
    }
}

