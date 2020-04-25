package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLIView;
import it.polimi.ingsw.serializableObjects.ClientCell;
import it.polimi.ingsw.serializableObjects.Message;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Client implements Runnable, ServerObserver {

    private String response = null;
    private CLIView clientCLIView;
    private Scanner scanner;

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

        //Client waits until further information from the networkHandler
        try {
            wait();
        } catch (InterruptedException e) {
        }


    }

    public void setUpView() {

        String selectedView;

        while (true) {

            System.out.println("Choose your view mode: cli or gui? Type it here: ");

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
    public void update(Message receivedMessage) {

        callMethod(receivedMessage);
    }

    private void callMethod(Message receivedMessage) throws NoSuchMethodException {

        int typeOfMessage;
        Method method;
        String stringParam;
        int intParam1;
        int intParam2;
        ArrayList<String> stringListParam;
        ClientCell toUpdateCell;
        ArrayList<WorkerClient> workersParam;
        WorkerClient worker;

        typeOfMessage = receivedMessage.getTypeOfMessage();

        switch (typeOfMessage) {

            case 1: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod());

                    //Invoke method in ClientCliView
                    try {
                        method.invoke(clientCLIView);
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

            case 2: {

                //Trying to find the method in ClientCLIView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), String.class);

                    //Invoke method in ClientCliView
                    try {
                        method.invoke(clientCLIView, receivedMessage.getStringParam());
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

            case 3: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), ArrayList.class);

                    //Invoke method in ClientCliView
                    try {
                        method.invoke(clientCLIView, receivedMessage.getStringParam(), receivedMessage.getStringListParam());
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


            case 4: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), int.class, int.class);

                    //Invoke method in ClientCliView
                    try {
                        method.invoke(clientCLIView, receivedMessage.getIntParam1(), receivedMessage.getIntParam2());
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


            case 5: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), ClientCell.class);

                    //Invoke method in ClientCliView
                    try {
                        method.invoke(clientCLIView, receivedMessage.getToUpdateCell());
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

            case 6: {

                //Trying to find the method in ClientCliView
                try {

                    method = clientCLIView.getClass().getMethod(receivedMessage.getMethod(), ArrayList.class, WorkerClient.class);

                    //Invoke method in ClientCliView
                    try {
                        method.invoke(clientCLIView, receivedMessage.getWorkersParam(), receivedMessage.getWorker());
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


        }
    }

}
