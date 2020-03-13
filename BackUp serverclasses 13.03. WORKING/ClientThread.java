package serverclasses;

import java.net.*;
import java.io.*;

public class ClientThread implements Runnable {

    int iD;
    Socket socket;

    //Constructor:
    public ClientThread(int iD, Socket socket) {
        this.iD = iD;
        this.socket = socket;
    }

    public void run() {
        
        try {

            //after the connection to this Client is made:
            System.out.println("Client #" + this.iD + " is connected to the Server\n\nWaiting for Input of this Client\n\n");
            
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            /**Messages form Server to Client need to be
             * converted to byte-streams. Therefore, let client know, 
             * that connection was made and give instructions via byte[]
             * UPDATE: EasyWay: out.write with getBytes()
             */
            out.write(("\nConnection to Server with IP \"" + Server.serverIP + "\" successfully established\n\nPlease type in your echo: ").getBytes());
    
            /**Same for output-stream: Byte is read as a int.
             * As long as int is not -1 (no byte) write bytes to client
             * and repeat what was written on console.
             */ 
            int byteCode;

            while ((byteCode = in.read()) != -1) {
                out.write((char) byteCode);
                System.out.print((char) byteCode);
            }

            /**At this point, inputStream is still
             * waiting for input...
             * 
             * After that:
             */ 
            System.out.println("\n\nClient #" + this.iD + " has disconnected\n");
            
            //Operations for correct IDs after dissconnection
            Server.playersOnline -= 1;
            Server.clientID = this.iD;
        
            socket.close();

        } catch (IOException e) {
                System.err.println(e.toString());
        }      

    }

}