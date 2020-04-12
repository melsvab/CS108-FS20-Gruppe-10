package server;

import game.Lobby;
import game.PlayerTurtle;
import game.Field;
import gui.MainFrame;

import java.io.IOException;

/**
 * @author Natasha, Dennis, Melanie, Rohail
 *
 * This class is for the server and the client to know
 * about states and other specific  information concerning a client.
 */
public class Parameter {


    public boolean isCorrect;

    public int numberOne;
    public int numberTwo;

    public String wordOne;
    public String wordTwo;


    //constructor for the ServerThreadForClient

    /**
     * Instantiates a new Parameter.
     *
     * @param message the message given
     * @param aCase this depends on what we want to check
     */
    public Parameter (String message, int aCase) {
        switch (aCase) {
            case 0:
                // is used for input with no specific length
                //does not exist yet!
            case 1:
                // is used for two ints
                isCorrect = checkForTwoInt(message);
                break;
            case 2:
               // is used for two strings
                isCorrect = checkForTwoWords(message);
                break;
            case 3:
               // is used for names (no ":" or " ")
                isCorrect = checkForName(message);
                break;
            case 4:
                // is used for one string
                isCorrect = checkForWord(message);
                break;
            case 5:
                // is used for one number
                isCorrect = checkForNumber(message);
                break;
            default:
                //this is impossible!
        }
    }



    /**
     * Check for two int boolean.
     *
     * @param original the original
     * @return boolean boolean
     */
    public boolean checkForTwoInt(String original) {
        //an example of an input:
        //KEYW:5:355

        if(checkForTwoWords(original)) {
            String[] words = original.split(":");
            //check if it is possible to transfer the words into numbers
            try {
                numberOne = Integer.parseInt(words[1]);
                numberTwo = Integer.parseInt(words[2]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * An example of an input: KEYW:word:secondWord
     * <p>
     * Check if there are two words (or at least letters) in between ":".
     *
     * @param original the original
     * @return boolean boolean
     */
    public boolean checkForTwoWords(String original) {

        int lengthInput = original.length();

        //check for usage of ":" and minimum input of KEYW:a:b (without checking details)
        if (lengthInput > 7 && original.contains(":")) {
            String[] words = original.split(":");

            //check if there are two words (or at least letters) in between ":"
            if (words.length > 2) {
                // word[0] is the keyword!
                wordOne = words[1];
                wordTwo = words[2];
                return words[1].length() > 0 && words[2].length() > 0;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * An example of an input: KEYW:name (without any further ":")
     * <p>
     * Check if there is only one ":". (So if you split the string at ":", there will be two
     * substrings) Why? Because names cannot have a ":" in them.
     *
     * @param original the original
     * @return boolean boolean
     */
    public boolean checkForName(String original) {

        if (checkForWord(original)) {
            String[] words = original.split(":");
            if (words.length == 2) {
                wordOne = original.substring(5);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }


    /**
     * An example of an input: KEYW:word
     * <p>
     * Check for usage of ":" and minimum input of "KEYW:a".
     *
     * @param original the original
     * @return boolean boolean
     */
    public boolean checkForWord(String original) {
        int lenghtInput = original.length();
        if (lenghtInput > 5 && original.contains(":")) {

            return original.indexOf(':') == 4;
        } else {
            return false;
        }
    }


    /**
     * Check for number boolean.
     *
     * @param original the original
     * @return boolean boolean
     */
    public boolean checkForNumber(String original) {
        if (checkForWord(original)) {
            String[] words = original.split(":");

            try {
                numberOne = Integer.parseInt(words[1]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * to move a turtle one field
     *
     * @param direction the direction
     */
    public void moveTurtle(int direction, PlayerTurtle turtle) {
        turtle.turtleposition.isTaken = false;
        turtle.turtleposition.turtle = null;

        switch (direction) {

            case 0:
                turtle.turtleposition = turtle.turtleposition.up;
                break;
            case 1:
                turtle.turtleposition = turtle.turtleposition.right;
                break;
            case 2:
                turtle.turtleposition = turtle.turtleposition.down;
                break;

            case 3:
                turtle.turtleposition = turtle.turtleposition.left;
                break;

        }
        turtle.turtleposition.isTaken = true;
        turtle.turtleposition.turtle = turtle;

        if (turtle.turtleposition.hasCoin) {
            turtle.points += 2;
            turtle.turtleposition.hasCoin = false;
        }

        if (!turtle.turtleposition.steppedOn &&
                !turtle.turtleposition.isStartPosition) {
            turtle.points++;
            turtle.turtleposition.steppedOn = true;
        }
    }

}