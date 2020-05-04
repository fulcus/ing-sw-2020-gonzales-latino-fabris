package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Heartbeat extends TimerTask implements Runnable {

    private NetworkHandler networkHandler;
    private Message pingMessage;

    public Heartbeat(NetworkHandler clientNetworkHandler) {

        networkHandler = clientNetworkHandler;
        pingMessage = new Message("PING");

    }

    @Override
    public void run() {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this::sendPing, 0, 10, TimeUnit.SECONDS);

        boolean stop = false;

        while (!stop) {
            if (!networkHandler.isConnected()) {
                scheduledExecutorService.shutdownNow();
                stop = true;
            }
        }
    }

    public void sendPing() {
        try {
            networkHandler.handleClientResponse(pingMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
