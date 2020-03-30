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
    Profil profil = new Profil();

    public Client(String ip, int serverPort,String name){
        this.serverIpServerName = ip;
        this.serverPort = serverPort;
        profil.nickname = name;
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


            /**
             *chooses a server and port
             * and builds up a connection
             */

            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

            Socket socket = new Socket(serverIpServerName, serverPort);

            //Connection established.

            System.out.println("\nConnected!\n");

            //Create In- & Ouputstreams for reading and sending Strings

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            //Start ClientReaderThread for reading input from Server

            ClientReaderThread clientReaderThread = new ClientReaderThread(dis, dos, profil);
            Thread clientThread = new Thread(clientReaderThread);
            clientThread.start();

            //Choose nickname

            if (profil.nickname.equalsIgnoreCase("YEAH")) {
                profil.nickname = System.getProperty("user.name");
            }

            //Nickname chosen

            dos.writeUTF(profil.nickname);

            //sets DataOutputStream for the ChatGUI and creates an invisible chat
            profil.ccg.setDos(dos);
            profil.ccg.createChat();

            //Start processing inputs.


            while (profil.clientIsOnline) {

                //Read keyboardinput from client.

                String original = readKeyBoard.readLine();
                int lenghtInput = original.length();
                while (lenghtInput < 4) {
                    System.out.println("This keyword is too short. Try again!");
                    original = readKeyBoard.readLine();
                    lenghtInput = original.length();
                }
                String clientchoice = original.toUpperCase().substring(0, 4);

                if (contains(clientchoice)) {

                    switch (Protocol.valueOf(clientchoice)) {
                        //Descide what to do next

                        /*case BRC1: //not needed because of JFrame
                            //in case clients forgets to send a message
                            if (lenghtInput > 5) {
                                dos.writeUTF(original);
                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.BRC1.name()
                                        + ":message");
                            }

                            break;*/

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

                            dos.writeUTF(Protocol.PLL1.name());
                            break;

                        case GML1: /*Under Construction*/

                            dos.writeUTF(Protocol.GML1.name());
                            break;

                        case HSC1: /*Under Construction */

                            dos.writeUTF("HSC1");
                            break;

                        case CRE1: /* check if input is correct for a new lobby */

                            if (profil.isInGame) {
                                System.out.println(Message.inLobbyAlready);
                            } else {
                                dos.writeUTF(original);
                                dos.writeUTF(Protocol.CHAT.name() + ":" + Message.enterLobby);
                            }
                            break;

                        case JOIN:

                            if (profil.isInGame) {
                                System.out.println(Message.inLobbyAlready);

                            } else if (profil.checkForNumber(original)) {
                                dos.writeUTF(original);
                                dos.writeUTF(Protocol.CHAT.name() + ":" + Message.enterLobby);
                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.JOIN.name()
                                        + ":number");
                            }
                            break;

                        case BACK:
                            if (profil.isInGame) {
                                dos.writeUTF(Protocol.BACK.name());
                            } else {
                                System.out.println("You have not joined a lobby yet so there is no need to go back!");
                            }
                            break;

                        case STR1:

                            if (profil.checkForTwoInt(original) && profil.isInGame) {
                                dos.writeUTF(original);

                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.CRE1.name()
                                        + ":boardsize:maximumNumberOfPoints and you must be in a lobby");
                            }
                            break;

                        case UPPR:

                            if (profil.isInGame /*&& something like "Game has started == true"*/) {
                                dos.writeUTF(Protocol.UPPR.name());
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case DOWN: /*Under Construction*/

                            if (profil.isInGame/*&& something like "Game has started == true"*/) {
                                dos.writeUTF(Protocol.DOWN.name());
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case LEFT: /*Under Construction*/

                            if (profil.isInGame /*&& something like "Game has started == true"*/) {
                                dos.writeUTF(Protocol.LEFT.name());
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case RIGT: /*Under Construction*/

                            if (profil.isInGame/*&& something like "Game has started == true"*/) {
                                dos.writeUTF(Protocol.RIGT.name());
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case WHP1: /*Under Construction*/
                            //whisperchat

                            if (profil.isInGame) {
                                String msg = original.substring(1);
                                dos.writeUTF("WHP1" + msg);
                            } else {
                                System.out.println("\nInput unknown...\n\n" + Message.helpMessage);
                            }
                            break;

                        case IDKW:

                            System.out.println("STOP THAT!");

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

            profil.ccg.closeChat();
            dis.close();
            dos.close();
            socket.close();

        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }
    }
}