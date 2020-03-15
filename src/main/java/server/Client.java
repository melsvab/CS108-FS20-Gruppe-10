package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try {

            /**
             * Let client choose a server and port
             * and build up a connection
             */
            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

            System.out.println("\n\nPlease type in the IP-Address or the name of the Server: ");
            String serverIP_serverName = readKeyBoard.readLine();

            System.out.println("\nPlease type in the port to be connected to: ");
            int serverPort = Integer.parseInt(readKeyBoard.readLine());

            System.out.println("\nConnection to Server \"" + serverIP_serverName + "\", to port " + serverPort + "...");

            Socket socket = new Socket(serverIP_serverName, serverPort);
            System.out.println("\n\nConnected!\n\n\n");
            /**Connection established. */

            /**
             * Create In- & Ouputstreams for reading and sending Strings
             */
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            /**
             * Get message from server to choose nickname
             */
            System.out.print(dataInputStream.readUTF());
            /**
             * Send nickname to Server.
             */
            String nickname = readKeyBoard.readLine();

            dataOutputStream.writeUTF(nickname);

            System.out.println("\n\nYour nickname is: " + nickname);

            while (true) {

                /**
                 * Leave loop if client enters QUIT.
                 * Else, send String entered to Server.
                 */
                String input = readKeyBoard.readLine();

                if (input.equalsIgnoreCase("QUIT")) {
                    break;
                } else {
                    dataOutputStream.writeUTF(input);
                }
        
            }

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }

    }

}