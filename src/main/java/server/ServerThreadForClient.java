package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class ServerThreadForClient implements Runnable {

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    //TO DO ATTRIBUTE VON CLIENT! NAME, IN GAME? IN CHAT? etc. wird von Client bereit gestellt!

    public ServerThreadForClient(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    public void run () {
        
        try {

            while (true) {

                Server.message += dataInputStream.readUTF(); //TO DO NICHT MESSAGE SONDER QUEUE 
                System.out.println(Server.message); // LAST IN FIRTS OUT 
                
            }

        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}