package it.polimi.ingsw.server;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatServer implements Runnable {

    private final Message pongMessage;
    private final ViewClient client;


    public HeartbeatServer(ViewClient client) {
        this.client = client;
        pongMessage = new Message("PONG");
    }

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


    public void sendPong() {
        if (client.isInGame()) {
          //  System.out.println("SENDING PONG to" + client);
            client.sendMessage(pongMessage);
        }
    }
}

