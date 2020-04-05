import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.Server;
import server.Client;

/**
 * @author Rohail
 * The Main-class of our Project. Depending on the arguments it starts a server or
 * client thread
 */
public class Start {

    /**
     * The constant logger.
     */
    public static final Logger logger = LoggerFactory.getLogger(Start.class);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            if (args[0].equalsIgnoreCase("server")) {
                Server server = new Server(Integer.parseInt(args[1]));
                Thread serverThread = new Thread(server);
                serverThread.start();
                logger.info("Server started");
            } else if (args[0].equalsIgnoreCase("client")) {
                String[] host = args[1].split(":");
                if (args.length == 3) {
                    Client client = new Client(host[0], host[1], args[2]);
                    Thread clientThread = new Thread(client);
                    clientThread.start();
                    logger.info("ClientThread started");
                } else {
                    Client client = new Client(host[0], host[1]);
                    Thread clientThread = new Thread(client);
                    clientThread.start();
                    logger.info("ClientThread started");
                }


            }
        } catch (Exception e) {
            System.out.println("Your input was incorrect. Please try again! \n\n"
                    + "It has to be done as followed: \n"
                    + "client <hostadress>:<port> [<username>] | server <port> \n\n"
                    + "Example 1: server 1111\n"
                    + "Example 2: client localhost:1111\n"
                    + "Example 3: client localhost:1111 me\n");
        }
    }
}