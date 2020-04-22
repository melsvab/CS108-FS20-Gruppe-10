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

    public PlayerTurtle[] turtles;

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
            addSpectators(aUser);
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
        // spectator will be informed about board
        if (gamestate > 1) {
            aUser.sendMessage(Protocol.STR1.name() + ":" + board.boardSize + ":" + numberOfPlayers);

            for (PlayerTurtle turtle: turtles) {
                aUser.sendMessage(Protocol.TURS.name()
                        + ":" + turtle.num
                        + ":" + turtle.turtlename
                        + ":" + turtle.xPos + "-" + turtle.yPos);
            }
            String coins = Protocol.COIN.name() + ":1";
            String draw = Protocol.DRAW.name() + ":1";
            for (int i = 0; i < board.board.length; i++) {
                for (int j = 0; j < board.board.length; j++) {
                    if (board.board[i][j].hasCoin) {
                        coins += ":" + i + "-" + j;
                    } else if (board.board[i][j].steppedOn) {
                        draw += ":" + i + "-" + j;
                    }
                }
            }

            aUser.sendMessage(coins);
            aUser.sendMessage(draw);
        }

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
     */
    public synchronized void createGame(int boardSize, int maxCoins) {
        //Create a board out of fields and assert attributes.
        //boardSize: min = 10, max = 20.
        if (boardSize < 10) {
            boardSize = 10;
        } else if (boardSize > 50) {
            boardSize = 50;
        }
        board = new Board(boardSize);

        //max coins = 500; determine the probability for coins on the board.
        if (maxCoins > 100) {
            maxCoins = 100;
        }

        board.coinOccurrence =  boardSize + maxCoins/3;
        board.maxCoinsInGame = maxCoins;
        writeToAll(Protocol.STR1 + ":" + boardSize + ":" + numberOfPlayers);
    }


    /**
     *
     */
    public void run() {
        /*
        Create for every PLAYER a turtle and its name.
         */

        turtles = new PlayerTurtle[numberOfPlayers];

        int turtleNum = 0;
        for (ServerThreadForClient aPlayer : players) {
            aPlayer.profil.myTurtle = new PlayerTurtle(aPlayer.profil.nickname + "-Junior");
            aPlayer.profil.myTurtle.num = turtleNum;
            turtles[turtleNum] = aPlayer.profil.myTurtle;

            writeToPlayer(Protocol.GMSG.name()
                    + ":You have adopted a turtle baby and named it "
                    + aPlayer.profil.myTurtle.turtlename, aPlayer);
            //Set this turtle to a start-position not taken yet.
            A: for (int x = 0; x < this.board.boardSize; x++) {
                for (int y = 0; y < this.board.boardSize; y++) {
                    if (this.board.board[x][y].isStartPosition && !this.board.board[x][y].isTaken) {
                        aPlayer.profil.myTurtle.turtleposition = this.board.board[x][y];
                        // String with all turtle positions will be sent
                        writeToAll(Protocol.TURS.name()
                                + ":" + turtleNum
                                + ":" + aPlayer.profil.nickname + "-Junior"
                                + ":" + x + "-" + y);
                        this.board.board[x][y].isTaken = true;
                        aPlayer.profil.myTurtle.xPos = x;
                        aPlayer.profil.myTurtle.yPos = y;
                        break A;
                    }
                }
            }
            turtleNum ++;

        }

        // all positions that have coins now
        String coins = board.spawnRandomCoins();
        // "1" for new coins that spawn. "2" would be for coins that were taken.
        writeToAll(Protocol.COIN.name() + ":1" + coins);

        gamestate = 2;


        /*
        * Show Startboard to server (for tests)
        * // System.out.println(board.printBoard());
        */

        //countdown before the game starts.
        for (ServerThreadForClient aPlayer : players) {
            aPlayer.profil.waitingForEvent = true;
        }
        pleaseWait(2);
        writeToAll(Protocol.GMSG.name() + ":Game starts in ");
        pleaseWait(2);
        for (int i = 5; i >= 0; i--) {
            writeToAll(Protocol.GMSG.name() + ":" + i);
            pleaseWait(1);
        }
        writeToAll(Protocol.GMSG.name() + ":GO!");
        for (ServerThreadForClient aPlayer : players) {
            aPlayer.profil.waitingForEvent = false;
        }

        //game has started.
        int rounds = 1;
        boolean definivWinner = false;
        while (!definivWinner) {
            writeToAll(Protocol.RNDS.name() + ":" + rounds);
            pleaseWait(30);
            for (ServerThreadForClient aPlayer : players) {
                aPlayer.profil.waitingForEvent = true;
            }
            writeToAll(Protocol.GMSG.name() + ":An Event has started!");
            pleaseWait(2);
            Random randomEvent = new Random();
            int whichEvent = randomEvent.nextInt(10);

            if (whichEvent < 7) {
                Random howOften = new Random();
                int randomOften = howOften.nextInt(5) + 1;
                String flood = this.board.floodBoard(randomOften, this);
                writeToAll(Protocol.WATR.name() + ":1" + flood);
                pleaseWait(2);
                this.board.afterEvent();
                writeToAll(Protocol.RSET.name());
            } else if (whichEvent == 8) {
                // all positions that have coins now -> should be impossible right now
                String coin = board.spawnRandomCoins();
                // "1" for new coins that spawn. "2" would be for coins that were taken.
                writeToAll(Protocol.COIN.name() + ":1" + coin);
            } else {
                Random howStrong = new Random();
                int magnitude = howStrong.nextInt(30) + 5;
                String quake = this.board.earthquake(magnitude, this);
                writeToAll(Protocol.QUAK.name() + ":1" + quake);
                pleaseWait(2);
                this.board.afterEvent();
                writeToAll(Protocol.RSET.name());
            }
            pleaseWait(2);
            for (ServerThreadForClient aPlayer : players) {
                aPlayer.profil.waitingForEvent = false;
                if (aPlayer.profil.myTurtle.wasHitByEvent) {
                    writeToPlayer(Protocol.GMSG.name() + ":You got hit and lost xx points!", aPlayer);
                    aPlayer.profil.myTurtle.wasHitByEvent = false;
                } else {
                    writeToPlayer(Protocol.GMSG.name() + ":You survived!", aPlayer);
                }
            }
            if(rounds >= 10) {
                int pointsCounter = -100;
                String placeholder = "";
                for (ServerThreadForClient aPlayer : players) {
                    if (aPlayer.profil.myTurtle.points > pointsCounter) {
                        pointsCounter = aPlayer.profil.myTurtle.points;
                        placeholder = aPlayer.profil.nickname;

                    }
                }
                int counter = 1;
                for (ServerThreadForClient aPlayer : players) {
                    if (!(placeholder.equals(aPlayer.profil.nickname))) {
                        if (pointsCounter == aPlayer.profil.myTurtle.points) {
                            counter++;
                        }
                    }
                }
                if(counter > 1){
                    definivWinner = false;
                    counter = 1;
                } else {
                    definivWinner = true;
                }
            }

            rounds += 1;
        }

        writeToAll(Protocol.GMSG.name() + ":The game has ended!");
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


    }
}
