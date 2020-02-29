package serverclasses;

import java.net.*;
import java.io.*;

class Server {

    static final int port = 1111;

    public static void main(String[] args) {

        try {

            String serverIP = Inet4Address.getLocalHost().getHostAddress();
            //get IP of server printed out.

            System.out.println("\n\nWaiting for a connection to IP: " + serverIP + " on port " + port + " ...\n");

            ServerSocket iWaitForRequests = new ServerSocket(port); 
            /**A server socket waits for requests to come in 
             * over the network. It performs some operation 
             * based on that request, and then possibly 
             * returns a result to the requester.
             * Here: Server socke is bound to the specified port.
             */

            Socket endpoint = iWaitForRequests.accept(); 
            /**Server socket "iWaitForRequests" now listens 
             * for a connection to be made to this socket and accepts it.
             * This is stored in a socket.
             * A socket is an endpoint for communication between two machines
             */

            //after the connection is made:
            System.out.println("Connected\n\nWaiting for Input\n");
            
            InputStream in = endpoint.getInputStream();
            OutputStream out = endpoint.getOutputStream();
            
            /**Messages form Server to Client need to be
             * converted to byte-streams. Therefore, let client know, 
             * that connection was made and give instructions via byte[]
             */ 
            byte[] connected = ("\nConnected\n").getBytes();
            byte[] messageToClient = ("\nPlease type in your echo: ").getBytes();
            for(int i = 0; i < connected.length; i++) {
                out.write(connected[i]);
            }
            for(int i = 0; i < messageToClient.length; i++) {
                out.write(messageToClient[i]);
            }
    
            /**Same for output-stream: Byte is read as a int.
             * As long as int is not -1 (no byte) write bytes to client
             * and repeat what was written on console.
             */ 
            int byteCode; //TO DO: How to change output to client and on console? Strings? no bytes?

            while ((byteCode = in.read()) != -1) {
                out.write((char) byteCode);
                System.out.print((char) byteCode);
            }

            /**At this point, inputStream is still
             * waiting for input...
             * 
             * After that:
             */ 
            System.out.println("\n\nConnection terminated by Client\n"); 
            endpoint.close();
            iWaitForRequests.close();

        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        }

    }

}