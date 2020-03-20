package server;

import java.io.DataOutputStream;
import java.io.IOException;

public class ChatSender implements Runnable {

    DataOutputStream dataOutputStream;
    ClientProfil client_profil;

    /**
     * This Thread is for the server sending the newest message
     */
    public ChatSender(DataOutputStream dataOutputStream, ClientProfil client_profil) {
        this.dataOutputStream = dataOutputStream;
        this.client_profil = client_profil;
    }

    public void run() {       

        try {

            while (client_profil.isInGlobalChat) {

                //synchronized (this) {

                    while (!ServerThreadForClient.newChatMessage) {

                    }

                    dataOutputStream.writeUTF(Server.latestChatMessage);
                    System.out.println("HABE GESENDET");

                    ServerThreadForClient.newChatMessage = false;
                    
                //}

            }
                     
        } catch (IOException exception) {
            System.err.println(exception.toString());
        }

    }

}