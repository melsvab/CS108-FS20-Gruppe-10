package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class ClientReaderThread implements Runnable {

     //This Thread is for reading and processing input coming from the server.


    DataInputStream dis;
    DataOutputStream dos;
    ClientProfil profil;
    
    public ClientReaderThread(DataInputStream dis, DataOutputStream dos, ClientProfil profil) {
            this.dis = dis;
            this.dos = dos;
            this.profil = profil;
    }

    public static boolean contains(String keyword) {

        for (Protocol p : Protocol.values()) {
            if (p.name().equals(keyword)) {
                return true;
            }
        }

        return false;
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
                String clientchoice = original.toUpperCase().substring(0, 4);

                if (contains(clientchoice)) {
                    switch (Protocol.valueOf(clientchoice)) {

                        case WELC:
                            //gets welcome message

                            System.out.println(Message.welcomeMessage + Message.changeName);
                            break;

                        case NAM2:
                            //gets welcome message

                            System.out.println("Your desired name exists already!");
                            break;

                        case NAM1:
                            //gets answer for a name change
                            String message = original.substring(5,lenghtInput);
                            System.out.println(message);
                            break;

                        case HELP:

                            //gets help message

                            System.out.println(Message.helpMessage);
                            break;

                        case QUIT:

                            //thread stops reading messages of the server

                            threadIsRunning = false;
                            break;

                        case ENDE:

                            //thread stops reading messages of the server

                            threadIsRunning = false;
                            break;

                        case PLL1:

                            //playerlist gets printed

                            System.out.println(original.substring(4));
                            break;

                        case GML1:

                            //Under Construction: received gamelist gets printed

                            System.out.println(Message.underConstruction);
                            break;

                        case HSC1:

                            //Under Construction: received highscore gets printed

                            System.out.println(original);
                            break;

                        case CRE1:
                            /*
                             * Under Construction: Informs player that the game is created
                             * and which game_ID it has
                             */

                            System.out.println(Message.underConstruction);

                            break;

                        case JOIN:
                            /*
                             * Under Construction: Informs the player that he joined the
                             * game and is in the lobby.
                             */

                            System.out.println("\nYou joined the game!\n");
                            break;

                        case EJON:
                            /*
                             * Under Construction: Informs the player that he could not join
                             * the game.
                             */

                            System.out.println("You could not join the game. Try another game_ID!");
                            break;

                        case STR1:
                            /*
                             * Under Construction: Informs players in the lobby that the game
                             * has started
                             */

                            System.out.println("\nThe game has started!\n");
                            break;

                        case WHP1:

                            //Under Construction: received whisperchat is printed

                            System.out.println("[" + "]:##" + original.substring(4) + "##");
                            break;

                        case EWHP:
                            /*
                             * Under Construction: prints an ERROR-Message if the chosen playername
                             * does not exist.
                             */

                            System.out.println("The playername was not found. The message could not be send.");
                            break;

                        case RNDS:

                            //Under Construction: prints the number of rounds (left)

                            System.out.println(Message.underConstruction);
                            break;

                        case YTRN:

                            //Under Construction: Informs the player that it's his turn

                            System.out.println(Message.underConstruction);
                            break;

                        case DICE:
                            /*
                             * Under Construction: Shows how many
                             * moves the player got (left) this round
                             */

                            System.out.println(Message.underConstruction);
                            break;

                        case ERMO:

                            //Under Construction: Informs the player that the move is invalid.

                            System.out.println(Message.underConstruction);
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

                        default:
                            //It should be impossible to get here
                            System.out.println("How did this end up here?" + "\n" + original);


                            break;

                    }

                } else {
                    /*
                     * Message might be from a chat
                     * in any case: Client sees on terminal what the server sent him/her
                     */
                    System.out.println(original);
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