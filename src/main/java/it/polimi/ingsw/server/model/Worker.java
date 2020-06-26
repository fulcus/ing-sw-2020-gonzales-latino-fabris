package it.polimi.ingsw.server.model;


/**
 * The worker owned by a player.
 * Actions of the player in the game will be referred to the selected worker in each turn.
 */
public class Worker {

    private final Player player;
    private final Sex sex;
    private Cell position;
    private final WorkerMoveMap moveMap;
    private final WorkerBuildMap buildMap;
    private int level;
    private int levelVariation; //level before moving - level after moving


    /**
     * Creates a worker.
     *
     * @param player The worker's owner.
     * @param sex    A worker can be Male or Female.
     */
    public Worker(Player player, Sex sex) {

        this.player = player;
        this.sex = sex;
        level = 0;
        levelVariation = 0;
        moveMap = new WorkerMoveMap(this);
        buildMap = new WorkerBuildMap(this);
    }


    /**
     * Changes position of the worker and updates level and movedUp.
     *
     * @param x Coordinate of the new position of the worker.
     * @param y Coordinate of the new position of the worker.
     */
    public void setPosition(int x, int y) {
        Cell newPosition = player.getGame().getBoard().findCell(x, y);
        int newLevel = newPosition.getLevel();

        //moveOut of previous cell and moveIn new one
        if (position != null) {
            position.moveOut();
        }

        levelVariation = newLevel - level;
        level = newLevel;
        position = newPosition;

        newPosition.moveIn(this);

        System.out.println("level: " + level);
        System.out.println("levelVariation: " + levelVariation);
    }


    /**
     * Changes position of the worker and updates level and movedUp.
     *
     * @param newPosition Cell the worker is moving into.
     */
    public void setPosition(Cell newPosition) {
        int newLevel = newPosition.getLevel();

        //moveOut of previous cell and moveIn new one
        if (position != null) {
            position.moveOut();
        }
        position = newPosition;
        newPosition.moveIn(this);

        levelVariation = newLevel - level;
        level = newLevel;

        System.out.println("level: " + level);
        System.out.println("levelVariation: " + levelVariation);
    }


    /**
     * Swaps the Worker with the other worker in the newPosition.
     * Assumes that there is a worker in the newPosition.
     * If there's no one this method should not be invoked.
     *
     * @param newPosition Cell the worker wants to move into.
     */
    public void swapPosition(Cell newPosition) {
        int newLevel = newPosition.getLevel();
        Worker workerInCell = newPosition.getWorker();

        workerInCell.setPosition(position);
        //NO moveOut of previous cell
        position = newPosition;
        newPosition.moveIn(this);

        levelVariation = newLevel - level;
        level = newLevel;
        //position = newPosition;

    }


    /**
     * The worker builds a new block in the specified cell.
     *
     * @param x Coordinate of the position to build in.
     * @param y Coordinate of the position to build in.
     */
    public void buildBlock(int x, int y) {
        Cell buildPosition = player.getGame().getBoard().findCell(x, y);
        buildPosition.buildBlock();
    }


    /**
     * The worker builds a new dome in the specified cell.
     *
     * @param x Coordinate of the position to build in.
     * @param y Coordinate of the position to build in.
     */
    public void buildDome(int x, int y) {
        Cell buildPosition = player.getGame().getBoard().findCell(x, y);
        buildPosition.buildDome();
    }

    /**
     * @return The specific player that owns this worker.
     */
    public Player getPlayer() {
        return player;
    }


    /**
     * @return The specific cell of the board of the game where the worker stands on.
     */
    public Cell getPosition() {
        return position;
    }


    /**
     * @return The level of the cell of the board where the worker stands on.
     */
    public int getLevel() {
        return level;
    }


    /**
     * @return When the worker moves, the difference between the levels of the cells is the level variation.
     */
    public int getLevelVariation() {
        return levelVariation;
    }


    public Sex getSex() {
        return sex;
    }


    public WorkerMoveMap getMoveMap() {
        return moveMap;
    }


    public WorkerBuildMap getBuildMap() {
        return buildMap;
    }


}
