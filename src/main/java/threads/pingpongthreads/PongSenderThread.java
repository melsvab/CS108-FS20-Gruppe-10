package threads.pingpongthreads;

import java.io.DataOutputStream;
import java.io.IOException;

public class PongSenderThread implements Runnable {

    DataOutputStream dataOutputStream;

    public PongSenderThread(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void run () {

        System.out.println("PongSenderThread started by Server...\n");
            
        while (true) {
                        
            try {

                dataOutputStream.writeUTF("PONG ");
                Thread.sleep(1000);

            } catch (Exception exception) {
                System.err.println(exception.toString());
            }
            
        }
        
    }
    
}