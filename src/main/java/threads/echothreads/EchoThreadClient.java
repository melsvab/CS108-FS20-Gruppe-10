package threads.echothreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class EchoThreadClient implements Runnable {

    InputStream inputStream;
    OutputStream outputStream;

    public EchoThreadClient(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void run () {
        
        try {

            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);
                
            while (true) {

                outputStream.write(readKeyBoard.readLine().getBytes());
                System.out.print((char) inputStream.read());

            }

        } catch (IOException exception) {
            System.err.println(exception.toString());
        }

    }
    
}