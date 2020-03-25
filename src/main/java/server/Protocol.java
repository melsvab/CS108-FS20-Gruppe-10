package server;

public enum Protocol {

    CHAT("chat" ),
    NAME("name"),
    IDK("idk"),
    QUIT("quit"),
    PLL1("playerlist"),
    GML1("gamelist"),
    HSC1(""),
    CRE1(""),
    JON1("join game"),
    STR1("sdfkj"),
    HXXD("fhsj"),
    DXXN("studd"),
    LXXT("and more stuff"),
    RXXT("more stuff"),
    WHP1("stuff"),
    BACK("back after chat"),;

    public final String keyword;

    private Protocol(String s) {
        this.keyword = s.toUpperCase();
    }






}