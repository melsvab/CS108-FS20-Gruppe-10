package server;

import java.io.DataOutputStream;
import java.io.IOException;

public class ChatSender implements Runnable {

    DataOutputStream dataOutputStream;

    /**
     * This Thread is for the client reading the newest message
     */
    public ChatSender(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void run() {       

        try {

            while (true) {

                synchronized (Server.latestChatMessage) {

                    dataOutputStream.writeUTF(Server.latestChatMessage);
    
                }
    
            }
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
        }

    }

}