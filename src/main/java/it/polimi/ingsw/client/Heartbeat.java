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
        if(networkHandler.isConnected()) {
            try {
                networkHandler.handleClientResponse(pingMessage);
            } catch (IOException e) {
                System.out.println("SEND PING CATCH");
                //CAUGHT WHEN TRYING TO PING WHILE THE SOCKED HAS BEEN ALREADY CLOSED BY SHUTDOWN
                e.printStackTrace();
            }
        }
    }


}
