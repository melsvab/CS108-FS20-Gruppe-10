package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServerThreadForClient implements Runnable {

    //In- & Ouputstreams for reading and sending Strings
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    /**
     * Every Thread gets a client Profile
     */
    ClientProfil client_profil;

    public ServerThreadForClient(
        int client_ID, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {

            this.client_profil = new ClientProfil(client_ID);
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
    }

    public String checkForDublicates (String desiredName) {

        String newName;
        //checks if there is a String in the list that equals desired name
        if (Server.namesOfAllClients.contains(desiredName)) {
            System.out.println("There is someone with this name already!");
            newName = desiredName + "_0";

        } else {
            newName = desiredName;
        }
        //adds name to the list of all clients
        Server.namesOfAllClients.addFirst(newName);
        return newName;
    }

    public void run () {
        
        try {
            
            /**
             * Let client choose his nickname.
             */
            dataOutputStream.writeUTF("Would you like to use the username of your system?\n" + 
                "If so, please type in >YEAH<. Otherwise type in your new nickname:\n");

            /**
             * Receiving nickname by Client and checking for duplicates
             */
            String desiredName = dataInputStream.readUTF();
            synchronized (this) {
                client_profil.nickname = checkForDublicates(desiredName);
            }

            System.out.println("\nNickname of client #" + client_profil.client_ID + ": " + client_profil.nickname);

            /**
             * Ask client what he wants to do.
             */           
            String helpMessage = ("What would you like to do?\n\n" +
                "enter >CHAT< to join the global chat.\n" +
                "enter >START< to start the game.\n" +
                "enter >IDK< to do something else.\n" +
                "enter >QUIT< to end this program.\n");

            dataOutputStream.writeUTF(helpMessage);

            while (client_profil.clientIsOnline) {

                String clientchoice = dataInputStream.readUTF();

                switch (clientchoice) {

                    case "CHAT": /**TO DO: synchronize message for chat - print chat at Client's terminal */

                        //DataOutputStream chatMessageOut = dataOutputStream; /**If no new Outpustream is generated, server crashes... */
                        
                        client_profil.isInGlobalChat = true;
                        
                        System.out.println("\n\n" + client_profil.nickname + " has joined the chat!\n");

                        /*ChatSender chatsender = new ChatSender(chatMessageOut);
                        Thread chatsenderthread = new Thread(chatsender);
                        chatsenderthread.start();*/
                        
                        while (client_profil.isInGlobalChat) {

                            synchronized (Server.chatHistory) {
                
                                String input = dataInputStream.readUTF();

                                if (input.equalsIgnoreCase("QUIT")) {
                                    System.out.println("\n\n" + client_profil.nickname + " has left the chat!\n");
                                    dataOutputStream.writeUTF(helpMessage);
                                    client_profil.isInGlobalChat = false;
                                    break;
                                }

                                Server.chatHistory += client_profil.nickname + ": " + input; //TO DO NICHT MESSAGE SONDER QUEUE

                                Server.latestChatMessage = client_profil.nickname + ": " + input;
                                
                                System.out.println(Server.chatHistory); // LAST IN FIRTS OUt
                                System.out.println("LATEST CHAT MESSAGE: " + Server.latestChatMessage);

                            }
                            
                        }

                        break;

                    case "NAME":
                        //reads next line
                        String changedName = dataInputStream.readUTF();
                        //checks for duplicates
                        synchronized (this) {
                            changedName = checkForDublicates(changedName);
                            Server.namesOfAllClients.remove(client_profil.nickname);
                        }
                        System.out.println(client_profil + " changes his/her name to " + changedName);
                        client_profil.nickname = changedName;
                        //outprints new name
                        dataOutputStream.writeUTF("Your nickname has been changed to: " + changedName);


                    case "QUIT":
                        //removes name of the client from the list for online clients
                        synchronized (this) {
                            Server.namesOfAllClients.remove(client_profil.nickname);
                        }
                        System.out.println("\nClient #" + client_profil.client_ID + " \"" + client_profil.nickname + "\" has disconnected.");
                        client_profil.clientIsOnline = false;
                        break;


                    default:

                        dataOutputStream.writeUTF("\nInput unknown...\n\n" + helpMessage);
            
                }

            }

            dataInputStream.close();
            dataOutputStream.close();
            
        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}