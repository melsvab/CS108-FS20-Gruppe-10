package threads.pingpongthreads;

import java.io.DataInputStream;
import java.io.IOException;

public class PingReaderThread implements Runnable {

    DataInputStream dataInputStream;

    public PingReaderThread(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public void run () {

        System.out.println("PingReaderThread started by Server...\n");
            
        while (true) {
                        
            try {

                System.out.print(dataInputStream.readUTF() + "\n");

            } catch (IOException exception) {
                System.err.println(exception.toString());
            }
            
        }
        
    }
    
}