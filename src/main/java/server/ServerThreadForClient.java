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
             * Receiving nickname by Client and checking for duplicates
             */
            String nickname = dataInputStream.readUTF();
            client_profil.nickname = Server.checkForDublicates(nickname);

            System.out.println("\nNickname of client #" + client_profil.client_ID + ": " + client_profil.nickname);

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

                        break;

                    case "NAME":
                        //reads next line
                        String changedName = dataInputStream.readUTF();
                        if (changedName.equalsIgnoreCase("YEAH")) {
                            changedName = System.getProperty("user.name");
                        }
                        //checks for duplicates
                        Server.namesOfAllClients.remove(client_profil.nickname);
                        changedName = Server.checkForDublicates(changedName);
                        System.out.println(client_profil.nickname + " changes his/her name to " + changedName);
                        client_profil.nickname = changedName;
                        //outprints new name
                        dataOutputStream.writeUTF("\n\nYour nickname has been changed to: " + changedName + "\n");
                        break;

                    case "QUIT":
                        dataOutputStream.writeUTF(clientchoice);
                        //removes name and thread of the client from the list for online clients
                        Server.removeUser(client_profil.nickname, this);
                        System.out.println("\nClient #" + client_profil.client_ID + " \"" + client_profil.nickname + "\" has disconnected.");
                        client_profil.clientIsOnline = false;
                        break;

                    case "PLL1":
                        String listOfPlayers = Arrays.toString(Server.namesOfAllClients.toArray());
                        dataOutputStream.writeUTF("PLL2" + listOfPlayers);
                        break;

                    case "GML1":
                        String listOfGames = "Under Construction! Why not try something else for the moment?";
                        dataOutputStream.writeUTF("GML2" + listOfGames);
                        break;

                    case "HSC1":
                        String currentHighScore = "Under Construction! Why not try something else for the moment?";
                        dataOutputStream.writeUTF("GML2" + currentHighScore);
                        break;

                    case "CRE1":
                        String dontEvenKnowMyselfWhatShouldBeHere = "Under Construction! Why not try something else for the moment?";
                        dataOutputStream.writeUTF("CRE1" + dontEvenKnowMyselfWhatShouldBeHere);
                        break;

                    case "JON1":
                        String game_id = "Under Construction! Why not try something else for the moment?";
                        dataOutputStream.writeUTF("JON2" + game_id);
                        break;

                    default:

                        if (client_profil.isInGlobalChat) {
                            if (clientchoice.equals("BACK")) {
                                System.out.println("\n\n" + client_profil.nickname + " has left the chat!\n");
                                dataOutputStream.writeUTF(Message.helpMessage);
                                client_profil.isInGlobalChat = false;
                            } else {
                                String serverMessage = "[" + client_profil.nickname + "]: " + clientchoiceOriginal;
                                Server.globalChat(serverMessage);
                            }

                        } else {
                            dataOutputStream.writeUTF("\nInput unknown...\n\n" + Message.helpMessage);
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