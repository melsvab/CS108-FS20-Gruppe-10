package server;

import game.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Dennis,Rohail,Natasha,Melanie
 *
 * The main function of this class is to genereate a server. After that this Thread waits for
 * new connections and starts a thread for each new connection made.
 */
public class Server  implements Runnable {
    int port;

    /**
     *
     * @param port
     */
    public Server(int port) {
        this.port = port;
    }

    /*
     * Global variables also used by the ServerThreadForClient.
     */

    public static Set<ServerThreadForClient> userThreads = new HashSet<>();
    public static Set<Lobby> games = new HashSet<>();
    public static int gamesRunningCounter = 0;

    /*
     * Static variables with server information.
     */

    public static boolean serverIsOnline = true;


    /**
     * Variables to identify clients.
     */

    public static int playersOnline = 0;
    public static int clientConnections = 0;

    /*
     * Function "checkForName" compares Strings in the list to a desired name
     * Function "checkForDuplicates" changes duplicates in appropriate names
     */

    /**
     * checks if the nickname already exists or not
     * @param desiredName
     * @param user
     * @return true or false
     */
    public static synchronized boolean checkForName(String desiredName, ServerThreadForClient user) {

        if (clientConnections <= 1) {
            return false;
        }
        for (ServerThreadForClient aUser: userThreads) {
            // Do not compare with your own nickname -> NullpointerException!!
            if (aUser != user) {
                if ( aUser.profil.nickname.equals(desiredName) ) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     *
     * @param desiredName
     * @param aUser
     * @return
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
     * @param message
     * @param group
     */
    public static synchronized void chat(String message, Set<ServerThreadForClient> group) {
        testConnectionLost(group);
        for (ServerThreadForClient aUser : group) {
            aUser.sendMessage(message);
        }
    }

    /**
     * To send a message to one person only
     * @param message
     * @param aPerson
     */
    public static synchronized void chatSingle(String message, ServerThreadForClient aPerson) {
        aPerson.sendMessage(message);
    }

    /**
     * Check if the player exists
     * @param message
     * @param playerName
     * @param group
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
     *
     * @return gamecounter
     */
    public static synchronized int countGame() {
        gamesRunningCounter++;
        return gamesRunningCounter;
    }

    /**
     *
     * @return boolean if there are any games at all
     */
    public static synchronized boolean checkOutGames() {
        return !games.isEmpty();
    }

    /**
     *
     * @param lobbyNumber
     * @param aUser
     * @param watch
     * @return
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
     *
     * @param aUser
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
     *
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
     * @param group
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
     * is used to end the whole program
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

            System.out.println("\n\n\nServerSocket at port " + port + " successfully build.\n\n"
                    + "Server IP-Adrdress: " + serverIP + "\n"
                    + "Servername: " + serverName + "\n\n\n"
                    + "Now waiting for a connection to this IP/name by a Client...\n\n\n");

            //Server is online now

            while (serverIsOnline) {

                //Waits for a connection to the server by a client

                Socket socket = serverSocket.accept();

                //Connection to one client established

                System.out.println("\nClient #" + ++clientConnections + " is connected to the Server.\n");

                //Create In- & Ouputstreams for reading and sending Strings

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                /*
                 * Start ServerThread for client who has connected.
                 * In this thread, the client and server can
                 * communicate with each other.
                 * The new client will be added to the list of all client threads
                 */

                ServerThreadForClient serverThreadForClient = new ServerThreadForClient(
                        ++playersOnline, dis, dos);
                userThreads.add(serverThreadForClient);
                Thread serverThread = new Thread(serverThreadForClient);
                serverThread.start();

            }

            serverSocket.close();

        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }
    }
}
