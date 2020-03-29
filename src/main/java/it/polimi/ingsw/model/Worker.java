package it.polimi.ingsw.model;


public class Worker {

    private final Player player;
    private final Sex sex;
    private Cell position;
    private int level;
    private boolean movedUp;

    /** Creates a worker.
     * @param player The worker's owner.
     * @param sex A worker can be Male or Female.
     */
    public Worker(Player player, Sex sex) {
        this.player = player;
        this.sex = sex;
        level = 0;
        movedUp = false;
    }

    /**
     * Changes position of the worker and updates level and movedUp.
     * @param x Coordinate of the new position of the worker.
     * @param y Coordinate of the new position of the worker.
     */
    //prima di chiamare fare check se posso andare (isOccupied)
    public void setPosition(int x, int y) {
        Cell newPosition = player.getGame().getMap().findCell(x,y);

        //vado via da cella precedente e Position nella nuova
        position.moveOut(this);
        newPosition.moveIn(this);

        movedUp = hasMovedUp(newPosition);
        level = newPosition.getLevel();
        position = newPosition;

    }

    /**
     * Builds a new block in the specified cell.
     * @param x Coordinate of the position to build in.
     * @param y Coordinate of the position to build in.
     */
    //prima di chiamare faccio check se posso costruire block (canBuildBlock)
    public void buildBlock(int x, int y) {
        Cell buildPosition = player.getGame().getMap().findCell(x,y);
        buildPosition.buildBlock();
    }

    /**
     * Builds a new dome in the specified cell.
     * @param x Coordinate of the position to build in.
     * @param y Coordinate of the position to build in.
     */
    //prima di chiamare faccio check se posso costruire dome (canBuildDome)
    public void buildDome(int x, int y) {
        Cell buildPosition = player.getGame().getMap().findCell(x,y);
        buildPosition.buildDome();
    }

    /**
     * Checks if new position is higher than the previous position.
     * @param newPosition Cell the worker is moving into.
     * @return True if worker is moving up, false otherwise.
     */
    private boolean hasMovedUp(Cell newPosition) {
        return newPosition.getLevel() > level;
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

    public Sex getSex() {
        return sex;
    }



}
