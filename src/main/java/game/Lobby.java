package game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import server.*;

/**
 * @author Natasha, Melanie, Dennis
 * In this class, a lobby is created where the players are collected and the gamestate and
 * board is saved.
 */
public class Lobby extends Thread {
    /*
    * one for an open game
    * two for an ongoing game
    * three for a finished game
    */
    public int gamestate;
    public int lobbyNumber;
    public int numberOfPlayers;
    public Board board;
    public Set<ServerThreadForClient> players = new HashSet<>();
    public Set<ServerThreadForClient> spectators = new HashSet<>();

    /**
     * Instantiates a new Lobby and adds clients to the players-set
     * @param aUser the user who creates the lobby is added to the set of players
     * @param number every lobby gets an number / ID.
     */
    public Lobby(ServerThreadForClient aUser, int number) {
        setDaemon(true);
        players.add(aUser);
        gamestate = 1;
        lobbyNumber = number;
        numberOfPlayers = 1;
    }

    /**
     * Function often used. Let thread wait several seconds
     * @param seconds how many seconds to wait.
     */
    public void pleaseWait(int seconds) {
        long start = System.currentTimeMillis();
        long now = System.currentTimeMillis();
        while ((now - start) / 1000 < seconds) {
            now = System.currentTimeMillis();
        }
    }

    /**
     * Function sends a message to all players in this lobby.
     * @param message to send.
     */
    public synchronized void writeToAll(String message) {
        if (!players.isEmpty()) {
            Server.chat(message, players);
        }
        if (!spectators.isEmpty()) {
            Server.chat(message, spectators);
        }
    }

    /**
     * Function to send a message to a specific client.
     * @param message to send.
     * @param aPerson client who receives Message
     */
    public synchronized void writeToPlayer(String message, ServerThreadForClient aPerson) {
        Server.chatSingle(message, aPerson);
    }

    /**
     * Functions adds a player to the lobby.
     * @param aUser player to be added.
     */
    public synchronized void addPlayer(ServerThreadForClient aUser) {
        //if the game has already started or there are four players already, the new client will be a spectator
        if (gamestate > 1 || numberOfPlayers >= 4) {
            if (gamestate > 1) {
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
     * Adds a Client to the set of spectators.
     * @param aUser client to be added.
     */
    public synchronized void addSpectators(ServerThreadForClient aUser) {
        spectators.add(aUser);
    }

    /**
     * Removes a client or spectator from the list / set and from the lobby.
     * @param aUser client to be removed.
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
     * Changes the state of the game (one for an open game /
     * two for an ongoing game / three for a finished game)
     * @param state to change to.
     */
    public synchronized void changeGameState(int state) {
        gamestate = state;
    }

    /**
     * Returns the number / ID of the lobby.
     * @return lobby int.
     */
    public synchronized int getLobbyNumber() {
        return lobbyNumber;
    }

    /**
     * Creates a new board object.
     * @param boardSize size of the board (Field[][]).
     * @param maxCoins max number of coins.
     */
    public synchronized void createGame(int boardSize, int maxCoins) {
        board = new Board(boardSize,maxCoins);
    }


    /**
     * The run method is used to send the board with its events to all clients
     */

    public void run() {
        /*
        Create for every PLAYER a turtle and its name.
         */
        for (ServerThreadForClient aPlayer : players) {
            aPlayer.profil.myTurtle = new PlayerTurtle(aPlayer.profil.nickname + "-Junior");
            Server.chatSingle(Protocol.MSSG.name()
                 + ":You have adopted a turtle baby and named it "
                 + aPlayer.profil.myTurtle.turtlename, aPlayer);
            //Set this turtle to a start-position not taken yet.
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

        //Show Startboard to all clients in the lobby.
        writeToAll(Protocol.LOBY.name() + ":" + board.printBoard());

        //countdown before the game starts.
        for (ServerThreadForClient aPlayer : players) {
            aPlayer.profil.waitingForEvent = true;
        }
        writeToAll(Protocol.LOBY.name() + ":Game starts in ");
        for (int i = 5; i >= 0; i--) {
            writeToAll(Protocol.LOBY.name() + ":" + i);
            pleaseWait(1);
        }
        for (ServerThreadForClient aPlayer : players) {
            aPlayer.profil.waitingForEvent = false;
        }

        //game has started.
        int rounds = 1;
        while (rounds <= 10) {
            writeToAll(Protocol.RNDS.name() + ":" + rounds);
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

        writeToAll(Protocol.WINR.name() + ":" + winner + ":" + maxPoints);

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
