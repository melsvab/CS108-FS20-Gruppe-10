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
                String[] host = args[1].split(":");
                if(args.length == 3) {
                    Client client = new Client(host[1],host[0],args[2]);
                    Thread clientThread = new Thread(client);
                    clientThread.start();
                    logger.info("ClientThread started");
                } else {
                    Client client = new Client(host[0],host[1]);
                    Thread clientThread = new Thread(client);
                    clientThread.start();
                    logger.info("ClientThread started");
                }


        }
    }
}