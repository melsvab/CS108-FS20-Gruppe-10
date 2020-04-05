package game;

import java.util.Random;

/**
 * @author  Dennis
 * This class represents a field on the board and includes several attributes.
 */
public class Field {

    public boolean isFlood,
    hasCoin,
    isQuake;

    public boolean steppedOn,
    isTaken,
    isBoundary,
    isStartPosition; //TO DO !!

    public PlayerTurtle turtle;

    /**
     * Every field has a pointer to the field next to it.
     */
    public Field up,
    right,
    down,
    left;

    /**
     * Instantiates a new Field.
     *
     * @param probabilityForCoin the probability for coin
     */
    public Field(int probabilityForCoin) {
        Random random = new Random();
        int x = random.nextInt(100);
        if (x <= probabilityForCoin) {
            this.hasCoin = true;
        }
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
     * Copy field field.
     *
     * @param copyThis the copy this
     * @return the field
     */
    public static Field copyField(Field copyThis) {
        Field copy = new Field(-1);
        copy.isFlood = copyThis.isFlood;
        copy.hasCoin = copyThis.hasCoin;
        copy.isQuake = copyThis.isQuake;
        copy.steppedOn = copyThis.steppedOn;
        copy.isTaken = copyThis.isTaken;
        copy.isBoundary = copyThis.isBoundary;
        copy.turtle = copyThis.turtle;
        copy.up = copyThis.up;
        copy.right = copyThis.right;
        copy.down = copyThis.down;
        copy.left = copyThis.left;

        return copy;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
//TESTCODE
    public static void main(String[] args) {

    }




}
