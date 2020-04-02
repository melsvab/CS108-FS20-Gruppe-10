package server;

import game.PlayerTurtle;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class ClientReaderThread implements Runnable {

     /*
      * This thread is for reading and processing input coming from the server.
      * If a message from the ServerThreadForClient is send from different methods, there will be a specific keyword.
      * Otherwise there are general keywords that are used by different methods that send a message only once.
      */


    DataInputStream dis;
    DataOutputStream dos;
    Profil profile;
    
    public ClientReaderThread(DataInputStream dis, DataOutputStream dos, Profil profil) {
            this.dis = dis;
            this.dos = dos;
            this.profile = profil;
    }

    public static boolean contains(String keyword) {

        for (Protocol p : Protocol.values()) {
            if (p.name().equals(keyword)) {
                return true;
            }
        }

        return false;
    }

    public boolean checkMessage(String original) {
        return original.length() > 5;
    }

    public void run() {
        InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
        BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);
        boolean threadIsRunning = true;

        try {

            while (threadIsRunning) {


                //Get message from server (in LETTERS)

                String original = dis.readUTF();
                int lenghtInput = original.length();
                while (lenghtInput < 4) {
                    original = dis.readUTF();
                    lenghtInput = original.length();
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

                        case HELP:

                            //Client gets a help message

                            System.out.println(Message.helpMessage);
                            break;

                        case QUIT:

                            //Thread stops reading messages of the server

                            threadIsRunning = false;
                            break;


                        case HSC1:

                            //Under Construction: received highscore gets printed

                            System.out.println(original);
                            break;

                        case CRE2:
                            /*
                             * Informs player that the game is created
                             * and which game_ID it has
                             */
                            if (profile.checkForNumber(original)) {
                                profile.isInGame = true;
                                String[] words = original.split(":");
                                int lobbyNumber = Integer.parseInt(words[1]);

                                System.out.println("You entered lobby number " + lobbyNumber + "!");
                            } else {
                                System.out.println(Message.garbage + " *3");
                            }

                            break;

                        case EWHP:
                            /*
                             * playerDoesNotExist Error printed in ChatArea
                             */
                            profile.ccg.receiveMsg(Message.playerDoesNotExist);
                            break;

                        case BACK:
                            profile.isInGame = false;

                            //Client got out of a lobby / game
                            System.out.println("You are not in a lobby anymore!");
                            break;

                        case RNDS:

                            //Under Construction: prints the number of rounds (left)

                            System.out.println(Message.underConstruction);
                            break;

                        case YTRN:

                            //Under Construction: Informs the player that it's his turn

                            System.out.println(Message.underConstruction);
                            break;

                        case LOBY:
                            /*
                             * Client prints out information about the board
                             * (that will have a graphical impact later)
                             *
                             *
                             * info: this is identical with case ERRO right now, but that will change soon
                             * due to the fact that changes from the board will be analysed in the future
                             */
                            if (checkMessage(original)) {
                                String message = original.substring(5);
                                System.out.println(message);
                            } else {
                                System.out.println(Message.garbage + " *4");
                            }
                            break;

                        case DICE:
                            /*
                             * Under Construction: Shows how many
                             * moves the player got (left) this round
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case ERMO:

                            //Informs the player that the move is invalid.
                            System.out.println("This move is not possible");
                            break;


                        case POIN:
                            /*
                             * Under Construction: Show the player how many points he got for
                             * this move
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case POIC:
                            /*
                             * Under Construction: Shows the player that he got extra points
                             * for getting a coin
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case SCOR:

                            //Under Construction: shows the score of the player

                            System.out.println(Message.underConstruction);
                            break;

                        case STPX:
                            /*
                             * Under Construction: Prints that there are
                             * no more moves left
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case EVEN:
                            /*
                             * Under Construction: Server announces that
                             * an event is happening (flood/earthquake)
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case DEAD:
                            /*
                             * Under Construction: Informs the player that he was hit by an event.
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case MIPO:
                            /*
                             * Under Construction: Player gets minus points.
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case NTRN:
                            /*
                             * Under Construction: Informs the players that the next round starts
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case RNDX:
                            /*
                             * Under Construction: Informs the players that no more rounds are left
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case RNDA:
                            /*
                             * Under Construction: extra round is added if a even score
                             * exists between two or more players at the end.
                             */
                            System.out.println(Message.underConstruction);
                            break;

                        case WINX:
                            /*
                             * Under Construction: Winner is announced
                             */
                            System.out.println(Message.underConstruction);
                            break;

                        //new highscore is added
                        case HGHN:
                            /*
                             * Under Construction: Message that a new highscore was added
                             */
                            System.out.println(Message.underConstruction);
                            break;

                        case ENDX:
                            /*
                             * Under Construction: Informs the player that the game closed
                             *
                             */
                            System.out.println(Message.underConstruction);
                            break;

                        case MSG0:
                            /*
                             * Client sees a chat message
                             */
                            if(checkMessage(original)) {
                                profile.ccg.receiveMsg(original.substring(5));
                            }
                            break;

                        case MSG1:
                            /*
                             * Client sees an error message in the chat
                             */
                            if (checkMessage(original)) {
                                profile.ccg.receiveMsg(original.substring(5) + Message.nobodyHearsYou);
                            }
                            break;

                        default:
                            //It should be impossible to get here
                            System.out.println("How did this end up here?" + "\n" + original);

                            break;

                    }

                } else {

                    //This is only possible if the server is sending garbage
                    System.out.println(Message.garbage + " *5");

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