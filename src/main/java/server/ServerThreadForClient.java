package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerThreadForClient implements Runnable {

    //In- & Ouputstreams for reading and sending Strings
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    /**
     * All attributes a Client requires
     */
    int client_ID;
    String client_nickname;
    String client_message;

    public ServerThreadForClient(
        int client_ID, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            this.client_ID = client_ID;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
    }

    public void run () {
        
        try {
            
            /**
             * Let client choose his nickname.
             */
            dataOutputStream.writeUTF("\nPlease type in your nickname: ");
            /**
             * Receive Nickname by Client and save it.
             */
            this.client_nickname = dataInputStream.readUTF();

            System.out.println("\nNickname client #" + this.client_ID + ": " + this.client_nickname);

            /**
             * Now start chatmode.
             */
            while (true) {

                Server.message += dataInputStream.readUTF(); //TO DO NICHT MESSAGE SONDER QUEUE 
                System.out.println(Server.message); // LAST IN FIRTS OUT 
                
            }

        } catch (IOException exception) {
            System.err.println(exception.toString());
        }

    }
    
}