package server;

//versuch von Natasha
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


public class Message {
    /**
     * This class is a database fore text which is used
     * more often in the program. Easy access.
     */

    //von Natasha: Versuch, der aber komischerweise nicht klappt
    //public static final Logger LOGGER = LogManager.getLogger();
    
    public static String welcomeMessage = ("\nWelcome to the server!\n\n");
    
    public static String changeName = (
            "\nWould you like to use the username of your system?\n"
                    + "If so, please type in >YEAH<. Otherwise type in the nickname of your choice:\n");

    public static String helpMessage = (
        "\nWhat would you like to do?\n\n"
                + "enter >CHAT:message< to chat in your lobby.\n"
                + "enter >BRC1:message< to send a message to all players\n"
                + "enter >NAME:desiredName< to change your nickname to your desiredName\n"
                + "enter >IDKW< to do something else.\n"
                + "enter >PLL1< to see a list of currently connected players.\n"
                + "enter >GML1< to see a list of open, ongoing and finished games. \n"
                + "enter >HSC1< to see the current highscore. \n"
                + "enter >CRE1:boardsize:maximumNumberOfPoints< to create a lobby for a new game. \n"
                + "enter >JOIN< to join a lobby. \n"
                + "enter >QUIT< to end this program.\n");

    public static String nameIsUsedAlready = "Your desired name exists already!";

    public static String underConstruction = (
        "Under Construction! Why not try something else for the moment?");

    //Idee von >garbage< : dort sollten später logger stehen!
    public static String garbage = (
            "Someone is sending garbage\n");

    public static String youAreDoingItWrong = (
            "It seems as if you used this keyword wrongly. \n"
                    + "This keyword is used like this: ");
}