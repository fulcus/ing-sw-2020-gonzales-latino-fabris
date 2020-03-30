package it.polimi.ingsw.model;

/**
 * This class represents a map's cell structure
 */

public class Cell {

    private boolean dome;
    private int level;
    private Worker worker;
    private final int x;
    private final int y;
    private final boolean inPerimeter;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.level = 0;
        this.worker = null;
        this.dome = false;
        inPerimeter = x == 0 || x == (Map.SIDE - 1) || y == 0 || y == (Map.SIDE - 1);
    }

    public int getLevel() {
        return level;
    }

    /**
     * Builds 1 block more in this cell
     */
    public void buildBlock() {
        level++;
    }

    private boolean hasDome() {
        return dome;
    }

    /**
     * Build the dome in the cell
     */
    public void buildDome() {
        dome = true;
    }

    /**
     * Tells if in this cell there's a worker or not
     * @return  true if the cell contains a worker
     */
    public boolean hasWorker() {
        return worker != null;
    }

    /**
     * States that a worker is moving in the cell
     * @param w Is the worker arriving in the cell
     */
    public void moveIn(Worker w) {
        worker = w;
    }

    /**
     * States that a worker is no more in this cell
     */
    public void moveOut() {
        worker = null;
    }

    /**
     * States if the cell stays on the border of the map
     * @return  true if the cell is on the border of the map
     */
    public boolean isInPerimeter() {
        return inPerimeter;
    }

    /**
     * Checks if cell has a worker or a dome on it.
     */
    public boolean isOccupied() {
        return hasWorker() || hasDome();
    }

    public Worker getWorker() {
        return worker;
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

}
