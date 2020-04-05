package server;

import game.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis, Rohail, Natasha, Melanie
 *
 * The main function of this class is to genereate a server.
 * After that this Thread waits for new connections and starts a thread for each new
 * connection made.
 */
public class Server implements Runnable {
    int port;

    /**
     * Constructor. Instantiates a new Server.
     * @param port connection is made to this port.
     */
    public Server(int port) {
        this.port = port;
    }

    /*
     * Global variables also used by the ServerThreadForClient.
     */
    public static Set<ServerThreadForClient> userThreads = new HashSet<>();
    /**
     * The constant games.
     */
    public static Set<Lobby> games = new HashSet<>();
    /**
     * The constant gamesRunningCounter.
     */
    public static int gamesRunningCounter = 0;

    /*
     * Static variables with server information.
     */
    public static boolean serverIsOnline = true;


    /**
     * Variables to identify clients.
     */
    public static int playersOnline = 0;
    /**
     * The constant clientConnections.
     */
    public static int clientConnections = 0;

    /**
     * Function compares Strings in the list to a desired name
     * @param desiredName new nickname chosen by client.
     * @param user represents a ServerThreadForClient (a single connection to the server).
     * @return true if name already exists or false if name is not taken yet.
     */
    public static synchronized boolean checkForName(String desiredName, ServerThreadForClient user) {
        if (clientConnections <= 1) {
            return false;
        }
        for (ServerThreadForClient aUser: userThreads) {
            if (aUser != user) { //To not compare with your own nickname -> NullPointerException!!
                if (aUser.profil.nickname.equals(desiredName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Function changes duplicates in appropriate names
     * @param desiredName new nickname chosen by client.
     * @param aUser represents a ServerThreadForClient (a single connection to the server).
     * @return desiredName by client with ending _# if name already exists.
     */
    public static synchronized String checkForDuplicate(String desiredName, ServerThreadForClient aUser) {
        int position = desiredName.length();
        if (checkForName(desiredName, aUser)) {
            aUser.sendMessage(Protocol.ERRO.name() +
                    ":\nYour desired name exists already!\n");
            int i = 1;
            if (!desiredName.endsWith("_0")) {
                desiredName += "_0";
            } else {
                position = position - 2;
            }
            //if there is more than just one person with the same name
            while (checkForName(desiredName, aUser)) {
                desiredName = desiredName.substring(0, position);
                desiredName += "_" + i;
                i++;
            }
        }
        return desiredName;
    }

    /**
     * Functions for broadcast and other chats.
     * Goes through each ServerThreadForClient in the group and sends a message.
     * @param message that is send out.
     * @param group set of clients connected to the server.
     */
    public static synchronized void chat(String message, Set<ServerThreadForClient> group) {
        testConnectionLost(group);
        for (ServerThreadForClient aUser : group) {
            aUser.sendMessage(message);
        }
    }

    /**
     * To send a message to a specific client.
     * @param message that is send out.
     * @param aPerson client who receives the message.
     */
    public static synchronized void chatSingle(String message, ServerThreadForClient aPerson) {
        aPerson.sendMessage(message);
    }

    /**
     * Check if the player exists
     *
     * @param message    the message
     * @param playerName the player name
     * @param group      the group
     * @return true or false
     */
    public static synchronized boolean doesThePlayerExist(
            String message, String playerName, Set<ServerThreadForClient> group) {
        for (ServerThreadForClient aUser : group) {
                if (aUser.profil.nickname.equals(playerName)) {
                    aUser.sendMessage(message);
                    return true;
                }
        }
        return false;
    }

    /**
     * Counter to determine how many games are created and running.
     * @return how many games are running.
     */
    public static synchronized int countGame() {
        gamesRunningCounter++;
        return gamesRunningCounter;
    }

    /**
     * Check if there are games created.
     * @return boolean if there are any games at all.
     */
    public static synchronized boolean checkOutGames() {
        return !games.isEmpty();
    }

    /**
     * Check lobbies boolean.
     *
     * @param lobbyNumber the lobby number
     * @param aUser       the a user
     * @param watch       the watch
     * @return boolean
     */
    public static synchronized boolean checkLobbies(int lobbyNumber, ServerThreadForClient aUser, boolean watch) {
        if (checkOutGames()) {
            for (Lobby lobby : games) {
                if (lobby.getLobbyNumber() == lobbyNumber) {
                    if (watch) {
                        lobby.addSpectators(aUser);
                        aUser.profil.isSpectator = true;
                        aUser.sendMessage(Protocol.SPEC.name());
                    } else {
                        lobby.addPlayer(aUser);
                    }
                    aUser.profil.lobby = lobby;
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Game list boolean.
     *
     * @param aUser the a user
     * @return boolean if there are any lobbies at all
     */
    public static synchronized boolean gameList(ServerThreadForClient aUser) {
        if (checkOutGames()) {
            String gameList = Protocol.MSSG.name() + ":These are the games so far: \n";
            for (Lobby lobby: games) {
                //undefined should be impossible
                String status = "undefined";
                if(lobby.gamestate == 1) {
                    status = "open";
                } else if (lobby.gamestate == 2) {
                    status = "ongoing";
                } else if (lobby.gamestate == 3) {
                    status = "finished";
                }
                gameList += "lobby #" + lobby.lobbyNumber + " is " + status + "\n";
            }
            aUser.sendMessage(gameList);
            return true;
        } else {
            return false;

        }
    }

    /**
     * Function returns a string containing all player nicknames divided by ",".
     * @return String of all players
     */
    public static synchronized String printPlayers() {
        testConnectionLost(userThreads);
        String listOfPlayers = "Players at the server are: ";
        for (ServerThreadForClient oneOfAllUsers: userThreads) {
            listOfPlayers += oneOfAllUsers.profil.nickname + ", ";
        }
        return listOfPlayers;
    }

    /**
     * method that checks trivial operation - if exception, then there is a connection lost
     *
     * @param group the group
     */
    public static synchronized void testConnectionLost(Set<ServerThreadForClient> group) {
        for (ServerThreadForClient aUser : group) {
            try {
                DataOutputStream test = aUser.testConnection();
                test.writeUTF(Protocol.TEST.name());
            } catch (Exception e) {
                group.remove(aUser);
                System.out.println("There was a connection lost!");
            }
        }
    }

    /*
     * Function to end the whole program.
     */
    public static synchronized void sendClientsToSleep() {
        for (ServerThreadForClient aUser : userThreads) {
            aUser.end();
        }
    }

    /**
     * Builds a server and gives feedback as long as the server is online.
     */
    public void run() {
        try {
            String serverIP = Inet4Address.getLocalHost().getHostAddress();
            String serverName = Inet4Address.getLocalHost().getHostName();
            ServerSocket serverSocket = new ServerSocket(port);
            logger.info("connecting to Client");
            System.out.println("\n\n\nServerSocket at port " + port + " successfully build.\n\n"
                    + "Server IP-Adrdress: " + serverIP + "\n"
                    + "Servername: " + serverName + "\n\n\n"
                    + "Now waiting for a connection to this IP/name by a Client...\n\n\n");
            //Server is online now
            while (serverIsOnline) {
                //Waits for a connection to the server by a client
                Socket socket = serverSocket.accept();
                logger.info("connected to Client");
                //Connection to one client established
                System.out.println("\nClient #" + ++clientConnections + " is connected to the Server.\n");
                //Create In- & Ouputstreams for reading and sending Strings
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                /*
                 * Start ServerThread for client who has connected.
                 * In this thread, the client and server can communicate with each other.
                 * The new client will be added to the list of all client threads
                 */
                ServerThreadForClient serverThreadForClient = new ServerThreadForClient(
                        ++playersOnline, dis, dos);
                userThreads.add(serverThreadForClient);
                Thread serverThread = new Thread(serverThreadForClient);
                logger.info("ServerThreadForClient started");
                serverThread.start();
            }
            serverSocket.close();
            logger.info("Serversocket closed");

        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }
    }
}
