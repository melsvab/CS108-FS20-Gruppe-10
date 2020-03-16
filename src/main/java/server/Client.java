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
             * Get message from server to choose nickname,
             * send nickname to Server and show it to Client
             */
            System.out.print(dataInputStream.readUTF());

            String nickname = readKeyBoard.readLine();
            
            if (nickname.equalsIgnoreCase("YEAH")) {
                nickname = System.getProperty("user.name");
            }

            dataOutputStream.writeUTF(nickname);

            System.out.println("\n\nYour nickname is: " + nickname + "\n\n");
            /**Nickname chosen. */

            /**
             * Get messages from server
             */
            System.out.println(dataInputStream.readUTF());

            boolean playerActive = true;
            
            while (playerActive) {
                
                String clientchoice = readKeyBoard.readLine();
                clientchoice = clientchoice.toUpperCase();
                dataOutputStream.writeUTF(clientchoice);

                switch (clientchoice) {

                    case "CHAT":

                        //DataInputStream chatMessageIn = dataInputStream;

                        System.out.println("\nYou have joined the global chat.\n");

                        /*ChatReader chatreader = new ChatReader(chatMessageIn);
                        Thread chatReaderThread = new Thread(chatreader);
                        chatReaderThread.start();*/

                        while (true) {
                            
                            String input = readKeyBoard.readLine();

                            if (input.equalsIgnoreCase("QUIT")) {
                                dataOutputStream.writeUTF(input);
                                System.out.println("\nYou have left the chat...\n");
                                System.out.println(dataInputStream.readUTF());
                                break;
                            } else {
                            dataOutputStream.writeUTF(input + "\n");
                            }    

                        } 
                        
                        break;

                    case "QUIT":

                        playerActive = false;
                        break;

                    default: 

                        System.out.println(dataInputStream.readUTF());

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