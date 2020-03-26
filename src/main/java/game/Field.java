package game;

import java.util.Random;

public class Field {
    /**
     * This class represents a filed on the board and
     * includes several attributes.
     */
    boolean isFlood, hasCoin, hasApple, isQuake;
    boolean steppedOn, isTaken;

    /**
     * Every field has a pointer to the filed next to it.
     */
    Field up, right, down, left;

    /**
     * Constructor empty for now.
     */
    public Field(boolean createRandomField) {
        if (createRandomField) {
            Random random = new Random();
            this.hasCoin = random.nextBoolean();
            this.hasApple = random.nextBoolean();
            //this.isFlood = random.nextBoolean();
            //this.isQuake = random.nextBoolean();
        }
    }

    /**
     *  Function to reset a Field to standard values.
     */
    public void resetField() {
        this.isFlood = false;
        this.isQuake = false;
        this.steppedOn = false;
        this.isTaken = false;
    }

    //TESTCODE
    public static void main(String[] args) {

    }




}
