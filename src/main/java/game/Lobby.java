package game;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import server.*;

/**
 * @author Natasha,Melanie,Dennis
 *
 */
public class Lobby extends Thread {



    /*
    * one for an open game
    * two for an ongoing game
    * three for a finished game
    */
    public int gamestate;

    /**
     * The Players.
     */
    public Set<ServerThreadForClient> players = new HashSet<>();
    /**
     * The Spectators.
     */
    public Set<ServerThreadForClient> spectators = new HashSet<>();

    /**
     * The Board.
     */
    public Board board;
    /**
     * The Lobby number.
     */
    public int lobbyNumber;
    /**
     * The Number of players.
     */
    public int numberOfPlayers;

    /**
     * Instantiates a new Lobby.
     *
     * @param aUser  the a user
     * @param number the number
     */
    public Lobby(ServerThreadForClient aUser, int number) {
        setDaemon(true);
        players.add(aUser);
        gamestate = 1;
        lobbyNumber = number;
        numberOfPlayers = 1;

    }

    /**
     * Please wait.
     *
     * @param seconds the seconds
     */
    public void pleaseWait(int seconds) {
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        while((now - start)/1000 < seconds) {
            now = System.currentTimeMillis();
        }
    }

    /**
     * Write to all.
     *
     * @param message the message
     */
    public synchronized void writeToAll(String message) {
        // In case nobody is in this lobby anymore, there is no need to send messages.
        if (!players.isEmpty() || !spectators.isEmpty()) {

            Server.chat(message, players);
            //if there are spectators, they will get the message as well
            if (!spectators.isEmpty()) {
                Server.chat(message, spectators);
            }
        }

    }

    /**
     * Write to player.
     *
     * @param message the message
     * @param aPerson the a person
     */
    public synchronized void writeToPlayer(String message, ServerThreadForClient aPerson) {
        Server.chatSingle(message, aPerson);

    }

    /**
     * Add player.
     *
     * @param aUser the a user
     */
    public synchronized void addPlayer(ServerThreadForClient aUser) {
        //if the game has already started or there are four players already, the new client will be a spectator
        if (gamestate > 1 || numberOfPlayers >= 4) {

            if(gamestate > 1) {
                aUser.sendMessage(Protocol.ERRO.name() + ":The game has started already! You are a spectator now!");
            } else {
                aUser.sendMessage(Protocol.ERRO.name()
                        + ":This lobby has four players already! You were added as a spectator.");
            }
            spectators.add(aUser);
            aUser.sendMessage(Protocol.SPEC.name());
            aUser.profil.isSpectator = true;

        } else {
            // the new client will be a player
            numberOfPlayers++;
            players.add(aUser);

        }

    }

    /**
     * Delete player.
     *
     * @param aUser the a user
     */
    public synchronized void deletePlayer(ServerThreadForClient aUser) {
        if (aUser.profil.isSpectator) {
            spectators.remove(aUser);
            aUser.profil.isSpectator = false;
        } else {

            if (aUser.profil.myTurtle != null) {
                aUser.profil.myTurtle = null;
            }

            numberOfPlayers--;
            players.remove(aUser);
        }
        aUser.profil.isInGame = false;
        aUser.profil.lobby = null;
    }

    /**
     * Add spectators.
     *
     * @param aUser the a user
     */
    public synchronized void addSpectators(ServerThreadForClient aUser) {
       spectators.add(aUser);

    }

    /**
     * Change game state.
     *
     * @param state the state
     */
    public synchronized void changeGameState(int state) {
        gamestate = state;

    }

    /**
     * Gets lobby number.
     *
     * @return the lobby number
     */
    public synchronized int getLobbyNumber() {
        return lobbyNumber;

    }

    /**
     * Create game.
     *
     * @param boardSize the board size
     * @param maxCoins  the max coins
     */
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
                for (int y = 0; y < this.board.boardSize; y++) {
                    if (this.board.board[x][y].isStartPosition && !this.board.board[x][y].isTaken) {
                        aPlayer.profil.myTurtle.turtleposition = this.board.board[x][y];
                        this.board.board[x][y].isTaken = true;
                        break A;
                    }
                }
            }
        }

        writeToAll(Protocol.LOBY.name() + ":" + board.printBoard());

        int rounds = 1;
        while (rounds <= 10) {
            writeToAll(Protocol.RNDS.name() + ":" + String.valueOf(rounds));
            pleaseWait(20);
            for (ServerThreadForClient aPlayer : players) {
                aPlayer.profil.waitingForEvent = true;
            }
            writeToAll(Protocol.MSSG.name() + ":OMG! NOO! WHAT IS HAPPENING? ");
            pleaseWait(5);
            Random randomEvent = new Random();
            int whichEvent = randomEvent.nextInt(10);
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
                if (aPlayer.profil.myTurtle.wasHitByEvent) {
                    writeToPlayer(Protocol.MSSG.name() + ":Oh crap, I lost a lot of points!", aPlayer);
                    aPlayer.profil.myTurtle.wasHitByEvent = false;
                } else {
                    writeToPlayer(Protocol.MSSG.name() + ":Puh ok no, that was close!", aPlayer);
                }
            }
            rounds += 1;
        }

        writeToAll(Protocol.MSSG.name() + ":\n\nThe game has ended!\n\n");
        int maxPoints = -100;
        String winner = "";
        for (ServerThreadForClient aPlayer : players) {
            writeToAll(Protocol.MSSG.name() + ":" + aPlayer.profil.nickname + " has "
                    + aPlayer.profil.myTurtle.points + " points");
            if (aPlayer.profil.myTurtle.points > maxPoints) {
                maxPoints = aPlayer.profil.myTurtle.points;
                winner = aPlayer.profil.nickname; //To Do: Even winners.
            }
        }

        writeToAll(Protocol.WINR.name() + ":" + winner + ":" + String.valueOf(maxPoints));

        gamestate = 3;

        writeToAll(Protocol.BACK.name());
        if (!players.isEmpty()) {
            for (ServerThreadForClient aPlayer : players) {
                deletePlayer(aPlayer);
            }
        }
        if (!spectators.isEmpty()) {
            for (ServerThreadForClient aSpectator : spectators) {
                deletePlayer(aSpectator);
            }
        }

    }
}