package it.polimi.ingsw.server;

import it.polimi.ingsw.serializable.Message;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Sends ping -- here called pong as opposed to ping from client to server) --
 * to client in order to let a disconnected client know that he is no longer connected.
 */
public class HeartbeatServer implements Runnable {

    private final Message pongMessage;
    private final VirtualView client;


    public HeartbeatServer(VirtualView client) {
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
            client.sendMessage(pongMessage);
        }
    }
}

