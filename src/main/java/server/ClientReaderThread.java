package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientReaderThread implements Runnable {
    /**
     * This Thread is for reading and processing input coming from the server.
     */

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ClientProfil profil;
    
    public ClientReaderThread(
        DataInputStream dataInputStream, DataOutputStream dataOutputStream, ClientProfil profil) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.profil = profil;
    }

    public void run() {

        InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
        BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

        boolean threadIsRunning = true;

        try {

            while (threadIsRunning) {
    
                /**
                 * Get message from server (in LETTERS)
                 */
                String originalMessage = dataInputStream.readUTF();
                String keyword = originalMessage.substring(0,4);
                keyword = keyword.toUpperCase();

                switch (keyword) {

                    case "WELC":
                        /*
                        * gets welcome message
                        */
                        System.out.println(Message.welcomeMessage + Message.changeName);
                        
                        break;

                    case "HELP":
                        /*
                         * gets help message
                         */
                        System.out.println(Message.helpMessage);
                        
                        break;

                    case "QUIT":
                        /*
                         * thread stops reading messages of the server
                         */
                        threadIsRunning = false;
                        
                        break;

                    case "PLL2":
                        /*
                         * playerlist gets printed
                         */
                        System.out.println(originalMessage.substring(4));

                        break;

                    case "GML2":
                        /*
                         * received gamelist gets printed
                         */
                        System.out.println(Message.underConstruction);

                        break;

                    case "HSC2":
                        /*
                         * received highscore gets printed
                         */
                        System.out.println(Message.underConstruction);

                        break;

                    case "CRE2":

                        /*
                         *
                         */
                        System.out.println(Message.underConstruction);

                        break;

                    case "JON2":

                        System.out.println(Message.underConstruction);

                        break;

                    case "EJON":

                        System.out.println("You could not join the game. Try another game_ID!");

                        break;

                    //game is started
                    case "STR2":
                        /*
                         * gamelist gets printed
                         */
                        System.out.println("");
                        System.out.println("The game has started!");
                        System.out.println("");

                        break;

                    case "WHP2":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("[" + "]:##" + originalMessage.substring(4) + "##");

                        break;

                    case "EWHP":
                        /*
                         * prints an ERROR-Message if the chosen playername does not exist.
                         */
                        System.out.println("The playername was not found. The message could not be send.");

                        break;

                    //rounds are shown
                    case "RNDS":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("RNDS");

                        break;

                    //server signals your turn
                    case "YTRN":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("YTRN");

                        break;

                    //server will roll the dice between 1 and 6
                    case "DICE":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("DICE");

                        break;

                    //error: move is invalid
                    case "ERMO":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("ERMO");

                        break;

                    //points of the player for the move
                    case "POIN":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("POIN");

                        break;

                    //points of the player for getting a coin
                    case "POIC":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("POIC");

                        break;

                    //score of the player
                    case "SCOR":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("SCOR");

                        break;

                    //server shows how many steps are left
                    case "STPL":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("STPL");

                        break;

                    //server announces there are no steps
                    case "STPX":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("STPX");

                        break;

                    //event is happing
                    case "EVEN":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("EVEN");

                        break;

                    //player got hit by an event
                    case "DEAD":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("DEAD");

                        break;

                    //minus points get substracted for being hit by an event
                    case "MIPO":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("MIPO");

                        break;

                    //next turn is announced
                    case "NTRN":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("NTRN");

                        break;

                    //there are no more rounds
                    case "RNDX":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("RNDX");

                        break;

                    //even score between players
                    case "SCRE":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("SCRE");

                        break;

                    //an extra round is added
                    case "RNDA":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("RNDA");

                        break;

                    //winner is announced
                    case "WINX":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("WINX");

                        break;

                    //new highscore is added
                    case "HGHN":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("HGHN");

                        break;

                    //game ends
                    case "ENDX":
                        /*
                         * received whisperchat is printed
                         */
                        System.out.println("ENDX");

                        break;

                    default:
                        /*
                         * Message might be from a chat
                         * in any case: Client sees on terminal what the server sent him/her
                         */
                        System.out.println(originalMessage);

                        break;

                }


            }

            /*
             * thread will end soon so Input and Output will be closed
             */
            dataInputStream.close();
            dataOutputStream.close();

        } catch (IOException ioException) {
            System.err.println(ioException.toString());
        }

    }

}