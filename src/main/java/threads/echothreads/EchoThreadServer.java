package threads.echothreads;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class EchoThreadServer implements Runnable {

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public EchoThreadServer(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    public void run () {
        
        try {

            Object lock = new Object();

            synchronized (lock) {

                while (true) {

                    dataOutputStream.writeUTF(dataInputStream.readUTF()); //Send Back
                    System.out.println(dataInputStream.readUTF()); //Output Console
                    Thread.sleep(100);
    
                }

            }

        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}