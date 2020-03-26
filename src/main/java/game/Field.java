package game;

public class Field {
    /**
     * This class represents a filed on the board and
     * includes several attributes.
     */
    boolean flood, coin, apple, quake;
    boolean steppedOn, taken;

    /**
     * Every field has a pointer to the filed next to it.
     */
    Field up, right, down, left;

    /**
     * Constructor empty for now.
     */
    public Field() {

    }

    /**
     *  Function to reset a Field to standard values.
     */
    public void resetField() {
        this.flood = false;
        this.quake = false;
        this.steppedOn = false;
        this.taken = false;
    }




}
