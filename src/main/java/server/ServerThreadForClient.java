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

    public void run () {
        
        try {
            
            /**
             * Let client choose his nickname.
             */
            dataOutputStream.writeUTF("Would you like to use the username of your system?\n" + 
                "If so, please type in >YEAH<. Otherwise type in your new nickname:\n");

            /**
             * Receive nickname by Client and save it.
             */
            client_profil.nickname = dataInputStream.readUTF(); /**TO DO: Server checks for duplicates */

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

                    case "QUIT": 
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