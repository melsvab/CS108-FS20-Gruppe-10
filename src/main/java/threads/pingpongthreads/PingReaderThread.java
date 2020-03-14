package threads.pingpongthreads;

import java.io.IOException;
import java.io.InputStream;

public class PingReaderThread implements Runnable {

    InputStream inputStream;

    public PingReaderThread(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void run () {

        System.out.println("PingReaderThread started by Server...\n");
            
        while (true) {
                        
            try {

                for(int i = 0; i < 5; i++) {
                    System.out.print((char) inputStream.read());
                }
                System.out.print("\n");

            } catch (IOException exception) {
                System.err.println(exception.toString());
            }
            
        }
        
    }
    
}