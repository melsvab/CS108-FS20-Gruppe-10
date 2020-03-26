package server;

import game.Board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Server {

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
    public static Board[] gamesRunningList = new Board[10]; /**TO DO: CREATE LIST TO ADD GAMES*/
    public static int gamesRunningCounter = 0;

    /*
     * Static variables with server information.
     */

    public static boolean serverIsOnline = true;
    public static final int port = 1111;

    /**
     * Variables to identify clients.
     */

    public static int playersOnline = 0;
    public static int clientConnections = 0;

    /**
     * Function for global Chat. Go through each ServerThreadForClient
     * and send message from client if a client is in global Chat.
     */

    public static void globalChat(String message) {
        for (ServerThreadForClient aUser : userThreads) {
            if (aUser.globalChat()) {
                aUser.sendMessage(message);
            }
        }
    }

    /**
     * Function for broadcast. Go through each ServerThreadForClient
     * and send message (client does not have to be in the global chat).
     */

    public static void broadcast(String message) {
        for (ServerThreadForClient aUser : userThreads) {
            aUser.sendMessage(message);
        }
    }

    /**
     * If a client disconnects, it´s name is removed form 
     * the List on the server and the Thread which will be terminated,
     * is removed from the list on the server as well.
     */

    public static synchronized void removeUser(String nickname, ServerThreadForClient aUser) {
        namesOfAllClients.remove(nickname);
        userThreads.remove(aUser);
    }

    /**
     * Function checks if there are Strings in the list that are equal to the desired name
     */

    public static synchronized String checkForDublicates(String desiredName, ServerThreadForClient client) {
        int position = desiredName.length();
        if (namesOfAllClients.contains(desiredName)) {
            client.sendMessage(Message.nameIsUsedAlready);
            int i = 1;

            if (!desiredName.endsWith("_0")) {
                desiredName += "_0";

            } else {
                position = position - 2;
            }

            //if there is more than just one person with the same name
            while (namesOfAllClients.contains(desiredName)) {
                desiredName = desiredName.substring(0, position);
                desiredName +=  "_" + i;
                i++;
            }
        }
        namesOfAllClients.addFirst(desiredName);
        return desiredName;

    }

    /**
     * Function to create a new game.
     */
    public void startNewGame(int boardSize, int maxApples, int maxCoins) {
        if (gamesRunningCounter >= gamesRunningList.length) { return; }
        gamesRunningList[gamesRunningCounter] = new Board(boardSize, maxApples, maxCoins);
    }

    /**
     * Build a Server and give feedback, when server is online.
     */

    public static void main(String[] args) {

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