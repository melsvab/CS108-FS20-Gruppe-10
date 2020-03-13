package serverclasses;

import java.net.*;
import java.io.*;

class Client {

    public static void main(String[] args) {

        try {

            System.out.println("\n\nconnecting...");

            Socket sock = new Socket(args[0], Integer.parseInt(args[1]));

            InputStream in = sock.getInputStream();
            OutputStream out = sock.getOutputStream();

            //creat server reading thread
            InThread th = new InThread(in);
            Thread iT = new Thread(th);
            iT.start();

            //stream input
            BufferedReader conin = new BufferedReader(new InputStreamReader(System.in));

            String line = " ";

            while (true) {
                //reading input stream
                line = conin.readLine();
                if (line.equalsIgnoreCase("QUIT")) {
                    break;
                }

                //writing to ECHO server
                out.write(line.getBytes());
                out.write('\n');
            } //terminate program

            System.out.println("terminating...");

            in.close();
            out.close();
            sock.close();

        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        }

    }

}