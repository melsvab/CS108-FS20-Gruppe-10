package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import threads.echothreads.EchoThreadClient;
import threads.pingpongthreads.PingSenderThread;
import threads.pingpongthreads.PongReaderThread;

public class Client {

    public static void main(String[] args) {

        try { //TO DO ECHOTHREAD CLIENT IN HEREE !!!!!!

            /**Let Client choose a Server and port and build a connection */

            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

            System.out.println("\n\nPlease type in the IP-Address or the name of the Server: ");
            String serverIP_serverName = readKeyBoard.readLine();

            System.out.println("\nNext, please type in the port to be connected to: ");
            int serverPort = Integer.parseInt(readKeyBoard.readLine());

            System.out.println("\nTry to connect to Server " + serverIP_serverName + " on port " + serverPort + "...");

            Socket socket = new Socket(serverIP_serverName, serverPort);
            System.out.println("\n\nConnection to server " + serverIP_serverName + " on port " + serverPort + " successfully made\n\n\n");

            /**Connection established. */

            /**For every Thread we use the same Stremas (In- & Output).
             * Therefore, we have to give them to each Thread!
            */

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            /**
             * Now Start all Threads 
            */

            /**Echo Threads */
            EchoThreadClient echoThreadClient = new EchoThreadClient(dataInputStream, dataOutputStream);
            Thread echoThread = new Thread(echoThreadClient);
            echoThread.start();

            /**PingPong Threads */
            /*PingSenderThread pingsenderThread = new PingSenderThread(dataOutputStream);
            Thread pingThread = new Thread(pingsenderThread);
            pingThread.start();

            PongReaderThread pongsenderThread = new PongReaderThread(dataInputStream);
            Thread pongThread = new Thread(pongsenderThread);
            pongThread.start();
            /** */

            while (true) {

            }
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }

    }

}