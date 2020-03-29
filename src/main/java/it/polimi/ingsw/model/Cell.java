package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class represents one map's cell structure
 */

public class Cell {

    private Map map;
    private boolean dome;
    private int level;
    private ArrayList<Worker> workers;
    private int x;
    private int y;
    private final boolean perimetral;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.level = 0;
        this.workers = null;
        this.dome = false;
        if (x == 0 || x == (map.getBorderLength()-1) || y == 0 || y == (map.getBorderLength()-1))
            perimetral = true;
        else
            perimetral = false;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean getDome() {
        return dome;
    }

    public void setDome(boolean dome) {
        this.dome = dome;
    }

    /**
     * Tells if in this cell there's a worker or not
     * @return  true if the cell contains a worker
     */
    public boolean hasWorker() {
        if (workers.size()>0)
            return true;
        return false;
    }

    /**
     * States if the cell stays on the border of the map
     * @return  true if the cell is on the border of the map
     */
    public boolean isPerimetral() {
        return perimetral;
    }

}
