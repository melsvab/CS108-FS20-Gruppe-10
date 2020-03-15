package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static String message = "";

    /**
     * Static variables for the server.
     */
    public static boolean serverIsOnline = true;
    public static final int port = 1111;

    /**
     * Variables for identify clients.
     */
    public static int playersOnline = 0;
    public static int clientConnections = 0;

    //public static String message = ""; //TO DO DURCH QUEUE ERSETZEN: Objecte mit String und nickname + ID 

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
                Thread serverThread = new Thread(serverThreadForClient);
                serverThread.start();

            }
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }

    }

}