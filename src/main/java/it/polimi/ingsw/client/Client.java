package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.Cli;
import it.polimi.ingsw.client.gui.Gui;
import it.polimi.ingsw.client.gui.GuiManager;
import it.polimi.ingsw.serializable.CellClient;
import it.polimi.ingsw.serializable.Message;
import it.polimi.ingsw.serializable.WorkerClient;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Client wants to play Santorini and tries to establish a connection to the server.
 */
public class Client {

    private View view;
    private Socket server;


    public static void main(String[] args) {
        Client client = new Client();
        client.setView();
        client.setUpConnection();
    }


    /**
     * Allows to set the connection to the game server.
     * Starts the main threads to communicate with it, keeping also track of the heartbeat.
     */
    public void setUpConnection() {

        boolean connected = false;

        while (!connected) {
            String IP = view.getServerAddress();
            connected = true;

            //open connection with the server
            try {
                server = new Socket();
                server.connect(new InetSocketAddress(IP, Server.SOCKET_PORT), 5000);

            } catch (IOException e) {
                connected = false;
            }

            view.connectionOutcome(connected);
        }


        NetworkHandler networkHandler = new NetworkHandler(server, this);
        new Thread(networkHandler).start();

        HeartbeatClient heartBeat = new HeartbeatClient(networkHandler);
        new Thread(heartBeat).start();

    }


    /**
     * Allows to choose the type of interface: the player can choose between CLI and GUI.
     */
    public void setView() {

        String selectedView;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Choose your view mode: CLI or GUI? Type it here:");
            selectedView = scanner.nextLine();

            if (selectedView.toUpperCase().equals("CLI")) {
                view = new Cli();
                break;
            } else if (selectedView.toUpperCase().equals("GUI")) {
                new Thread(Gui::main).start();
                view = new GuiManager();
                break;
            } else
                System.out.println("Invalid input.\n");
        }
    }


    /**
     * Calls the specific method on the chosen interface, so that the client can follow the flow of the game answering the server requests.
     * Messages of different nature rise different calls inside the switch-case of the method.
     *
     * @param receivedMessage The received message from the server
     * @return The answer to send to the server.
     */
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


    /**
     * Updates and calls the specific method referring to the message received from the server.
     * @param receivedMessage The message received from the server.
     * @return The answer to send to the server.
     */
    public Object update(Message receivedMessage) {
        return callMethod(receivedMessage);
    }


    /**
     * Shows that the client is disconnecting from the server.
     */
    public void disconnect() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
