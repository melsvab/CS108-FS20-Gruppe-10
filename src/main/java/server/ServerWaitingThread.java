package server;

public class ServerWaitingThread implements Runnable {

    public void run () {

        try {

            do {

                Socket socket = Server.serverSocket.accept();

                System.out.println("A Client has successfully connected to this server on port " + Server.port);

            } while (true);
            
        } catch (IOException exception) {
            System.err.println(exception.toString());
            System.exit(1);
        }

    }

}