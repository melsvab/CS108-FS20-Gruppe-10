package game;

import java.util.Random;

/**
 * The type Field.
 *
 * @author Dennis
 * This class represents a field on the board and includes several attributes.
 */
public class Field {


    public Field up, right, down, left;

    public PlayerTurtle turtle;

    /**
     * The Is flood.
     */
    public boolean isFlood,
    /**
     * The Has coin.
     */
    hasCoin,
    /**
     * The Is quake.
     */
    isQuake,
    /**
     * The Stepped on.
     */
    steppedOn,
    /**
     * The Is taken.
     */
    isTaken,
    /**
     * The Is boundary.
     */
    isBoundary,
    /**
     * The Is start position.
     */
    isStartPosition;

    /**
     * Instantiates a new Field with a probability of a coin. the probability for a coin on this
     * field.
     *
     * @param option the option
     */
    public Field(int option) {
        // to create a board
        switch (option) {
            case 0:
                // is used for normal field
                isBoundary = false;
                isFlood = false;
                isStartPosition = false;
                break;
            case 1:
                // is used for boundaries
                isBoundary = true;
                isFlood = true;
                isStartPosition = false;

                break;
            case 2:
                // is used for start positions
                isBoundary = false;
                isFlood = false;
                isStartPosition = true;
                break;

        }


        hasCoin = false;
        isQuake = false;
        steppedOn = false;
        isTaken = false;
        turtle = null;

    }

    /**
     * Instantiates a new Field.
     */
    public Field() {
        // to copy a field
        isBoundary = false;
        isFlood = false;
        hasCoin = false;
        isQuake = false;
        steppedOn = false;
        isTaken = false;
        isStartPosition = false;
        turtle = null;

    }

    /**
     * Coins boolean.
     *
     * @param probabilityForCoin the probability for coin
     * @return the boolean
     */
    public boolean coins(int probabilityForCoin) {
        Random random = new Random();
        int x = random.nextInt(60);
        return x <= probabilityForCoin;
    }

    /**
     * Function to reset a Field to standard values.
     */
    public void resetField() {
        this.isFlood = false;
        this.hasCoin = false;
        this.isQuake = false;
        this.steppedOn = false;
    }

    /**
     * Function to copy a field.
     *
     * @param copyThis Field to copy
     * @return new field which is a copy of the field given
     */
    public static Field copyField(Field copyThis) {  //HIER WEITER MACHEN
        Field copy = new Field();
        copy.up = copyThis.up;
        copy.right = copyThis.right;
        copy.down = copyThis.down;
        copy.left = copyThis.left;

        copy.turtle = copyThis.turtle;

        copy.isFlood = copyThis.isFlood;
        copy.hasCoin = copyThis.hasCoin;
        copy.isQuake = copyThis.isQuake;
        copy.steppedOn = copyThis.steppedOn;
        copy.isTaken = copyThis.isTaken;
        copy.isBoundary = copyThis.isBoundary;
        copy.isStartPosition = copyThis.isStartPosition;

        return copy;
    }

}
