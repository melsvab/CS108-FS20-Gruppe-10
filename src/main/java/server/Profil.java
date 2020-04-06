package server;

import game.Lobby;
import game.PlayerTurtle;
import game.Field;

/**
 * @author Natasha, Dennis, Melanie, Rohail
 *
 * This class is for the server and the client to know
 * about states and other specific  information concerning a client.
 */
public class Profil {

    /**
     * The Client id.
     */
    int clientID;
    /**
     * The Nickname.
     */
    public String nickname;
    /**
     * The Lobby.
     */
    public Lobby lobby;
    /**
     * The My turtle.
     */
    public PlayerTurtle myTurtle;

    /**
     * The Is in game.
     */
    public boolean isInGame;
    /**
     * The Waiting for event.
     */
    public boolean waitingForEvent;
    /**
     * The Client is online.
     */
    boolean clientIsOnline;
    /**
     * The Is spectator.
     */
    public boolean isSpectator;
    /**
     * The Ccg.
     */
    ClientChatGUI ccg;


    //constructor for the ServerThreadForClient

    /**
     * Instantiates a new Profil.
     *
     * @param clientID the client id
     */
    public Profil(int clientID) {
        this.clientID = clientID;
        this.clientIsOnline = true;
        lobby = null;
    }


    //constructor for a client

    /**
     * Instantiates a new Profil.
     */
    public Profil() {
        this.clientIsOnline = true;
        lobby = null;
        this.ccg = new ClientChatGUI();
    }

    /**
     * Goes to sleep.
     *
     * @param aUser the a user
     */
    public void goesToSleep(ServerThreadForClient aUser) {
        isInGame = false;
        clientIsOnline = false;
        if (lobby != null) {
            lobby.deletePlayer(aUser);
            aUser.sendMessage(Protocol.BACK.name());
        }
        Server.userThreads.remove(aUser);
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
                int num1 = Integer.parseInt(words[1]);
                int num2 = Integer.parseInt(words[2]);
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
            return words.length == 2;
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
                int num1 = Integer.parseInt(words[1]);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * to move the turtle one field
     *
     * @param direction the direction
     */
    public void moveTurtle(int direction) {
        this.myTurtle.turtleposition.isTaken = false;
        this.myTurtle.turtleposition.turtle = null;

        switch (direction) {

            case 0:
                this.myTurtle.turtleposition = this.myTurtle.turtleposition.up;
                break;
            case 1:
                this.myTurtle.turtleposition = this.myTurtle.turtleposition.right;
                break;
            case 2:
                this.myTurtle.turtleposition = this.myTurtle.turtleposition.down;
                break;

            case 3:
                this.myTurtle.turtleposition = this.myTurtle.turtleposition.left;
                break;

        }
        this.myTurtle.turtleposition.isTaken = true;
        this.myTurtle.turtleposition.turtle = this.myTurtle;

        if (this.myTurtle.turtleposition.hasCoin) {
            this.myTurtle.points += 2;
            this.myTurtle.turtleposition.hasCoin = false;
        }

        if (!this.myTurtle.turtleposition.steppedOn &&
                !this.myTurtle.turtleposition.isStartPosition) {
            this.myTurtle.points++;
            this.myTurtle.turtleposition.steppedOn = true;
        }

        this.lobby.writeToAll(Protocol.LOBY.name() + ":" + this.lobby.board.printBoard() +
                "\n" + this.nickname + " has " + this.myTurtle.points + " points!");
    }

}