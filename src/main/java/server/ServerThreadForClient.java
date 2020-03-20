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

    public static boolean newChatMessage = false;

    /**
     * Constructor
     */
    public ServerThreadForClient(
        int client_ID, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {

            this.client_profil = new ClientProfil(client_ID);
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;

    }

    /**
     * Function checks if there is a String in the list that is equal to the desired name
     */
    public synchronized String checkForDublicates (String desiredName) {

        if (Server.namesOfAllClients.contains(desiredName)) {

            String nameIsUsedAlready = "There is someone with this name already!";
            System.out.println(nameIsUsedAlready);

            desiredName += "_0";
        }
        
        Server.namesOfAllClients.addFirst(desiredName); ////Add name to the namesOfAllClients-List
        return desiredName;

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
            client_profil.nickname = checkForDublicates(nickname);

            System.out.println("\nNickname of client #" + client_profil.client_ID + ": " + client_profil.nickname);

            /**
             * Ask client what he wants to do.
             */
            dataOutputStream.writeUTF("HELP");

            while (client_profil.clientIsOnline) {

                String clientchoice = dataInputStream.readUTF();

                switch (clientchoice) {

                    case "CHAT": /**TO DO: synchronize message for chat - print chat at Client's terminal */
                        
                        client_profil.isInGlobalChat = true;
                        
                        System.out.println("\n\n" + client_profil.nickname + " has joined the chat!\n");

                        ChatSender chatsender = new ChatSender(dataOutputStream, client_profil);
                        Thread chatsenderthread = new Thread(chatsender);
                        chatsenderthread.start();
                        
                        while (client_profil.isInGlobalChat) {

                            //synchronized (this) {
                
                                String input = dataInputStream.readUTF();

                                if (input.equalsIgnoreCase("QUIT")) {
                                    System.out.println("\n\n" + client_profil.nickname + " has left the chat!\n");
                                    dataOutputStream.writeUTF(Message.helpMessage);
                                    client_profil.isInGlobalChat = false;
                                    break;
                                }

                                Server.chatHistory += client_profil.nickname + ": " + input; //TO DO NICHT MESSAGE SONDER QUEUE

                                Server.latestChatMessage = client_profil.nickname + ": " + input;

                                newChatMessage = true;
                                
                                System.out.println(Server.chatHistory); // LAST IN FIRTS OUt
                                System.out.println("LATEST CHAT MESSAGE: " + Server.latestChatMessage);

                           //}
                            
                        }

                        break;

                    case "NAME":
                        //reads next line
                        String changedName = dataInputStream.readUTF();
                        if (changedName.equalsIgnoreCase("YEAH")) {
                            changedName = System.getProperty("user.name");
                        }
                        //checks for duplicates
                        Server.namesOfAllClients.remove(client_profil.nickname);
                        changedName = checkForDublicates(changedName);
                        System.out.println(client_profil.nickname + " changes his/her name to " + changedName);
                        client_profil.nickname = changedName;
                        //outprints new name
                        dataOutputStream.writeUTF("\n\nYour nickname has been changed to: " + changedName + "\n");
                        break;

                    case "QUIT":
                        dataOutputStream.writeUTF(clientchoice);
                        //removes name of the client from the list for online clients
                        synchronized (this) {
                            Server.namesOfAllClients.remove(client_profil.nickname);
                        }
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

                        //dataOutputStream.writeUTF("\nInput unknown...\n\n" + Message.helpMessage);
            
                }

            }

            dataInputStream.close();
            dataOutputStream.close();
            
        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}