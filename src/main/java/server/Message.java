package server;

/**
 * @author Natasha
 * This class is a database for text that is used quite often in the program or the
 * text is very long. This is like an enum but without official keywords
 */
public class Message {

    /**
     * The constant helpMessage.
     */
    public static String helpMessage = (
        "\nWhat would you like to do?\n\n"
                + "enter >NAME:desiredName< to change your nickname to your \ndesiredName\n"
                + "enter >NAME:yeah< to change your nickname to the name of your \nsystem\n"
                + "enter >IDKW< to do something else.\n"
                + "enter >PLL1< to see a list of currently connected players.\n"
                + "enter >GML1< to see a list of open, ongoing and finished games. \n"
                + "enter >HSC1< to see the current highscore. \n"
                + "enter >CRE1< to create a lobby for a new game. \n"
                + "enter >JOIN:lobbyNumber< to join a lobby. \n"
                + "enter >SPEC:lobbyNumber< to join a lobby as a spectator \n"
                + "enter >STR1:boardSize:maxCoins< to start a game with its \npersonalized board. \n"
                + "enter >HELP< to see this message again.\n"
                + "enter >QUIT< to end this program.\n");

    /**
     * The constant underConstruction.
     */
    public static String underConstruction = (
        "Under Construction! Why not try something else for the moment?");

    /**
     * The constant garbage.
     */
// is used for placeholder
    public static String garbage = (
            "Someone is sending garbage\n");

    /**
     * The constant inLobbyAlready.
     */
    public static String inLobbyAlready = ("You are in a lobby already. If you want to exit," + "write >BACK<");
    /**
     * The constant enterLobby.
     */
    public static String enterLobby = (" has joined the lobby!\n");

    /**
     * The constant youAreDoingItWrong.
     */
    public static String youAreDoingItWrong = ("It seems as if you used this keyword wrongly. \n"
                    + "This keyword is used like this: ");

    /**
     * The constant nobodyHearsYou.
     */
    public static String nobodyHearsYou = ("\nNobody can hear you here.\n"
                    + "Write /b yourMessage for broadcast,"
                    + " /w playername \nyourMessage for whisper or join a Lobby!\n");

    /**
     * The constant playerDoesNotExist.
     */
    public static String playerDoesNotExist = (
            "\n The message could not be sent. There is no player with this name.");

    /**
     * The constant youCannotDoThat.
     */
    public static String youCannotDoThat = "You cannot do that now!\n";

    /**
     * The constant invalidMove.
     */
    public static String invalidMove = "This move is not possible.\n";
}