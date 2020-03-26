package server;

public enum Protocol {

    CHAT("chat" ),
    NAME("desired name from client"),
    NAM1("checked name from server"),
    NAM2("username exists already"),
    QUIT("quit"),
    ENDE("server goes to sleep"),
    PLL1("playerlist"),
    GML1("gamelist"),
    HSC1("highscore"),
    CRE1("create a new lobby"),
    JOIN("join game"),
    STR1("sdfkj"),
    UPPR("up"),
    DOWN("down"),
    LEFT("left"),
    RIGT("right"),
    WHP1("whisperchat"),
    BACK("back after chat"),
    IDK("idk"),
    HELP("help"),
    WELC("Welcome Message"),
    EJON("Client can't join a game"),
    EWHP("Whisperchat:Playername does not exist"),
    RNDS("Number of rounds left"),
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
    ENDX("informs player that the game has finished");



    public final String keyword;

    private Protocol(String s) {
        this.keyword = s.toUpperCase();
    }






}