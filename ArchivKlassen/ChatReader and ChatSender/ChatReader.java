package server;

import java.io.DataInputStream;
import java.io.IOException;

import server.Server;

public class ChatReader implements Runnable {

    DataInputStream dataInputStream;

    /**
     * This Thread is for the client reading the newest message
     */
    public ChatReader(
        DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public void run() {

        while (true) {

            try {

                synchronized (Server.latestChatMessage) {

                    String message = dataInputStream.readUTF();

                    System.out.println(message);

                }

            } catch (IOException exception) {
                System.err.println(exception.toString());
            }
                     
        }

    }

}