package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLIMainView;
import it.polimi.ingsw.client.view.GodView;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;


public class NetworkHandler implements Runnable
{

    private enum Commands {

    }
    private Commands nextCommand;
    private String convertStringParam;

    private Socket server;
    private ObjectOutputStream outputStm;
    private ObjectInputStream inputStm;

    private Scanner input;
    private CLIMainView cliMainView;
    private GodView godView;

    private List<ServerObserver> observers = new ArrayList<>();


    public NetworkHandler(Socket server) {
        this.server = server;
        input = new Scanner(System.in);
    }

    public void init() {

        //è la parte iniziale di codice che permette di selezionare il tipo di view che si vuole: cli o gui
        String viewMode;
        while(true) {

            System.out.println("Choose your view mode: cli or gui? Type it here: ");
            viewMode = input.nextLine();

            if (viewMode.toLowerCase().equals("cli")) {
                cliMainView = new CLIMainView();
                godView = new GodView();
            }
            /*if (viewMode.toLowerCase().equals("gui"))
               istanzia gui
             */
            System.out.println("Wrong input.\n\n");
        }
    }


    public void addObserver(ServerObserver observer)
    {
        synchronized (observers) {
            observers.add(observer);
        }
    }


    public void removeObserver(ServerObserver observer)
    {
        synchronized (observers) {
            observers.remove(observer);
        }
    }


    public synchronized void stop()
    {
        nextCommand = Commands.STOP;
        notifyAll();
    }


    public synchronized void requestConversion(String input)
    {
        nextCommand = Commands.CONVERT_STRING;
        convertStringParam = input;
        notifyAll();
    }


    @Override
    public void run() {

        init();

        try {
            outputStm = new ObjectOutputStream(server.getOutputStream());
            inputStm = new ObjectInputStream(server.getInputStream());
            handleServerConnection();
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("protocol violation");
        }

        try {
            server.close();
        } catch (IOException e) { }
    }


    private synchronized void handleServerConnection() throws IOException, ClassNotFoundException
    {
        /* wait for commands */
        while (true) {
            nextCommand = null;
            try {
                wait();
            } catch (InterruptedException e) { }

            if (nextCommand == null)
                continue;

            // NEI CASE DI QUESTO SWITCH DOBBIAMO METTERE I METODI delle richieste del server
            switch (nextCommand) {
                case CONVERT_STRING:
                    doStringConversion();
                    break;

                case STOP:
                    return;
            }
        }
    }


    private synchronized void doStringConversion() throws IOException, ClassNotFoundException
    {
        /* send the string to the server and get the new string back */
        outputStm.writeObject(convertStringParam);
        String newStr = (String)inputStm.readObject();

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer: observersCpy) {
            observer.didReceiveConvertedString(convertStringParam, newStr);
        }
    }

}
