package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.lang.System.*;

import server.*;

public class Lobby implements Runnable {


    /*
    * one for an open game
    * two for ongoing game
    * three for finished game
     */
    public static int gamestate;

    public static Set<ServerThreadForClient> players = new HashSet<>();
    public static Set<ServerThreadForClient> spectators = new HashSet<>();

    Board board;
    int lobbynumber;
    
    public Lobby(ServerThreadForClient aUser, int boardsize, int maxPoints, int number) {
        players.add(aUser);
        board = new Board(boardsize,maxPoints);
        gamestate = 1;
        lobbynumber = number;

    }

    public static void writeToAll (String message) {
        Server.chat(message,players);
        //if there are spectators, they will get the message as well
        if (!spectators.isEmpty()) {
            Server.chat(message,spectators);
        }

    }

    public static void writeToPlayer(String message, ServerThreadForClient aPerson) {
        Server.chatSingle(message, aPerson);

    }

    public static void addPlayers(ServerThreadForClient aUser) {
        players.add(aUser);

    }

    public static void addSpectators(ServerThreadForClient aUser) {
       spectators.add(aUser);

    }

    public static void changeGameState(int state) {
        gamestate = state;

    }



    public void run() {

        for (int i = 0; i < 10; i++) {

            long startTime = System.currentTimeMillis();
            long elapsedTime;
            double seconds;
            while (gamestate == 1) {

                elapsedTime = System.currentTimeMillis() - startTime;
                seconds = elapsedTime * 1000;
                if (seconds > 20) {
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