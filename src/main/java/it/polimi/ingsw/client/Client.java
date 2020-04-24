package it.polimi.ingsw.client;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.client.NetworkHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;


public class Client implements Runnable{

    private String response = null;

    public static void main(String[] args) {

        /* Instantiate a new Client which will also receive events from
         * the server by implementing the ServerObserver interface */
        Client client = new Client();
        client.run();

    }


    @Override
    public void run()
    {
        /*
         * WARNING: this method executes IN THE CONTEXT OF THE MAIN THREAD
         */

        Scanner scanner = new Scanner(System.in);

        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        /* open a connection to the server */
        Socket server;
        try {
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");

        /* Create the adapter that will allow communication with the server
         * in background, and start running its thread */
        NetworkHandler networkHandler = new NetworkHandler(server);
        networkHandler.addObserver((ServerObserver) this);

        Thread serverAdapterThread = new Thread(networkHandler);
        serverAdapterThread.start();


    }





}
