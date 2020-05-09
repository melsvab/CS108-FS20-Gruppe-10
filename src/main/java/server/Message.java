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
                + "Enter >NAME:desiredName< to change your nickname to your desiredName\n"
                + "Enter >NAME:yeah< to change your nickname to the name of your system\n"
                + "Enter >IDKW< to do something else.\n"
                + "Enter >PLL1< to see a list of currently connected players.\n"
                + "Enter >GML1< to see a list of open, ongoing and finished games. \n"
                + "Enter >HSC1< to see the current highscore. \n"
                + "Enter >CRE1< to create a lobby for a new game. \n"
                + "Enter >JOIN:lobbyNumber< to join a lobby. \n"
                + "Enter >SPEC:lobbyNumber< to join a lobby as a spectator \n"
                + "Enter >STR1:boardSize:maxCoins< to start a game with its personalized board. \n"
                + "Enter >HELP< to see this message again.\n"
                + "Enter >QUIT< to end this program.\n");

    public static String gameMessage = (
            "\nYou can used the buttons in the left corner to use the program.\n\n"
                    + "To move your turtle during the game, press the arrow keys in the desired direction. \n"
                    + "To see more of the map during the game, move your mouse to the panels around the board.\n"
                    );
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
    public static String inLobbyAlready = ("You are in a lobby already. \nIf you want to exit, write >BACK<.");
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
    public static String nobodyHearsYou = ("\n\nNobody can hear you here.\n\n"
                    + "Change to BC for the broadcast chat or write"
                    + " /w playername yourMessage for the whisper chat or join a lobby!\n");

    /**
     * The constant playerDoesNotExist.
     */
    public static String playerDoesNotExist = (
            "\nThe message could not be sent.\nThere is no player with this name.");

    /**
     * The constant youCannotDoThat.
     */
    public static String youCannotDoThat = "You cannot do that now!\n";

    /**
     * The constant invalidMove.
     */
    public static String invalidMove = "This move is not possible!";

    /**
     * The constant tooScaredToMove.
     */
    public static String tooScaredToMove = " is too scared to move!";
}