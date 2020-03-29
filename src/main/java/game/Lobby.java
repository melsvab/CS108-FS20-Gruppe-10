package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.lang.System.*;

import server.*;

public class Lobby extends Thread {


    /*
    * one for an open game
    * two for ongoing game
    * three for finished game
     */
    public int gamestate;

    public Set<ServerThreadForClient> players = new HashSet<>(); /**SHOULD NOT BE STATIC BOTH!*/
    public Set<ServerThreadForClient> spectators = new HashSet<>();

    public Board board;
    public int lobbynumber;
    
    public Lobby(ServerThreadForClient aUser, int number) {
        setDaemon(true);
        players.add(aUser);
        gamestate = 1;
        lobbynumber = number;

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

    public synchronized void addPlayers(ServerThreadForClient aUser) {
        players.add(aUser);

    }

    public synchronized void addSpectators(ServerThreadForClient aUser) {
       spectators.add(aUser);

    }

    public synchronized void changeGameState(int state) {
        gamestate = state;

    }

    public synchronized int getLobbyNumber() {
        return lobbynumber;

    }

    public synchronized void createGame(int boardsize, int maxCoins) {
        board = new Board(boardsize,maxCoins);

    }



    public void run() {

        for (ServerThreadForClient aPlayer : players) {
            aPlayer.clientProfil.myTurtle = new PlayerTurtle(aPlayer.clientProfil.nickname + "-Junior");
            Server.chatSingle(Protocol.LOBY.name()
                + ":You have adopted a turtle baby and named it "
                + aPlayer.clientProfil.myTurtle.turtlename, aPlayer);
            A: for (int x = 0; x < this.board.boardSize; x++) {
                B: for (int y = 0; y < this.board.boardSize; y++) {
                    if (this.board.board[x][y].isStartPosition && !this.board.board[x][y].isTaken) {
                        aPlayer.clientProfil.myTurtle.turtleposition = this.board.board[x][y];
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
         */

    }
    
}