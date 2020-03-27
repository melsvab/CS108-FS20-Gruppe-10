package server;

public class ClientProfil {

    /**This class is for the server and the client
     * to know about the states and about specific
     * information about a client.
     */

    int clientID;
    String nickname;

    boolean isInGlobalChat;
    boolean isInWhisperChat; //example for later
    boolean isInBroadcast; //example for later
    boolean isInGame; //example for later
    boolean clientIsOnline;
    boolean changeNameNow;


    //constructor for serverthread

    public ClientProfil(int clientID) {
        this.clientID = clientID;
        this.clientIsOnline = true;
    }


    //constructor for client

    public ClientProfil() {
        this.clientIsOnline = true;
        this.changeNameNow = true;
    }

    public boolean checkForTwoInt(String original) {
        //an example of an input:
        //KEYW:5.355
        int lenghtInput = original.length();

        if (lenghtInput > 7 || original.contains(":") || original.contains(".")) {

            if (original.indexOf(':') == 4 && original.indexOf('.') > 5) {
                int posDot =  original.indexOf('.');

                try {
                    if(5 - (posDot-1) == 0) {
                        String s = "" + original.charAt(5);
                        int num1 = Integer.parseInt(s);
                    } else {
                        int num1 = Integer.parseInt(original.substring(5, posDot - 1));
                    }

                    int num2 = Integer.parseInt(original.substring(posDot + 1));

                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }

            } else {
                return false;
            }

        } else {
            return false;
        }

    }


    public boolean checkForTwoWords(String original) {
        //an example of an imput:
        //KEYW:word.secondWord
        int lenghtInput = original.length();

        if (lenghtInput > 7 || original.contains(":") || original.contains(".")) {

            if (original.indexOf(':') == 4 && original.indexOf('.') > 5) {
               return true;
            } else {
                return false;
            }

        } else {
            return false;
        }


    }

}