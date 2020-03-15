package threads.echothreads;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class EchoThreadClient implements Runnable {

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public EchoThreadClient(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    public void run () {
        
        try {

            Object lock = new Object();

            InputStreamReader keyBoardInputStream = new InputStreamReader(System.in);
            BufferedReader readKeyBoard = new BufferedReader(keyBoardInputStream);

            synchronized (lock) {
                
                while (true) {

                    dataOutputStream.writeUTF(readKeyBoard.readLine());
                    System.out.println(dataInputStream.readUTF());
                    Thread.sleep(100);
    
                }

            }

        } catch (Exception exception) {
            System.err.println(exception.toString());
        }

    }
    
}