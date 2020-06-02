package it.polimi.ingsw.client;

import it.polimi.ingsw.serializableObjects.Message;


import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartbeatClient extends TimerTask implements Runnable {

    private final NetworkHandler networkHandler;
    private final Message pingMessage;


    public HeartbeatClient(NetworkHandler clientNetworkHandler) {

        networkHandler = clientNetworkHandler;
        pingMessage = new Message("PING");



    }

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

    public void sendPing() {

        InputReader inputReader = networkHandler.getInputReader();

        if(inputReader.isConnected()) {
            try {
                networkHandler.handleClientResponse(pingMessage);
            } catch (IOException e) {
                System.out.println("SEND PING CATCH");
                //CAUGHT WHEN TRYING TO PING WHILE THE SOCKED HAS BEEN ALREADY CLOSED BY SHUTDOWN
            }
        }
    }


}
