package game;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.lang.System.*;

import server.*;

public class Lobby extends Thread {


    /*
    * one for an open game
    * two for an ongoing game
    * three for a finished game
    */
    public int gamestate;

    public Set<ServerThreadForClient> players = new HashSet<>();
    public Set<ServerThreadForClient> spectators = new HashSet<>();

    public Board board;
    public int lobbyNumber;
    
    public Lobby(ServerThreadForClient aUser, int number) {
        setDaemon(true);
        players.add(aUser);
        gamestate = 1;
        lobbyNumber = number;

    }

    public void pleaseWait(int seconds) {
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        while((now - start)/1000 < seconds) {
            now = System.currentTimeMillis();
        }
    }

    public synchronized void writeToAll(String message) {
        Server.chat(message,players);
        //if there are spectators, they will get the message as well
        if (!spectators.isEmpty()) {
            Server.chat(message,spectators);
        }

    }

    public synchronized void writeToPlayer(String message, ServerThreadForClient aPerson) {
        Server.chatSingle(message, aPerson);

    }

    public synchronized void addPlayer(ServerThreadForClient aUser) {
        players.add(aUser);

    }

    public synchronized void deletePlayer(ServerThreadForClient aUser) {
        if (aUser.profil.isSpectator) {
            spectators.remove(aUser);
        } else {

            if (aUser.profil.myTurtle != null) {
                aUser.profil.myTurtle = null;
            }
            aUser.profil.lobby = null;
            players.remove(aUser);
        }

    }

    public synchronized void addSpectators(ServerThreadForClient aUser) {
       spectators.add(aUser);

    }

    public synchronized void changeGameState(int state) {
        gamestate = state;

    }

    public synchronized int getLobbyNumber() {
        return lobbyNumber;

    }

    public synchronized void createGame(int boardSize, int maxCoins) {
        board = new Board(boardSize,maxCoins);

    }



    public void run() {

        for (ServerThreadForClient aPlayer : players) {
            aPlayer.profil.myTurtle = new PlayerTurtle(aPlayer.profil.nickname + "-Junior");
            Server.chatSingle(Protocol.MSSG.name()
                + ":You have adopted a turtle baby and named it "
                + aPlayer.profil.myTurtle.turtlename, aPlayer);
            A: for (int x = 0; x < this.board.boardSize; x++) {
                B: for (int y = 0; y < this.board.boardSize; y++) {
                    if (this.board.board[x][y].isStartPosition && !this.board.board[x][y].isTaken) {
                        aPlayer.profil.myTurtle.turtleposition = this.board.board[x][y];
                        this.board.board[x][y].isTaken = true;
                        break A;
                    }
                }
            }
        }

        writeToAll(Protocol.LOBY.name() + ":" + board.printBoard());

        /**
         * WHILE SCHLAUFE ADDEN MIT INPUT PLAYER 1? Player 2? ETC:
         * DANN EVENT!
         *
         * ganz am ENDE: gamestate = 3; (gamestate wird auf >finished< geändert)
         */

        while (gamestate != 3) { //Game is still running TO DO: HOW IS GAME FINISHED?
            pleaseWait(20);
            for (ServerThreadForClient aPlayer : players) {
                aPlayer.profil.waitingForEvent = true;
            }
            writeToAll(Protocol.MSSG.name() + ":OMG NOO WHAT IS HAPPENING!!! ");
            pleaseWait(5);
            Random randomEvent = new Random();
            int whichEvent = randomEvent.nextInt(10); /* TO DO ACHTUGN EVENT MELDUNG UND DANN EVENT DANN WIEDER GO*/
            if (whichEvent < 9) {
                Random howOften = new Random();
                int randomOften = howOften.nextInt(5) + 1;
                this.board.floodBoard(randomOften);
                writeToAll(Protocol.LOBY.name() + ":" + this.board.printBoard());
                pleaseWait(4);
                this.board.afterEvent();
                writeToAll(Protocol.LOBY.name() + ":" + this.board.printBoard());
            } else {
                Random howStromng = new Random();
                int magnitude = howStromng.nextInt(30) + 5;
                this.board.earthquake(magnitude);
                writeToAll(Protocol.LOBY.name() + ":" + this.board.printBoard());
                pleaseWait(4);
                this.board.afterEvent();
                writeToAll(Protocol.LOBY.name() + ":" + this.board.printBoard());
            }
            pleaseWait(4);
            writeToAll(Protocol.MSSG.name() + ":I think its over... wait... did I lose some points?!");
            for (ServerThreadForClient aPlayer : players) {
                aPlayer.profil.waitingForEvent = false;
            }
        }
    }
}