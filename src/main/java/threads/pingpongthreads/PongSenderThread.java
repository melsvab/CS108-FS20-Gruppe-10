package threads.pingpongthreads;

import java.io.IOException;
import java.io.OutputStream;

public class PongSenderThread implements Runnable {

    OutputStream outputStream;

    public PongSenderThread(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void run () {

        System.out.println("PongSenderThread started by Server...\n");
            
        while (true) {
                        
            try {

                outputStream.write(("PONG ").getBytes());
                Thread.sleep(1000);

            } catch (Exception exception) {
                System.err.println(exception.toString());
            }
            
        }
        
    }
    
}