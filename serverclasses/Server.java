package serverclasses;

import java.net.*;
import java.io.*;

class Server {

    static final int port = 1111;

    public static int clientID = 1; //every Client gets an ID.
    public static int playersOnline = 0; //for a correct ID if a client reconnects to Server

    public static String serverIP;

    public static void main(String[] args) {

        try {

            serverIP = Inet4Address.getLocalHost().getHostAddress();
            
            //get IP of server printed out.
            System.out.println("\n\nWaiting for a connection to IP: " + serverIP + " on port " + port + "...\n\n\n");

            ServerSocket iWaitForRequests = new ServerSocket(port); 
            /**A server socket waits for requests to come in 
             * over the network. It performs some operation 
             * based on that request, and then possibly 
             * returns a result to the requester.
             * Here: Server socke is bound to the specified port.
             */

            do {
                Socket endpoint = iWaitForRequests.accept(); 
                /**Server socket "iWaitForRequests" now listens 
                 * for a connection to be made to this socket and accepts it.
                 * This is stored in a socket.
                 * A socket is an endpoint for communication between two machines
                 */

                //Start a Thread for a Client and give correct ID
                HelloThread helloThread = new HelloThread(clientID, endpoint);
                Thread thread = new Thread(helloThread);
                thread.start();

                //For correct IDs after (re-)connections
                playersOnline += 1;
                clientID = playersOnline + 1;

            } while (playersOnline != 0); //TO DO different Bedingung

            iWaitForRequests.close(); //..and close Server //DOES NOT WORK - STUCK IN ACCEPT();

        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        }

    }

}