package server;

/**
 * @author Natasha,Dennis,Rohail,Melanie
 */
public enum Protocol {

    /**
     * Welcome message and intro will be shown
     */
    WELC("Welcome message and intro will be shown"),

    /**
     * Help message will be shown
     */
    HELP("Help message will be shown"),

    /**
     * Client sends a message in a lobby chat
     */
    CHAT("Client sends a message in a lobby chat"),

    /**
     * Message in chat is written that nobody hears the player
     */
    MSG1("Message in chat is written that nobody hears the player"),

    /**
     * Player sends broadcast message
     */
    BRC1("Player sends broadcast message"),

    /**
     * Whisperchat
     */
    WHP1("Whisperchat"),

    /**
     * Playername does not exist for wisperchat
     */
    EWHP("Playername does not exist for wisperchat"),

    /**
     * Message in a chat is written in a chat
     */
    MSG0("Message in a chat is written in a chat"),

    /**
     * An informational message from server to client (that is only sent in this specific occasion)
     */
    MSSG("An informational message from server to client (that is only sent in this specific occasion)"),

    /**
     * An error message from server to client (that is only sent in this specific occasion)
     */
    ERRO("An error message from server to client (that is only sent in this specific occasion)"),

    /**
     * Client quits server connection
     */
    QUIT("Client quits server connection"),

    /**
     * Server goes to sleep
     */
    ENDE("Server goes to sleep"),

    /**
     * Desired name from client (name:nickname)
     */
    NAME("Desired name from client (name:nickname)"),

    /**
     * List of all players that are connected to the server
     */
    PLL1("List of all players that are connected to the server"),

    /**
     * List of all games that are on the server with their states
     */
    GML1("List of all games that are on the server with their states"),

    /**
     * Highscore list
     */
    HSC1("Highscore list"),

    /**
     * A message from the server to a client concerning a list (playerlist, gamelist, highscore)
     */
    LIST("A message from the server to a client concerning a list (playerlist, gamelist, highscore)"),

    /**
     * A client wants to create a new lobby
     */
    CRE1("A client wants to create a new lobby"),

    /**
     * Join a lobby/game
     */
    JOIN("Join a lobby/game"),

    /**
     * User wants to watch a game = spectator
     */
    SPEC("User wants to watch a game = spectator"),

    /**
     * Server informs client in what lobby he/she is in right now.
     */
    CRE2("Server informs client in what lobby he/she is in right now."),

    /**
     * Client goes out of a game / lobby
     */
    BACK("Client goes out of a game / lobby"),

    /**
     * Start a game
     */
    STR1("Start a game"),

    /**
     * Turtle goes up
     */
    UPPR("Turtle goes up"),

    /**
     * Turtle goes down
     */
    DOWN("Turtle goes down"),

    /**
     * Turtle goes left
     */
    LEFT("Turtle goes left"),

    /**
     * Turtle goes right
     */
    RIGT("Turtle goes right"),

    /**
     * Informs clients about how many rounds are lef
     */
    RNDS("Informs clients about how many rounds are left"),

    /**
     * Informs client who has won
     */
    WINR("Informs client who has won"),

    /**
     * Client gets message from lobby
     */
    LOBY("Client gets message from lobby"),

    /**
     * Message from server to client to inform of the location of new coins
     */
    COIN("Message from server to client to inform of the location of new coins"),

    /**
     * Set start position of the turtles
     */
    TURS("Set start position of the turtles"),

    /**
     * Turtle was hit by an event and is set to start position again
     */
    TUST("Turtle was hit by an event and is set to start position again"),

    /**
     * Change of a turtle position
     */
    TURT("Change of a turtle position"),

    /**
     * A field on the board was stepped on
     */
    DRAW("Field was stepped on"),

    /**
     * A player got points
     */
    POIN("A player got points"),

    /**
     * will be sent if there was an earthquake on the board
     */
    QUAK("Earthquake on the board (server to client)"),

    /**
     * will be sent if there was a flood on the board
     */
    WATR("Flood on the board (server to client)"),

    /**
     * will be sent to reset board
     */
    RSET("Reset board"),

    /**
     * Test from server to check for connection lost
     */
    TEST("Test from server to check for connection lost"),

    /**
     * a secret cheat code
     */
    IDKW("Idkw");

    public final String keyword;

    private Protocol(String s) {
        this.keyword = s;
    }

    public String getKeyword() {
        return keyword;
    }

}