package game;

import java.awt.*;

/**
 * The type Player turtle.
 *
 * @author Dennis
 * This class represents the turtle, which a client can move over the field It has a
 * color, a nickname, a position, points and a boolean if it is hit by an event.
 */
public class PlayerTurtle {

    public Color color;
    /**
     * The Turtlename.
     */
    public String turtlename;
    /**
     * The Turtleposition.
     */
    public Field turtleposition;
    /**
     * The Points.
     */
    public int points;
    /**
     * The Was hit by event.
     */
    public boolean wasHitByEvent;
    /**
     * The Direction.
     */
    public int direction;


    /**
     * The Num.
     */
// is used at ClientReaderThread
    public int num;
    /**
     * The X pos.
     */
    public int xPos;
    /**
     * The Y pos.
     */
    public int yPos;

    /**
     * Instantiates a new Player turtle.
     *
     * @param turtlename the nickname which is given to the turtle.
     */
    public PlayerTurtle(String turtlename) {
        this.turtlename = turtlename;
        this.direction = 0;
    }


    /**
     * Instantiates a new Player turtle.
     *
     * @param number the number
     * @param name   the name
     * @param xPos   the x pos
     * @param yPos   the y pos
     */
    public PlayerTurtle(int number, String name, int xPos, int yPos) {

        num = number;
        this.turtlename = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = 0;

    }


}
