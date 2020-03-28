package server;

public enum Protocol {

    CHAT("client: sends message in lobby chat; clientReaderThread: gets message"),
    BRC1("player sends broadcast message (brc1:message)"),
    BRC2("user gets broadcast message"),
    NAME("desired name from client (name:nickname)"),
    NAM1("checked name from server"),
    NAM2("username exists already"),
    QUIT("quit"),
    ENDE("server goes to sleep"),
    PLL1("playerlist"),
    GML1("gamelist"),
    HSC1("highscore"),
    CRE1("client wants to create a new lobby"),
    CRE2("server informs client that he created a lobby"),
    JOIN("join a lobby/game"),
    SPEC("user wants to watch a game = spectator"),
    STR1("start a game"),
    UPPR("up"),
    DOWN("down"),
    LEFT("left"),
    RIGT("right"),
    WHP1("whisperchat"),
    BACK("back after chat"),
    IDKW("idkw"),
    HELP("help"),
    WELC("Welcome Message"),
    EJON("client can't join a game"),
    EWHP("Whisperchat:Playername does not exist"),
    RNDS("number of rounds left"),
    YTRN("It's your turn!"),
    DICE("shows player how many moves are left"),
    ERMO("invalid move"),
    POIN("shows points for move"),
    POIC("shows score of a player"),
    SCOR("player gets extrapoint because of a coin"),
    STPX("No more moves are left"),
    EVEN("An event (flood/earthquake) is happening"),
    DEAD("player was hit by an event"),
    MIPO("player gets minus points"),
    NTRN("Informs player that the next round starts"),
    RNDX("Informs the players that no more rounds are left"),
    RNDA("extra round begins"),
    WINX("Winner is anncounced"),
    HGHN("There is a new highscore"),
    MSG0("Message in Chat is written in Chat"),
    ENDX("informs player that the game has finished");



    public final String keyword;

    private Protocol(String s) {
        this.keyword = s.toUpperCase();
    }

    public String getKeyword() {
        return keyword;
    }




}