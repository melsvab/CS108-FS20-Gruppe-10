package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

import game.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis, Natasha, Rohail, Melanie
 * A thread is created for every client that connects to the server.
 * Represents an interface between client and server.
 */
public class ServerThreadForClient implements Runnable {

    /**
     * The constant logger.
     */
    public static final Logger logger = LoggerFactory.getLogger(ServerThreadForClient.class);


    DataInputStream dis;

    DataOutputStream dos;

    public Profil profil;

    /**
     * constructor of SeverThreadForClient
     * @param clientID the clientID
     * @param dis data input stream
     * @param dos data output stream
     */
    public ServerThreadForClient(int clientID, DataInputStream dis, DataOutputStream dos) {
        this.profil = new Profil(clientID);
        this.dis = dis;
        this.dos = dos;
    }

    /**
     * ends program
     */
    public void end() {
        profil.goesToSleep(this);
        sendMessage(Protocol.QUIT.name());
    }

    /**
     * Sends message to output stream.
     * @param message that is send.
     */
    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (Exception exception) {
            System.err.println(exception.toString());
        }
    }

    /**
     * Test connection data output stream.
     * @return data output stream data output stream
     */
    public DataOutputStream testConnection() {

        return dos;

    }

    /**
     * checks if the keyword exists in our protocol
     * @param keyword to be checked.
     * @return true if keyword does exist.
     */
    public static boolean contains(String keyword) {
        for (Protocol p : Protocol.values()) {
            if (p.name().equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Join.
     * @param original the original
     * @param watch    the watch
     */
    public void join(String original, boolean watch) {
        // checks if there are two ints and the player is not in a lobby already
        Parameter lobbyNumber = new Parameter(original, 5);
        if (lobbyNumber.isCorrect && !profil.isInGame) {
            //checks if there are any games at all

            String[] words = original.split(":");
            int lobbyNum = lobbyNumber.numberOne;
            //checks for the lobbynumber and adds client if possible
            if (Server.checkLobbies(lobbyNum, this, watch)) {
                profil.isInGame = true;
                sendMessage(Protocol.CRE2.name() + ":" + lobbyNum);
            } else {
                sendMessage(Protocol.ERRO.name()
                        + ":This game ID does not exist. Try another one!\n");
            }

        } else {

            //if(profil.checkForWord(original) == false){
            //logger.info("forgot to type the colon");
            //}else if(profil.checkForWord(original) == true && profil.checkForNumber(original) == false ){
            // logger.info("typed a lobbynumber that doesnt exist");


        }
    }

    public void run() {
        try {
            //Send a welcome message
            dos.writeUTF(Protocol.WELC.name());
            /*
             * server receives nickname by client and checks for duplicates.
             * player gets information about his/her nickname
             */
            String nickname = dis.readUTF();
            profil.nickname = Server.checkForDuplicate(nickname, this);
            System.out.println("\nNickname of client #" + profil.clientID + ": "
                    + profil.nickname);
            dos.writeUTF(Protocol.MSSG.name()
                    + ":Your nickname: " + profil.nickname + "\n");
            //Asks client what he / she wants to do.
            dos.writeUTF(Protocol.HELP.name());

            while (profil.clientIsOnline) {
                //Gets choice from a client and decides what to do.
                String original = dis.readUTF();
                int lengthInput = original.length();
                while (lengthInput < 4) { //keyword is not long enough
                    original = dis.readUTF();
                    lengthInput = original.length();
                }
                String clientChoice = original.toUpperCase().substring(0, 4);

                if (contains(clientChoice)) {

                    switch (Protocol.valueOf(clientChoice)) {
                        case CHAT:
                            // The message will be sent in lobby chat if the client is in one.
                            Parameter word = new Parameter(original, 4);
                            if (profil.lobby != null && word.isCorrect) {
                                String msg;
                                if (original.substring(5).equalsIgnoreCase(Message.enterLobby)) {
                                    msg = Protocol.MSG0.name() + ":" + profil.nickname
                                            + Message.enterLobby;
                                } else {
                                    msg = Protocol.MSG0.name() + ":[" + profil.nickname + "] "
                                            + original.substring(5);
                                }
                                profil.lobby.writeToAll(msg);
                            } else {
                                dos.writeUTF(Protocol.MSG1.name() + ":" + original.substring(5));
                            }
                            break;

                        case BRC1:
                            /*
                             * A Message will be send to all clients that are online.
                             * (and therefore in our list of ServerThreadForClients called userThreads)
                             */
                            if (lengthInput > 5) {
                                String message = Protocol.MSG0.name() + ":Broadcast to all: \n"
                                        + "[" + profil.nickname + "] " + original.substring(5);
                                Server.chat(message, Server.userThreads);
                            } else {
                                //logger.info("\n" + "no message available");
                            }
                            break;

                        case WHP1:
                            //Whisperchat
                            int i = original.indexOf(" ");
                            String playername = original.substring(5, i);
                            String msg = Protocol.MSG0.name()
                                    + ":Whisper from:" + "[" + profil.nickname + "]"
                                    + original.substring(i);
                            String msg2 = Protocol.MSG0.name() + ":Whisper to:[" + playername + "]"
                                    + original.substring(i);
                            dos.writeUTF(msg2);
                            boolean f = Server.doesThePlayerExist(msg, playername, Server.userThreads);
                            if (!f) {
                                dos.writeUTF(Protocol.EWHP.name());
                            }
                            break;

                        case QUIT:
                            // inform server that this client wants to end his / her connection
                            System.out.println("\nClient #" + profil.clientID + " \""
                                    + profil.nickname + "\" has disconnected.");
                            end();
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                System.err.println(e.toString());
                            }
                            break;

                        case ENDE:
                            //Under Construction: Can stop server
                            System.out.println(profil.nickname + " wants to end the whole program.");
                            Server.chat(Protocol.MSSG.name()
                                    + ":Our server goes to sleep\n", Server.userThreads);
                            Server.sendClientsToSleep();
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                System.err.println(e.toString());
                            }
                            Server.serverIsOnline = false;
                            break;

                        case NAME:
                            Parameter name = new Parameter(original, 3);
                            if (name.isCorrect) {
                                //old name will be removed from the server list, new name is checked for duplicates
                                String oldNickname = profil.nickname;
                                String desiredName = name.wordOne;
                                desiredName = Server.checkForDuplicate(desiredName, this);
                                String answerToClient = Protocol.MSSG.name()
                                    + ":Your name has been changed from " + oldNickname
                                    + " to " + desiredName + "\n";
                                //write decision to client
                                dos.writeUTF(answerToClient);
                                //server has accepted new name
                                System.out.println(
                                        "\n" + profil.nickname + " changed his/her name to " + desiredName);
                                profil.nickname = desiredName;
                            } else {
                                // logger.info("forgot the colon");
                            }
                            break;

                        case PLL1:

                            /* Takes list from server and prints out the name of all players
                             * that are currently connected to the server.
                             */

                            String listOfPlayers = Server.printPlayers();
                            dos.writeUTF(Protocol.LIST.name() + ":" + listOfPlayers);

                            break;

                        case GML1:

                            // Sends a list of open, ongoing and finished games including their game_ID
                            if (!Server.gameList(this)) {
                                dos.writeUTF(Protocol.LIST.name()
                                        + ":Nobody has created a game yet!" );

                            }

                            break;

                        case HSC1:

                            //Under Construction: Sends the current highscore to the player

                            dos.writeUTF(Protocol.LIST.name() + ":This list is empty.");
                            break;

                        case CRE1:

                            // creates a new lobby

                            if (!profil.isInGame) {
                                int lobbyNumber = Server.countGame();
                                Lobby lobby = new Lobby(this, lobbyNumber);
                                Server.games.add(lobby);
                                profil.lobby = lobby;
                                profil.isInGame = true;
                                dos.writeUTF(Protocol.CRE2.name() + ":" + lobbyNumber);
                            } else {
                               // logger.info("the client is already in a lobby/Game");
                            }

                            break;

                        case JOIN:

                            /*
                             * Player joins a Game with the fitting game_ID as a player.
                             * If there is no game with the game_ID an error message will be sent.
                             */

                            join(original, false);

                            break;

                        case SPEC:

                            /*
                             * Player joins a Game with the fitting game_ID as a spectator.
                             * If there is no game with the game_ID an error message will be sent.
                             */

                            join(original, true);

                            break;

                        case BACK:
                            // Is used to exit a lobby

                            if (profil.isInGame) {

                                profil.lobby.deletePlayer(this);
                                dos.writeUTF(Protocol.BACK.name());
                            } else {
                               // logger.info("used BACK even though he/she is not in a lobby or game");
                            }

                            break;

                        case STR1:
                            if (profil.lobby != null) {
                                //check if it is possible to transfer the words into numbers
                                Parameter boardsizeAndMaxCoins = new Parameter(original, 1);
                                if (boardsizeAndMaxCoins.isCorrect && profil.isInGame) {
                                    if (profil.lobby.numberOfPlayers >= 1) {

                                        String[] words = original.split(":");

                                        int boardSize = boardsizeAndMaxCoins.numberOne;
                                        int maxCoins = boardsizeAndMaxCoins.numberTwo;

                                        //creates a board and adds all information from the client
                                        profil.lobby.createGame(boardSize, maxCoins);
                                        profil.lobby.start();
                                        profil.lobby.writeToAll(Protocol.MSSG.name()
                                                + ":\nThe game has started!\n");
                                    } else {
                                        dos.writeUTF(Protocol.MSSG.name() + ":Your lobby has no players. "
                                                + "Therefore it cannot be started.");
                                    }
                                } else {
                                    // should be impossible because it is checked at the client class
                                    logger.info("input to start a game is not correct or " +
                                            "client's boolean isInGame is wrong");
                                }
                            } else {
                                // should be impossible because it is checked at the client class
                                logger.info("Client wants to start a game without being in a lobby");
                            }
                            break;

                        case UPPR:
                            //Player moves a block up in the game.
                            if (profil.gameIsReady()) {
                                if (profil.myTurtle.turtleposition.up.isTaken
                                        || profil.myTurtle.turtleposition.up.isFlood
                                        || profil.myTurtle.turtleposition.up.isBoundary) {
                                    dos.writeUTF(Protocol.INVM.name() + ":" + Message.invalidMove);
                                } else if (profil.waitingForEvent) {
                                    dos.writeUTF(Protocol.INVM.name() + ":" + profil.myTurtle.turtlename + Message.tooScaredToMove);
                                } else {
                                    profil.moveTurtle(0);
                                }
                            } else {
                                dos.writeUTF(Protocol.ERRO.name() + ":" + Message.youCannotDoThat);
                            }
                            break;

                        case DOWN:
                            //Player moves a block down in the game.
                            if (profil.gameIsReady()) {
                                if (profil.myTurtle.turtleposition.down.isTaken
                                        || profil.myTurtle.turtleposition.down.isFlood
                                        || profil.myTurtle.turtleposition.down.isBoundary) {
                                    dos.writeUTF(Protocol.INVM.name() + ":" + Message.invalidMove);
                                } else if (profil.waitingForEvent) {
                                    dos.writeUTF(Protocol.INVM.name() + ":" + profil.myTurtle.turtlename + Message.tooScaredToMove);
                                } else {
                                    profil.moveTurtle(2);
                                }
                            } else {
                                dos.writeUTF(Protocol.ERRO.name() + ":" + Message.youCannotDoThat);
                            }

                            break;

                        case LEFT:

                            // Player moves a block left in the game.
                            if (profil.gameIsReady()) {
                                if (profil.myTurtle.turtleposition.left.isTaken
                                        || profil.myTurtle.turtleposition.left.isFlood
                                        || profil.myTurtle.turtleposition.left.isBoundary) {
                                    dos.writeUTF(Protocol.INVM.name() + ":" + Message.invalidMove);
                                } else if (profil.waitingForEvent) {
                                    dos.writeUTF(Protocol.INVM.name() + ":" + profil.myTurtle.turtlename + Message.tooScaredToMove);
                                } else {
                                    profil.moveTurtle(3);
                                }
                            } else {
                                dos.writeUTF(Protocol.ERRO.name() + ":" + Message.youCannotDoThat);
                            }
                            break;


                        case RIGT:

                            // Player moves a block right in the game.
                            if (profil.gameIsReady()) {
                                if (profil.myTurtle.turtleposition.right.isTaken
                                        || profil.myTurtle.turtleposition.right.isFlood
                                        || profil.myTurtle.turtleposition.right.isBoundary) {
                                    dos.writeUTF(Protocol.INVM.name() + ":" + Message.invalidMove);
                                } else if (profil.waitingForEvent) {
                                    dos.writeUTF(Protocol.INVM.name() + ":" + profil.myTurtle.turtlename + Message.tooScaredToMove);
                                } else {
                                    profil.moveTurtle(1);
                                }
                            } else {
                                dos.writeUTF(Protocol.ERRO.name() + ":" + Message.youCannotDoThat);
                            }
                            break;

                        case IDKW:
                            // secret cheat code
                            if (profil.gameIsReady()) {
                                int cheatPlus = 10;
                                dos.writeUTF(Protocol.MSSG.name()
                                    + ":CHEAT CODE USED! YOU RECEIVED " + cheatPlus + " POINTS!");
                                profil.myTurtle.points += cheatPlus;
                                profil.lobby.writeToAll(Protocol.POIN.name() + ":"
                                        + profil.myTurtle.num + ":" + profil.myTurtle.points);
                            } else {
                                dos.writeUTF(Protocol.MSSG.name() + ":STOP THAT!");
                            }
                            break;


                        default:

                            //This should be impossible
                            System.out.println(Message.garbage + " 7");
                            break;



                    }

                } else {
                    //logger.info("keyword does not exist");
                }

            }

            dis.close();
            dos.close();

        } catch (Exception exception) {
            System.err.println(exception.toString());
            Server.userThreads.remove(this);
            if (profil.lobby != null) {
                profil.lobby.deletePlayer(this);
            }
        }

    }
    
}