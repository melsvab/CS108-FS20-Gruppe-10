package server;

public class State {
    /*
     * This class is for the server as well as for clients to know about states
     */

    boolean generalChat;
    boolean whisperChat; //example for later
    boolean broadcast; //example for later
    boolean game; //example for later

    State () {
        generalChat = false;
        whisperChat = false;
        broadcast = false;
        game = false;
    }
    State (boolean generalChat, boolean whisperChat, boolean broadcast, boolean game) {
        this.generalChat = generalChat;
        this.whisperChat = whisperChat;
        this.broadcast = broadcast;
        this.game = game;
    }

}