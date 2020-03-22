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
     * In- & Ouputstreams for reading and sending Strings
     */
    DataInputStream dis;
    DataOutputStream dos;

    /**
     * Every Thread gets a client Profile
     */
    ClientProfil client_profil;

    /**
     * Constructor (creats a new clientProfil)
     */
    public ServerThreadForClient(
        int client_ID, DataInputStream dis, DataOutputStream dos) {

            this.client_profil = new ClientProfil(client_ID);
            this.dis = dis;
            this.dos = dos;

    }

    /**
     * Check if Client is in global Chat.
     */
    public boolean globalChat() {

        return client_profil.isInGlobalChat;
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

    public void run () {
        
        try {
            
            /**
             * Say Hello to client and let him choose his nickname.
             */
            dos.writeUTF("WELC");

            /**
             * Receiving nickname by Client and checking for duplicates.
             * Let Player know his nickname.
             */
            String nickname = dis.readUTF();
            client_profil.nickname = Server.checkForDublicates(nickname);

            System.out.println("\nNickname of client #" + client_profil.client_ID + ": " + client_profil.nickname);
            dos.writeUTF("\nYour nickname: " + client_profil.nickname + "\n");
            /**Nickname chosen. */

            /**
             * Ask client what he wants to do.
             */
            dos.writeUTF("HELP");

            while (client_profil.clientIsOnline) {

                /**
                 * Get choice from Client and descide what to do.
                 */
                String clientchoiceOriginal = dis.readUTF();
                String clientchoice = clientchoiceOriginal.toUpperCase();


                switch (clientchoice) {

                    case "CHAT":
                        
                        client_profil.isInGlobalChat = true;
                        
                        System.out.println("\n\n" + client_profil.nickname + " has joined the chat!\n");

                        String userJoined = (client_profil.nickname + " has joined the chat!\n");
                        Server.globalChat(userJoined);

                        break;

                    case "NAM1":

                        /**
                         * If in Chat, let other players know.
                         */
                        String oldNickname = client_profil.nickname;
                        /**
                         * Reveive new nickname from client
                         */
                        dos.writeUTF("\n\nYour current nickname is: " + client_profil.nickname + "\n");

                        String changedName = dis.readUTF();
                        /**
                         * Check, if this name is already used
                         */
                        Server.namesOfAllClients.remove(client_profil.nickname);
                        changedName = Server.checkForDublicates(changedName);
                        /**
                         * Accept new name 
                         */
                        System.out.println("\n" + client_profil.nickname + " changed his/her name to " + changedName);
                        client_profil.nickname = changedName;
                        /**
                         * Let Player know his new name
                         */
                        dos.writeUTF("\n\nYour nickname has been changed to: " + changedName + "\n");
                        /**
                         * If in Chat, let others know
                         */
                        if (client_profil.isInGlobalChat) {
                            String confirmation = (oldNickname + " changed his/her nickname to " + client_profil.nickname + "!\n");
                            Server.globalChat(confirmation);
                        }

                        break;

                    case "QUIT":

                        dos.writeUTF(clientchoice);
                        /**
                         * Remove name and thread of this client form the list on the server
                         */
                        Server.removeUser(client_profil.nickname, this);
                        /**
                         * Give Feedback
                         */
                        System.out.println("\nClient #" + client_profil.client_ID + " \"" + client_profil.nickname + "\" has disconnected.");
                        client_profil.clientIsOnline = false;
                        break;

                    case "PLL1":
                        /**
                         * Take list from server and print out players name.
                         */
                        String listOfPlayers = Arrays.toString(Server.namesOfAllClients.toArray());
                        dos.writeUTF("PLL2" + listOfPlayers);

                        break;

                    case "GML1":
                        /**
                         * Under Construction: Sends a list of open, ongoing and finished Games including their game_ID
                         */
                        dos.writeUTF("GML2");

                        break;

                    case "HSC1":
                        /**
                         * Under Construction: Sends the current highscore to the player
                         */
                        dos.writeUTF("HSC2" + "1000 points");

                        break;

                    case "CRE1":
                        /**
                         * Under Construction: Creates a new game with an individual game_ID
                         */
                        dos.writeUTF("CRE2");

                        break;

                    case "JON1":
                        /**
                         * Under Construction: Player joins a Game with the fitting game_ID.
                         * If there is no game with the game_ID or the game has already started, EJON2 is sended.
                         */
                        if (true)
                            dos.writeUTF("JON2");
                        else
                            dos.writeUTF("EJON");
                        break;

                    case "STR1":
                        /**
                         * Under Construction: Starts the game. Sends STR2 to all players in the
                         * same lobby.
                         */
                        dos.writeUTF("STR2");

                        break;

                    case "HXXD":
                        /**
                         * Under Construction: Player moves a block up ingame.
                         */
                        break;

                    case "DXXN":
                        /**
                         * Under Construction: Player moves a block down ingame.
                         */
                        break;

                    case "LXXT":
                        /**
                         * Under Construction: Player moves a block left ingame.
                         */
                        break;

                    case "RXXT":
                        /**
                         * Under Construction: Player moves a block right ingame.
                         */
                        break;

                    case "WHP1":
                        /**
                         * Under Construction: Player sends a whisperchat. Sends WHP2
                         * when the playername exist, sends EWHP when not.
                         */
                        if(true)
                            dos.writeUTF("WHP2" + clientchoiceOriginal);
                        else
                            dos.writeUTF("EWHP");
                        break;

                    default:
                        /**
                         * If no keyword is received and player is in Chat, send message.
                         */
                        if (client_profil.isInGlobalChat) {

                            if (clientchoice.equals("BACK")) { /**Player leaves chat. */

                                String serverMessage = (client_profil.nickname + " has left the chat!\n");
                                Server.globalChat(serverMessage);
                                System.out.println(serverMessage);
                                dos.writeUTF("HELP");
                                client_profil.isInGlobalChat = false;

                            } else { /**Send chat message */
                                String serverMessage = "[" + client_profil.nickname + "]: " + clientchoiceOriginal;
                                Server.globalChat(serverMessage);

                            }
        
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