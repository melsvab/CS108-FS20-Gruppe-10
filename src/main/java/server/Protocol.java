package server;

/**
 * @author Natasha,Dennis,Rohail,Melanie
 */
public enum Protocol {

    WELC("Welcome message and intro will be shown"),
    HELP("Help message will be shown"),
    CHAT("client sends a message in lobby chat"),
    MSG1("Message in Chat is written that nobody hears the player"),
    BRC1("player sends broadcast message"),
    WHP1("whisperchat"),
    EWHP("Playername does not exist for wisperchat"),
    MSG0("Message in Chat is written in Chat"),
    MSSG("An informational message from server to client (that is only sent in this specific occasion)"),
    ERRO("An error message from server to client (that is only sent in this specific occasion)"),
    QUIT("Client quits server connection"),
    ENDE("Server goes to sleep"),
    NAME("Desired name from client (name:nickname)"),
    PLL1("List of all players that are connected to the server"),
    GML1("List of all games that are on the server with their states"),
    HSC1("Highscore list"),
    LIST("A message from the server to client concerning a list (playerlist, gamelist, highscore)"),
    CRE1("Client wants to create a new lobby"),
    JOIN("Join a lobby/game"),
    SPEC("User wants to watch a game = spectator"),
    CRE2("Server informs client in what lobby he/she is in right now."),
    BACK("Client goes out of a game / lobby"),
    STR1("Start a game"),
    UPPR("Up"),
    DOWN("Down"),
    LEFT("Left"),
    RIGT("Right"),
    RNDS("Informs Client about how many Rounds there are left"),
    LOBY("Client gets message from lobby"),
    TEST("test from server to check for connection lost"),
    IDKW("Idkw");

    public final String keyword;

    private Protocol(String s) {
        this.keyword = s;
    }

    public String getKeyword() {
        return keyword;
    }

}