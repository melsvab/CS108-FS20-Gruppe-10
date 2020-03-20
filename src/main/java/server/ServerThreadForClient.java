package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

public class ServerThreadForClient implements Runnable {

    //In- & Ouputstreams for reading and sending Strings
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    /**
     * Every Thread gets a client Profile
     */
    ClientProfil client_profil;

    /**
     * Constructor
     */
    public ServerThreadForClient(
        int client_ID, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {

            this.client_profil = new ClientProfil(client_ID);
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;

    }

    public boolean globalChat() {
        return client_profil.isInGlobalChat;
    }

    void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (Exception exception) {
        System.err.println(exception.toString());
        }
    }

    public void run () {
        
        try {
            
            /**
             * Say Hello to client and let him choose his nickname.
             */
            dataOutputStream.writeUTF("WELC");

            /**
             * Receiving nickname by Client and checking for duplicates.
             * Let Player know his nickname.
             */
            String nickname = dataInputStream.readUTF();
            client_profil.nickname = Server.checkForDublicates(nickname);

            System.out.println("\nNickname of client #" + client_profil.client_ID + ": " + client_profil.nickname);

            dataOutputStream.writeUTF("\nYour nickname: " + client_profil.nickname + "\n");

            /**
             * Ask client what he wants to do.
             */
            dataOutputStream.writeUTF("HELP");

            while (client_profil.clientIsOnline) {

                String clientchoiceOriginal = dataInputStream.readUTF();
                String clientchoice = clientchoiceOriginal.toUpperCase();

                switch (clientchoice) {

                    case "CHAT": /**TO DO: synchronize message for chat - print chat at Client's terminal */
                        
                        client_profil.isInGlobalChat = true;
                        
                        System.out.println("\n\n" + client_profil.nickname + " has joined the chat!\n");

                        String userJoined = (client_profil.nickname + " has joined the chat!\n");
                        Server.globalChat(userJoined);

                        break;

                    case "NAME":

                        /**
                         * If in Chat, let others know
                         */
                        String oldNickname = client_profil.nickname;

                        /**
                         * Reveive new nickname from client
                         */
                        dataOutputStream.writeUTF("\n\nYour current nickname is: " + client_profil.nickname + "\n");

                        String changedName = dataInputStream.readUTF();
        
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
                        dataOutputStream.writeUTF("\n\nYour nickname has been changed to: " + changedName + "\n");

                        /**
                         * If in Chat, let others know
                         */
                        if (client_profil.isInGlobalChat) {
                            String confirmation = (oldNickname + " changd his/her nickname to " + client_profil.nickname + "!\n");
                            Server.globalChat(confirmation);
                        }

                        break;

                    case "QUIT":
                        dataOutputStream.writeUTF(clientchoice);
                        /**
                         * Remove name and thread of this client form the list on the server
                         */
                        Server.removeUser(client_profil.nickname, this);
                        
                        System.out.println("\nClient #" + client_profil.client_ID + " \"" + client_profil.nickname + "\" has disconnected.");
                        
                        client_profil.clientIsOnline = false;
                        break;

                    case "PLL1":
                        String listOfPlayers = Arrays.toString(Server.namesOfAllClients.toArray());
                        dataOutputStream.writeUTF("PLL2" + listOfPlayers);
                        break;

                    case "GML1": /**Under Construction */
                        dataOutputStream.writeUTF("GML2" + Message.underConstruction);
                        break;

                    case "HSC1":
                        dataOutputStream.writeUTF("GML2" + Message.underConstruction); //DENNIS: Should be HSC2?
                        break;

                    case "CRE1":
                        //String dontEvenKnowMyselfWhatShouldBeHere = "Under Construction! Why not try something else for the moment?";
                        /**DENNIS: Hanni dinne gloh willis so luschtig find x) */
                        dataOutputStream.writeUTF("CRE1" + Message.underConstruction); //DENNIS: Should be CRE2?
                        break;

                    case "JON1":
                        dataOutputStream.writeUTF("JON2" + Message.underConstruction);
                        break;

                    default:

                        if (client_profil.isInGlobalChat) {

                            if (clientchoice.equals("BACK")) {

                                String serverMessage = ("[" + client_profil.nickname + "] has left the chat!\n");
                                Server.globalChat(serverMessage);
                                System.out.println("\n\n" + client_profil.nickname + " has left the chat!\n");
                                dataOutputStream.writeUTF(Message.helpMessage);
                                client_profil.isInGlobalChat = false;

                            } else {
                                String serverMessage = "[" + client_profil.nickname + "]: " + clientchoiceOriginal;
                                Server.globalChat(serverMessage);

                            }
        
                        }
            
                }

            }

            dataInputStream.close();
            dataOutputStream.close();
            
        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}