package game;

import java.awt.*;

/**
 * @author Dennis
 * This class represents the turtle, which a client can move over the field
 * It has a color, a nickname, a position, points and a boolean if it is hit by an event.
 */
public class PlayerTurtle {

    public Color color;
    public int num;
    public String turtlename;
    public Field turtleposition;
    public int points;
    public boolean wasHitByEvent;

    /**
     * Instantiates a new Player turtle.
     *
     * @param turtlename the nickname which is given to the turtle.
     */
    public PlayerTurtle(String turtlename) {
        this.turtlename = turtlename;
    }

    public PlayerTurtle(int number) {
        num = number;
    }


}
