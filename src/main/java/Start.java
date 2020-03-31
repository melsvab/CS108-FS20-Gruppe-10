import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.Server;
import server.Client;
public class Start {
    public static final Logger logger = LoggerFactory.getLogger(Start.class);
    public static void main(String[] args) {
        if (args[0].equals("server")) {
            Server server = new Server(Integer.parseInt(args[1]));
            Thread serverThread = new Thread(server);
            serverThread.start();
            logger.info("Server started");
        } else if (args[0].equals("client")) {
            Client client = new Client(args[1],Integer.parseInt(args[2]),args[3]);
            Thread clientThread = new Thread(client);
            clientThread.start();
            logger.info("ClientThread started");
        }
    }
}