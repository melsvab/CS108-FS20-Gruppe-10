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

    public Set<ServerThreadForClient> players = new HashSet<>();
    public Set<ServerThreadForClient> spectators = new HashSet<>();

    Board board;
    public int lobbynumber;
    
    public Lobby(ServerThreadForClient aUser, int boardsize, int maxPoints, int number) {
        setDaemon(true);
        players.add(aUser);
        board = new Board(boardsize,maxPoints);
        gamestate = 1;
        lobbynumber = number;
        start();

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



    public void run() {

        for (int i = 0; i < 10; i++) {

            long startTime = System.currentTimeMillis();
            long elapsedTime;
            double seconds;
            while (gamestate == 1) {

                elapsedTime = System.currentTimeMillis() - startTime;
                seconds = elapsedTime * 1000;
                if (seconds > 600) {
                    writeToAll("Lobby is waiting for players");
                    break;
                }


            }
            if (gamestate != 1) {
                //Game starts
                i += 10;
            }
        }

        if (gamestate == 1) {
            writeToAll("It seems as if you waited to long to start a game");
        } else {
            writeToAll("The Games starts!");
        }


    }
    
}