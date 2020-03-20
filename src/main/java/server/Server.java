package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

public class Server {

    /**
     * The main function of this class is to genereate a server.
     * After that this Thread waits for new connections and starts
     * a Thread for this each new connection made.
     */

    /**
     * Global variables also used by clients.
     */

    public static LinkedList<String> namesOfAllClients = new LinkedList<String>();
    public static Set<ServerThreadForClient> userThreads = new HashSet<>();

    /**
     * Static variables with server infromation.
     */
    public static boolean serverIsOnline = true;
    public static final int port = 1111;

    /**
     * Variables to identify clients.
     */
    public static int playersOnline = 0;
    public static int clientConnections = 0;

    public static void globalChat(String message) {
        for (ServerThreadForClient aUser : userThreads) {
            if (aUser.globalChat()) {
                aUser.sendMessage(message);
            }
        }
    }

    public static void broadcast(String message) {
        for (ServerThreadForClient aUser : userThreads) {
            aUser.sendMessage(message);

        }
    }

    public static synchronized void removeUser(String nickname, ServerThreadForClient aUser) {
        namesOfAllClients.remove(nickname);
        userThreads.remove(aUser);
    }

    /**
     * Function checks if there is a String in the list that is equal to the desired name
     */
    public static synchronized String checkForDublicates(String desiredName) {

        if (namesOfAllClients.contains(desiredName)) {

            String nameIsUsedAlready = "There is someone with this name already!";
            System.out.println(nameIsUsedAlready);

            desiredName += "_0";
        }

        namesOfAllClients.addFirst(desiredName);
        return desiredName;

    }
   

    public static void main(String[] args) {

        try {

            /**
             * Build a Server and give feedback, when server is online.
             */
            String serverIP = Inet4Address.getLocalHost().getHostAddress();
            String serverName = Inet4Address.getLocalHost().getHostName();

            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("\n\n\nServerSocket at port " + port + " successfully build.\n\n" + 
                "Server IP-Adrdress: " + serverIP + "\n" + 
                "Servername: " + serverName + "\n\n\n" +                
                "Now waiting for a connection to this IP/name by a Client...\n\n\n");

            while (serverIsOnline) {

                /**
                 * Wait for a connection to the server by a Client
                 */              
                Socket socket = serverSocket.accept();

                System.out.println("\nClient #" + ++clientConnections + " is connected to the server.\n\n");
                /** Connection to one client established */
            
                /**
                 * Create In- & Ouputstreams for reading and sending Strings
                 */
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                /**
                 * Start ServerThread for client who has connected.
                 * In this Thread, this client and the server can
                 * communicate with eacc other
                 */
                ServerThreadForClient serverThreadForClient = new ServerThreadForClient(
                    ++playersOnline, dataInputStream, dataOutputStream);
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