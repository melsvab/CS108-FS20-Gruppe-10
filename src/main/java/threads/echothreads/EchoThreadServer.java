package threads.echothreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class EchoThreadServer implements Runnable {

    InputStream inputStream;
    OutputStream outputStream;

    public EchoThreadServer(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void run () {
        
        try {
                
            while (true) {

                outputStream.write((char) inputStream.read());
                System.out.print((char) inputStream.read());

            }

        } catch (IOException exception) {
            System.err.println(exception.toString());
        }

    }
    
}