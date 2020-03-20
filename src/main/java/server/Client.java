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
             * Create Client Profil
             */
            ClientProfil profil = new ClientProfil();

            /**
             * Let client choose a server and port
             * and build up a connection
             */
            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

            /*System.out.println("\n\nPlease type in the IP-Address or the name of the Server: ");
            String serverIP_serverName = readKeyBoard.readLine();

            System.out.println("\nPlease type in the port to be connected to: ");
            int serverPort = Integer.parseInt(readKeyBoard.readLine());

            System.out.println("\nConnection to Server \"" + serverIP_serverName + "\", to port " + serverPort + "...");*/

            Socket socket = new Socket(/*serverIP_serverName, serverPort*/"localhost", 1111);
            System.out.println("\n\nConnected!\n\n\n");
            /**Connection established. */

            /**
             * Create In- & Ouputstreams for reading and sending Strings
             */
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            /**
             * Start ClientReaderThread for reading input from Server 
             */
            ClientReaderThread clientReaderThread = new ClientReaderThread(
                dataInputStream, dataOutputStream, profil);
            Thread client_thread = new Thread(clientReaderThread);
            client_thread.start();
    
            /**
             * Choose Nickname 
             */
            String nickname = readKeyBoard.readLine();

            if (nickname.equalsIgnoreCase("YEAH")) {
                nickname = System.getProperty("user.name");
            }

            dataOutputStream.writeUTF(nickname);
            /**Nickname chosen. */

            /**
             * Start 
             */           
            boolean playerActive = true;
            
            while (playerActive) {

                String clientchoice = readKeyBoard.readLine();
                clientchoice = clientchoice.toUpperCase();
                dataOutputStream.writeUTF(clientchoice);

                switch (clientchoice) {

                    case "CHAT":

                        //DataInputStream chatMessageIn = dataInputStream;

                        System.out.println("\nYou have joined the global chat.\n");

                        /*ChatReader chatReader = new ChatReader(chatMessageIn);
                        Thread chatReaderThread = new Thread(chatReader);
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

                    case "NAME":

                        try {

                            System.out.print(Message.changeName);
                            String newNickname = readKeyBoard.readLine();
                            dataOutputStream.writeUTF(newNickname);
                            Thread.sleep(50);
                            System.out.println(Message.helpMessage);
                            break;

                        } catch (InterruptedException exception) {
                            System.err.println(exception.toString());
                        }

                    case "QUIT":

                        dataOutputStream.writeUTF(clientchoice);
                        System.out.println("\nClosing program...\n");
                        playerActive = false;
                        break;

                    case "PLL1":
                        break;

                    case "GML1":
                        break;

                    case "HSC1":
                        break;

                    case "CRE1":
                        break;

                    case "JON1":
                        break;

                    default: 

                        System.out.println("\nInput unknown...\n\n" + Message.helpMessage);

                }

            }

            dataInputStream.close();
            dataOutputStream.close();
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }

    }

}