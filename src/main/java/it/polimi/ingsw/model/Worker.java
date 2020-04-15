package it.polimi.ingsw.model;

/**
 * The worker of a player.
 */
public class Worker {


    private final Player player;
    private final Sex sex;
    private Cell position;
    private final WorkerMoveMap moveMap;
    private final WorkerBuildMap buildMap;
    private int level;
    private int levelVariation; //level before moving - level after moving


    /** Creates a worker.
     * @param player The worker's owner.
     * @param sex A worker can be Male or Female.
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
     * @param x Coordinate of the new position of the worker.
     * @param y Coordinate of the new position of the worker.
     */
    public void setPosition(int x, int y) {
        Cell newPosition = player.getGame().getBoard().findCell(x,y);
        int newLevel = newPosition.getLevel();

        //moveOut of previous cell and moveIn new one
        if(position != null) {
            position.moveOut();
        }
        newPosition.moveIn(this);

        levelVariation = newLevel - level;
        level = newLevel;
        position = newPosition;
    }

    /**
     * Changes position of the worker and updates level and movedUp.
     * @param newPosition Cell the worker is moving into.
     */
    public void setPosition(Cell newPosition) {
        int newLevel = newPosition.getLevel();

        //moveOut of previous cell and moveIn new one
        if(position != null) {
            position.moveOut();
        }
        newPosition.moveIn(this);

        levelVariation = newLevel - level;
        level = newLevel;
        position = newPosition;
    }


    /**
     * Swaps the Worker with the other worker in the newPosition.
     * Assumes that there is a worker in the newPosition.
     * @param newPosition Cell the worker wants to move into.
     */
    //...else NullPointer!!!
    public void swapPosition(Cell newPosition) {
        int newLevel = newPosition.getLevel();
        Worker workerInCell = newPosition.getWorker();

        workerInCell.setPosition(position);
        //NO moveOut of previous cell
        newPosition.moveIn(this);

        levelVariation = newLevel - level;
        level = newLevel;
        position = newPosition;

    }

    /**
     * Builds a new block in the specified cell.
     * @param x Coordinate of the position to build in.
     * @param y Coordinate of the position to build in.
     */
    public void buildBlock(int x, int y) {
        Cell buildPosition = player.getGame().getBoard().findCell(x,y);
        buildPosition.buildBlock();
    }

    /**
     * Builds a new dome in the specified cell.
     * @param x Coordinate of the position to build in.
     * @param y Coordinate of the position to build in.
     */
    public void buildDome(int x, int y) {
        Cell buildPosition = player.getGame().getBoard().findCell(x,y);
        buildPosition.buildDome();
    }

    public Player getPlayer() {
        return player;
    }

    public Cell getPosition() {
        return position;
    }

    public int getLevel() {
        return level;
    }

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
