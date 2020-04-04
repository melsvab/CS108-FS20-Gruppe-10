package server;

import game.Lobby;
import game.PlayerTurtle;
import game.Field;

/**
 * @author Natasha,Dennis,Melanie,Rohail
 *  This class is for the server and the client to know about the states and about specific
 *  information about a client.
 */
public class Profil {

    /*
     *
     */

    int clientID;
    public String nickname;
    public Lobby lobby;
    public PlayerTurtle myTurtle;

    boolean isInGame;
    public boolean waitingForEvent;
    boolean clientIsOnline;
    public boolean isSpectator;
    ClientChatGUI ccg;


    //constructor for the ServerThreadForClient

    public Profil(int clientID) {
        this.clientID = clientID;
        this.clientIsOnline = true;
        lobby = null;
    }


    //constructor for client

    public Profil() {
        this.clientIsOnline = true;
        lobby = null;
        this.ccg = new ClientChatGUI();
    }

    /**
     *
     * @param aUser
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
     * @param original
     * @return boolean
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
     * an example of an input:
     *  KEYW:word:secondWord
     *  check if there are two words (or at least letters) in between ":"
     * @param original
     * @return boolean
     */

    public boolean checkForTwoWords(String original) {

        int lenghtInput = original.length();

        //check for usage of ":" and minimum input of KEYW:a:b (without checking details)
        if (lenghtInput > 7 && original.contains(":")) {
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
     * an example of an input: KEYW:name (without any further ":")
     * check if there is only one ":"
     * so if you split the string at ":", there will be two substrings)
     * why? names cannot have a ":" in them.
     * @param original
     * @return
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
     * an example of an input: KEYW:word
     * check for usage of ":" and minimum input of KEYW:a
     * @param original
     * @return
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
     * @param original
     * @return
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
     *
     * to move the turtle up
     */
    public void moveTurtleUp() {
        this.myTurtle.turtleposition.isTaken = false;
        this.myTurtle.turtleposition.turtle = null;
        this.myTurtle.turtleposition = this.myTurtle.turtleposition.up;
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

    /**
     * to move the turtle to the right
     */
    public void moveTurtleRight() {
        this.myTurtle.turtleposition.isTaken = false;
        this.myTurtle.turtleposition.turtle = null;
        this.myTurtle.turtleposition = this.myTurtle.turtleposition.right;
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

    /**
     * to move the turtle to the left left
     */
    public void moveTurtleLeft() {
        this.myTurtle.turtleposition.isTaken = false;
        this.myTurtle.turtleposition.turtle = null;
        this.myTurtle.turtleposition = this.myTurtle.turtleposition.left;
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

    /**
     * to move the turtle down
     */
    public void moveTurtleDown() {
        this.myTurtle.turtleposition.isTaken = false;
        this.myTurtle.turtleposition.turtle = null;
        this.myTurtle.turtleposition = this.myTurtle.turtleposition.down;
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