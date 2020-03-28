package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client implements Runnable {
    String serverIpServerName;
    int serverPort;

    public Client(String ip, int serverPort){
        this.serverIpServerName = ip;
        this.serverPort = serverPort;
    }
    /**
     * This class represents a Client which connects to the server.
     * In here, client inputs will be sent to the server and will be
     * processed.
     */

    public static boolean contains(String keyword) {

        for (Protocol p : Protocol.values()) {
            if (p.name().equals(keyword)) {
                return true;
            }
        }

        return false;
    }

    public void run(){
        try {

            //Creates Client Profil

            ClientProfil profil = new ClientProfil();

            /**
             *chooses a server and port
             * and builds up a connection
             */

            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

        /*  System.out.println("\n\nPlease type in the IP-Address or the name of the Server: ");
            String serverIpServerName = readKeyBoard.readLine();

            System.out.println("\nPlease type in the port to be connected to: ");
            int serverPort = Integer.parseInt(readKeyBoard.readLine());

            System.out.println("\nConnection to Server \"" + serverIpServerName
                    + "\", to port " + serverPort + "...");
        */
            Socket socket = new Socket(serverIpServerName, serverPort);

            //Connection established.

            System.out.println("\n\nConnected!\n\n\n");

            //Create In- & Ouputstreams for reading and sending Strings

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            //sets DataOutputStream for the ChatGUI and creates an invisible chat
            profil.ccg.setDos(dos);
            profil.ccg.createChat();

            //Start ClientReaderThread for reading input from Server

            ClientReaderThread clientReaderThread = new ClientReaderThread(dis, dos, profil);
            Thread clientThread = new Thread(clientReaderThread);
            clientThread.start();

            //Choose nickname

            String nickname = readKeyBoard.readLine();
            if (nickname.equalsIgnoreCase("YEAH")) {
                nickname = System.getProperty("user.name");
            }

            //Nickname chosen

            dos.writeUTF(nickname);

            //Start processing inputs.


            while (profil.clientIsOnline) {

                //Read keyboardinput from client.

                String original = readKeyBoard.readLine();
                int lenghtInput = original.length();
                while (lenghtInput < 4) {
                    System.out.println("YOU HAVE NOT DONE THIS RIGHT");
                    original = readKeyBoard.readLine();
                    lenghtInput = original.length();
                }
                String clientchoice = original.toUpperCase().substring(0, 4);

                if (contains(clientchoice)) {

                    switch (Protocol.valueOf(clientchoice)) {
                        //Descide what to do next


                        /*case CHAT: //Not needed anymore because of JFrame


                            /
                              checks for appropriate input and if client is in a lobby
                             /

                            if (lenghtInput > 5 && profil.isInGame) {
                                dos.writeUTF(original);
                            } else if(!profil.isInGame) {
                                System.out.println("\nYou have not joined a lobby yet!\n");
                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.CHAT.name()
                                        + ":message");
                            }

                            break; */

                        case BRC1:
                            //in case clients forgets to send a message
                            if (lenghtInput > 5) {
                                dos.writeUTF(original);
                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.BRC1.name()
                                        + ":message");
                            }

                            break;

                        case NAME:
                            /*
                             * gets message that there is the option to use system username
                             * and analyses answer from Client to this question
                             */

                            if(profil.checkForName(original)) {
                                String newNickname = original.substring(5);

                                /*
                                 * if the answer is <YEAH> the nickname is change to the system username
                                 * if the answer is something else, this input will be used as the
                                 * nickname
                                 */

                                if (newNickname.equalsIgnoreCase("YEAH")) {
                                    newNickname = System.getProperty("user.name");
                                }

                                /*
                                 * sending the desired nickname to server
                                 */

                                dos.writeUTF(Protocol.NAME.name() + ":" + newNickname);


                            } else {
                                //in case client forgets to send his/her desired name
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.NAME.name()
                                        + ":desiredName");
                            }

                            break;


                        case QUIT:
                            /*
                             * informing client about his choice.
                             * If player is not active he cannot write anymore.
                             */
                            dos.writeUTF(clientchoice);
                            System.out.println("\nClosing program...\n");
                            profil.clientIsOnline = false;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                System.err.println(e.toString());
                            }
                            break;

                        case ENDE:

                            dos.writeUTF(clientchoice);
                            System.out.println("\nStop server...\n");
                            profil.clientIsOnline = false;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                System.err.println(e.toString());
                            }
                            break;

                        case PLL1:

                            dos.writeUTF("PLL1");
                            break;

                        case GML1: /**Under Construction*/

                            dos.writeUTF("GML1");
                            break;


                        case HSC1: /**Under Construction */

                            dos.writeUTF("HSC1");
                            break;

                        case CRE1: /* check if input is correct for a new lobby */

                            if (profil.checkForTwoInt(original)) {
                                dos.writeUTF(original);
                                profil.ccg.setVisible(true);
                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.CRE1.name()
                                        + ":boardsize:maximumNumberOfPoints");
                            }

                            break;

                        case JOIN: /**Under Construction*/

                            if (profil.isInGame) {
                                System.out.println("You already joined a lobby!");

                            } else if (profil.checkForNumber(original)) {
                                dos.writeUTF(original);
                                profil.ccg.setVisible(true);
                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.JOIN.name()
                                        + "number");
                            }
                            break;


                        case STR1: /**Under Construction*/

                            if (profil.isInGame /*&& something like "Game has started == false"*/) {
                                dos.writeUTF("STR1");
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case UPPR: /**Under Construction*/

                            if (profil.isInGame /*&& something like "Game has started == true"*/) {
                                dos.writeUTF("UPPR");
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case DOWN: /**Under Construction*/

                            if (profil.isInGame/*&& something like "Game has started == true"*/) {
                                dos.writeUTF("DOWN");
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case LEFT: /**Under Construction*/

                            if (profil.isInGame /*&& something like "Game has started == true"*/) {
                                dos.writeUTF("LEFT");
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case RIGT: /**Under Construction*/

                            if (profil.isInGame/*&& something like "Game has started == true"*/) {
                                dos.writeUTF("RIGT");
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case BACK: /**Under Construction*/
                            //will be used for to end e whisperchat

                        case WHP1: /**Under Construction*/
                            //whisperchat

                            if (profil.isInGame) {
                                String msg = original.substring(1);
                                dos.writeUTF("WHP1" + msg);
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case IDKW:

                            System.out.println("STOP BULLSHITTING");

                            /* our secret cheat code */
                            //dos.writeUTF(original);

                            /*try {
                                System.out.println("\n...let me help you...\n");
                                Thread.sleep(2000);
                                System.out.println("Please type in a number:\n\n");
                                int howLong = Integer.parseInt(readKeyBoard.readLine());
                                System.out.println("\n");
                                for (int i = 0; i < howLong; i++) {
                                    Thread.sleep(2000);
                                    System.out.print("This ");
                                    Thread.sleep(1000);
                                    System.out.print("is ");
                                    Thread.sleep(1000);
                                    System.out.print("gonna ");
                                    Thread.sleep(1000);
                                    System.out.print("take ");
                                    Thread.sleep(1000);
                                    System.out.print("a ");
                                    Thread.sleep(1000);
                                    System.out.print("while");
                                    Thread.sleep(2000);
                                    System.out.print("... ");
                                    Thread.sleep(3000);
                                    System.out.print("sorry ");
                                    Thread.sleep(2000);
                                    System.out.print("not ");
                                    Thread.sleep(10);
                                    System.out.print("sorry");
                                    Thread.sleep(10);
                                    System.out.print("... ");
                                    Thread.sleep(3000);
                                    System.out.print(i + 1);
                                    Thread.sleep(1000);
                                    System.out.print(" out ");
                                    Thread.sleep(1000);
                                    System.out.print("of ");
                                    Thread.sleep(1000);
                                    System.out.print(howLong);
                                    Thread.sleep(1000);
                                    System.out.print("... ");
                                    Thread.sleep(2000);
                                }
                                System.out.println("\n" + Message.helpMessage);
                            } catch (InterruptedException e) {
                                System.err.println(e.toString());
                            }*/

                            break;


                        default: //this should be impossible if you typed in correctly
                            System.out.println("You cannot use this keyword.");
                            break;

                    }

                } else {
                    System.out.println("This keyword does not exist.");
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