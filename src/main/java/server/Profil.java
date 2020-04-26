package server;

import game.Lobby;
import game.PlayerTurtle;
import gui.MainFrame;

import java.io.IOException;

/**
 * The type Profil.
 *
 * @author Natasha, Dennis, Melanie, Rohail
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
    public boolean clientIsOnline;
    /**
     * The Is spectator.
     */
    public boolean isSpectator;
    /**
     * The Ccg.
     */
    public MainFrame mainFrame;


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
     * Instantiates a new profile.
     *
     * @throws IOException the io exception
     */
    public Profil() throws IOException {
        this.clientIsOnline = true;
        lobby = null;
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
     * Game is ready boolean.
     *
     * @return the boolean
     */
    public boolean gameIsReady() {
        if (isInGame && lobby != null) {
            return lobby.gamestate == 2;
        }
        return false;
    }

    /**
     * to move the turtle one field
     *
     * @param direction the direction
     */
    public synchronized void moveTurtle(int direction) {
        this.myTurtle.turtleposition.isTaken = false;
        this.myTurtle.turtleposition.turtle = null;

        switch (direction) {

            case 0:
                this.myTurtle.turtleposition = this.myTurtle.turtleposition.up;
                this.myTurtle.yPos += 1;
                break;
            case 1:
                this.myTurtle.turtleposition = this.myTurtle.turtleposition.right;
                this.myTurtle.xPos += 1;
                break;
            case 2:
                this.myTurtle.turtleposition = this.myTurtle.turtleposition.down;
                this.myTurtle.yPos -= 1;
                break;

            case 3:
                this.myTurtle.turtleposition = this.myTurtle.turtleposition.left;
                this.myTurtle.xPos -= 1;
                break;

        }
        this.myTurtle.turtleposition.isTaken = true;
        this.myTurtle.turtleposition.turtle = this.myTurtle;

        if (this.myTurtle.turtleposition.hasCoin) {
            this.myTurtle.points += 2;
            this.myTurtle.turtleposition.hasCoin = false;
        }

        if (!this.myTurtle.turtleposition.steppedOn
            && !this.myTurtle.turtleposition.isStartPosition) {
            this.myTurtle.points++;
            this.myTurtle.turtleposition.steppedOn = true;

        }
        this.lobby.writeToAll(Protocol.TURT.name() + ":" + this.myTurtle.num + ":" + direction);

        this.lobby.writeToAll(Protocol.POIN.name() + ":" + this.myTurtle.num + ":" + this.myTurtle.points);
        /*System.out.println(this.lobby.board.printBoard() +
                "\n" + this.nickname + " has " + this.myTurtle.points + " points!");
         */
    }

}