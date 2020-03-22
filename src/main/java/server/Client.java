package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    /**
     * This class represents a Client which connects to the server.
     * In here, client inputs will be sent to the server and will be
     * processed.
     */

    public static void main(String[] args) {
        try {
            /**
             * Creates Client Profil
             */

            ClientProfil profil = new ClientProfil();

            /**
             *chooses a server and port
             * and builds up a connection
             */

            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

            System.out.println("\n\nPlease type in the IP-Address or the name of the Server: ");
            String serverIP_serverName = readKeyBoard.readLine();

            System.out.println("\nPlease type in the port to be connected to: ");
            int serverPort = Integer.parseInt(readKeyBoard.readLine());

            System.out.println("\nConnection to Server \"" + serverIP_serverName + "\", to port " + serverPort + "...");

            Socket socket = new Socket(serverIP_serverName, serverPort/*"localhost", 1111*/);
            System.out.println("\n\nConnected!\n\n\n");

            //Connection established.

            /**
             * Create In- & Ouputstreams for reading and sending Strings
             */
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            /**
             * Start ClientReaderThread for reading input from Server 
             */
            ClientReaderThread clientReaderThread = new ClientReaderThread(dis, dos, profil);
            Thread client_thread = new Thread(clientReaderThread);
            client_thread.start();
            /**
             * Choose nickname
             */
            String nickname = readKeyBoard.readLine();
            if (nickname.equalsIgnoreCase("YEAH")) {
                nickname = System.getProperty("user.name");
            }

            //Nickname chosen
            dos.writeUTF(nickname);


            /**
             * Start processing inputs.
             */           
            boolean playerActive = true;
            
            while (playerActive) {
                /**
                 * Read keyboardinput from client.
                 */
                String original = readKeyBoard.readLine();
                String clientchoice = original.toUpperCase();

                switch (clientchoice) {
                    //Descide what to do next

                    case "CHAT":
                        dos.writeUTF(original);

                        /*
                        * changes boolean <isInGlobalChat> and informs at terminal.
                        * if aleady in chatroom, terminal message about that fact will be send
                        */

                        if (profil.isInGlobalChat) {
                            System.out.println("\nYou have already joined the global chat.\n");
                        } else {
                            profil.isInGlobalChat = true;
                            System.out.println("\nYou have joined the global chat.\n");
                        }

                        break;

                    case "NAME":
                        dos.writeUTF(original);
                        try {
                            /*
                            * gets message that there is the option to use system username
                            * and analyses answer from Client to this question
                            */

                            Thread.sleep(50);
                            System.out.print(Message.changeName);
                            String newNickname = readKeyBoard.readLine();

                            /*
                            * if the answer is <YEAH> the nickname is change to the system username
                            * if the answer is something else, this input will be used as the nickname
                            */

                            if (newNickname.equalsIgnoreCase("YEAH")) {
                                newNickname = System.getProperty("user.name");
                            }

                            /*
                            * sending the desired nickname to server
                            */

                            dos.writeUTF(newNickname);
                            Thread.sleep(50);

                            /*
                            * help message only necessary while not be in global chat
                            */
                            if (!profil.isInGlobalChat) {
                                System.out.println(Message.helpMessage);
                            }

                            break;

                        } catch (InterruptedException exception) {
                            System.err.println(exception.toString());
                        }

                    case "IDK": /* our secret cheat code */
                        dos.writeUTF(original);

                        try {
                            System.out.println("\n...let me help you...\n");
                            Thread.sleep(2000);
                            System.out.println("Please type in a number:\n\n");
                            int howLong = Integer.parseInt(readKeyBoard.readLine());
                            System.out.println("\n");
                            for(int i = 0; i < howLong; i++) {
                                Thread.sleep(2000);
                                System.out.print("This "); Thread.sleep(1000);
                                System.out.print("is "); Thread.sleep(1000);
                                System.out.print("gonna "); Thread.sleep(1000);
                                System.out.print("take "); Thread.sleep(1000);
                                System.out.print("a "); Thread.sleep(1000);
                                System.out.print("while"); Thread.sleep(2000);
                                System.out.print("... "); Thread.sleep(3000);
                                System.out.print("sorry "); Thread.sleep(2000);
                                System.out.print("not "); Thread.sleep(10);
                                System.out.print("sorry"); Thread.sleep(10);
                                System.out.print("... "); Thread.sleep(3000);
                                System.out.print(i+1); Thread.sleep(1000);
                                System.out.print(" out "); Thread.sleep(1000);
                                System.out.print("of "); Thread.sleep(1000);
                                System.out.print(howLong); Thread.sleep(1000);
                                System.out.print("... "); Thread.sleep(2000);
                            }
                            System.out.println("\n" + Message.helpMessage);
                        } catch (InterruptedException e) {
                            System.err.println(e.toString());
                        }

                        break;

                    case "QUIT":
                        /*
                        * informing client about his choice.
                        * If player is not active he cannot write anymore.
                        */
                        dos.writeUTF(clientchoice);
                        System.out.println("\nClosing program...\n");
                        playerActive = false;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            System.err.println(e.toString());
                        }
                        break;

                    case "PLAYERLIST":

                        dos.writeUTF("PLL1");

                        break;

                    case "GAMELIST": /**Under Construction*/

                        dos.writeUTF("GML1");

                        break;


                    case "HIGHSCORE": /**Under Construction */

                        dos.writeUTF("HSC1");

                        break;

                    case "CREATE": /**Under Construction */

                        dos.writeUTF("CRE1");

                        break;

                    case "JOIN": /**Under Construction*/

                        if (!profil.isInGame) {
                            System.out.println("Type in the GameNumber of the open game you want to join:");
                            String game_ID = readKeyBoard.readLine();
                            dos.writeUTF("JON1" + game_ID);
                        }
                        else if (profil.isInGlobalChat || profil.isInBroadcast)
                            dos.writeUTF(original);
                        else
                            System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                        break;


                    case "START": /**Under Construction*/

                        if (profil.isInGame /*&& something like "Game has started == false"*/)
                            dos.writeUTF("STR1");
                        else if (profil.isInGlobalChat || profil.isInBroadcast)
                            dos.writeUTF(original);
                        else
                            System.out.println("\nInput unknown...\n\n" + Message.helpMessage);

                        break;


                    case "UP": /**Under Construction*/

                        if (profil.isInGame /*&& something like "Game has started == true"*/)
                            dos.writeUTF("HXXD");
                        else if (profil.isInGlobalChat || profil.isInBroadcast)
                            dos.writeUTF(original);
                        else
                            System.out.println("\nInput unknown...\n\n" + Message.helpMessage);

                        break;


                    case "DOWN": /**Under Construction*/

                        if (profil.isInGame/*&& something like "Game has started == true"*/)
                            dos.writeUTF("DXXN");
                        else if (profil.isInGlobalChat || profil.isInBroadcast)
                            dos.writeUTF(original);
                        else
                            System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                        break;


                    case "LEFT": /**Under Construction*/

                        if (profil.isInGame /*&& something like "Game has started == true"*/)
                            dos.writeUTF("LXXT");
                        else if (profil.isInGlobalChat || profil.isInBroadcast)
                            dos.writeUTF(original);
                        else
                            System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                        break;


                    case "RIGHT": /**Under Construction*/

                        if (profil.isInGame/*&& something like "Game has started == true"*/)
                            dos.writeUTF("RXXT");
                        else if (profil.isInGlobalChat || profil.isInBroadcast)
                            dos.writeUTF(original);
                        else
                            System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                        break;

                    case "/": /**Under Construction*/

                        if (profil.isInGlobalChat) {
                            String msg = original.substring(1);
                            dos.writeUTF("WHP1" + msg);
                        } else
                            System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                        break;


                    default:

                        dos.writeUTF(original);

                        /*
                         * If client is in global chat, he can get out of it by sending <BACK>
                         * Also: Let player know if his input is unknown.
                         */

                        if (profil.isInGlobalChat) {
                            if (clientchoice.equalsIgnoreCase("BACK")) {
                                profil.isInGlobalChat = false;
                                System.out.println("\nYou have left the chat...\n");
                            }
                        } else {
                            System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                        }
                }
            }

            /*
            * Client is not active anymore
            * Input and Output will be closed
            */

            dis.close();
            dos.close();
            socket.close();
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }
    }
}