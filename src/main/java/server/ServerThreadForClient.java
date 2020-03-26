package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

public class ServerThreadForClient implements Runnable {

    /**
     * This Thread is created for every Client that connects
     * to the server. Represents an interface between Client & server.
     */

    /**
     * In- & Ouputstreams for reading and sending Strings.
     */

    DataInputStream dis;
    DataOutputStream dos;

    /**
     * Every Thread gets a client Profile
     */

    ClientProfil clientProfil;
    
    //Constructor (creats a new clientProfil)
    
    public ServerThreadForClient(
        int clientID, DataInputStream dis, DataOutputStream dos) {
            this.clientProfil = new ClientProfil(clientID);
            this.dis = dis;
            this.dos = dos;
    }

    /**
     * Check if Client is in global Chat.
     */
    public boolean globalChat() {
        return clientProfil.isInGlobalChat;
    }

    /**
     * Chatfunction sends a message to client.
     */
    void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (Exception exception) {
        System.err.println(exception.toString());
        }
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
        try {
            
            /**
             * Say Hello to client and let him choose his nickname.
             */
            
            dos.writeUTF(Protocol.WELC.name());

            /**
             * Receiving nickname by Client and checking for duplicates.
             * Let Player know his nickname.
             */
            
            String nickname = dis.readUTF();
            clientProfil.nickname = Server.checkForDublicates(nickname, this);

            System.out.println("\nNickname of client #" + clientProfil.clientID + ": "
                    + clientProfil.nickname);
            dos.writeUTF("Your nickname: " + clientProfil.nickname + "\n");
            /**Nickname chosen. */

            /**
             * Ask client what he wants to do.
             */
            dos.writeUTF(Protocol.HELP.name());

            while (clientProfil.clientIsOnline) {

                /**
                 * Get choice from Client and decide what to do.
                 */
                String original = dis.readUTF();
                int lenghtInput = original.length();
                String clientchoice = original.toUpperCase().substring(0, 4);

                if (contains(clientchoice)) {

                    switch (Protocol.valueOf(clientchoice)) {

                        case CHAT:

                            clientProfil.isInGlobalChat = true;

                            System.out.println("\n\n" + clientProfil.nickname + " has joined the chat!\n");

                            String userJoined = (clientProfil.nickname + " has joined the chat!\n");
                            Server.globalChat(userJoined);

                            break;

                        case NAME:

                            //old name will be removed from the server list, new name is checked for dublicates
                            String oldNickname = clientProfil.nickname;
                            Server.namesOfAllClients.remove(clientProfil.nickname);
                            String desiredName = original.substring(5);
                            desiredName = Server.checkForDublicates(desiredName, this);
                            String answerToClient = Protocol.NAM1.name() +
                                    ":Your name has been changed from " + oldNickname +
                                    " to " + desiredName + "\n";
                            //write decision to Client
                            dos.writeUTF(answerToClient);

                            //server has accepted new name
                            System.out.println(
                                    "\n" + clientProfil.nickname + " changed his/her name to " + desiredName);
                            clientProfil.nickname = desiredName;

                            //If in Chat, let others know - TO DO: change to enum friendly version
                            if (clientProfil.isInGlobalChat) {
                                String confirmation = (
                                        oldNickname + " changed his/her nickname to " + clientProfil.nickname + "!\n");
                                Server.globalChat(confirmation);
                            }

                            break;

                        case QUIT:

                            dos.writeUTF(clientchoice);


                            //Remove name and thread of this client form the list on the server

                            Server.removeUser(clientProfil.nickname, this);

                            //Give Feedback

                            System.out.println("\nClient #" + clientProfil.clientID + " \""
                                    + clientProfil.nickname + "\" has disconnected.");
                            clientProfil.clientIsOnline = false;
                            break;

                        case ENDE:
                            /**
                             * Under Construction: Can stop server
                             */
                            dos.writeUTF(clientchoice);
                            Server.broadcast("Our server goes to sleep", Server.userThreads);
                            Server.serverIsOnline = false;
                            break;

                        case PLL1:

                            //Take list from server and print out players name.


                            String listOfPlayers = Arrays.toString(Server.namesOfAllClients.toArray());
                            dos.writeUTF(Protocol.PLL1 + listOfPlayers);
                            break;

                        case GML1:

                            //Under Construction: Sends a list of open, ongoing and finished Games including their game_ID

                            dos.writeUTF("GML2");
                            break;

                        case HSC1:

                            //Under Construction: Sends the current highscore to the player

                            dos.writeUTF("HSC2" + "1000 points");
                            break;

                        case CRE1:

                            //Under Construction: Creates a new game with an individual game_ID

                            dos.writeUTF("CRE2");
                            break;

                        case JOIN:

                            /**
                             * Under Construction: Player joins a Game with the fitting game_ID.
                             * If there is no game with the game_ID or the game has already started,
                             * EJON2 is sended.
                             */

                            if (true) {
                                dos.writeUTF("JON2");
                            } else {
                                dos.writeUTF("EJON");
                                break;
                            }

                        case STR1:
                            /**
                             * Under Construction: Starts the game. Sends STR2 to all players in the
                             * same lobby.
                             */
                            dos.writeUTF("STR2");
                            break;

                        case UPPR:

                            //Under Construction: Player moves a block up ingame.

                            break;

                        case DOWN:

                            //Under Construction: Player moves a block down ingame.

                            break;

                        case LEFT:

                            //Under Construction: Player moves a block left ingame.

                            break;

                        case RIGT:

                            //Under Construction: Player moves a block right ingame.

                            break;

                        case WHP1:
                            /**
                             * Under Construction: Player sends a whisperchat. Sends WHP2
                             * when the playername exist, sends EWHP when not.
                             */
                            if (true) {
                                dos.writeUTF("WHP2" + original);
                            } else {
                                dos.writeUTF("EWHP");
                                break;
                            }

                        case BACK:

                            //Under Construction: Player moves a block right ingame.
                            if (clientProfil.isInGlobalChat) {

                                //Player leaves chat.
                                String serverMessage = (clientProfil.nickname + " has left the chat!\n");
                                Server.globalChat(serverMessage);
                                System.out.println(serverMessage);
                                dos.writeUTF(Protocol.HELP.name());
                                clientProfil.isInGlobalChat = false;


                            }

                            break;

                        default:

                            //This should be impossible
                            System.out.println("How did this happen?");
                            break;



                    }

                } else {
                    //send chat message
                    if (clientProfil.isInGlobalChat) {
                        String serverMessage = "[" + clientProfil.nickname + "]: "
                                + original;
                        Server.globalChat(serverMessage);
                    } else {
                        dos.writeUTF(original);
                    }
                }

            }

            dis.close();
            dos.close();
            
        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}