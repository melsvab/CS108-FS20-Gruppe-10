package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import threads.echothreads.ServerThreadForClient;
import threads.pingpongthreads.PingReaderThread;
import threads.pingpongthreads.PongSenderThread;

public class Server {

    static final int port = 1111;

    public static String message = ""; //TO DO DURCH QUEUE ERSETZEN: Objecte mit String und nickname + ID 

    public static void main(String[] args) {

        try {

            /**
             * Build a Server and wait for a connection.
             */
            String serverIP = Inet4Address.getLocalHost().getHostAddress();
            String serverName = Inet4Address.getLocalHost().getHostName();

            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("\n\n\nServerSocket at port " + port + " successfully build\n\n" + 
                "Server IP-Adrdress: " + serverIP + "\n" + 
                "Servername: " + serverName + "\n\n\n" +                
                "Now waiting for a connection to this IP (or name) by a Client...\n\n\n");

            do {

                Socket socket = serverSocket.accept();

                System.out.println("A Client has successfully connected to this server on port " + port + "\n\n");
    
                /**Connection to a Client established */

                /**For every Thread, we use Streams (In- & Output). */
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                /**Now Start all Threds for this Client */

                /**Echo Threads */
                ServerThreadForClient serverThreadForClient = new ServerThreadForClient(dataInputStream, dataOutputStream);
                Thread echoThread = new Thread(serverThreadForClient);
                echoThread.start();

                /**PingPong Threads */
                /*PingReaderThread pingReaderThread = new PingReaderThread(dataInputStream);
                Thread newthread = new Thread(pingReaderThread);
                newthread.start();
    
                PongSenderThread pongsenderThread = new PongSenderThread(dataOutputStream);
                Thread pongThread = new Thread(pongsenderThread);
                pongThread.start();
                /** */

            } while (true);
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }

    }

}