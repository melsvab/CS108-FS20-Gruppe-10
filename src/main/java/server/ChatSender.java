package server;

import java.io.DataOutputStream;
import java.io.IOException;

public class ChatSender implements Runnable {

    DataOutputStream dataOutputStream;

    /**
     * This Thread is for the server sending the newest message
     */
    public ChatSender(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void run() {       

        try {

            while (true) {

                synchronized (Server.chatHistory) {
                    
                    dataOutputStream.writeUTF(Server.chatHistory);
    
                }
    
            }
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
        }

    }

}