package game;

import java.util.Random;

/**
 * @author Dennis
 * This class represents a field on the board and includes several attributes.
 */
public class Field {

    public Field up, right , down , left;

    public PlayerTurtle turtle;

    public boolean isFlood,
            hasCoin,
            isQuake,
            steppedOn,
            isTaken,
            isBoundary,
            isStartPosition;

    /**
     * Instantiates a new Field with a probability of a coin.
     *  the probability for a coin on this field.
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

    public boolean coins(int probabilityForCoin) {
        Random random = new Random();
        int x = random.nextInt(100);
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
