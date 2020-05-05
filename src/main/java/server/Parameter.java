package server;

import game.Board;
import game.PlayerTurtle;
import game.Field;
import msc.GameMusic;


/**
 * The type Parameter.
 *
 * @author Natasha, Dennis, Melanie, Rohail
 *
 * This class is for the server and the client to know
 * about parameters and wheter they are correct or not
 */
public class Parameter {


    /**
     * The Is correct.
     */
    public boolean isCorrect = false;

    /**
     * The Number one.
     */
    public int numberOne;
    /**
     * The Number two.
     */
    public int numberTwo;
    /**
     * The Positions.
     */
    public int[][] positions;

    /**
     * The Word one.
     */
    public String wordOne;
    /**
     * The Word two.
     */
    public String wordTwo;


    /**
     * Instantiates a new Parameter.
     *
     * @param message the message given
     * @param aCase   this depends on what we want to check
     */
    public Parameter(String message, int aCase) {
        switch (aCase) {

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
                isCorrect = checkForWord(message, true);
                break;

            case 4:
                // is used for one string
                isCorrect = checkForWord(message, false);
                break;

            case 5:
                // is used for one number
                isCorrect = checkForNumber(message);
                break;

            case 6:
                // is used for the position of the turtles at the beginning of the game
                isCorrect = checkForTurtleSetUp(message);
                break;

            case 7:
                // is used for input concerning the board
                isCorrect = checkForNumbers(message);
                break;

            case 8:
                // is used for input concerning the board
                wordOne = changeToChatMessageFormat(message);
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

        if (checkForTwoWords(original)) {
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
     * An example of an input: KEYW:word
     * <p>
     * Check for usage of ":" and minimum input of "KEYW:a".
     *
     * @param original the original
     * @param name     the name
     * @return boolean boolean
     */
    public boolean checkForWord(String original, boolean name) {
        int lengthInput = original.length();
        if (lengthInput > 5 && original.contains(":")) {

            if (name && original.indexOf(':') == 4) {
                //for names
                wordOne = original.substring(5);
                //names cannot have spaces or ':'
                wordOne = wordOne.replaceAll(" ", "");
                wordOne = wordOne.replaceAll(":","");
                return wordOne.length() > 0;
            } else {
                return original.indexOf(':') == 4;
            }
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
        if (checkForWord(original, false)) {
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
     * Check for turtle set up boolean.
     *
     * @param original the original
     * @return the boolean
     */
    public boolean checkForTurtleSetUp(String original) {
        if (checkForNumber(original)) {
            // numberOne will be used for turtle number
            String[] words = original.split(":");

            try {
                // username is for the turtle
                wordOne = words[2];
                String[] pos = words[3].split("-");
                positions = new int[1][2];
                positions[0][0] = Integer.parseInt(pos[0]); // x coordinate
                positions[0][1] = Integer.parseInt(pos[1]); // y coordinate
                return true;

            } catch (Exception e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check for numbers boolean.
     *
     * @param original the original
     * @return the boolean
     */
    public boolean checkForNumbers(String original) {
        try {
            String[] words = original.split(":");
            // this is the turtle number or an indicator whether to change a boolean of a field to true or false
            numberOne = Integer.parseInt(words[1]);

            // creates an array for all following parameters
            positions = new int[words.length - 2][2];

            for (int i = 2; i < words.length; i++) {
                String[] numbers = words[i].split("-");
                positions[i - 2][0] = Integer.parseInt(numbers[0]);
                positions[i - 2][1] = Integer.parseInt(numbers[1]);
            }

            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
    }

    /**
     * This method is used to move a turtle one field up, down, left or right.
     *
     * @param board     the game board
     * @param direction the direction
     * @param turtle    the turtle that moves
     */
    public synchronized void  moveTurtle(Board board, int direction, PlayerTurtle turtle) {
        turtle.direction = direction;
        turtle.animation += 1;


        switch (direction) {

            case 0:
                //case >up<

                changeTurtlePosition(board, false, turtle.xPos, turtle.yPos, 1);
                turtle.yPos += 1;

                break;
            case 1:
                //case >right<

                changeTurtlePosition(board, true, turtle.xPos, turtle.yPos, 1);
                turtle.xPos += 1;
                break;
            case 2:
                //case >down<

                changeTurtlePosition(board, false, turtle.xPos, turtle.yPos, -1);
                turtle.yPos -= 1;
                break;

            case 3:
                //case >left<

                changeTurtlePosition(board, true, turtle.xPos, turtle.yPos, -1);
                turtle.xPos -= 1;
                break;

        }

    }

    /**
     * Change turtle position.
     *
     * @param board  the board
     * @param isX    the is x
     * @param xPos   the x pos
     * @param yPos   the y pos
     * @param change the change
     */
    public synchronized void changeTurtlePosition(Board board, boolean isX, int xPos, int yPos, int change) {
        PlayerTurtle placeholder = board.board[xPos][yPos].turtle;
        board.board[xPos][yPos].turtle = null;

        if (isX) {
            //changes at x
            int newX = xPos + change;
            board.board[newX][yPos].turtle = placeholder;

        } else {
            //changes at y
            int newY = yPos + change;
            board.board[xPos][newY].turtle = placeholder;
        }
        // change field to >steppedOn< if nobody was there before (just like it happens at the server)
        if (!board.board[xPos][yPos].steppedOn && !board.board[xPos][yPos].isStartPosition) {
            board.board[xPos][yPos].steppedOn = true;
        }
        // coin will be collected if a turtle steps on a field with coins
        if (board.board[xPos][yPos].hasCoin) {
            GameMusic x = new GameMusic();
            x.createCoinSound();
            board.board[xPos][yPos].hasCoin = false;
        }
    }


    /**
     * Change board.
     *
     * @param board the board
     * @param aCase the a case
     */
    public void changeBoard(Board board, int aCase) {
        boolean change = false;
        if (numberOne == 1) {
            change = true;
        }

        for (int[] position : positions) {
            int x = position[0];
            int y = position[1];

            switch (aCase) {
                case 0:
                    board.board[x][y].steppedOn = change;
                    break;
                case 1:
                    board.board[x][y].isFlood = change;
                    board.board[x][y].hasCoin = false;
                    break;
                case 2:
                    board.board[x][y].hasCoin = change;
                    break;
                case 3:
                    board.board[x][y].isQuake = change;
                    board.board[x][y].hasCoin = false;
                    break;
            }


        }


    }

    /**
     * Change board.
     *
     * @param original the original message
     * @return originalWithMoreLines a String that has lines so the message is displace in the right way
     */
    public String changeToChatMessageFormat(String original) {
        String originalWithMoreLines = "";
        int numberOfCharsPerLine = 25;
        int numberOfCharsAtTheMoment = 0;

        String[] words = original.split(" ");

        for(int i = 0; i < words.length; i++) {
            numberOfCharsAtTheMoment += words[i].length();

            if (words[i].contains("\n")) {
                numberOfCharsAtTheMoment = 0;
            } else if (words[i].contains("?") || words[i].contains("!")
                    || words[i].contains(".")) {
                words[i] =  words[i] + "\n";
                numberOfCharsAtTheMoment = 0;
            } else if (numberOfCharsAtTheMoment > numberOfCharsPerLine) {
                words[i] = "\n" + words[i];
                numberOfCharsAtTheMoment = words[i].length();
            }
            originalWithMoreLines += words[i] + " ";
        }
        originalWithMoreLines += "\n";

        return originalWithMoreLines;
    }


}