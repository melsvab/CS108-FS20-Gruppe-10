package server;

public enum Protocol {

    CHAT("client: sends message in chat"),
    BRC1("player sends broadcast message"),
    NAME("Desired name from client (name:nickname)"),
    ERRO("An error message from server to client (that is only sent in this specific occasion)"),
    MSSG("An informational message from server to client (that is only sent in this specific occasion)"),
    QUIT("Quit"),
    ENDE("Server goes to sleep"),
    PLL1("Playerlist"),
    GML1("Gamelist"),
    HSC1("Highscore"),
    CRE1("Client wants to create a new lobby"),
    CRE2("Server informs client that he created a lobby"),
    JOIN("Join a lobby/game"),
    SPEC("User wants to watch a game = spectator"),
    STR1("Start a game"),
    LOBY("Client gets message from lobby"),
    UPPR("Up"),
    DOWN("Down"),
    LEFT("Left"),
    RIGT("Right"),
    BACK("Client goes out of a game / lobby"),
    IDKW("Idkw"),
    HELP("Help"),
    WELC("Welcome message"),
    EJON("Client can't join/start a game because he/she is in one already"),
    RNDS("Number of rounds left"),
    YTRN("It's your turn!"),
    DICE("Shows player how many moves are left"),
    ERMO("Invalid move"),
    POIN("Shows points for move"),
    POIC("Shows score of a player"),
    SCOR("Player gets extrapoint because of a coin"),
    STPX("No more moves are left"),
    EVEN("An event (flood/earthquake) is happening"),
    DEAD("Player was hit by an event"),
    MIPO("player gets minus points"),
    NTRN("Informs player that the next round starts"),
    RNDX("Informs the players that no more rounds are left"),
    RNDA("extra round begins"),
    WINX("Winner is anncounced"),
    HGHN("There is a new highscore"),
    MSG0("Message in Chat is written in Chat"),
    MSG1("Message in Chat is written that nobody hears the player"),
    WHP1("whisperchat"),
    EWHP("Playername does not exist for wisperchat"),
    ENDX("informs player that the game has finished");



    public final String keyword;

    private Protocol(String s) {
        this.keyword = s;
    }

    public String getKeyword() {
        return keyword;
    }

}