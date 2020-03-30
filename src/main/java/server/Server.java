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

public class Server  implements Runnable {
    int port;

    public Server(int port) {
        this.port = port;
    }

    /**
     * The main function of this class is to genereate a server.
     * After that this Thread waits for new connections and starts
     * a Thread for this each new connection made.
     */

    /*
     * Global variables also used by the ServerThreadForClient.
     */

    public static LinkedList<String> namesOfAllClients = new LinkedList<>();
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

    public static synchronized boolean checkForName(String desiredName, ServerThreadForClient user) {

        if (clientConnections <= 1) {return false;}
        for (ServerThreadForClient aUser: userThreads) {

            if (aUser != user && aUser.profil.nickname.equals(desiredName) ) {
                return true;
            }
        }

        return true;
    }
    public static synchronized String checkForDublicates(String desiredName, ServerThreadForClient aUser) {
        int position = desiredName.length();
        if (namesOfAllClients.contains(desiredName)) {
            aUser.sendMessage(Protocol.ERRO.name() +
                    ":\nYour desired name exists already!\n");
            int i = 1;

            if (!desiredName.endsWith("_0")) {
                desiredName += "_0";

            } else {
                position = position - 2;
            }

            //if there is more than just one person with the same name
            while (namesOfAllClients.contains(desiredName)) {
                desiredName = desiredName.substring(0, position);
                desiredName += "_" + i;
                i++;
            }
        }
        namesOfAllClients.addFirst(desiredName);
        return desiredName;

    }

    /*
     * Functions for broadcast and other chats.
     * Goes through each ServerThreadForClient in the group / sends message to a person
     */

    public static synchronized void chat(String message, Set<ServerThreadForClient> group) {
        for (ServerThreadForClient aUser : group) {
            aUser.sendMessage(message);
        }
    }

    public static synchronized void chatSingle(String message, ServerThreadForClient aPerson) {
        aPerson.sendMessage(message);
    }

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




    public static synchronized int countGame() {
        gamesRunningCounter++;
        return gamesRunningCounter;
    }

    public static synchronized boolean checkOutGames() {
        return !games.isEmpty();
    }

    public static synchronized boolean checkLobbies(int lobbyNumber, ServerThreadForClient aUser) {

        if (checkOutGames()) {
            for (Lobby lobby : games) {
                if (lobby.getLobbyNumber() == lobbyNumber) {
                    lobby.addPlayer(aUser);
                    aUser.profil.lobby = lobby;
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

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

    public static synchronized void playerList(ServerThreadForClient aUser) {
        String listOfPlayers = "Players at the server are: ";
        for (ServerThreadForClient oneOfAllUsers: userThreads) {
            listOfPlayers += oneOfAllUsers.profil.nickname + ", ";
        }

        aUser.sendMessage(Protocol.MSSG.name() + ":" + listOfPlayers);
    }


    /**
     * If a client disconnects, it´s name is removed form
     * the List on the server and the Thread which will be terminated,
     * is removed from the list on the server as well.
     */

    public static synchronized void removeUser(String nickname, ServerThreadForClient aUser) {
        userThreads.remove(aUser);

        if (aUser.profil.lobby != null) {
            aUser.profil.lobby.deletePlayer(aUser);
            aUser.profil.isInGame = false;
            aUser.sendMessage(Protocol.BACK.name());
        }
    }

    public static synchronized void sendClientsToSleep() {
        for (ServerThreadForClient aUser : userThreads) {
            aUser.suddenEnding();
        }
    }



    /**
     * Build a Server and give feedback, when server is online.
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

                //Wait for a connection to the server by a Client


                Socket socket = serverSocket.accept();

                //Connection to one client established

                System.out.println("\nClient #" + ++clientConnections + " is connected to the Server.\n");


                //Create In- & Ouputstreams for reading and sending Strings

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                /**
                 * Start ServerThread for client who has connected.
                 * In this Thread, this client and the server can
                 * communicate with eacc other. Also add Thread to List on Server.
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
