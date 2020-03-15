package threads.pingpongthreads;

import java.io.DataOutputStream;
import java.io.IOException;

public class PingSenderThread implements Runnable {

    DataOutputStream dataOutputStream;

    public PingSenderThread(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void run () {

        System.out.println("PingsenderThread started by Client...\n");
            
        while (true) {
                        
            try {

                dataOutputStream.writeUTF("PING ");
                Thread.sleep(1000);

            } catch (Exception exception) {
                System.err.println(exception.toString());
            }
            
        }
        
    }
    
}