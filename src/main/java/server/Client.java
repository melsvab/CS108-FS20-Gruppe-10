package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gui.*;




/**
 * @author Dennis, Natasha, Melanie, Rohail
 * This class represents a client which connects to the server.
 * In here, client inputs will be sent to the server and will be processed.
 */
public class Client implements Runnable {
    public static final Logger logger = LoggerFactory.getLogger(Client.class);

    /**
     * The Server ip server name.
     */
    String serverIpServerName;
    /**
     * The Server port.
     */
    int serverPort;
    /**
     * The Profile.
     */
    public Profil profile = new Profil();

    /**
     * creates a client constructor
     *
     * @param name the name
     */
    public Client(String ... name) throws IOException {
        this.serverIpServerName = name[0];
        this.serverPort = Integer.parseInt(name[1]);
        if (name.length == 3) {
            profile.nickname = name[2];
        } else {
            profile.nickname = System.getProperty("user.name");
        }

    }

    /**
     * checks if the keyword exists in our protocol.
     *
     * @param keyword the keyword
     * @return if the keyword exists in our protocol or not
     */
    public static boolean contains(String keyword) {

        for (Protocol p : Protocol.values()) {
            if (p.name().equals(keyword)) {
                return true;
            }
        }

        return false;
    }


    /**
     * chooses a server and port, builds up a connection and starts the ClientReaderThread
     * for reading input from Server. It sets a DataOutputStream for the ChatGUI and
     * creates an invisible chat that processes input.
     */
    public void run() {
        try {

            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

            Socket socket = new Socket(serverIpServerName, serverPort);
            logger.info("Socket created");
            System.out.println("\nConnected!\n");

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            ClientReaderThread clientReaderThread = new ClientReaderThread(dis, dos, profile);
            Thread clientThread = new Thread(clientReaderThread);
            logger.info("clientreaderThread started");
            clientThread.start();

            // The nickname given will be changed to system username if wanted.

            if (profile.nickname.equalsIgnoreCase("YEAH")) {
                profile.nickname = System.getProperty("user.name");
            }

            //Nickname chosen

            dos.writeUTF(profile.nickname);

            //sets DataOutputStream for the ClientChatGUI and ButtonsClient
            profile.mainFrame.chat.setDos(dos);
            profile.mainFrame.buttonsClient.setDosProLogger(dos, profile, logger);

            //Start processing inputs.
            while (profile.clientIsOnline) {

                //Reads keyboard input from client.

                String original = readKeyBoard.readLine();
                int lengthInput = original.length();
                while (lengthInput < 4) {
                    System.out.println("This keyword is too short. Try again!");
                    original = readKeyBoard.readLine();
                    lengthInput = original.length();
                }
                String clientchoice = original.toUpperCase().substring(0, 4);

                if (contains(clientchoice)) {

                    switch (Protocol.valueOf(clientchoice)) {

                        case HELP:

                            System.out.println(Message.helpMessage);
                            break;

                        case QUIT:
                            logger.info("Quitting");
                            /*
                             * Informing server about his / her choice.
                             * If player is not active he / she cannot write anymore.
                             */
                            dos.writeUTF(clientchoice);
                            System.out.println("\nClosing program...\n");
                            profile.clientIsOnline = false;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                System.err.println(e.toString());
                            }
                            break;


                        case ENDE:

                            dos.writeUTF(clientchoice);
                            System.out.println("\nStop server...\n");
                            profile.clientIsOnline = false;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                System.err.println(e.toString());
                            }
                            break;

                        case NAME:
                            logger.info("changing the name");

                            Parameter name = new Parameter(original, 3);
                            if (name.isCorrect) {
                                String newNickname = name.wordOne;

                                /*
                                 * if the answer is <YEAH> the nickname is change to the
                                 * system username. If the answer is something else,
                                 * this input will be used as the nickname.
                                 */

                                if (newNickname.equalsIgnoreCase("YEAH")) {
                                    newNickname = System.getProperty("user.name");
                                }

                                // sending the desired nickname to server

                                dos.writeUTF(Protocol.NAME.name() + ":" + newNickname);


                            } else {
                                //in case the client forgets to send his/her desired name
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.NAME.name()
                                        + ":desiredName");
                            }

                            break;

                        case PLL1:
                            logger.info("asked for list of all players");
                            /*
                             * Sends keyword to server.
                             * Here: client asks for the list of all players that are currently on the server
                             *
                             */
                            dos.writeUTF(Protocol.PLL1.name());
                            break;

                        case GML1:

                            /*
                             * Sends keyword to server.
                             * Here: client asks for the list of all games and their status
                             *
                             */

                            dos.writeUTF(Protocol.GML1.name());
                            break;

                        case HSC1:
                            logger.info("asking for Highscore");
                            /*
                             * Sends keyword to server.
                             * Here: client asks for the high score list
                             *
                             */

                            dos.writeUTF(Protocol.HSC1.name());
                            break;

                        case CRE1:
                            logger.info("created a new lobby");
                            // This keyword is used to create a new lobby.
                            if (profile.isInGame) {
                                // Clients cannot join a new lobby if they are already in one.
                                System.out.println(Message.inLobbyAlready);
                            } else {
                                dos.writeUTF(original);
                                dos.writeUTF(Protocol.CHAT.name() + ":" + Message.enterLobby);
                            }
                            break;

                        case BACK:
                            // This keyword is used to go out of a lobby.
                            if (profile.isInGame) {
                                dos.writeUTF(Protocol.BACK.name());
                            } else {
                                System.out.println("You have not joined a lobby yet "
                                        + "so there is no need to go back!");
                            }
                            break;

                        case JOIN:
                            logger.info("joined a lobby");
                            // This keyword is used to join a lobby as a player.
                            Parameter gameNumber = new Parameter(original, 5);
                            if (profile.isInGame) {
                                System.out.println(Message.inLobbyAlready);

                            } else if (gameNumber.isCorrect) {
                                dos.writeUTF(original);
                                dos.writeUTF(Protocol.CHAT.name() + ":" + Message.enterLobby);
                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.JOIN.name()
                                        + ":number");
                            }
                            break;

                        case SPEC:
                            // This keyword is used to join a lobby as a spectator.
                            logger.info("joined the lobby as a spectator");
                            Parameter gameNumberToWatch = new Parameter(original, 5);
                            if (profile.isInGame) {
                                System.out.println(Message.inLobbyAlready);

                            } else if (gameNumberToWatch.isCorrect) {
                                dos.writeUTF(original);
                                dos.writeUTF(Protocol.CHAT.name() + ":" + Message.enterLobby);
                            } else {
                                System.out.println(Message.youAreDoingItWrong
                                        + Protocol.SPEC.name()
                                        + ":number");
                            }

                            break;

                        case STR1:
                            // This keyword is used to start a game while you are in a lobby
                            Parameter boardsizeAndMaxCoins = new Parameter(original, 5);
                            if (boardsizeAndMaxCoins.isCorrect && profile.isInGame) {
                                dos.writeUTF(original);

                            } else {
                                System.out.println(Message.youAreDoingItWrong + Protocol.STR1.name()
                                        + ":boardsize:maximumNumberOfPoints and "
                                        + "you must be in a lobby");
                            }
                            break;

                        case UPPR:

                            //To do: check for >board != null<
                            if (profile.isInGame && !profile.isSpectator) {
                                dos.writeUTF(Protocol.UPPR.name());
                            } else {
                                System.out.println(Message.youCannotDoThat);
                            }
                            break;

                        case DOWN:

                            if (profile.isInGame && !profile.isSpectator) {
                                dos.writeUTF(Protocol.DOWN.name());
                            } else {
                                System.out.println(Message.youCannotDoThat);
                            }
                            break;

                        case LEFT:

                            if (profile.isInGame && !profile.isSpectator) {
                                dos.writeUTF(Protocol.LEFT.name());
                            } else {
                                System.out.println(Message.youCannotDoThat);
                            }
                            break;

                        case RIGT:

                            if (profile.isInGame && !profile.isSpectator) {
                                dos.writeUTF(Protocol.RIGT.name());
                            } else {
                                System.out.println(Message.youCannotDoThat);
                            }
                            break;

                        case IDKW:
                            //our secret cheat code

                            dos.writeUTF(Protocol.IDKW.name());

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

            profile.mainFrame.closeFrame();
            dis.close();
            dos.close();
            socket.close();
            System.exit(1);

        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }
    }
}