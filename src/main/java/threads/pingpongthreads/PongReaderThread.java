package threads.pingpongthreads;

import java.io.DataInputStream;
import java.io.IOException;

public class PongReaderThread implements Runnable {

    DataInputStream dataInputStream;

    public PongReaderThread(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public void run () {

        System.out.println("PongReaderThread started by Client...\n");
            
        while (true) {
                        
            try {

                System.out.print(dataInputStream.readUTF() + "\n");

            } catch (IOException exception) {
                System.err.println(exception.toString());
            }
            
        }
        
    }
    
}