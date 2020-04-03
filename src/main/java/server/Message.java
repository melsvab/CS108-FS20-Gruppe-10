package server;

//versuch von Natasha
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


public class Message {
    /*
     * This class is a database for text that is used quite often in the program
     * or the text is very long
     *
     * like an enum but without official keywords
     */

    //von Natasha: Versuch, der aber komischerweise nicht klappt
    //public static final Logger LOGGER = LogManager.getLogger();

    public static String helpMessage = (
        "\nWhat would you like to do?\n\n"
                + "enter >NAME:desiredName< to change your nickname to your desiredName\n"
                + "enter >NAME:yeah< to change your nickname to the name of your system\n"
                + "enter >IDKW< to do something else.\n"
                + "enter >PLL1< to see a list of currently connected players.\n"
                + "enter >GML1< to see a list of open, ongoing and finished games. \n"
                + "enter >HSC1< to see the current highscore. \n"
                + "enter >CRE1< to create a lobby for a new game. \n"
                + "enter >JOIN:lobbyNumber< to join a lobby. \n"
                + "enter >SPEC:lobbyNumber< to join a lobby as a spectator"
                + "enter >STR1:boardSize:maxCoins< to start a game with its personalized board. \n"
                + "enter >HELP< to see this message again."
                + "enter >QUIT< to end this program.\n");

    public static String underConstruction = (
        "Under Construction! Why not try something else for the moment?");

    //TO DO: Idee von >garbage< : dort sollten später logger stehen!
    public static String garbage = (
            "Someone is sending garbage\n");

    public static String inLobbyAlready = ("You are in a lobby already. If you want to exit, write >BACK<");
    public static String enterLobby = (" has joined the lobby!\n");

    public static String youAreDoingItWrong = (
            "It seems as if you used this keyword wrongly. \n"
                    + "This keyword is used like this: ");

    public static String nobodyHearsYou = (
            "\n\nNobody can hear you here. "
                    + "Write /b yourMessage for broadcast,"
                    + " /w playername yourMessage for whisper or join a Lobby!\n\n");

    public static String playerDoesNotExist = (
            "\n The message could not be sent. There is no player with this playername.");

    public static String youCannotDoThat = "You cannot do that now!\"";

    public static String invalidMove = "This move is not possible.";
}