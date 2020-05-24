package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.Cli;
import it.polimi.ingsw.client.gui.Gui;
import it.polimi.ingsw.client.gui.GuiManager;
import it.polimi.ingsw.serializableObjects.CellClient;
import it.polimi.ingsw.serializableObjects.Message;
import it.polimi.ingsw.serializableObjects.WorkerClient;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Client wants to play Santorini establishes a connection to the server.
 */
public class Client {

    private View view;
    private Socket server;

    public Client() {
        server = null;
        view = null;
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.setView();
        client.setUpConnection();

    }


    public void setUpConnection() {

        boolean connected = false;

        while(!connected) {
            String IP = view.getServerAddress();

            connected = true;
            //open connection with the server
            try {
                server = new Socket(IP, Server.SOCKET_PORT);

                if(server == null)
                    connected = false;

                System.out.println("server " + server);

            } catch (IOException e) {
                connected = false;
                System.out.println("catch");
            }
            System.out.println("out of try catch");
            view.connectionOutcome(connected);
        }

        NetworkHandler networkHandler = new NetworkHandler(server, this);
        Thread networkHandlerThread = new Thread(networkHandler);
        networkHandlerThread.start();

        Heartbeat heartBeat = new Heartbeat(networkHandler);
        Thread heartBeatThread = new Thread(heartBeat);
        heartBeatThread.start();

    }


    /**
     * Allows to choose the type of view: the player can choose between cli and view.
     */
    public void setView() {

        String selectedView;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Choose your view mode: CLI or GUI? Type it here: ");

            selectedView = scanner.nextLine();

            if (selectedView.toUpperCase().equals("CLI")) {
                view = new Cli();
                break;
            } else if (selectedView.toUpperCase().equals("GUI")) {
                new Thread(Gui::main).start();
                view = new GuiManager();
                break;
            }
            else
                System.out.println("Invalid input.\n\n");
        }
    }

    private Object callMethod(Message receivedMessage) {

        Method method;
        int messageType = receivedMessage.getMessageType();

        switch (messageType) {

            case Message.NO_PARAMETERS: {

                //Trying to find the method in ClientCliView
                try {

                    method = view.getClass().getMethod(receivedMessage.getMethod());

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(view);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}
                //If there is no such method in clientCli
                catch (NoSuchMethodException e) {
                }
            }

            case Message.STRING: {

                //Trying to find the method in ClientCLIView
                try {

                    method = view.getClass().getMethod(receivedMessage.getMethod(), String.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(view, receivedMessage.getStringParam());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}

                //If there is no such method in clientCli
                catch (NoSuchMethodException e) {
                }

            }

            case Message.STRING_ARRAYLIST: {

                //Trying to find the method in ClientCliView
                try {

                    method = view.getClass().getMethod(receivedMessage.getMethod(), ArrayList.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(view, receivedMessage.getStringListParam());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}
                //If there is no such method in clientCli
                catch (NoSuchMethodException e) {
                }

            }


            case Message.TWO_INT: {

                //Trying to find the method in ClientCliView
                try {

                    method = view.getClass().getMethod(receivedMessage.getMethod(), int.class, int.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(view, receivedMessage.getIntParam1(), receivedMessage.getIntParam2());
                    } catch (IllegalAccessException | InvocationTargetException e) {
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

                    method = view.getClass().getMethod(receivedMessage.getMethod(), CellClient.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(view, receivedMessage.getToUpdateCell());
                    } catch (IllegalAccessException | InvocationTargetException e) {
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

                    method = view.getClass().getMethod(receivedMessage.getMethod(), ArrayList.class, WorkerClient.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(view, receivedMessage.getWorkersParam(), receivedMessage.getWorker());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}

                //If there is no such method in clientCliView
                catch (NoSuchMethodException e) {
                }
            }

            case Message.TWO_STRING: {

                //Trying to find the method in ClientCLIView
                try {

                    method = view.getClass().getMethod(receivedMessage.getMethod(), String.class, String.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(view, receivedMessage.getStringParam(), receivedMessage.getStringParam2());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                } catch (SecurityException e) { /*PRIVATE EXCEPTION to complete*/}

                //If there is no such method in clientCli
                catch (NoSuchMethodException e) {
                }

            }

            case Message.ONE_INT: {

                //Trying to find the method in ClientCliView
                try {

                    method = view.getClass().getMethod(receivedMessage.getMethod(), int.class);

                    //Invoke method in ClientCliView
                    try {
                        return method.invoke(view, receivedMessage.getIntParam1());
                    } catch (IllegalAccessException | InvocationTargetException e) {
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


    public Object update(Message receivedMessage) {
        return callMethod(receivedMessage);
    }

}
