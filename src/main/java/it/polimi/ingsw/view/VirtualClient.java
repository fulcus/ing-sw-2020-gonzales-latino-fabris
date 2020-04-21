package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.god.God;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Represents the interface of each client with the server.
 */
public class VirtualClient implements Runnable {

    private Socket client;   //a virtual view instance for each client
    private Player player;

    public VirtualClient(Socket client) {
        this.client = client;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String askTypeOfView(){

    }

    /**
     * This method displays to the user Initial Game Interface
     */
    public void beginningView() {

    }


    /**
     * @return The number of players.
     */
    public int askNumberOfPlayers() {

    }



    private void santoriniAscii() {


    }




    /**
     * This method asks the player to set his worker initial position.
     *
     * @param workerSex This is the sex of the worker to be placed on the board.
     * @return Array with x,y coordinates of the chosen position.
     */
    public int[] askInitialWorkerPosition(Sex workerSex, String currentPlayerNickname) {
    }

    public void invalidInitialWorkerPosition() {
    }


    public String askPlayerNickname() {
    }

    public String askPlayerColor(String playerNickname) {

    }


    /**
     * @return The name of the chosen God.
     */
    public String askPlayerGod(String playerNickname) {


    }

    public void playerChoseInvalidGod() {

    }

    public void printChallenger(String challengerNickname) {

    }

    public String getGodFromChallenger(int n) {

    }

    public String challengerChooseStartPlayer(String challengerNickname) {


    }

    public void invalidStartPlayer() {
    }

    public void notAvailableColor(){
    }

    public void notAvailableNickname() {
    }

    public String askChosenWorker(String currentPlayerNickname) {

    }


    /**
     * Allows to print the ERROR to the screen
     */
    public void printErrorScreen() {
    }

    /**
     * Prints to screen that one of the player has won the game
     */
    public void winningView(String winnerNickname) {
    }

    public void unableToMoveLose(String loserNickname){
    }

    public void unableToBuildLose(String loserNickname){
    }



    /**
     * This method prints an updated version of the Board, depending on the Class' parameter "mymap".
     */
    public void printMap() {


    }


    @Override
    public void update(Cell toBeUpdatedCell) {

    }


    public void printAllGods(ArrayList<God> godsDeck) {


    }

    public void challengerError() {

    }


    public void printChosenGods() {

    }


    public void selectedWorkerCannotMove(String sex) {
    }

    public void selectedWorkerCannotBuild(String sex) {
    }










    /**
     * This method asks the user to insert the position where he wants to build.
     *
     * @return The compass direction of the place where to build.
     */
    public String askBuildingDirection() {

    }


    /**
     * This method asks to Atlas' owner to insert the position where he wants to build and what type of building.
     *
     * @return The compass direction of the place where to build.
     */
    public String[] askBuildingDirectionAtlas() {

    }


    /**
     * This method asks the user to insert the direction of his next movement.
     *
     * @return The compass direction of the movement.
     */
    public String askMovementDirection() {


    }


    /**
     * Allows to get the input of the player to move again.
     *
     * @return The will of the player on keeping going moving his worker on the board.
     */
    public String askMoveAgain() {

    }


    /**
     * Allows to get the input of the player to jump to an higher level.
     *
     * @return The will of the player to reach an higher level.
     */
    public String askWantToMoveUp() {

    }


    /**
     * Allows to get the input of the player to move an enemy's worker.
     *
     * @return The will of the player to move an enemy's worker
     */
    public String askWantToMoveEnemy() {

    }


    /**
     * Allows to move one worker's enemy.
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The Worker to move selected by the player.
     */
    public String askWorkerToMove(ArrayList<Worker> enemyWorkers, Worker myWorker) {

    }


    /**
     * Helps to show the positions of the neighboring workers
     *
     * @param enemyWorkers It's the list of the neighbour movable enemy workers.
     * @param myWorker     It's the chosen worker of the current player.
     * @return The position of the selected worker to move.
     */
    public ArrayList<String> printFoundEnemiesPosition(ArrayList<Worker> enemyWorkers, Worker myWorker) {

    }


    /**
     * Says that the worker can build under himself/herself.
     * This is allowed only when playing with Zeus.
     */
    public void printBuildUnderneath(){

    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHephaestus() {

    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainDemeter() {

    }


    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build again.
     */
    public String askBuildAgainHestia() {

    }

    /**
     * The name of the method describes itself.
     *
     * @return The will of the player to build before moving.
     */
    public String askBuildPrometheus() {

    }


    /**
     * Allows to get the answer of a player to a question.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    private String playerAnswerYN() {

    }


    /**
     * Points out the player cannot move in a certain position.
     */
    public void printMoveErrorScreen() {

    }


    /**
     * Asks the player if he still wants to move during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printMoveDecisionError() {

    }


    /**
     * Asks the player if he still wants to build during this turn.
     *
     * @return Y for a positive answer, N for a negative one.
     */
    public String printBuildDecisionError() {

    }


    /**
     * Points out a player is not allowed to build.
     */
    public void printBuildGeneralErrorScreen() {


    }


    /**
     * Points out a player is not allowed to build a block in a certain position.
     */
    public void printBuildBlockErrorScreen() {

    }


    /**
     * Points out that a player is not allowed to build again in a certain position.
     */
    public void printBuildInSamePositionScreen() {

    }


    @Override
    public void run()
    {
        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }
    }


    private void handleClientConnection() throws IOException
    {
        System.out.println("Connected to " + client.getInetAddress());

        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());

        try {
            while (true) {
                /* read a String from the stream and write an uppercase string in response */
                Object next = input.readObject();
                String str = (String)next;
                output.writeObject(str.toUpperCase());
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }

        client.close();
    }

}
