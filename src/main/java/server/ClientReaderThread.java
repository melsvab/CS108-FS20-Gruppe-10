package server;

import java.io.*;
import game.Board;
import game.PlayerTurtle;



/**
 * @author Natasha, Dennis, Melanie, Rohail
 * This thread is for reading and processing input coming from the server.
 * If a message from the ServerThreadForClient is send from different methods,
 * there will be a specific keyword.Otherwise there are general keywords that are used by different
 * methods that send a message only once.
 */
public class ClientReaderThread implements Runnable {

    /**
     * The Dis.
     */
    DataInputStream dis;
    /**
     * The Dos.
     */
    DataOutputStream dos;
    /**
     * The Profile.
     */
    Profil profile;
    Board game = null;
    PlayerTurtle[] turtles = null;
    int turtlePlace = 0;

    /**
     * Instantiates a new Client reader thread.
     *
     * @param dis    the dis
     * @param dos    the dos
     * @param profil the profil
     */
    public ClientReaderThread(DataInputStream dis, DataOutputStream dos, Profil profil) {
        this.dis = dis;
        this.dos = dos;
        this.profile = profil;
    }

    /**
     * checks if the keyword exists in  our protocol
     *
     * @param keyword the keyword
     * @return true or false
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
     * Check message boolean.
     *
     * @param original the original
     * @return boolean
     */
    public boolean checkMessage(String original) {

        return original.length() > 5;
    }

    /**
     * Start the reader thread
     */
    public void run() {
        InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
        BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);
        boolean threadIsRunning = true;

        try {

            while (threadIsRunning) {


                // Get message from server (in LETTERS)

                String original = dis.readUTF();
                int lengthInput = original.length();
                while (lengthInput < 4) {
                    original = dis.readUTF();
                    lengthInput = original.length();
                }
                String clientChoice = original.toUpperCase().substring(0, 4);

                if (contains(clientChoice)) {
                    switch (Protocol.valueOf(clientChoice)) {

                        case WELC:
                            /*
                             * Gets welcome message
                             *
                             * To do: logo/intro will be shown
                             */

                            System.out.println("\nWelcome to the server!\n\n");
                            break;

                        case HELP:

                            //Client gets a help message

                            profile.mainFrame.chat.receiveMsg(Message.helpMessage);
                            break;

                        case MSG1:
                            /*
                             * Client sees an error message in the chat
                             */
                            if (checkMessage(original)) {
                                profile.mainFrame.chat.receiveMsg(original.substring(5) + Message.nobodyHearsYou);
                            }
                            break;

                        case EWHP:
                            /*
                             * playerDoesNotExist Error printed in ChatArea
                             */
                            profile.mainFrame.chat.receiveMsg(Message.playerDoesNotExist);
                            break;

                        case MSG0:
                            /*
                             * Client sees a chat message
                             */
                            if (checkMessage(original)) {
                                profile.mainFrame.chat.receiveMsg(original.substring(5));
                            }
                            break;

                        case MSSG:
                            /*
                             * Prints out informational message from the server
                             *
                             * This will be a small text box in the future.
                             * The text will be white or black (depends on the background).
                             */

                            if (checkMessage(original)) {
                                System.out.println(original.substring(5));
                            } else {
                                System.out.println(Message.garbage + " *1");
                            }
                            break;

                        case ERRO:
                            /*
                             * Client gets a message from the server that a command
                             * did not have the impact that is desired by the client.
                             *
                             * The text will be red in the future
                             * and added to the text box for informational messages (see keyword >MSSG<).
                             */

                            if (checkMessage(original)) {
                                String errorText = original.substring(5);
                                System.out.println(errorText);
                            } else {
                                System.out.println(Message.garbage + " *2");
                            }

                            break;

                        case QUIT:

                            //Thread stops reading messages of the server

                            threadIsRunning = false;
                            break;

                        case LIST:
                            /*
                             * Prints out lists
                             *
                             * This will be a text box in the middle of the screen soon.
                             *
                             */

                            if (checkMessage(original)) {
                                profile.mainFrame.chat.receiveMsg(original.substring(5));
                            } else {
                                profile.mainFrame.chat.receiveMsg(Message.garbage + " *1.5");
                            }
                            break;

                        case SPEC:
                            //message will be send so client knows that he/she is a spectator
                            profile.isSpectator = true;
                            break;

                        case CRE2:
                            /*
                             * Informs player that the game is created
                             * and which game_ID it has
                             */
                            Parameter number = new Parameter(original, 5);
                            if (number.isCorrect) {
                                profile.isInGame = true;
                                String[] words = original.split(":");
                                int lobbyNumber = Integer.parseInt(words[1]);

                                profile.mainFrame.chat.receiveMsg("You entered lobby number " + lobbyNumber + "!");
                            } else {
                                profile.mainFrame.chat.receiveMsg(Message.garbage + " *3");
                            }

                            break;

                        case BACK:
                            profile.isInGame = false;
                            profile.isSpectator = false;
                            game = null;
                            turtles = null;
                            turtlePlace = 0;

                            //Client went out of a lobby / game
                            profile.mainFrame.chat.receiveMsg("You are not in a lobby anymore!");
                            break;

                        case STR1:
                            Parameter boardInfo = new Parameter(original, 1);
                            if (boardInfo.isCorrect) {
                                // boardInfo.numberOne == boardSize
                                this.game = new Board(boardInfo.numberOne);
                                // boardInfo.numberTwo == numberOfPlayers
                                this.turtles = new PlayerTurtle[boardInfo.numberTwo];
                            }
                            break;

                        case RNDS:

                            int rounds = Integer.parseInt(original.substring(5));
                            if (rounds <= 8) {
                                System.out.println("\n -----------------------\n Round " + rounds
                                    + "\n There are " + (10 - rounds)
                                    + " rounds left \n -----------------------\n");
                            } else if (rounds == 9) {
                                System.out.println("\n -----------------------\n Round " + rounds
                                    + "\n There is " + (10 - rounds)
                                    + " round left \n -----------------------\n");
                            } else {
                                System.out.println("\n -----------------------\n Round " + rounds
                                        + "\n Last round \n -----------------------\n");
                            }
                            break;

                        case WINR:
                            int i = original.indexOf(":", 5);
                            String winner = original.substring(5, i);
                            String points = original.substring(i + 1);
                            profile.mainFrame.chat.receiveMsg("The winner is: " + winner + " with " + points
                                + " points!");
                            break;

                        case LOBY:
                            /*
                             * Client prints out information about the board
                             * (that will have a graphical impact later)
                             *
                             *
                             * info: this is identical with case ERRO right now,
                             * but that will change soon due to the fact that changes from the
                             * board will be analysed in the future
                             */
                            if (checkMessage(original)) {
                                String message = original.substring(5);
                                System.out.println(message);
                            } else {
                                System.out.println(Message.garbage + " *4");
                            }
                            break;

                        case COIN:

                            /*
                             * This is not in use
                             */

                            break;

                        case TURS:

                            /*
                             * This is a set up for the start position for the turtles
                             */
                            System.out.println(original);
                            Parameter startPos = new Parameter(original, 6);
                            if (startPos.isCorrect) {
                                int x = startPos.positions[0][0];
                                int y = startPos.positions[0][1];

                                turtles[turtlePlace] = new PlayerTurtle(startPos.numberOne, startPos.wordOne, x, y);
                                turtlePlace++;
                            }

                            break;

                        case TUST:

                            /*
                             * This is not in use
                             */

                            break;

                        case TURT:

                            /*
                             * This is not in use
                             */

                            break;

                        case DRAW:

                            /*
                             * This is not in use
                             */

                            break;

                        case POIN:

                            /*
                             * This is not in use
                             */

                            break;
                        case QUAK:

                            /*
                             * This is not in use
                             */

                            break;

                        case WATR:

                            /*
                             * This is not in use
                             */

                            break;

                        case RSET:

                            /*
                             * changes fields with flood or earthquake back to normal
                             */
                            if (game != null) {
                                game.afterEvent();
                            }

                            break;

                        case TEST:

                            /*
                             * This is used in case of an connection lost.
                             * If the message can be read, than there is no connection lost
                             * Therefore this is only in use if we test our program!
                             */

                            break;

                        default:
                            //It should be impossible to get here
                            System.out.println("How did this end up here?" + "\n" + original);

                            break;

                    }

                } else {

                    //This is only possible if the server is sending garbage
                    System.out.println(Message.garbage + " *5\n" + original);

                }
            }



            //thread will end soon so Input and Output will be closed

            dis.close();
            dos.close();

        } catch (IOException ioException) {
            System.err.println(ioException.toString());
        }
    }
}