package threads.pingpongthreads;

import java.io.IOException;
import java.io.OutputStream;

public class PingSenderThread implements Runnable {

    OutputStream outputStream;

    public PingSenderThread(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void run () {

        System.out.println("PingsenderThread started by Client...\n");
            
        while (true) {
                        
            try {

                outputStream.write(("PING ").getBytes());
                Thread.sleep(1000);

            } catch (Exception exception) {
                System.err.println(exception.toString());
            }
            
        }
        
    }
    
}