package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLIView;
import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.Message;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;


/**
 * Client wants to play Santorini establishes a connection to the server.
 */
public class Client implements Runnable, ServerObserver {

    private CLIView clientCLIView;
    private Scanner scanner;
    private boolean myTurn;

    public static void main(String[] args) {

        Client client = new Client();
        client.run();

    }


    @Override
    public void run() {

        scanner = new Scanner(System.in);


        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        //open a connection to the server
        Socket server;
        try {
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");

        setUpView();

        NetworkHandler networkHandler = new NetworkHandler(server, this);
        Thread networkHandlerThread = new Thread(networkHandler);

        networkHandlerThread.start();


        OutOfTurn type = new OutOfTurn();
        Timer timer = new Timer();
        type.run();

        
        while (true) {

            while (!myTurn) {

                timer.scheduleAtFixedRate(type, 0, 1);
                if (type.getClientWrites()!=null) {
                    //System.out.println("It's not your turn! Wait for other player(s)...\n");
                    type.setClientWritesNull();
                }

            }

        }


    }


    /**
     * Allows to choose the type of view: the player can choose between cli and view.
     */
    public void setUpView() {

        String selectedView;

        while (true) {

            System.out.println("Choose your view mode: CLI or GUI? Type it here: ");

            selectedView = scanner.nextLine();

            if (selectedView.toUpperCase().equals("CLI")) {
                clientCLIView = new CLIView();
                return;
            }
            /*if (viewMode.toUpperCase().equals("GUI"))
               create gui
               return;
             */
            System.out.println("Wrong input.\n\n");
        }
    }

    @Override
    public Object update(Message receivedMessage) {
        return callMethod(receivedMessage);
    }


    public Object setMyTurn(Message turnStatus) {

        if (turnStatus.getMethod().equals("startYourTurn")) {
            myTurn = true;

            update(turnStatus);
        }

        if (turnStatus.getMethod().equals("endTurn")) {
            myTurn = false;

            update(turnStatus);
        }


        return null;
    }


    private Object callMethod(Message receivedMessage) {

        Method method;
        int messageType = receivedMessage.getMessageType();

        switch (messageType) {

            case Message.NO_PARAMETERS: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod());

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(clientCLIView);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}
                //If there is no such method in clientCLIView
                catch (NoSuchMethodException e) {
                }
            }

            case Message.STRING: {

                //Trying to find the method in ClientCLIView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), String.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(clientCLIView, receivedMessage.getStringParam());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}

                //If there is no such method in clientCLIView
                catch (NoSuchMethodException e) {
                }

            }

            case Message.STRING_ARRAYLIST: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), ArrayList.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(clientCLIView, receivedMessage.getStringListParam());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}
                //If there is no such method in clientCLIView
                catch (NoSuchMethodException e) {
                }

            }


            case Message.TWO_INT: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), int.class, int.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(clientCLIView, receivedMessage.getIntParam1(), receivedMessage.getIntParam2());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }


                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}

                //If there is no such method in clientCliView
                catch (NoSuchMethodException e) {
                }
            }


            case Message.CELL_CLIENT: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), CellClient.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(clientCLIView, receivedMessage.getToUpdateCell());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }


                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}

                //If there is no such method in clientCliView
                catch (NoSuchMethodException e) {
                }
            }

            case Message.WORKER_CLIENT_ARRAYLIST_WORKER_CLIENT: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), ArrayList.class, WorkerClient.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(clientCLIView, receivedMessage.getWorkersParam(), receivedMessage.getWorker());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}

                //If there is no such method in clientCliView
                catch (NoSuchMethodException e) {
                }
            }

            default:
                return null;
        }


    }

}
