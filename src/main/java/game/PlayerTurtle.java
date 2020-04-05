package game;

import java.awt.*;

/**
 * @author Dennis
 */
public class PlayerTurtle {

    /**
     * The Color.
     */
    Color color;
    /**
     * The Turtlename.
     */
    public String turtlename;
    /**
     * The Points.
     */
    public int points;
    /**
     * The Turtleposition.
     */
    public Field turtleposition;
    /**
     * The Was hit by event.
     */
    public boolean wasHitByEvent;

    /**
     * Instantiates a new Player turtle.
     *
     * @param turtlename the turtlename
     */
    public PlayerTurtle(String turtlename) {
        //this.color = color;
        this.turtlename = turtlename;

    }


}
