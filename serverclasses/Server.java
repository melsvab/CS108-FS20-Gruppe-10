package serverclasses;

import java.net.*;
import java.io.*;

class Server {

    static final int port = 1111;

    public static void main(String[] args) {

        try {

            System.out.println("Warte auf Port " + port + "...");

            ServerSocket echo = new ServerSocket(port);

            Socket socket = echo.accept();

            System.out.println("Connected");
            
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
    
            int c;

            while ((c = in.read()) != -1) {
                out.write((char) c);
                
                System.out.println((char) c); // mein kommentar ist besser

            }

            System.out.println("Connection terminated");
            socket.close();
            echo.close();

        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        }

    }

}