package game;

import java.util.Random;

public class Field {
    /**
     * This class represents a filed on the board and
     * includes several attributes.
     */
    boolean isFlood, hasCoin, isQuake;
    boolean steppedOn, isTaken, isBoundary;
    /**
     * Every field has a pointer to the filed next to it.
     */
    Field up, right, down, left;

    public Field(boolean hasCoinMaybe) {
        if (hasCoinMaybe) {
            Random random = new Random();
            this.hasCoin = random.nextBoolean();
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
        this.isTaken = false;
    }

    //TESTCODE
    public static void main(String[] args) {

    }




}
