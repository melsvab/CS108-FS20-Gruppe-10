package server;

public class ClientProfil {

    /**This class is for the server and the client
     * to know about the states and about specific
     * information about a client.
     */

    int client_ID;
    String nickname;

    boolean isInGlobalChat;
    boolean isInWhisperChat; //example for later
    boolean isInBroadcast; //example for later
    boolean isInGame; //example for later
    boolean clientIsOnline;
    boolean changeNameNow;

    /*
    * constructor for serverthread
    */
    public ClientProfil(int client_ID) {
        this.client_ID = client_ID;
        this.clientIsOnline = true;
    }

    /*
    * constructor for client
    */
    public ClientProfil() {
        this.clientIsOnline = true;
        this.changeNameNow = true;
    }  

}