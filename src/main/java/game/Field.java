package game;

import java.util.Random;

public class Field {
    /**
     * This class represents a filed on the board and
     * includes several attributes.
     */
    public boolean isFlood, hasCoin, isQuake;
    public boolean steppedOn, isTaken, isBoundary, isStartPosition; //TO DO !!

    public PlayerTurtle turtle;

    /**
     * Every field has a pointer to the filed next to it.
     */
    public Field up, right, down, left;

    public Field(int probabilityForCoin) {
        Random random = new Random();
        int x = random.nextInt(100);
        if (x <= probabilityForCoin) {
            this.hasCoin = true;
        }
    }

    /**
     *  Function to reset a Field to standard values.
     */
    public void resetField() {
        this.isFlood = false;
        this.hasCoin = false;
        this.isQuake = false;
        this.steppedOn = false;
    }

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

    //TESTCODE
    public static void main(String[] args) {

    }




}
