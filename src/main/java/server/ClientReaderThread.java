package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientReaderThread implements Runnable {

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ClientProfil profil;
    
    public ClientReaderThread(
        DataInputStream dataInputStream, DataOutputStream dataOutputStream, ClientProfil profil) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.profil = profil;
    }

    public void run() {

        InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
        BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

        boolean threadIsRunning = true;

        try {

            while (threadIsRunning) {
    
                /**
                 * Get message from server (in LETTERS)
                 */
                String originalMessage = dataInputStream.readUTF();
                String messageFromServer = originalMessage.toUpperCase();

                switch (messageFromServer) {

                    case "WELC": 

                        System.out.println(Message.welcomeMessage + Message.changeName);
                        
                        break;

                    case "HELP": 

                        System.out.println(Message.helpMessage);
                        
                        break;

                    case "QUIT": 

                        threadIsRunning = false;
                        
                        break;

                    default: 
                        
                        System.out.println(originalMessage);

                        break;

                }


            }

            dataInputStream.close();
            dataOutputStream.close();

        } catch (IOException ioException) {
            System.err.println(ioException.toString());
        }

    }

}